package com.example.sergio.caminando.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.sergio.caminando.R;
import com.example.sergio.caminando.endpoints.ConferenceLoader;
import com.example.sergio.caminando.endpoints.utils.ConferenceException;
import com.example.sergio.caminando.endpoints.utils.ConferenceUtils;
import com.example.sergio.caminando.endpoints.utils.DecoratedConference;
import com.example.sergio.caminando.endpoints.utils.Utils;
import com.example.sergio.caminando.util.AccountUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by sergio on 25/05/15.
 */
public class BroseSessionsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<DecoratedConference>>  {

    private static final String TAG = "RoutesFragment";

    private ConferenceDataAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private View mLoadingView;

    //TODO Implement Collection View. in MyLisAdapter. Trasladate to InventoryGroup
    List<DecoratedConference> conferences = null;
    private int mContentTopClearance = 0;


    public boolean canCollectionViewScrollUp() {
        return ViewCompat.canScrollVertically(mRecyclerView, -1);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        LayoutAnimationController controller = AnimationUtils
                .loadLayoutAnimation(getActivity(), R.anim.list_layout_controller);
        mRecyclerView.setLayoutAnimation(controller);
        //mRecyclerView.setEmptyView(mEmptyView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sessions, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.sessions_collection_view);
        mEmptyView = (TextView) root.findViewById(R.id.empty_text);
        mLoadingView = root.findViewById(R.id.loading);

        //TODO: DELETE WHEN COLLECTIONVIEW
        final TypedArray xmlArgs = getActivity().obtainStyledAttributes(null,
                R.styleable.CollectionView, 0, 0);
        mContentTopClearance = xmlArgs.getDimensionPixelSize(
                R.styleable.CollectionView_contentTopClearance, 0);
        return root;
    }

    @Override
    public Loader<List<DecoratedConference>> onCreateLoader(int id, Bundle args) {
        return new ConferenceLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<DecoratedConference>> loader, List<DecoratedConference> data) {

        //TODO: DELETE WHEN COLLECTIONVIEW
        this.conferences = data;
        ConferenceLoader conferenceLoader = (ConferenceLoader) loader;
        if (conferenceLoader.getException() != null) {
            Utils.displayNetworkErrorMessage(getActivity());
            return;
        }
        mAdapter = new ConferenceDataAdapter(getActivity(),data);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onLoaderReset(Loader<List<DecoratedConference>> loader) {
        mAdapter = new ConferenceDataAdapter(getActivity(),null);
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

    public static BroseSessionsFragment newInstance() {
        BroseSessionsFragment f = new BroseSessionsFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    public void loadConferences() {
        // Authorization check successful, get conferences.
        ConferenceUtils.build(getActivity(), AccountUtils.getActiveAccountName(getActivity()));
        getLoaderManager().initLoader(0, null, this);
    }

    public void reload() {
        getLoaderManager().restartLoader(0, null, this).startLoading();
    }

    public void reload(List<DecoratedConference> conferences) {
        mAdapter = new ConferenceDataAdapter(getActivity(),conferences);
        mAdapter.notifyDataSetChanged();
    }



    public static class ConferenceViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        CardView cardView;
        ImageView photoRouteCover;
        TextView titleView;
        TextView descriptionView;
        TextView cityAndDateView;
        ImageView registerView;
        BroseSessionsFragment.ItemClickListener mItemClickListener;

        ConferenceViewHolder(View itemView, BroseSessionsFragment.ItemClickListener itemClickListener) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
            photoRouteCover = (ImageView) cardView.findViewById(R.id.imageRouteCover);
            titleView = (TextView)cardView.findViewById(R.id.textView1);
            descriptionView = (TextView)cardView.findViewById(R.id.textView2);
            cityAndDateView = (TextView)cardView.findViewById(R.id.textView3);
            registerView = (ImageView)cardView.findViewById(R.id.imageView);
            mItemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    private class ConferenceDataAdapter extends RecyclerView.Adapter<ConferenceViewHolder>
            implements ItemClickListener{

        public List<DecoratedConference> conferences;
        private Context mContext;

        public ConferenceDataAdapter(Context context, List<DecoratedConference> attractions) {
            super();
            mContext = context;
            conferences = attractions;
        }

        @Override
        public ConferenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.conference_row, parent, false);
            return new ConferenceViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(ConferenceViewHolder conferenceViewHolder, int i) {

            String urlPath = conferences.get(i).getConference().getPhotoUrlRouteCover();
            //mImageloaderCover.loadImage("",conferenceViewHolder.photoRouteCover);
            //TODO: Change Glide to ImageLoader
            final ImageView mImageView = conferenceViewHolder.photoRouteCover;
            Glide.with(getActivity())
                    .load(urlPath)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(100,100) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mImageView.setImageBitmap(resource);
                        }
                    });

            conferenceViewHolder.titleView.setText(conferences.get(i).getConference().getName());
            conferenceViewHolder.descriptionView.setText(conferences.get(i).getConference().getDescription());
            conferenceViewHolder.cityAndDateView.setText(conferences.get(i).getConference().getCity() + ", " +
                    Utils.getConferenceDate(mContext, conferences.get(i).getConference()));
            conferenceViewHolder.registerView.setVisibility(conferences.get(i).isRegistered() ? View.VISIBLE : View.GONE);

        }

        @Override
        public int getItemCount() {
            return conferences.size();
        }

        @Override
        public void onItemClick(View view, int position) {
            View heroView = view.findViewById(android.R.id.icon);
            // TODO: ADD Detail Activity
            /*
            DetailActivity.launch(
                    getActivity(), mAdapter.mAttractionList.get(position).name, heroView);
            */
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private void notifyAdapterDataSetChanged() {
        // We have to set up a new adapter (as opposed to just calling notifyDataSetChanged()
        // because we might need MORE view types than before, and ListView isn't prepared to
        // handle the case where its existing adapter suddenly needs to increase the number of
        // view types it needs.
        if (conferences != null){
            mRecyclerView.setAdapter(new ConferenceDataAdapter(getActivity(),conferences));
        }

    }
    public void setContentTopClearance(int topClearance) {

        if(mContentTopClearance != topClearance){
            //TODO: CollectionView
            mContentTopClearance = topClearance;
            mRecyclerView.setPadding(mRecyclerView.getPaddingLeft(),
                    mContentTopClearance,
                    mRecyclerView.getPaddingRight(),
                    mRecyclerView.getPaddingBottom());
            notifyAdapterDataSetChanged();
        }

    }
}