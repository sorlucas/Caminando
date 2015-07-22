package com.example.sergio.caminando.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.sergio.caminando.R;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by sergio on 22/07/15.
 */
public class MapsUtils {

    private static boolean checkReady(Context context, GoogleMap map) {
        if (map == null) {
            Toast.makeText(context, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static GoogleMap updateAllTypesOfViews (Context context, GoogleMap map){
        map = updateTraffic(context,map);
        map = updateMyLocation(context, map);
        return map;
    }
    // implementations
    public static GoogleMap updateTraffic(Context context, GoogleMap map) {
        if (checkReady(context,map)) {
            map.setTrafficEnabled(true);
        }
        return map;
    }
    public static GoogleMap updateMyLocation(Context context, GoogleMap map) {
        if (checkReady(context,map)) {
            map.setMyLocationEnabled(true);
        }
        return map;
    }

    public static void setLayerMap (Context context, GoogleMap mMap, String layerName) {
        if (layerName.equals(context.getString(R.string.normal))) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if (layerName.equals(context.getString(R.string.hybrid))) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else if (layerName.equals(context.getString(R.string.satellite))) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else if (layerName.equals(context.getString(R.string.terrain))) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } else if (layerName.equals(context.getString(R.string.none_map))) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        } else {
            Log.i("LDA", "Error setting layer with name " + layerName);
        }
    }

}
