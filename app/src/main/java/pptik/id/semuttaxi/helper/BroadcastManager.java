package pptik.id.semuttaxi.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import pptik.id.semuttaxi.setup.Constant;


public class BroadcastManager {
    private Context context;

    public interface UIBroadcastListener{
        public void onMessageReceived(String type, String msg);
    }

    BroadcastReceiver uiReceiver;

    public BroadcastManager(Context ctx){
        context = ctx;
    }


    public void sendBroadcastToUI(String broadcastType, String msg){
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_OPANG_BROKER_BROADCAST_TO_UI);
        intent.putExtra(Constant.INTENT_BROADCAST_MSG, msg);
        intent.putExtra(Constant.INTENT_BROADCAST_TYPE, broadcastType);
        context.sendBroadcast(intent);
    }

    public void subscribeToUi(final UIBroadcastListener listener){
        IntentFilter intentFilter = new IntentFilter(Constant.ACTION_OPANG_BROKER_BROADCAST_TO_UI);
        uiReceiver = new BroadcastReceiver() {
            String resType = "";
            String _msg = "";
            @Override
            public void onReceive(Context context, Intent intent) {
                resType = intent.getStringExtra(Constant.INTENT_BROADCAST_TYPE);
                _msg = intent.getStringExtra(Constant.INTENT_BROADCAST_MSG);
                listener.onMessageReceived(resType, _msg);
            }
        };
        context.registerReceiver(uiReceiver, intentFilter);
    }


    public void unSubscribeToUi(){
        context.unregisterReceiver(uiReceiver);
        Log.i(this.getClass().getSimpleName(), "Unsubscribe from UI");
    }


}