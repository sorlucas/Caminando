package com.example.sergio.myapplication.backend;

import com.google.api.server.spi.Constant;

/**
 * Contains the client IDs and scopes for allowed clients consuming the conference API.
 */
public class Constants {
    public static final String WEB_CLIENT_ID = "87891804806-jtj2bpl9bb5o9ivbl4p0bbl7q89jpm0e.apps.googleusercontent.com";
    public static final String ANDROID_CLIENT_ID = "87891804806-ggf5b29so26ngp016iiaeug9rh3vboh1.apps.googleusercontent.com";
    public static final String IOS_CLIENT_ID = "replace this with your iOS client ID";
    public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;
    public static final String EMAIL_SCOPE = Constant.API_EMAIL_SCOPE;
    public static final String API_EXPLORER_CLIENT_ID = Constant.API_EXPLORER_CLIENT_ID;

    public static final String MEMCACHE_ANNOUNCEMENTS_KEY = "RECENT_ANNOUNCEMENTS";
}