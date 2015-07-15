package com.example.sergio.caminando.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.provider.RouteContract;
import com.example.sergio.caminando.service.DownloadResultReceiver;
import com.example.sergio.caminando.service.UploadRouteService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

public class CreateRouteActivity extends BaseActivity implements DownloadResultReceiver.Receiver, CreateRouteFragment.Callbacks {

    private static final String TAG = makeLogTag(CreateRouteActivity.class);

    private DownloadResultReceiver mReceiver;

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

    @Override
    public void onUploadRoute(HashMap<String, String> routeHasMap, String urlPhotoPath) {
        Toast.makeText(CreateRouteActivity.this, "Subir ruta", Toast.LENGTH_SHORT).show();

        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, UploadRouteService.class);

        // Format list topics
        List<String> listTopics = new ArrayList<>();
        listTopics.add(intent.getStringExtra(RouteContract.RouteEntry.COLUMN_TOPICS));

        /* Send optional extras to Download IntentService */
        intent.putExtra("receiver", mReceiver);
        intent.putExtra(RouteContract.RouteEntry.COLUMN_NAME_ROUTE, routeHasMap.get(RouteContract.RouteEntry.COLUMN_NAME_ROUTE));
        intent.putExtra(RouteContract.RouteEntry.COLUMN_DESCRIPTION, routeHasMap.get(RouteContract.RouteEntry.COLUMN_DESCRIPTION));
        intent.putExtra(RouteContract.RouteEntry.COLUMN_TOPICS, routeHasMap.get(RouteContract.RouteEntry.COLUMN_TOPICS));
        intent.putExtra(RouteContract.RouteEntry.COLUMN_CITY_NAME_INIT, routeHasMap.get(RouteContract.RouteEntry.COLUMN_CITY_NAME_INIT));
        intent.putExtra(RouteContract.RouteEntry.COLUMN_START_DATE, routeHasMap.get(RouteContract.RouteEntry.COLUMN_START_DATE));
        intent.putExtra(RouteContract.RouteEntry.COLUMN_URL_ROUTE_COVER, routeHasMap.get(RouteContract.RouteEntry.COLUMN_URL_ROUTE_COVER));
        intent.putExtra(RouteContract.RouteEntry.COLUMN_MAX_ATTENDEES, routeHasMap.get(RouteContract.RouteEntry.COLUMN_MAX_ATTENDEES));

        startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case UploadRouteService.STATUS_RUNNING:
                setProgressBarIndeterminateVisibility(true);
                break;
            case UploadRouteService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);
                //String[] results = resultData.getStringArray("result");
                Toast.makeText(getApplicationContext(),"Ruta subida",Toast.LENGTH_LONG).show();
                break;
            case UploadRouteService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }
}