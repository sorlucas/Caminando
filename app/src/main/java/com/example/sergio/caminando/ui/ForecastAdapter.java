package com.example.sergio.caminando.ui;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.sergio.caminando.R;
import com.example.sergio.caminando.endpoints.utils.Utils;
import com.example.sergio.caminando.ui.widget.CursorRecyclerViewAdapter;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorRecyclerViewAdapter<ForecastAdapter.ViewHolder> {

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    // Flag to determine if we want to use a separate view for "today".
    private boolean mUseTodayLayout = true;

    private Context mContext;

    public ForecastAdapter(Context context,Cursor cursor){
        super(context,cursor);
        this.mContext = context;
    }
    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView photoRouteCover;
        TextView titleView;
        TextView descriptionView;
        TextView cityAndDateView;
        ImageView registerView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.cardView);
            photoRouteCover = (ImageView) cardView.findViewById(R.id.imageRouteCover);
            titleView = (TextView)cardView.findViewById(R.id.textView1);
            descriptionView = (TextView)cardView.findViewById(R.id.textView2);
            cityAndDateView = (TextView)cardView.findViewById(R.id.textView3);
            registerView = (ImageView)cardView.findViewById(R.id.imageView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Choose the layout type
        int layoutId = -1;
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                layoutId = R.layout.conference_row;
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                // TODO: change tu other layout (future day)
                layoutId = R.layout.conference_row;
                break;
            }
        }

        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {

        // TODO: Implementar onBindViewHolder with viewType
        int viewType = getItemViewType(cursor.getPosition());
        /*
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                // Get weather icon
                viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(
                        cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                // Get weather icon
                viewHolder.iconView.setImageResource(Utility.getIconResourceForWeatherCondition(
                        cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
                break;
            }
        }
        */

        String urlPath = cursor.getString(BroseSessionsFragment.COL_URL_ROUTE_COVER);
        //mImageloaderCover.loadImage("",conferenceViewHolder.photoRouteCover);
        //TODO: Change Glide to ImageLoader
        final ImageView mImageView = viewHolder.photoRouteCover;
        Glide.with(mContext)
                .load(urlPath)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100,100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mImageView.setImageBitmap(resource);
                    }
                });

        viewHolder.titleView.setText(cursor.getString(BroseSessionsFragment.COL_NAME_ROUTE));
        viewHolder.descriptionView.setText(cursor.getString(BroseSessionsFragment.COL_DESCRIPTION));
        viewHolder.cityAndDateView.setText(cursor.getString(BroseSessionsFragment.COL_CITY_NAME_INIT) + ", " +
                Utils.getConferenceDate(mContext, cursor.getLong(BroseSessionsFragment.COL_START_DATE)));
        // TODO: Implement registerView
        //viewHolder.registerView.setVisibility(BroseSessionsFragment.COL_REGISTRATION ? View.VISIBLE : View.GONE);
    }


    public void setUseTodayLayout(boolean useTodayLayout) {
        mUseTodayLayout = useTodayLayout;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }
}