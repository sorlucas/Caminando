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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sergio.caminando.Config;
import com.example.sergio.caminando.R;
import com.example.sergio.caminando.ui.widget.DrawShadowFrameLayout;
import com.example.sergio.caminando.util.AccountUtils;
import com.example.sergio.caminando.util.PrefUtils;
import com.example.sergio.caminando.util.UIUtils;
import com.example.sergio.caminando.util.ViewServer;

import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

public class BrowseSessionsActivity extends BaseActivity  {

    private static final String TAG = makeLogTag(BrowseSessionsActivity.class);

    // How is this Activity being used?
    private static final int MODE_EXPLORE = 0; // as top-level "Explore" screen
    private static final int MODE_TIME_FIT = 1; // showing sessions that fit in a time interval
    private int mMode = MODE_EXPLORE;
    // time when the user last clicked "refresh" from the stale data butter bar
    private long mLastDataStaleUserActionTime = 0L;

    private BroseSessionsFragment mSessionsFrag = null;
    private DrawShadowFrameLayout mDrawShadowFrameLayout;
    private View mButterBar;

    private BroseSessionsFragment mRoutesFragment;

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

        mRoutesFragment = (BroseSessionsFragment) getSupportFragmentManager().findFragmentById(R.id.sessions_fragment);
        mButterBar = findViewById(R.id.butter_bar);
        mDrawShadowFrameLayout = (DrawShadowFrameLayout) findViewById(R.id.main_content);

        registerHideableHeaderView(mButterBar);

        //TODO: DELETO FOR RELEASE
        ViewServer.get(this).addWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO: DELETO FOR RELEASE
        ViewServer.get(this).removeWindow(this);
    }
    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        if (mSessionsFrag != null) {
            return mSessionsFrag.canCollectionViewScrollUp();
        }
        return super.canSwipeRefreshChildScrollUp();
    }
    @Override
    protected int getSelfNavDrawerItem() {
        // we only have a nav drawer if we are in top-level Explore mode.
        return mMode == MODE_EXPLORE ? NAVDRAWER_ITEM_EXPLORE : NAVDRAWER_ITEM_INVALID;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sessions_collection_view);
        if (recyclerView != null) {
            enableActionBarAutoHide(recyclerView);
        }

        mSessionsFrag = (BroseSessionsFragment) getSupportFragmentManager().findFragmentById(R.id.sessions_fragment);
        if (mSessionsFrag != null && savedInstanceState == null) {
            //TODO: AÑADIR para funcionalidad de recibir argumentos por intent para cargar desde argumentos
            /*
            Bundle args = intentToFragmentArguments(getIntent());
            mSessionsFrag.reloadFromArguments(args);
            */
        }
        getConferencesForList();

        registerHideableHeaderView(findViewById(R.id.headerbar));
    }

    @Override
    protected void onActionBarAutoShowOrHide(boolean shown) {
        super.onActionBarAutoShowOrHide(shown);
        mDrawShadowFrameLayout.setShadowVisible(shown, shown);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkShowStaleDataButterBar();
        getConferencesForList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.browse_sessions, menu);
        // remove actions when in time interval mode:
        if (mMode != MODE_EXPLORE) {
            menu.removeItem(R.id.menu_search);
            menu.removeItem(R.id.menu_refresh);
            menu.removeItem(R.id.menu_about);
        } else {
            configureStandardMenuItems(menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_route:

                Intent intent = new Intent(getApplicationContext(), CreateRouteActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Updates the Sessions fragment content top clearance to take our chrome into account
    private void updateFragContentTopClearance() {
        BroseSessionsFragment frag = (BroseSessionsFragment) getSupportFragmentManager().findFragmentById(
                R.id.sessions_fragment);
        if (frag == null) {
            return;
        }

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
        frag.setContentTopClearance(actionBarClearance + secondaryClearance + gridPadding);
    }

    private void checkShowStaleDataButterBar() {
        final boolean showingFilters = findViewById(R.id.filters_box) != null && findViewById(R.id.filters_box).getVisibility() == View.VISIBLE;
        final long now = UIUtils.getCurrentTime(this);
        final boolean inSnooze = (now - mLastDataStaleUserActionTime < Config.STALE_DATA_WARNING_SNOOZE);
        final long staleTime = now - PrefUtils.getLastSyncSucceededTime(this);
        //TODO: implement para mostrar la barra o no de pendiendo de si la ruta esta en ejecucion para asi mostrar los flitros
        /*
        final long staleThreshold = (now >= Config.CONFERENCE_START_MILLIS && now
                <= Config.CONFERENCE_END_MILLIS) ? Config.STALE_DATA_THRESHOLD_DURING_CONFERENCE :
                Config.STALE_DATA_THRESHOLD_NOT_DURING_CONFERENCE;

        final boolean isStale = (staleTime >= staleThreshold);
        */
        final boolean isStale = true;
        //final boolean bootstrapDone = PrefUtils.isDataBootstrapDone(this);
        final boolean mustShowBar = isStale && !inSnooze && !showingFilters;

        if (!mustShowBar) {
            mButterBar.setVisibility(View.GONE);
        } else {
            UIUtils.setUpButterBar(mButterBar, getString(R.string.data_stale_warning),
                    getString(R.string.description_refresh), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mButterBar.setVisibility(View.GONE);
                            updateFragContentTopClearance();
                            mLastDataStaleUserActionTime = UIUtils.getCurrentTime(
                                    BrowseSessionsActivity.this);
                            requestDataRefresh();
                        }
                    }
            );
        }
        updateFragContentTopClearance();
    }

    private void getConferencesForList() {
        if (TextUtils.isEmpty(AccountUtils.getActiveAccountName(this))) {
            return;
        }
        mRoutesFragment.loadConferences();
    }
}