package com.example.sergio.caminando.endpoints.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.sergio.caminando.BuildConfig;
import com.example.sergio.caminando.R;
import com.example.sergio.caminando.provider.RouteContract;
import com.example.sergio.myapplication.backend.domain.conference.model.Conference;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.util.DateTime;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

/**
 * A general utility class. All methods here are static and no state is maintained.
 */
public class Utils {

    private static final String TAG = makeLogTag(Utils.class);


    /**
     * Saves a string value under the provided key in the preference manager. If <code>value</code>
     * is <code>null</code>, then the provided key will be removed from the preferences.
     *
     * @param context context of running application
     * @param key key for write preference
     * @param value preference value
     */
    public static void saveStringToPreference(Context context, String key, String value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (null == value) {
            // we want to remove
            pref.edit().remove(key).apply();
        } else {
            pref.edit().putString(key, value).apply();
        }
    }

    /**
     * Retrieves a String value from preference manager. If no such key exists, it will return
     * <code>null</code>.
     *
     * @param context context of running application
     * @param key  key for search preference
     * @return value preference search by key
     */
    public static String getStringFromPreference(Context context, String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, null);
    }

    /**
     * Returns a detailed description of a conference.
     *
     * @param context context of running application
     * @param conference data conference
     * @return data conference in String
     */
    public static String getConferenceCard(Context context, Conference conference) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(conference.getDescription())) {
            sb.append(conference.getDescription() + "\n");
        }

        if (null != conference.getStartDate()) {
            sb.append("\n" + getConferenceDate(context, conference));
        }

        if (!TextUtils.isEmpty(conference.getCity())) {
            sb.append("\n" + conference.getCity());
        }

        if (null != conference.getMaxAttendees()) {
            sb.append("\n" +
                    context.getString(R.string.seats_max, conference.getMaxAttendees().intValue()));
        }

        if (null != conference.getSeatsAvailable()) {
            sb.append("\n" + context.getString(R.string.seats_available,
                    conference.getSeatsAvailable().intValue()));
        }
        return sb.toString();
    }

    /**
     * Returns the date of a conference.
     *
     * @param context context of running application
     * @param conference data conference
     * @return date in String
     */
    public static String getConferenceDate(Context context, Conference conference) {
        StringBuffer sb = new StringBuffer();
        if (null != conference.getStartDate() && null != conference.getEndDate()) {
            sb.append(getFormattedDateRange(context, conference.getStartDate(),
                    conference.getEndDate()));
        } else if (null != conference.getStartDate()) {
            sb.append(getFormattedDate(context, conference.getStartDate()));
        }
        return sb.toString();
    }

    /**
     * Returns a user-friendly localized date.
     *
     * @param context context of running application
     * @param dateTime date in string
     * @return
     */
    public static String getFormattedDate(Context context, DateTime dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateTime.getValue());
        return DateUtils
                .formatDateTime(context, cal.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH);
    }

    /**
     * Returns a user-friendly localized data range.
     *
     * @param context context of application running
     * @param dateTimeStart start Time
     * @param dateTimeEnd end time
     * @return date String
     */
    public static String getFormattedDateRange(Context context, DateTime dateTimeStart,
                                               DateTime dateTimeEnd) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(dateTimeStart.getValue());

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(dateTimeEnd.getValue());
        return DateUtils
                .formatDateRange(context, cal1.getTimeInMillis(), cal2.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH);
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     */
    public static boolean checkGooglePlayServicesAvailable(Activity activity) {
        final int connectionStatusCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(activity);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(activity, connectionStatusCode);
            return false;
        }
        return true;
    }

    /**
     * Called if the device does not have Google Play Services installed.
     */
    public static void showGooglePlayServicesAvailabilityErrorDialog(final Activity activity,
                                                                     final int connectionStatusCode) {
        final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode, activity, REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }

    /**
     * Returns the {@code Array} of Google Accounts, if any. Return value can be an empty array
     * (if no such account exists) but never <code>null</code>.
     *
     * @param context context of application running
     * @return accounts in AccountManager
     */
    public static Account[] getGoogleAccounts(Context context) {
        AccountManager am = AccountManager.get(context);
        return am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
    }

    /**
     * Displays error dialog when a network error occurs. Exits application when user confirms
     * dialog.
     *
     * @param context context of application running
     */
    public static void displayNetworkErrorMessage(Context context) {
        new AlertDialog.Builder(
                context).setTitle(R.string.api_error_title)
                .setMessage(R.string.api_error_message)
                .setCancelable(false)
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                System.exit(0);
                            }
                        }
                ).create().show();
    }

    public static int addRoutesToSQLite (Context context, List<DecoratedConference> decoratedConferences){

        // Insert the new weather information into the database
        Vector<ContentValues> cVVector = new Vector<>(decoratedConferences.size());
        ContentValues[] cvArray = new ContentValues[cVVector.size()];

        Iterator<DecoratedConference> decoratedConferenceIterator = decoratedConferences.iterator();

        while (decoratedConferenceIterator.hasNext()){
            Conference conference = decoratedConferenceIterator.next().getConference();
            // Create a new map of values, where column names are the keys
            ContentValues routeValues = new ContentValues();
            routeValues.put(RouteContract.RouteEntry._ID, conference.getId());
            routeValues.put(RouteContract.RouteEntry.COLUMN_NAME_ROUTE, conference.getName());
            routeValues.put(RouteContract.RouteEntry.COLUMN_DESCRIPTION,conference.getDescription() );
            routeValues.put(RouteContract.RouteEntry.COLUMN_TOPICS, conference.getTopics().toString());
            routeValues.put(RouteContract.RouteEntry.COLUMN_CITY_NAME_INIT, conference.getCity());
            routeValues.put(RouteContract.RouteEntry.COLUMN_START_DATE, conference.getStartDate().getValue());
            routeValues.put(RouteContract.RouteEntry.COLUMN_MAX_ATTENDEES, conference.getMaxAttendees());
            routeValues.put(RouteContract.RouteEntry.COLUMN_SEATS_AVAILABLE, conference.getSeatsAvailable());
            routeValues.put(RouteContract.RouteEntry.COLUMN_URL_ROUTE_COVER, conference.getPhotoUrlRouteCover());
            routeValues.put(RouteContract.RouteEntry.COLUMN_WEBSAFE_KEY, conference.getWebsafeKey());
            routeValues.put(RouteContract.RouteEntry.COLUMN_ORGANIZER_DISPLAY_NAME, conference.getOrganizerDisplayName());

            cVVector.add(routeValues);
        }

        /** Insert weather data into database */
        return insertWeatherIntoDatabase(context, cVVector);
    }

    public static int insertWeatherIntoDatabase(Context context, Vector<ContentValues> CVVector) {
        int rowsInserted = 0;
        if (CVVector.size() > 0) {
            ContentValues[] contentValuesArray = new ContentValues[CVVector.size()];
            CVVector.toArray(contentValuesArray);

            rowsInserted = context.getContentResolver().bulkInsert(RouteContract.RouteEntry.CONTENT_URI, contentValuesArray);

            // Use a DEBUG variable to gate whether or not you do this, so you can easily
            // turn it on and off, and so that it's easy to see what you can rip out if
            // you ever want to remove it.
            if (BuildConfig.DEBUG) {
                Cursor weatherCursor = context.getContentResolver().query(
                        RouteContract.RouteEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );

                if (weatherCursor.moveToFirst()) {
                    ContentValues resultValues = new ContentValues();
                    DatabaseUtils.cursorRowToContentValues(weatherCursor, resultValues);
                    Log.v(TAG, "Query succeeded! **********");
                    for (String key : resultValues.keySet()) {
                        Log.v(TAG, key + ": " + resultValues.getAsString(key));
                    }
                } else {
                    Log.v(TAG, "Query failed! :( **********");
                }
            }
        }
        return rowsInserted;
    }
}