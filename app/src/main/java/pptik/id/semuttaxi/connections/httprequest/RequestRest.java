package pptik.id.semuttaxi.connections.httprequest;


import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import pptik.id.semuttaxi.setup.Constant;

public class RequestRest extends ConnectionHandler {

    protected static AsyncHttpClient mClient = new AsyncHttpClient();
    private String TAG = this.getClass().getSimpleName();



    @Override
    public String getAbsoluteUrl(String relativeUrl) {
        return Constant.REST_BASE_URL + relativeUrl;
    }


    public RequestRest(Context context, IConnectionResponseHandler handler) {
        this.mContext = context;
        this.responseHandler = handler;
    }


    public void post(final String API, RequestParams params){
        post(API, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "Sending request : "+API);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(TAG, "Sending request : "+API);
                responseHandler.onSuccessRequest(response.toString(), API);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, e, errorResponse);
                Log.i(TAG, "Sending request : "+API);
                responseHandler.onSuccessRequest(String.valueOf(statusCode), Constant.REST_ERROR);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i(TAG, "Sending request : "+API);
            }

        }, mClient);
    }


}
