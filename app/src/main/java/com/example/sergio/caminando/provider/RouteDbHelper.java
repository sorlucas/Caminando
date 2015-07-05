package com.example.sergio.caminando.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sergio.caminando.provider.RouteContract.RouteEntry;

/**
 * Manages a local database for route data.
 */
public class RouteDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 101;

    public static final String DATABASE_NAME = "route.db";

    public RouteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_ROUTE_TABLE = "CREATE TABLE IF NOT EXISTS " + RouteEntry.TABLE_NAME + " (" +

                RouteEntry._ID + " INTEGER PRIMARY KEY NOT NULL," +
                RouteEntry.COLUMN_NAME_ROUTE + " TEXT NOT NULL, "  +
                RouteEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                RouteEntry.COLUMN_TOPICS + " TEXT NOT NULL, " +
                RouteEntry.COLUMN_CITY_NAME_INIT + " TEXT NOT NULL, " +
                RouteEntry.COLUMN_START_DATE + " INTEGER NOT NULL, " +
                RouteEntry.COLUMN_MAX_ATTENDEES + " INTEGER NOT NULL, " +
                RouteEntry.COLUMN_SEATS_AVAILABLE + " INTEGER NOT NULL, " +
                RouteEntry.COLUMN_URL_ROUTE_COVER + " TEXT NOT NULL, " +
                RouteEntry.COLUMN_WEBSAFE_KEY + " TEXT NOT NULL, " +
                RouteEntry.COLUMN_ORGANIZER_DISPLAY_NAME + " TEXT NOT NULL, " +

                "UNIQUE (" + RouteEntry._ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_ROUTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RouteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
