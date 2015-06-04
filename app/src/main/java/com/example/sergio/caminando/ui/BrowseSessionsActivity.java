/*
 * Copyright 2014 Google Inc. All rights reserved.
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.ui.widget.DrawShadowFrameLayout;
import com.example.sergio.caminando.util.UIUtils;

import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

public class BrowseSessionsActivity extends BaseActivity  {

    private static final String TAG = makeLogTag(BrowseSessionsActivity.class);

    // How is this Activity being used?
    private static final int MODE_EXPLORE = 0; // as top-level "Explore" screen
    private static final int MODE_TIME_FIT = 1; // showing sessions that fit in a time interval

    private int mMode = MODE_EXPLORE;

    private DrawShadowFrameLayout mDrawShadowFrameLayout;
    private View mButterBar;

    // time when the user last clicked "refresh" from the stale data butter bar
    private long mLastDataStaleUserActionTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_browse_sessions);

        Toolbar toolbar = getActionBarToolbar();

        overridePendingTransition(0, 0);


        if (mMode == MODE_EXPLORE) {
            // no title (to make more room for navigation and actions)
            // unless Nav Drawer opens
            toolbar.setTitle(null);
        }

        mDrawShadowFrameLayout = (DrawShadowFrameLayout) findViewById(R.id.main_content);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestDataRefresh();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        // we only have a nav drawer if we are in top-level Explore mode.
        return mMode == MODE_EXPLORE ? NAVDRAWER_ITEM_EXPLORE : NAVDRAWER_ITEM_INVALID;
    }

    // Updates the Sessions fragment content top clearance to take our chrome into account
    private void updateFragContentTopClearance() {

        View filtersBox = findViewById(R.id.filters_box);

        final boolean filterBoxVisible = filtersBox != null
                && filtersBox.getVisibility() == View.VISIBLE;
        final boolean butterBarVisible = mButterBar != null
                && mButterBar.getVisibility() == View.VISIBLE;

        int actionBarClearance = UIUtils.calculateActionBarSize(this);
        int butterBarClearance = butterBarVisible
                ? getResources().getDimensionPixelSize(R.dimen.butter_bar_height) : 0;
        int filterBoxClearance = filterBoxVisible
                ? getResources().getDimensionPixelSize(R.dimen.filterbar_height) : 0;
        int secondaryClearance = butterBarClearance > filterBoxClearance ? butterBarClearance :
                filterBoxClearance;
        int gridPadding = getResources().getDimensionPixelSize(R.dimen.explore_grid_padding);

        setProgressBarTopWhenActionBarShown(actionBarClearance + secondaryClearance);
        mDrawShadowFrameLayout.setShadowTopOffset(actionBarClearance + secondaryClearance);
    }

    @Override
    protected void onActionBarAutoShowOrHide(boolean shown) {
        super.onActionBarAutoShowOrHide(shown);
        mDrawShadowFrameLayout.setShadowVisible(shown, shown);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.browse_sessions, menu);
        // remove actions when in time interval mode:
        if (mMode != MODE_EXPLORE) {
            menu.removeItem(R.id.menu_search);
            menu.removeItem(R.id.menu_refresh);
            menu.removeItem(R.id.menu_wifi);
            menu.removeItem(R.id.menu_debug);
            menu.removeItem(R.id.menu_about);
        } else {
            configureStandardMenuItems(menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
