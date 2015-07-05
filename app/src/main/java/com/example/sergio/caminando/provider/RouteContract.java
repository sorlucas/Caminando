package com.example.sergio.caminando.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names for the Routes database.
 */
public class RouteContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.sergio.caminando";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_ROUTE = "route";

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }


    /* Inner class that defines the table contents of the route table */
    public static final class RouteEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ROUTE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROUTE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROUTE;

        public static final String TABLE_NAME = "route";

        public static final String COLUMN_NAME_ROUTE = "name_route";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TOPICS = "topics";
        public static final String COLUMN_CITY_NAME_INIT = "city_name_init";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_MAX_ATTENDEES = "max_attendes";
        public static final String COLUMN_SEATS_AVAILABLE = "seats_available";
        public static final String COLUMN_URL_ROUTE_COVER = "url_route_cover";
        public static final String COLUMN_WEBSAFE_KEY = "websafe_key";
        public static final String COLUMN_ORGANIZER_DISPLAY_NAME = "organizer_display_name";


        public static Uri buildRouteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getRouteIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static long getStartDateFromUri(Uri uri) {
            String dateString = uri.getQueryParameter(COLUMN_START_DATE);
            if (null != dateString && dateString.length() > 0)
                return Long.parseLong(dateString);
            else
                return 0;
        }
    }
}