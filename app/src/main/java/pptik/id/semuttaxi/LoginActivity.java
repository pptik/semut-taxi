package pptik.id.semuttaxi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.RequestParams;

import pptik.id.semuttaxi.connections.httprequest.ConnectionHandler;
import pptik.id.semuttaxi.connections.httprequest.RequestRest;
import pptik.id.semuttaxi.setup.Constant;

public class LoginActivity extends AppCompatActivity implements ConnectionHandler.IConnectionResponseHandler {

    private RequestRest mRequestRest;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private RequestParams mParams;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;
        mRequestRest = new RequestRest(mContext, this);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Memuat ...");
    }


    private void login(String pass, int loginType, String uniqueParam){
        mParams = new RequestParams();
        if(loginType == 0) mParams.put("Email", uniqueParam);
        else mParams.put("Phonenumber", uniqueParam);
        mParams.put("Password", pass);
        mProgressDialog.show();
        mRequestRest.post(Constant.REST_USER_LOGIN, mParams);
    }


    @Override
    public void onSuccessRequest(String pResult, String type) {
        mProgressDialog.dismiss();
        Log.i(TAG, pResult);
        switch (type){
            case Constant.REST_USER_LOGIN:
                break;
            case Constant.REST_ERROR:
                break;
        }
    }
}
