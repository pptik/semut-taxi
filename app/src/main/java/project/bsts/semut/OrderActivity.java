package project.bsts.semut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.bsts.semut.connections.rest.RequestRest;
import project.bsts.semut.helper.PreferenceManager;
import project.bsts.semut.pojo.Order;
import project.bsts.semut.pojo.RequestStatus;
import project.bsts.semut.setup.Constants;
import project.bsts.semut.ui.CommonAlerts;


public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.lay_end_trip)
    LinearLayout layoutEndTrip;
    @BindView(R.id.button_sms)
    Button buttonSms;
    @BindView(R.id.button_telp)
    Button buttonTelp;
    @BindView(R.id.text_nama)
    TextView textPemesan;
    @BindView(R.id.text_estimate)
    TextView textEstimate;
    @BindView(R.id.button_to_dest)
    Button buttonToDest;
    @BindView(R.id.text_dest)
    Button textDest;
    @BindView(R.id.button_to_source)
    Button buttonToSource;
    @BindView(R.id.text_source)
    TextView textSource;

    private String TAG = this.getClass().getSimpleName();
    private Context context;
    private PreferenceManager preferenceManager;
    private GSONSharedPreferences gPreferences;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order");
        toolbar.setSubtitle("Anda sedang dalam trip");
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        context = this;
        preferenceManager = new PreferenceManager(context);
        if(preferenceManager.getInt(Constants.IS_ONLINE, 0) != 11){
            ProgressDialog mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Memuat");
            RequestRest mRest = new RequestRest(context, (pResult, type) -> {
                mProgressDialog.dismiss();
                switch (type){
                    case Constants.REST_USER_UPDATE_ONLINE_STATUS:
                        RequestStatus requestStatus = new Gson().fromJson(pResult, RequestStatus.class);
                        if(requestStatus.getSuccess()) {
                            Log.i(TAG, requestStatus.getMessage());
                            preferenceManager.save(11, Constants.IS_ONLINE);
                            preferenceManager.apply();
                        }
                        else CommonAlerts.commonError(context, requestStatus.getMessage());
                        break;
                    case Constants.REST_ERROR:
                        CommonAlerts.commonError(context, Constants.MESSAGE_HTTP_ERROR);
                        break;
                }
            });
            mRest.updateOnlineStatus(11);
            mProgressDialog.show();
        }
        gPreferences = new GSONSharedPreferences(context);

        try {
            order = (Order) gPreferences.getObject(new Order());
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        textSource.setText(order.getSourceAddress());
        textDest.setText(order.getDestinationAddress());
        textEstimate.setText(""+order.getDistance()+"KM");
        textPemesan.setText(order.getRequestBy().getName());

        buttonToSource.setOnClickListener(view -> navigateTo(order.getSourceLat(), order.getSourceLon()));
        buttonToDest.setOnClickListener(view -> navigateTo(order.getDestinationLat(), order.getDestinationLon()));
        buttonSms.setOnClickListener(view -> sendSms(order.getRequestBy().getPhoneNumber(), order.getRequestBy().getName()));
        buttonTelp.setOnClickListener(view -> doCall(order.getRequestBy().getPhoneNumber()));

        layoutEndTrip.setOnClickListener(view -> finishTrip());
    }


    private void sendSms(String number, String nama){
        Uri uri = Uri.parse("smsto:"+number);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "Halo "+nama+", Saya akan sampai ketempat tujuan dalam beberapa saat.");
        startActivity(it);
    }

    private void doCall(String number){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }


    private void navigateTo(Double lat, double lon){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lon);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }else Toast.makeText(context, "Perangkat Anda tidak terinstall Google Map", Toast.LENGTH_LONG).show();
    }


    private void finishTrip(){
        preferenceManager.save(10, Constants.IS_ONLINE);
        preferenceManager.apply();
        finish();
    }

}
