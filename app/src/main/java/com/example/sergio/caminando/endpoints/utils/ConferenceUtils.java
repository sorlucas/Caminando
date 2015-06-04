package com.example.sergio.caminando.endpoints.utils;


import android.content.Context;
import android.util.Log;

import com.example.sergio.myapplication.backend.domain.conference.model.Conference;
import com.example.sergio.myapplication.backend.domain.conference.model.ConferenceCollection;
import com.example.sergio.myapplication.backend.domain.conference.model.Profile;
import com.example.sergio.myapplication.backend.domain.conference.model.WrappedBoolean;
import com.example.sergio.caminando.endpoints.AppConstants;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for communication with the Cloud Endpoint.
 */
public class ConferenceUtils {

    private final static String TAG = "ConferenceUtils";

    private static com.example.sergio.myapplication.backend.domain.conference.Conference sApiServiceHandler;

    public static void build(Context context, String email) {
        sApiServiceHandler = buildServiceHandler(context, email);
    }

    /**
     * Returns a list of {@link com.example.sergio.caminando.endpoints.utils.DecoratedConference}s.
     * This list includes information about what {@link Conference}s
     * user has registered for.
     *
     * @return List of DecoratedConferences
     * @throws ConferenceException
     * @see <code>getProfile</code>
     */
    public static List<DecoratedConference> getConferences()
            throws ConferenceException, IOException {
        if (null == sApiServiceHandler) {
            Log.e(TAG, "getConferences(): no service handler was built");
            throw new ConferenceException();
        }

        com.example.sergio.myapplication.backend.domain.conference.Conference.QueryConferences
                queryConferences = sApiServiceHandler.queryConferences(null);
        ConferenceCollection conferenceCollection = queryConferences.execute();

        if (conferenceCollection != null && conferenceCollection.getItems() != null) {
            List<Conference> conferences = conferenceCollection.getItems();
            List<DecoratedConference> decoratedList = null;
            if (null == conferences || conferences.isEmpty()) {
                return decoratedList;
            }
            decoratedList = new ArrayList<>();
            Profile profile = getProfile();
            List<String> registeredConfKeys = null;
            if (null != profile) {
                registeredConfKeys = profile.getConferenceKeysToAttend();
            }
            if (null == registeredConfKeys) {
                registeredConfKeys = new ArrayList<>();
            }
            for (Conference conference : conferences) {
                DecoratedConference decorated = new DecoratedConference(conference,
                        registeredConfKeys.contains(conference.getWebsafeKey()));
                decoratedList.add(decorated);
            }
            return decoratedList;
        }
        return null;
    }

    /**
     * Registers user for a {@link Conference}
     *
     * @param conference conference data
     * @return boolean result
     * @throws ConferenceException
     */
    public static boolean registerForConference(Conference conference)
            throws ConferenceException, IOException {
        if (null == sApiServiceHandler) {
            Log.e(TAG, "registerForConference(): no service handler was built");
            throw new ConferenceException();
        }

        com.example.sergio.myapplication.backend.domain.conference.Conference.RegisterForConference
                registerForConference = sApiServiceHandler.registerForConference(
                conference.getWebsafeKey());
        WrappedBoolean result = registerForConference.execute();
        return result.getResult();
    }

    /**
     * Unregisters user from a {@link Conference}.
     *
     * @param conference conference data
     * @return boolean result
     * @throws ConferenceException
     */
    public static boolean unregisterFromConference(Conference conference)
            throws ConferenceException, IOException {
        if (null == sApiServiceHandler) {
            Log.e(TAG, "unregisterFromConference(): no service handler was built");
            throw new ConferenceException();
        }

        com.example.sergio.myapplication.backend.domain.conference.Conference.UnregisterFromConference
                unregisterFromConference = sApiServiceHandler.unregisterFromConference(
                conference.getWebsafeKey());
        WrappedBoolean result = unregisterFromConference.execute();
        return result.getResult();
    }

    /**
     * Returns the user {@link Profile}. Can
     * be used to find out what conferences user is registered for.
     *
     * @return profile user
     * @throws ConferenceException
     */
    public static Profile getProfile() throws ConferenceException, IOException {
        if (null == sApiServiceHandler) {
            Log.e(TAG, "getProfile(): no service handler was built");
            throw new ConferenceException();
        }

        com.example.sergio.myapplication.backend.domain.conference.Conference.GetProfile getProfile =
                sApiServiceHandler.getProfile();
        return getProfile.execute();
    }

    /**
     * Build and returns an instance of {@link com.example.sergio.myapplication.backend.domain.conference.Conference}
     *
     * @param context context of running application
     * @param email email user
     * @return
     */
    public static com.example.sergio.myapplication.backend.domain.conference.Conference buildServiceHandler(
            Context context, String email) {
        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                context, AppConstants.AUDIENCE);
        credential.setSelectedAccountName(email);

        com.example.sergio.myapplication.backend.domain.conference.Conference.Builder builder
                = new com.example.sergio.myapplication.backend.domain.conference.Conference.Builder(
                AppConstants.HTTP_TRANSPORT,
                AppConstants.JSON_FACTORY, credential);
        builder.setApplicationName("conference-central-server");
        return builder.build();
    }
}