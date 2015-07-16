package com.example.sergio.caminando.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.endpoints.ConferenceLoader;
import com.example.sergio.caminando.endpoints.utils.DecoratedConference;
import com.example.sergio.caminando.endpoints.utils.Utils;
import com.example.sergio.caminando.provider.RouteContract;
import com.example.sergio.caminando.ui.BrowseSessionsActivity;

import java.util.List;

public class CaminandoSyncAdapter extends AbstractThreadedSyncAdapter {
    public final String LOG_TAG = CaminandoSyncAdapter.class.getSimpleName();
    // Interval at which to sync with the weather, in seconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 5;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    private static final int ROUTE_NOTIFICATION_ID = 3999;

    private static final String[] NOTIFY_WEATHER_PROJECTION = new String[] {
            RouteContract.RouteEntry._ID,
            RouteContract.RouteEntry.COLUMN_NAME_ROUTE,
            RouteContract.RouteEntry.COLUMN_TOPICS,
            RouteContract.RouteEntry.COLUMN_CITY_NAME_INIT,
            RouteContract.RouteEntry.COLUMN_START_DATE,
            RouteContract.RouteEntry.COLUMN_ORGANIZER_DISPLAY_NAME,
    };

    // these indices must match the projection
    private static final int INDEX_ID = 0;
    private static final int INDEX_COLUMN_NAME_ROUTE = 1;
    private static final int INDEX_TOPICS = 2;
    private static final int INDEX_CITY_NAME_INIT = 3;
    private static final int INDEX_START_DATE = 4;
    private static final int INDEX_ORGANIZER_DISPLAY_NAME = 5;

    public CaminandoSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        
        Log.d(LOG_TAG, "Starting sync");

        // TODO: FUTURO CAMBIO DE las conferencias que loader con GCM. mas eficiente. ahora se cargan todas desde ahora
        ConferenceLoader conferenceLoader = new ConferenceLoader(getContext());
        conferenceLoader.setFiltersQueryForm("STARTDATE", "GT",String.valueOf(System.currentTimeMillis()));
        List<DecoratedConference> decoratedConferences = conferenceLoader.loadInBackground();

        if (decoratedConferences.size() > 0){
            //Create route values today an insert in database
            int rowsInserted = Utils.addRoutesToSQLite(getContext(), decoratedConferences);
            Log.d(LOG_TAG, "Sync Complete. " + rowsInserted + " new Routes Inserted");
            if(rowsInserted > 0){
                notifyRoute();
            }
        }

        return;
    }

    // TODO: Notificaciones de rutas mal hecho. Hay que hacerlo de manera de que notifique solo las nuevas y con informacion
    // notifica que hay rutas nuevas
    private void notifyRoute() {
        Context context = getContext();
        //checking the last update and notify if it' the first of the day
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key);
        boolean displayNotifications = prefs.getBoolean(displayNotificationsKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        if ( displayNotifications ) {


                // TODO: Change iconId to future implementation. PHOTO ROUTE
                //int iconId = Utility.getIconResourceForRouteCondition(routeId);
                int iconId = R.mipmap.ic_launcher;
                Resources resources = context.getResources();

                // TODO: Change also large icon
                Bitmap largeIcon = BitmapFactory.decodeResource(resources,
                        R.drawable.io2014_logo);

                String title = context.getString(R.string.app_name);

                // Define the text of the forecast.
                String contentText = context.getString(R.string.notification_routes);

                // NotificationCompatBuilder is a very convenient way to build backward-compatible
                // notifications.  Just throw in some data.
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getContext())
                                .setColor(resources.getColor(R.color.theme_primary))
                                .setSmallIcon(iconId)
                                .setLargeIcon(largeIcon)
                                .setContentTitle(title)
                                .setContentText(contentText);

                // Make something interesting happen when the user clicks on the notification.
                // In this case, opening the app is sufficient.
                Intent resultIntent = new Intent(context, BrowseSessionsActivity.class);

                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager =
                        (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                // WEATHER_NOTIFICATION_ID allows you to update the notification later on.
                mNotificationManager.notify(ROUTE_NOTIFICATION_ID, mBuilder.build());

        }
    }


    //Metodos para configuracion de la Sync
    public static void initializeSyncAdapter(Context context) {
        Log.d("QuemedejesSyncAdapter", "initializeSyncAdapter");
        getSyncAccount(context);
    }
    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }
    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        CaminandoSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }
    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }
    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {

        Log.d("CaminadoSyncAdapter", "syncImmediately");

        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

}