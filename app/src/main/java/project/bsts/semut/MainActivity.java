package project.bsts.semut;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.bsts.semut.adapters.MainMenuAdapter;
import project.bsts.semut.connections.rest.IConnectionResponseHandler;
import project.bsts.semut.connections.rest.RequestRest;
import project.bsts.semut.helper.BroadcastManager;
import project.bsts.semut.helper.PreferenceManager;
import project.bsts.semut.pojo.MainMenuObject;
import project.bsts.semut.pojo.RequestStatus;
import project.bsts.semut.pojo.mapview.MyLocation;
import project.bsts.semut.services.LocationService;
import project.bsts.semut.setup.Constants;
import project.bsts.semut.ui.CommonAlerts;
import project.bsts.semut.ui.MainDrawer;
import project.bsts.semut.utilities.CheckService;
import project.bsts.semut.utilities.CustomDrawable;

public class MainActivity extends AppCompatActivity implements BroadcastManager.UIBroadcastListener {


    @BindView(R.id.switch_online)
    Switch switchOnline;
    @BindView(R.id.loadBar)
    LinearLayout locationErrorLayout;

    private Context context;
    private final String TAG = this.getClass().getSimpleName();
    PreferenceManager mPreferenceManager;
    private RequestRest mRest;
    private ProgressDialog mProgressDialog;
    private final int NOTIFICATION_ID = 666;
    private Intent locService;
    private BroadcastManager broadcastManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        context = this;
        locationErrorLayout.setVisibility(View.GONE);

        locService = new Intent(context, LocationService.class);
        mPreferenceManager = new PreferenceManager(context);
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Memuat");
        broadcastManager = new BroadcastManager(context);
        broadcastManager.subscribeToUi(this);

        mRest = new RequestRest(context, (pResult, type) -> {
            mProgressDialog.dismiss();
            switch (type){
                case Constants.REST_USER_UPDATE_ONLINE_STATUS:
                    RequestStatus requestStatus = new Gson().fromJson(pResult, RequestStatus.class);
                    if(requestStatus.getSuccess()) {
                        Log.i(TAG, requestStatus.getMessage());
                    }
                    else CommonAlerts.commonError(context, requestStatus.getMessage());
                    break;
                case Constants.REST_ERROR:
                    CommonAlerts.commonError(context, Constants.MESSAGE_HTTP_ERROR);
                    break;
            }
        });

        mRest.updateOnlineStatus(mPreferenceManager.getInt(Constants.IS_ONLINE, 0));
        mProgressDialog.show();

        boolean checked = (mPreferenceManager.getInt(Constants.IS_ONLINE, 0) != 0);
        switchOnline.setChecked(checked);

        switchOnline.setOnCheckedChangeListener((compoundButton, b) -> {
            int state = (b) ? 10 : 0;
            if(b) showNotification();
            else cancelNotification();
            mPreferenceManager.save(state, Constants.IS_ONLINE);
            mPreferenceManager.apply();

            if(state == 0){
                if(CheckService.isLocationServiceRunning2(context))
                    stopService(locService);
            }else {
                if(!CheckService.isLocationServiceRunning2(context))
                    startService(locService);
            }

            mRest.updateOnlineStatus(state);
            mProgressDialog.show();
        });

        new MainDrawer(context, toolbar, -1).initDrawer();

    }


    private void showNotification(){
        Intent intent = new Intent(context, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setOngoing(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setTicker("TLITS Taxi")
                .setContentTitle("TLITS Taxi")
                .setContentText("TLITS Taxi sedang berjalan di background")
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, b.build());
    }


    private void cancelNotification(){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(NOTIFICATION_ID);
    }

    @Override
    public void onMessageReceived(String type, String msg) {
        Log.i(TAG, "-------------------------------------");
        Log.i(TAG, "Receive on UI : Type : "+type);
        Log.i(TAG, msg);
        switch (type) {
            case Constants.BROADCAST_MY_LOCATION:
                MyLocation myLocationObject = new Gson().fromJson(msg, MyLocation.class);
                if(myLocationObject.getMyLatitude() == 0 || myLocationObject.getMyLongitude() == 0)
                    locationErrorLayout.setVisibility(View.VISIBLE);
                else {
                    if(locationErrorLayout.getVisibility() == View.VISIBLE) locationErrorLayout.setVisibility(View.GONE);
                }

                break;
        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        broadcastManager.unSubscribeToUi();
    }
}
