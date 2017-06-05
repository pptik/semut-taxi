package project.bsts.semut;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridView;
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
import project.bsts.semut.helper.PreferenceManager;
import project.bsts.semut.pojo.MainMenuObject;
import project.bsts.semut.pojo.RequestStatus;
import project.bsts.semut.setup.Constants;
import project.bsts.semut.ui.CommonAlerts;
import project.bsts.semut.ui.MainDrawer;
import project.bsts.semut.utilities.CustomDrawable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.gridview)
    GridView gridView;
    @BindView(R.id.switch_online)
    Switch switchOnline;

    private Context context;
    private final String TAG = this.getClass().getSimpleName();
    PreferenceManager mPreferenceManager;
    private RequestRest mRest;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        mPreferenceManager = new PreferenceManager(context);
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Memuat");

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

        switchOnline.setChecked(mPreferenceManager.getBoolean(Constants.IS_ONLINE));
        switchOnline.setOnCheckedChangeListener((compoundButton, b) -> {
            int state = (b) ? 10 : 0;
            mPreferenceManager.save(state, Constants.IS_ONLINE);
            mRest.updateOnlineStatus(state);
            mProgressDialog.show();
        });

        new MainDrawer(context, toolbar, -1).initDrawer();


        ArrayList<MainMenuObject> arrObj = new ArrayList<MainMenuObject>();

        MainMenuObject object1 = new MainMenuObject();
        object1.setTitle("Social \nReport");
        object1.setClassIntent(SocialReportActivity.class);
        object1.setIcon(CustomDrawable.create(context, GoogleMaterial.Icon.gmd_question_answer, 34, R.color.primary_dark));
        arrObj.add(object1);

        MainMenuObject object2 = new MainMenuObject();
        object2.setTitle("Street \nCamera");
        object2.setClassIntent(CityCctvActivity.class);
        object2.setIcon(CustomDrawable.create(context, GoogleMaterial.Icon.gmd_videocam, 34, R.color.primary_dark));
        arrObj.add(object2);

        MainMenuObject object3 = new MainMenuObject();
        object3.setTitle("Public \nTransport");
        object3.setClassIntent(TransportationListActivity.class);
        object3.setIcon(CustomDrawable.create(context, GoogleMaterial.Icon.gmd_directions_bus, 34, R.color.primary_dark));
        arrObj.add(object3);

        MainMenuObject object4 = new MainMenuObject();
        object4.setTitle("Tombol \nDarurat");
        object4.setClassIntent(EmergencyActivity.class);
        object4.setIcon(CustomDrawable.create(context, GoogleMaterial.Icon.gmd_pan_tool, 34, R.color.primary_dark));
        arrObj.add(object4);

    /*    MainMenuObject object4 = new MainMenuObject();
        object4.setTitle("Profile\n");
        object4.setClassIntent(SocialReportActivity.class);
        object4.setIcon(CustomDrawable.create(context, GoogleMaterial.Icon.gmd_account_circle, 34, R.color.primary_dark));
        arrObj.add(object4);

        MainMenuObject object5 = new MainMenuObject();
        object5.setTitle("Friends\n");
        object5.setClassIntent(SocialReportActivity.class);
        object5.setIcon(CustomDrawable.create(context, GoogleMaterial.Icon.gmd_supervisor_account, 34, R.color.primary_dark));
        arrObj.add(object5);

        MainMenuObject object6 = new MainMenuObject();
        object6.setTitle("Chat\n");
        object6.setClassIntent(SocialReportActivity.class);
        object6.setIcon(CustomDrawable.create(context, GoogleMaterial.Icon.gmd_chat, 34, R.color.primary_dark));
        arrObj.add(object6);

        MainMenuObject object7 = new MainMenuObject();
        object7.setTitle("Personal \nPayment");
        object7.setClassIntent(SocialReportActivity.class);
        object7.setIcon(CustomDrawable.create(context, GoogleMaterial.Icon.gmd_account_balance_wallet, 34, R.color.primary_dark));
        arrObj.add(object7);

        MainMenuObject object8 = new MainMenuObject();
        object8.setTitle("Settings\n");
        object8.setClassIntent(SocialReportActivity.class);
        object8.setIcon(CustomDrawable.create(context, GoogleMaterial.Icon.gmd_settings, 34, R.color.primary_dark));
        arrObj.add(object8); */

        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(context,arrObj);
        gridView.setAdapter(mainMenuAdapter);

    }

}
