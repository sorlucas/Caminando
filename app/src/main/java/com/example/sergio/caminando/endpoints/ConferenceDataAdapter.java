package com.example.sergio.caminando.endpoints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.endpoints.utils.DecoratedConference;
import com.example.sergio.caminando.endpoints.utils.Utils;

import java.util.List;

/**
 *
 */
public class ConferenceDataAdapter extends ArrayAdapter<DecoratedConference> {

    private static final String TAG = "ConferenceDataAdapter";

    private final Context mContext;

    public ConferenceDataAdapter(Context context) {
        super(context, 0);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        DecoratedConference decoratedConference = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.conference_row, null);
            holder = new ViewHolder();
            holder.titleView = (TextView) convertView.findViewById(R.id.textView1);
            holder.descriptionView = (TextView) convertView.findViewById(R.id.textView2);
            holder.cityAndDateView = (TextView) convertView.findViewById(R.id.textView3);
            holder.registerView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleView.setText(decoratedConference.getConference().getName());
        holder.descriptionView.setText(decoratedConference.getConference().getDescription());
        holder.cityAndDateView.setText(decoratedConference.getConference().getCity() + ", " + Utils
                .getConferenceDate(mContext, decoratedConference.getConference()));
        holder.registerView
                .setVisibility(decoratedConference.isRegistered() ? View.VISIBLE : View.GONE);

        return convertView;
    }

    private class ViewHolder {

        TextView titleView;
        TextView descriptionView;
        TextView cityAndDateView;
        ImageView registerView;
    }

    public void setData(List<DecoratedConference> data) {
        clear();
        if (data != null) {
            for (DecoratedConference item : data) {
                add(item);
            }
        }

    }
}
