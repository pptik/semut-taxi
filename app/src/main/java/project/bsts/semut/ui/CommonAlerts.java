package project.bsts.semut.ui;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import project.bsts.semut.LoginActivity;
import project.bsts.semut.R;
import project.bsts.semut.helper.PreferenceManager;
import project.bsts.semut.services.LocationService;
import project.bsts.semut.setup.Constants;

public class CommonAlerts {

    public static void commonError(Context context, String msg){
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                .setImageDrawable(new IconicsDrawable(context)
                        .icon(GoogleMaterial.Icon.gmd_cloud_off)
                        .sizeDp(100)
                        .color(context.getResources().getColor(R.color.cochineal_red)))
                .setTextTitle("Gagal memuat permintaan")
                .setTextSubTitle("Permintaan Anda tidak dapat diproses")
                .setBody(msg)
                .setAutoHide(false)
                .setPositiveButtonText("OK")
                .setPositiveColor(R.color.primary_dark)
                .setOnPositiveClicked((view, dialog) -> {
                    ((Activity)context).finish();
                })
                .build();


        alert.show();
    }


    public static void sessionDead(Context context){
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                .setImageDrawable(new IconicsDrawable(context)
                        .icon(GoogleMaterial.Icon.gmd_cloud_off)
                        .sizeDp(100)
                        .color(context.getResources().getColor(R.color.cochineal_red)))
                .setTextTitle("Gagal memuat permintaan")
                .setTextSubTitle("Sesi Anda telah berakhir")
                .setBody("Sesi Anda telah berakhir atau akun Anda telah masuk dari perangkat lain. Silahkan melakukan Login Kembali untuk menggunakan aplikasi ini.")
                .setAutoHide(false)
                .setPositiveButtonText("OK")
                .setPositiveColor(R.color.primary_dark)
                .setOnPositiveClicked((view, dialog) -> {
                    PreferenceManager preferenceManager = new PreferenceManager(context);
                    preferenceManager.save(false, Constants.IS_LOGGED_IN);
                    preferenceManager.save(0, Constants.IS_ONLINE);
                    preferenceManager.apply();
                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.cancel(666);
                    Toast.makeText(context, "Signout berhasil", Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context, LoginActivity.class));
                    context.stopService(new Intent(context, LocationService.class));
                    ((Activity)context).finish();
                })
                .build();


        alert.show();
    }


    public static void gspIsDisable(Context context){
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                .setImageDrawable(new IconicsDrawable(context)
                        .icon(GoogleMaterial.Icon.gmd_location_disabled)
                        .sizeDp(100)
                        .color(context.getResources().getColor(R.color.cochineal_red)))
                .setTextTitle("LOKASI ANDA TIDAK AKTIF")
                .setTextSubTitle("Aplikasi membutuhkan lokasi Anda")
                .setBody("Aplikasi ini membutuhkan lokasi Anda yang saat ini sedang non-aktif. Aktifkan terlebih dahulu untuk melanjutkan")
                .setAutoHide(false)
                .setPositiveButtonText("OK")
                .setPositiveColor(R.color.primary_dark)
                .setOnPositiveClicked((view, dialog) -> {
                    Intent callGPSSettingIntent = new Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(callGPSSettingIntent);
                    ((Activity)context).finish();
                }).setNegativeButtonText("Keluar")
                .setOnNegativeClicked(((view, dialog) -> {
                    ((Activity)context).finish();
                }))
                .build();


        alert.show();
    }


    public static void internetIsDisabled(Context context){
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                .setImageDrawable(new IconicsDrawable(context)
                        .icon(GoogleMaterial.Icon.gmd_cloud_off)
                        .sizeDp(100)
                        .color(context.getResources().getColor(R.color.cochineal_red)))
                .setTextTitle("INTERNET ANDA TIDAK AKTIF")
                .setTextSubTitle("Aplikasi membutuhkan koneksi internet Anda")
                .setBody("Aplikasi ini membutuhkan koneksi internet Anda yang saat ini sedang non-aktif. Aktifkan terlebih dahulu untuk melanjutkan")
                .setAutoHide(false)
                .setPositiveButtonText("OK")
                .setPositiveColor(R.color.primary_dark)
                .setOnPositiveClicked((view, dialog) -> {
                    Intent callGPSSettingIntent = new Intent(
                            Settings.ACTION_WIFI_SETTINGS);
                    context.startActivity(callGPSSettingIntent);
                    ((Activity)context).finish();
                }).setNegativeButtonText("Keluar")
                .setOnNegativeClicked(((view, dialog) -> {
                    ((Activity)context).finish();
                }))
                .build();


        alert.show();
    }



    public static void writeExternalDisabled(Context context){
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                .setImageDrawable(new IconicsDrawable(context)
                        .icon(GoogleMaterial.Icon.gmd_location_disabled)
                        .sizeDp(100)
                        .color(context.getResources().getColor(R.color.cochineal_red)))
                .setTextTitle("WRITE EXTERNAL DISABLED")
                //  .setTextSubTitle("Aplikasi membutuhkan lokasi Anda")
                .setBody("Storage permission is required to store map tiles to reduce data usage and for offline usage. Location permission is required to show the user's location on map.")
                .setAutoHide(false)
                .setPositiveColor(R.color.primary_dark)
                .setNegativeButtonText("Keluar")
                .setOnNegativeClicked(((view, dialog) -> {
                    ((Activity)context).finish();
                }))
                .build();


        alert.show();
    }


}
