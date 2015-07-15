package com.example.sergio.caminando.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.example.sergio.caminando.endpoints.utils.ConferenceUtils;
import com.example.sergio.caminando.endpoints.utils.StorageUtils;
import com.example.sergio.caminando.provider.RouteContract;
import com.example.sergio.myapplication.backend.domain.conference.model.Conference;
import com.example.sergio.myapplication.backend.domain.conference.model.ConferenceForm;

import java.util.ArrayList;
import java.util.List;

import static com.example.sergio.caminando.util.LogUtils.LOGD;
import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

/**
 * Created by sergio on 15/07/15.
 */

public class UploadRouteService extends IntentService {

    private static final String TAG = makeLogTag(UploadRouteService.class);

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    public UploadRouteService() {
        super(UploadRouteService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LOGD(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");

        Bundle bundle = new Bundle();

        String nameRoute = intent.getStringExtra(RouteContract.RouteEntry.COLUMN_NAME_ROUTE);
        if (!TextUtils.isEmpty(nameRoute)) {
            /* Update UI: Download Service is Running */
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            try {
                //ADD route to cloud storage
                StorageUtils.build(getApplicationContext());
                // TODO: CHANGE IMPLEMENTATION to insert different buckets. Framework
                String urlPublicLink = StorageUtils.uploadPhotoToBucket("photos_routes", intent.getStringExtra(RouteContract.RouteEntry.COLUMN_URL_ROUTE_COVER));

                // Add route to datatastore with urlPublicLInk saved on storage
                ConferenceUtils.build(getApplicationContext());
                Conference newConference = ConferenceUtils.createConference(getConferenceForm(intent, urlPublicLink));

                /* Sending result back to activity */
                if (null != newConference) {
                    bundle.putString("result", newConference.getId().toString());
                    receiver.send(STATUS_FINISHED, bundle);
                }
            } catch (Exception e){
                // create build to Storage
                Log.e(TAG, "Failed to upload Route",e);

                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }
        }

        LOGD(TAG, "Service Stopping!");
        this.stopSelf();
    }

    private ConferenceForm getConferenceForm (Intent intent, String urlPublicLinkCover){
        ConferenceForm conferenceForm = new ConferenceForm();

        // Format list topics
        List<String> listTopics = new ArrayList<>();
        listTopics.add(intent.getStringExtra(RouteContract.RouteEntry.COLUMN_TOPICS));

        conferenceForm.setName(intent.getStringExtra(RouteContract.RouteEntry.COLUMN_NAME_ROUTE));
        conferenceForm.setDescription(intent.getStringExtra(RouteContract.RouteEntry.COLUMN_DESCRIPTION));
        conferenceForm.setTopics(listTopics);
        conferenceForm.setCity(intent.getStringExtra(RouteContract.RouteEntry.COLUMN_CITY_NAME_INIT));
        conferenceForm.setStartDate(Long.parseLong(intent.getStringExtra(RouteContract.RouteEntry.COLUMN_START_DATE)));
        conferenceForm.setUrlPhotoCover(urlPublicLinkCover);
        conferenceForm.setMaxAttendees(Integer.parseInt(intent.getStringExtra(RouteContract.RouteEntry.COLUMN_MAX_ATTENDEES)));

        return  conferenceForm;
    }
}
