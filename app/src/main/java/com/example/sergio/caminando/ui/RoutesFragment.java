package com.example.sergio.caminando.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.endpoints.ConferenceDataAdapter;
import com.example.sergio.caminando.endpoints.ConferenceLoader;
import com.example.sergio.caminando.endpoints.utils.ConferenceException;
import com.example.sergio.caminando.endpoints.utils.ConferenceUtils;
import com.example.sergio.caminando.endpoints.utils.DecoratedConference;
import com.example.sergio.caminando.endpoints.utils.Utils;

import java.io.IOException;
import java.util.List;

/**
 * Created by sergio on 25/05/15.
 */
public class RoutesFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<DecoratedConference>>  {

    private static final String TAG = "ConferenceListFragment";

    private ConferenceDataAdapter mAdapter;

    private ListView mListView;
    private TextView mEmptyView;
    private View mLoadingView;

    public boolean canCollectionViewScrollUp() {
        return ViewCompat.canScrollVertically(mListView, -1);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView.setFastScrollEnabled(true);
        LayoutAnimationController controller = AnimationUtils
                .loadLayoutAnimation(getActivity(), R.anim.list_layout_controller);
        mListView.setLayoutAnimation(controller);
        mAdapter = new ConferenceDataAdapter(getActivity());
        mListView.setEmptyView(mEmptyView);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sessions, container, false);
        mListView = (ListView) root.findViewById(R.id.sessions_collection_view);
        mEmptyView = (TextView) root.findViewById(R.id.empty_text);
        mLoadingView = root.findViewById(R.id.loading);
        return root;
    }

    @Override
    public Loader<List<DecoratedConference>> onCreateLoader(int id, Bundle args) {
        return new ConferenceLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<DecoratedConference>> loader, List<DecoratedConference> data) {
        ConferenceLoader conferenceLoader = (ConferenceLoader) loader;
        if (conferenceLoader.getException() != null) {
            Utils.displayNetworkErrorMessage(getActivity());
            return;
        }
        mAdapter.setData(data);

    }

    @Override
    public void onLoaderReset(Loader<List<DecoratedConference>> loader) {
        mAdapter.setData(null);
    }


    private void registerToConference(DecoratedConference decoratedConference) {
        new RegistrationAsyncTask(decoratedConference).execute();
    }

    class RegistrationAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private final DecoratedConference mDecoratedConference;
        private List<DecoratedConference> mDecoratedConferences;
        private Exception mException;

        public RegistrationAsyncTask(DecoratedConference conference) {
            this.mDecoratedConference = conference;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                try {
                    if (mDecoratedConference.isRegistered()) {
                        boolean success = ConferenceUtils
                                .unregisterFromConference(mDecoratedConference.getConference());
                        if (success) {
                            mDecoratedConferences = ConferenceUtils.getConferences();
                        }
                        return success;
                    } else {
                        boolean success = ConferenceUtils.registerForConference(
                                mDecoratedConference.getConference());
                        if (success) {
                            mDecoratedConferences = ConferenceUtils.getConferences();
                        }
                        return success;
                    }
                } catch (IOException e) {
                    mException = e;
                }
            } catch (ConferenceException e) {
                //logged
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (null != result && result) {
                // success
                reload(mDecoratedConferences);
            } else {
                // failure
                Log.e(TAG, "Failed to perform registration update", mException);
                if (mException != null) {
                    Utils.displayNetworkErrorMessage(getActivity());
                }
            }
        }
    }

    public static RoutesFragment newInstance() {
        RoutesFragment f = new RoutesFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    public void loadConferences() {
        getLoaderManager().initLoader(0, null, this);
    }

    public void reload() {
        getLoaderManager().restartLoader(0, null, this).startLoading();
    }

    public void reload(List<DecoratedConference> conferences) {
        mAdapter.setData(conferences);
        mAdapter.notifyDataSetChanged();
    }

}