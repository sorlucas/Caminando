package com.example.sergio.caminando.endpoints;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.endpoints.utils.DecoratedConference;
import com.example.sergio.caminando.endpoints.utils.Utils;

import java.util.List;

/**
 *
 */
public class ConferenceDataAdapter extends RecyclerView.Adapter<ConferenceDataAdapter.ConferenceViewHolder> {

    private static final String TAG = "ConferenceDataAdapter";

    List<DecoratedConference> conferences;

    private Context mContext;

    public ConferenceDataAdapter(Context context, List<DecoratedConference> conferences){
        this.mContext = context;
        this.conferences = conferences;
    }

    public static class ConferenceViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView titleView;
        TextView descriptionView;
        TextView cityAndDateView;
        ImageView registerView;
        ConferenceViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
            titleView = (TextView)cardView.findViewById(R.id.textView1);
            descriptionView = (TextView)cardView.findViewById(R.id.textView2);
            cityAndDateView = (TextView)cardView.findViewById(R.id.textView3);
            registerView = (ImageView)cardView.findViewById(R.id.imageView);
        }

    }

    @Override
    public ConferenceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.conference_row,viewGroup,false);
        ConferenceViewHolder conferenceViewHolder = new ConferenceViewHolder(v);
        return conferenceViewHolder;
    }

    @Override
    public void onBindViewHolder(ConferenceViewHolder conferenceViewHolder, int i) {
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
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
