package project.bsts.semut.helper;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class PermissionHelper {

    private Context context;
    public static final int REQUEST_ACCESS_FINE_LOCATION = 101;
    public static final int REQUEST_WRITE_EXTERNAL = 102;

    public PermissionHelper(Context context){
        this.context = context;
    }

    public boolean requestFineLocation(){
        boolean state = false;
        int accessFineLocationPermission = ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (accessFineLocationPermission == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions((Activity)context,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        }
        //req
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("Permission Info", "Access Fine Location Denied"); // TO REQUEST
            state = false;
        }else {
            Log.i("Permission Info", "Access Fine Location Approve");
            state = true;
        }
        return state;
    }


    public boolean requestWriteExternal(){
        boolean state = false;
        int accessFineLocationPermission = ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (accessFineLocationPermission == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions((Activity)context,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL);
        }
        //req
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("Permission Info", "Access Write external storage Denied"); // TO REQUEST
            state = false;
        }else {
            Log.i("Permission Info", "Access Write external storage");
            state = true;
        }
        return state;
    }
}