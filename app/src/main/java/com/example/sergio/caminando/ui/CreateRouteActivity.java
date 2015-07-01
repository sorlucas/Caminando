package com.example.sergio.caminando.ui;

import android.content.ComponentName;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.sergio.caminando.R;

import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

public class CreateRouteActivity extends BaseActivity {

    private static final String TAG = makeLogTag(CreateRouteActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routes);

        Toolbar toolbar = getActionBarToolbar();
        toolbar.setTitle(R.string.title_activity_create_routes);
        toolbar.setNavigationIcon(R.drawable.ic_up);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateUpToFromChild(CreateRouteActivity.this,
                        IntentCompat.makeMainActivity(new ComponentName(CreateRouteActivity.this,
                                BrowseSessionsActivity.class)));
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CreateRouteFragment())
                    .commit();
        }
    }
}