package pptik.id.semuttaxi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import pptik.id.semuttaxi.connections.httprequest.ConnectionHandler;
import pptik.id.semuttaxi.connections.httprequest.RequestRest;
import pptik.id.semuttaxi.models.Authentication;
import pptik.id.semuttaxi.setup.Constant;

public class LoginActivity extends AppCompatActivity implements ConnectionHandler.IConnectionResponseHandler {

    private RequestRest mRequestRest;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private RequestParams mParams;
    private String TAG = this.getClass().getSimpleName();

    @BindView(R.id.edit_credential)
    EditText mEditCredential;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.button_login)
    Button mButtonLogin;

    String mRegex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    Pattern pattern = Pattern.compile(mRegex);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mContext = this;
        mRequestRest = new RequestRest(mContext, this);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Memuat ...");
        mButtonLogin.setOnClickListener(view ->
            login(
                    mEditPassword.getText().toString(),
                    isPhoneNumber(mEditCredential.getText().toString()) ? 1 : 0,
                    mEditCredential.getText().toString()
            )
        );


    }


    private boolean isPhoneNumber(String number){
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }


    private void login(String pass, int loginType, String uniqueParam){
        if(pass.equals("") || uniqueParam.equals("")){
            Snackbar.make(mButtonLogin, "Kolom tidak boleh kosong", Snackbar.LENGTH_LONG).show();
        }else {
            mParams = new RequestParams();
            if (loginType == 0) mParams.put("Email", uniqueParam);
            else mParams.put("Phonenumber", uniqueParam);
            mParams.put("Password", pass);
            mProgressDialog.show();
            mRequestRest.post(Constant.REST_USER_LOGIN, mParams);
        }
    }

    private void populateUserData(Authentication authentication){
        GSONSharedPreferences gsonSharedPreferences = new GSONSharedPreferences(mContext);
        gsonSharedPreferences.saveObject(authentication);
        //toDashBoard();
    }

    @Override
    public void onSuccessRequest(String pResult, String type) {
        mProgressDialog.dismiss();
        Log.i(TAG, pResult);
        switch (type){
            case Constant.REST_USER_LOGIN:
                Log.i(TAG, pResult);
                Authentication authentication = new Gson().fromJson(pResult, Authentication.class);
                if(authentication.getSuccess()) populateUserData(authentication);
                else Log.e(TAG, authentication.getMessage());
                break;
            case Constant.REST_ERROR:

                break;
        }
    }


}
