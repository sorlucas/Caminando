package com.example.sergio.caminando.endpoints;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.sergio.caminando.endpoints.utils.ConferenceException;
import com.example.sergio.caminando.endpoints.utils.ConferenceUtils;
import com.example.sergio.caminando.endpoints.utils.DecoratedConference;
import com.example.sergio.caminando.endpoints.utils.Utils;
import com.example.sergio.myapplication.backend.domain.conference.model.ConferenceQueryForm;

import java.io.IOException;
import java.util.List;

public class ConferenceLoader extends AsyncTaskLoader<List<DecoratedConference>> {

    private static final String TAG = "ConferenceLoader";
    private Exception mException;

    private ConferenceQueryForm mConferenceQueryForm = null;

    public ConferenceLoader(Context context) {
        super(context);
    }

    public void setmConferenceQueryForm (ConferenceQueryForm conferenceQueryForm){
        this.mConferenceQueryForm = conferenceQueryForm;
    }

    @Override
    public List<DecoratedConference> loadInBackground() {
        try {
            ConferenceUtils.getProfile();
            return ConferenceUtils.getConferences(mConferenceQueryForm);
        } catch (IOException e) {
            Log.e(TAG, "Failed to get conferences", e);
            mException = e;
        } catch (ConferenceException e) {
            // logged
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        cancelLoad();
        if (mException != null) {
            Utils.displayNetworkErrorMessage(getContext());
        }
    }

    public Exception getException() {
        return mException;
    }

}