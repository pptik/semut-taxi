package pptik.id.semuttaxi.utils;


import android.content.Context;

import pptik.id.semuttaxi.helper.PreferenceManager;
import pptik.id.semuttaxi.setup.Constant;

public class MapItem {

    public static String get(Context context){
        PreferenceManager preferenceManager = new PreferenceManager(context);
        return String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_USER, 1))+
                String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_CCTV,1))+
                String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_POLICE_POST,1))+
                String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_ACCIDENT_POST, 1))+
                String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_TRAFFIC_POST, 1))+
                String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_DISASTER_POST, 1))+
                String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_CLOSURE_POST, 1))+
                String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_OTHER_POST, 1))+
                String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_COMMUTER_TRAIN, 1))+
                String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_ANGKOT_LOCATION,1))+
                String.valueOf(preferenceManager.getInt(Constant.MAP_FILTER_TRANSPOST,1));
    }

}