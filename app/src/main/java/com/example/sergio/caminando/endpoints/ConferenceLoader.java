package com.example.sergio.caminando.endpoints;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.sergio.caminando.endpoints.utils.ConferenceException;
import com.example.sergio.caminando.endpoints.utils.ConferenceUtils;
import com.example.sergio.caminando.endpoints.utils.DecoratedConference;
import com.example.sergio.caminando.endpoints.utils.Utils;
import com.example.sergio.caminando.util.AccountUtils;
import com.example.sergio.myapplication.backend.domain.conference.model.ConferenceQueryForm;
import com.example.sergio.myapplication.backend.domain.conference.model.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConferenceLoader extends AsyncTaskLoader<List<DecoratedConference>> {

    private static final String TAG = "ConferenceLoader";
    private Exception mException;

    private ConferenceQueryForm mConferenceQueryForm = null;

    private Context mContext;
    public ConferenceLoader(Context context) {
        super(context);
        mContext = context;
    }

    public void setFiltersQueryForm (String field, String operator, String value){
        mConferenceQueryForm = new ConferenceQueryForm();
        List<Filter> filters = new ArrayList<>();
        Filter filter = new Filter();
        filter.setField(field).setOperator(operator).setValue(value);
        filters.add(filter);
        mConferenceQueryForm.setFilters(filters);
    }

    @Override
    public List<DecoratedConference> loadInBackground() {
        try {
            ConferenceUtils.build(mContext, AccountUtils.getActiveAccountName(mContext));
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