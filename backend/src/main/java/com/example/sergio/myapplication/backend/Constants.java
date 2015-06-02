package com.example.sergio.myapplication.backend;

import com.google.api.server.spi.Constant;

/**
 * Contains the client IDs and scopes for allowed clients consuming the conference API.
 */
public class Constants {
    public static final String WEB_CLIENT_ID = "393381625064-63mlldga2j1bnrn7du8pt3mme0ltm4q7.apps.googleusercontent.com";
    public static final String ANDROID_CLIENT_ID = "393381625064-ttm0ldsruthp2a3eb9evr8j8slnaugrt.apps.googleusercontent.com";
    public static final String IOS_CLIENT_ID = "replace this with your iOS client ID";
    public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;
    public static final String EMAIL_SCOPE = Constant.API_EMAIL_SCOPE;
    public static final String API_EXPLORER_CLIENT_ID = Constant.API_EXPLORER_CLIENT_ID;

    public static final String MEMCACHE_ANNOUNCEMENTS_KEY = "RECENT_ANNOUNCEMENTS";
}