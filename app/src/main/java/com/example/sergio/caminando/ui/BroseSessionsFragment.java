package com.example.sergio.caminando.ui;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.endpoints.utils.ConferenceException;
import com.example.sergio.caminando.endpoints.utils.ConferenceUtils;
import com.example.sergio.caminando.endpoints.utils.DecoratedConference;
import com.example.sergio.caminando.endpoints.utils.Utils;
import com.example.sergio.caminando.provider.RouteContract;
import com.example.sergio.caminando.ui.widget.AttractionsRecyclerView;

import java.io.IOException;
import java.util.List;

/**
 * Created by sergio on 25/05/15.
 */
public class BroseSessionsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>  {

    private static final String TAG = "RoutesFragment";

    private ForecastAdapter mAdapter;

    private AttractionsRecyclerView mRecyclerView;

    private int mPosition = RecyclerView.INVALID_TYPE;
    private static final String SELECTED_KEY = "selected_position";
    private boolean mUseTodayLayout;

    //TODO Implement Collection View. in MyLisAdapter. Trasladate to InventoryGroup
    List<DecoratedConference> conferences = null;
    private int mContentTopClearance = 0;

    private static final int FORECAST_LOADER = 0;
    // For the forecast view we're showing only a small subset of the stored data. Specify the columns we need.
    private static final String[] FORECAST_COLUMNS = {
            RouteContract.RouteEntry.TABLE_NAME + "." + RouteContract.RouteEntry._ID,
            RouteContract.RouteEntry.COLUMN_NAME_ROUTE,
            RouteContract.RouteEntry.COLUMN_DESCRIPTION,
            RouteContract.RouteEntry.COLUMN_TOPICS,
            RouteContract.RouteEntry.COLUMN_CITY_NAME_INIT,
            RouteContract.RouteEntry.COLUMN_START_DATE,
            RouteContract.RouteEntry.COLUMN_MAX_ATTENDEES,
            RouteContract.RouteEntry.COLUMN_URL_ROUTE_COVER,
            RouteContract.RouteEntry.COLUMN_SEATS_AVAILABLE,
            RouteContract.RouteEntry.COLUMN_WEBSAFE_KEY,
            RouteContract.RouteEntry.COLUMN_ORGANIZER_DISPLAY_NAME,
    };

    // These indices are tied to FORECAST_COLUMNS.  If FORECAST_COLUMNS changes, these
    // must change.
    static final int COL_ROUTE_ID = 0;
    static final int COL_NAME_ROUTE = 1;
    static final int COL_DESCRIPTION = 2;
    static final int COL_TOPICS = 3;
    static final int COL_CITY_NAME_INIT = 4;
    static final int COL_START_DATE = 5;
    static final int COL_MAX_ATTENDEES = 6;
    static final int COL_URL_ROUTE_COVER = 7;
    static final int COL_SEATS_AVAILABLE = 8;
    static final int COL_WEBSAFE_KEY = 9;
    static final int COL_ORGANIZER_DISPLAY_NAME = 10;

    public boolean canCollectionViewScrollUp() {
        return ViewCompat.canScrollVertically(mRecyclerView, -1);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Authorization check successful, get conferences.
        getLoaderManager().initLoader(FORECAST_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sessions, container, false);
        mRecyclerView = (AttractionsRecyclerView) root.findViewById(R.id.sessions_collection_view);
        mRecyclerView.setEmptyView(root.findViewById(android.R.id.empty));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.list_columns) ));

        //TODO: DELETE WHEN COLLECTIONVIEW
        final TypedArray xmlArgs = getActivity().obtainStyledAttributes(null,
                R.styleable.CollectionView, 0, 0);
        mContentTopClearance = xmlArgs.getDimensionPixelSize(
                R.styleable.CollectionView_contentTopClearance, 0);
        return root;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // fragment only uses one loader, so we don't care about checking the id.

        // To only show current and future dates, filter the query to return weather only for
        // dates after or including today.

        // Sort order:  Ascending, by date.
        String sortOrder = RouteContract.RouteEntry.COLUMN_START_DATE + " ASC";

        Uri routesUri = RouteContract.RouteEntry.buildRouteUriWithStartDate(System.currentTimeMillis());

        return new CursorLoader(getActivity(),
                routesUri,
                FORECAST_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor dataCursor) {

        mAdapter = new ForecastAdapter(getActivity(),dataCursor);
        mRecyclerView.setAdapter(mAdapter);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            mRecyclerView.smoothScrollToPosition(mPosition);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != RecyclerView.INVALID_TYPE) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    private void registerToConference(DecoratedConference decoratedConference) {
        new RegistrationAsyncTask(decoratedConference).execute();
    }



    public void loadConferences() {
        // Authorization check successful, get conferences.
        // ConferenceUtils.build(getActivity(), AccountUtils.getActiveAccountName(getActivity()));
        getLoaderManager().initLoader(FORECAST_LOADER, null, this);
    }

    public void reload() {
        getLoaderManager().restartLoader(FORECAST_LOADER, null, this).startLoading();
    }

    /*
    public void reload(List<DecoratedConference> conferences) {
        mAdapter = new ConferenceDataAdapter(getActivity(),conferences);
        mAdapter.notifyDataSetChanged();
    }
    */


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    /*
    private void notifyAdapterDataSetChanged() {
        // We have to set up a new adapter (as opposed to just calling notifyDataSetChanged()
        // because we might need MORE view types than before, and ListView isn't prepared to
        // handle the case where its existing adapter suddenly needs to increase the number of
        // view types it needs.
        if (conferences != null){
            mRecyclerView.setAdapter(new ConferenceDataAdapter(getActivity(),conferences));
        }

    }
    */
    public void setContentTopClearance(int topClearance) {

        if(mContentTopClearance != topClearance){
            //TODO: CollectionView
            mContentTopClearance = topClearance;
            mRecyclerView.setPadding(mRecyclerView.getPaddingLeft(),
                    mContentTopClearance,
                    mRecyclerView.getPaddingRight(),
                    mRecyclerView.getPaddingBottom());
            //TODO: notifyAdapterDataSetChanged();
        }

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
                            mDecoratedConferences = ConferenceUtils.getConferences(null);
                        }
                        return success;
                    } else {
                        boolean success = ConferenceUtils.registerForConference(
                                mDecoratedConference.getConference());
                        if (success) {
                            mDecoratedConferences = ConferenceUtils.getConferences(null);
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
                //reload(mDecoratedConferences);
            } else {
                // failure
                Log.e(TAG, "Failed to perform registration update", mException);
                if (mException != null) {
                    Utils.displayNetworkErrorMessage(getActivity());
                }
            }
        }
    }
}