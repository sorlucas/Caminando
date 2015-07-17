/*
 * Copyright 2015 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sergio.caminando.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sergio.caminando.R;
import com.example.sergio.caminando.common.Attraction;


/**
 * The tourist attraction detail fragment which contains the details of a
 * a single attraction (contained inside
 * {@link com.example.sergio.caminando.ui.DetailActivity}).
 */
public class DetailFragment extends Fragment {

    private static final String EXTRA_ATTRACTION = "attraction";
    private Attraction mAttraction;

    public static DetailFragment createInstance(String attractionName) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ATTRACTION, attractionName);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    public DetailFragment() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Implement to catch attractionName and catch from provider


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //setHasOptionsMenu(true);

        String attractionName = getArguments().getString(EXTRA_ATTRACTION);

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        if (mAttraction == null) {
            getActivity().finish();
            return null;
        }

        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        TextView descTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView distanceTextView = (TextView) view.findViewById(R.id.distanceTextView);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        nameTextView.setText(attractionName);

        //TODO: Change when add module common
        /*
        int imageSize = getResources().getDimensionPixelSize(R.dimen.image_size)
                * Constants.IMAGE_ANIM_MULTIPLIER;
        * */

        int imageSize = getResources().getDimensionPixelSize(R.dimen.image_size)
                * 2;

        Glide.with(getActivity())
                .load("http")
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.color.lighter_gray)
                .override(imageSize, imageSize)
                .into(imageView);
        return view;
    }
}
