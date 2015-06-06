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

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.ui.widget.DrawShadowFrameLayout;
import com.example.sergio.caminando.util.AccountUtils;
import com.example.sergio.caminando.util.ViewServer;

import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

public class BrowseSessionsActivity extends BaseActivity  {

    private static final String TAG = makeLogTag(BrowseSessionsActivity.class);

    // How is this Activity being used?
    private static final int MODE_EXPLORE = 0; // as top-level "Explore" screen
    private static final int MODE_TIME_FIT = 1; // showing sessions that fit in a time interval

    private int mMode = MODE_EXPLORE;

    private RoutesFragment mSessionsFrag = null;
    private DrawShadowFrameLayout mDrawShadowFrameLayout;

    private RoutesFragment mRoutesFragment;


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

        mRoutesFragment = (RoutesFragment) getSupportFragmentManager().findFragmentById(R.id.sessions_fragment);
        mDrawShadowFrameLayout = (DrawShadowFrameLayout) findViewById(R.id.main_content);

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
        RecyclerView ListView = (RecyclerView) findViewById(R.id.sessions_collection_view);
        if (ListView != null) {
            enableActionBarAutoHide(ListView);
        }

        mSessionsFrag = (RoutesFragment) getSupportFragmentManager().findFragmentById(R.id.sessions_fragment);
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

    private void getConferencesForList() {
        if (TextUtils.isEmpty(AccountUtils.getActiveAccountName(this))) {
            return;
        }
        mRoutesFragment.loadConferences();
    }
}