package com.example.sergio.caminando;

import android.content.ContentValues;

import com.example.sergio.caminando.provider.RouteContract;

import java.util.Random;

/**
 * Created by sergio on 5/07/15.
 */
public class RouteUtilities {

    public static ContentValues createRouteValuesToday(long date) {

        // Create a new map of values, where column names are the keys
        ContentValues routeValues = new ContentValues();

        Random rand = new Random();
        routeValues.put(RouteContract.RouteEntry._ID, rand.nextInt(10000) );
        routeValues.put(RouteContract.RouteEntry.COLUMN_NAME_ROUTE, "Nombre Ruta");
        routeValues.put(RouteContract.RouteEntry.COLUMN_DESCRIPTION,"Descripcion " );
        routeValues.put(RouteContract.RouteEntry.COLUMN_TOPICS, "list topics in String");
        routeValues.put(RouteContract.RouteEntry.COLUMN_CITY_NAME_INIT, "City init");
        routeValues.put(RouteContract.RouteEntry.COLUMN_START_DATE, date);
        routeValues.put(RouteContract.RouteEntry.COLUMN_MAX_ATTENDEES, 2);
        routeValues.put(RouteContract.RouteEntry.COLUMN_SEATS_AVAILABLE, 1);
        routeValues.put(RouteContract.RouteEntry.COLUMN_URL_ROUTE_COVER, "ciudad rodrigo");
        routeValues.put(RouteContract.RouteEntry.COLUMN_WEBSAFE_KEY, "ahNzfnJ1dGFzLXNlbmRlcmlzdGFzcjQLEgdQcm9maWxlIhUxMTE0MDgxNDE4ODkzMDY3NzQ4NDUMCxIKQ29uZmVyZW5jZRiDiCcM");
        routeValues.put(RouteContract.RouteEntry.COLUMN_ORGANIZER_DISPLAY_NAME, 64.7488);
        return routeValues;

    }

}
