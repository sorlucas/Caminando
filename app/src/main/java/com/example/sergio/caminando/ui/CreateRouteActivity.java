package com.example.sergio.caminando.ui;

import android.content.ComponentName;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.endpoints.utils.ConferenceException;
import com.example.sergio.caminando.endpoints.utils.ConferenceUtils;
import com.example.sergio.caminando.endpoints.utils.Utils;
import com.example.sergio.myapplication.backend.domain.conference.model.Conference;
import com.example.sergio.myapplication.backend.domain.conference.model.ConferenceForm;

import java.io.IOException;

import static com.example.sergio.caminando.util.LogUtils.LOGE;
import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

public class CreateRouteActivity extends BaseActivity implements CreateRouteFragment.Callbacks{

    private static final String TAG = makeLogTag(CreateRouteActivity.class);

    private String mEmailAccount;
    private CreateRouteTask mAuthTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routes);

        Toolbar toolbar = getActionBarToolbar();
        toolbar.setTitle(R.string.title_activity_create_routes);
        toolbar.setNavigationIcon(R.drawable.ic_up);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateUpToFromChild(CreateRouteActivity.this,
                        IntentCompat.makeMainActivity(new ComponentName(CreateRouteActivity.this,
                                BrowseSessionsActivity.class)));
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CreateRouteFragment())
                    .commit();
        }

        mEmailAccount = Utils.getEmailAccount(this);
    }

    private class CreateRouteTask extends AsyncTask<ConferenceForm, Integer, Conference> {

        private final static boolean SUCCESS = true;
        private final static boolean FAILURE = false;
        private Exception mException;


        @Override
        protected Conference doInBackground(ConferenceForm... conferenceForms) {
            Log.i(TAG, "Background task started.");

            ConferenceForm conferenceForm = conferenceForms[0];
            // Ensure only one task is running at a time.
            mAuthTask = this;

            // Authorization check successful, get conferences.
            ConferenceUtils.build(CreateRouteActivity.this, mEmailAccount);

            try {
                ConferenceUtils.getProfile();
                return ConferenceUtils.createConference(conferenceForm);
            } catch (IOException e) {
                Log.e(TAG, "Failed to create Route", e);
                mException = e;
            } catch (ConferenceException e) {
                // logged
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... stringIds) {
            // Toast only the most recent.
            Integer stringId = stringIds[0];
            Toast.makeText(CreateRouteActivity.this, getString(stringId), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            mAuthTask = this;
        }

        @Override
        protected void onPostExecute(Conference conference) {

            Toast.makeText(getApplicationContext(),"Upload Route",Toast.LENGTH_LONG).show();
            Log.e(TAG, conference.toString());
            //TODO: CATCH LAS EXCEPCIONES
            /*
            if (success) {

            } else {
                // Authorization check unsuccessful.
                mEmailAccount = null;
                if (mException != null) {
                    Utils.displayNetworkErrorMessage(CreateRoutesActivity.this);
                }
            }
            */
            mAuthTask = null;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mAuthTask != null) {
            mAuthTask.cancel(true);
            mAuthTask = null;
        }
    }

    @Override
    public void uploadRoute(ConferenceForm conference) {

        LOGE(TAG,"uploadRoute");

        // Cancel previously running tasks.
        if (mAuthTask != null) {
            mAuthTask.cancel(true);
        }
        // Start task to check authorization.
        mAuthTask = new CreateRouteTask();
        mAuthTask.execute(conference);
    }
}