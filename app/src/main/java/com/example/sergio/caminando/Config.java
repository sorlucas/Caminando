/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sergio.caminando;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import java.util.TimeZone;

public class Config {
    // General configuration

    // Is this an internal dogfood build?
    public static final boolean IS_DOGFOOD_BUILD = false;

    // Warning messages for dogfood build
    public static final String DOGFOOD_BUILD_WARNING_TITLE = "Test build";
    public static final String DOGFOOD_BUILD_WARNING_TEXT = "This is a test build.";

    // Hard-coded conference dates. This is hardcoded here instead of extracted from the conference
    // data to avoid the Schedule UI breaking if some session is incorrectly set to a wrong date.
    public static final int CONFERENCE_YEAR = 2014;
    public static final TimeZone CONFERENCE_TIMEZONE = TimeZone.getTimeZone("Europe/Madrid");

    // shorthand for some units of time
    public static final long SECOND_MILLIS = 1000;
    public static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    public static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    public static final long DAY_MILLIS = 24 * HOUR_MILLIS;

    // How long we snooze the stale data notification for after the user has acted on it
    // (to keep from showing it repeatedly and being annoying)
    public static final long STALE_DATA_WARNING_SNOOZE = 10 * MINUTE_MILLIS;


    //      Config Endpoint   //
    // Your WEB CLIENT ID from the API Access screen of the Developer Console for your project.
    // This is NOT the Android client id from that screen.
    public static final String WEB_CLIENT_ID = "87891804806-jtj2bpl9bb5o9ivbl4p0bbl7q89jpm0e.apps.googleusercontent.com";
    // The audience is defined by the web client id, not the Android client id.
    public static final String AUDIENCE = "server:client_id:" + WEB_CLIENT_ID;
    // Class instance of the JSON factory.
    public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();
    // Class instance of the HTTP transport.
    public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

    // Email Address from Service account
    public static final String SERVICE_ACCOUNT_EMAIL = "87891804806-cr75ek5cvj0bvssfpap9565mfjrmj427@developer.gserviceaccount.com";




}
