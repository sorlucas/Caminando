package com.example.sergio.caminando.endpoints.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateUtils;

import com.example.sergio.myapplication.backend.domain.conference.model.Conference;
import com.example.sergio.caminando.R;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.util.DateTime;

import java.util.Calendar;

/**
 * A general utility class. All methods here are static and no state is maintained.
 */
public class Utils {

    private static final String LOG_TAG = "Utils";


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
}