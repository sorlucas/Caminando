package com.example.sergio.caminando.util;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.sergio.caminando.R;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

/**
 * Created by sergio on 22/07/15.
 */
public class MapsUtils {

    // These settings are the same as the settings for the map. They will in fact give you updates
    // at the maximal rates currently possible.
    // TODO: Adjust to Speed Rate position
    public static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(10)         // 10 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    private static boolean checkReady(Context context, GoogleMap map) {
        if (map == null) {
            Toast.makeText(context, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static GoogleMap updateAllTypesOfViews (Context context, GoogleMap map){
        if (checkReady(context,map)) {
            map.setTrafficEnabled(true);
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
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

    public static String getDistanceRouteNoReal(LatLng my_latlong,LatLng frnd_latlong)    {

        Location l1 = new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2 = new Location("Two");
        l2.setLatitude(frnd_latlong.latitude);
        l2.setLongitude(frnd_latlong.longitude);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        float distance = l1.distanceTo(l2);
        String dist = df.format(distance) + " m";

        if(distance > 1000.0f)  {
            distance = distance/1000.0f;
            dist = df.format(distance) + " Km";
        }

        return dist;
    }

}
