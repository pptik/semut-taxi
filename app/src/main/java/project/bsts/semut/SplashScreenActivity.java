package project.bsts.semut;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;

import project.bsts.semut.connections.rest.IConnectionResponseHandler;
import project.bsts.semut.connections.rest.RequestRest;
import project.bsts.semut.helper.PermissionHelper;
import project.bsts.semut.helper.PreferenceManager;
import project.bsts.semut.pojo.RequestStatus;
import project.bsts.semut.setup.Constants;
import project.bsts.semut.ui.CommonAlerts;
import project.bsts.semut.utilities.CheckService;


public class SplashScreenActivity extends AppCompatActivity {


    private static final int SPLASH_TIME = 2 * 1000;// 3 * 1000
    private Context context;
    PreferenceManager preferenceManager;
    PermissionHelper permissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        context = this;
        preferenceManager = new PreferenceManager(context);

        new Handler().postDelayed(() -> {

            if(CheckService.isInternetAvailable(context)) {
                if (CheckService.isGpsEnabled(this)) {
                    permissionHelper = new PermissionHelper(this);
                    if (permissionHelper.requestFineLocation() && permissionHelper.requestWriteExternal() ) {
                        RequestRest rest = new RequestRest(context, (pResult, type) -> {
                            if(type.equals(Constants.REST_CHECK_SESSION)){
                                Log.i("Santai", pResult);
                                RequestStatus requestStatus = new Gson().fromJson(pResult, RequestStatus.class);
                                if(requestStatus.getSuccess()){
                                    if(preferenceManager.getInt(Constants.IS_ONLINE, 0) == 11){
                                        Intent intent = new Intent(this, OrderActivity.class);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    }else {
                                        Intent intent = new Intent(this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    }
                                }else CommonAlerts.sessionDead(context);
                            }
                        });
                        rest.checkSession();
                    }
                } else CommonAlerts.gspIsDisable(this);
            }else CommonAlerts.internetIsDisabled(this);


        }, SPLASH_TIME);

        new Handler().postDelayed(() -> {
        }, SPLASH_TIME);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionHelper.REQUEST_ACCESS_FINE_LOCATION:
                permissionHelper.requestWriteExternal();
                /*if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(this.getClass().getSimpleName(), "Location granted");
                    if(preferenceManager.getInt(Constants.IS_ONLINE, 0) == 11){
                        Intent intent = new Intent(this, OnTripActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }else {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                } else {
                    Log.i(this.getClass().getSimpleName(), "Location Rejected");
                    Toast.makeText(context, "Maaf, Anda harus memberi izin lokasi kepada aplikasi untuk melanjutkan", Toast.LENGTH_LONG).show();
                } */

                break;
            case PermissionHelper.REQUEST_WRITE_EXTERNAL:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(this.getClass().getSimpleName(), "Location granted");
                    RequestRest rest = new RequestRest(context, (pResult, type) -> {
                        if(type.equals(Constants.REST_CHECK_SESSION)){
                            Log.i("Santai", pResult);
                            RequestStatus requestStatus = new Gson().fromJson(pResult, RequestStatus.class);
                            if(requestStatus.getSuccess()){
                                if(preferenceManager.getInt(Constants.IS_ONLINE, 0) == 11){
                                    Intent intent = new Intent(this, OrderActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }else {
                                    Intent intent = new Intent(this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                            }else CommonAlerts.sessionDead(context);
                        }
                    });
                    rest.checkSession();
                } else {
                    Log.i(this.getClass().getSimpleName(), "Location Rejected");
                    Toast.makeText(context, "Maaf, Anda harus memberi izin lokasi kepada aplikasi untuk melanjutkan", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }



    @Override
    public void onBackPressed() {
        // this.finish();
        super.onBackPressed();
    }
}