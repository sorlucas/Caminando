package com.example.sergio.caminando.maps;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.util.MapsUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

public class MapsActivity extends FragmentActivity
        implements
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnCameraChangeListener,
        OnMapReadyCallback,
        RouteInfoFragment.OnFragmentInfoListener,
        RoutePaletteFragment.OnFragmentPaletteListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener {

    private static final String TAG = makeLogTag(MapsActivity.class);

    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;

    private RouteInfoFragment mInfoFragment;
    private RoutePaletteFragment mPaletteFragment;

    private Marker mMarkerInit;
    private Marker mMarketFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mInfoFragment = (RouteInfoFragment) getSupportFragmentManager().findFragmentById(R.id.information_fragment);
        mPaletteFragment = (RoutePaletteFragment) getSupportFragmentManager().findFragmentById(R.id.palette_fragment);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }
    @Override
    public void onMapReady(GoogleMap map) {
        // Update mTrafficCheckbox and setMyLocationEnabled. Initial functions
        mMap = MapsUtils.updateAllTypesOfViews(this, map);

        // Setting an info window adapter allows us to change the both the contents and look of the
        // info window.
        mMap.setInfoWindowAdapter(new MarketInfoWindowAdapter(getApplicationContext()));

        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnCameraChangeListener(this);
    }

    @Override
    public void onMapClick(LatLng point) {
        if (mMarkerInit == null) {
            mMarkerInit = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title("Start")
                    .snippet("Descripcion")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow))
                    .draggable(true));
            mInfoFragment.setText("tapped, point=" + mMarkerInit.getPosition(), 0);
        } else if (mMarketFinal == null) {
            mMarketFinal = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title("End")
                    .snippet("Descripcion")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_flag))
                    .draggable(true));
        } else {
            Toast.makeText(getApplicationContext(),
                    "Already created Init and finish route",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onMapLongClick(LatLng point) {
        mInfoFragment.setText("long pressed, point=" + point, 0);
    }

    @Override
    public void onCameraChange(final CameraPosition position) {
        mInfoFragment.setText(position.toString(), 1);
    }

    /**
     * Callback Fragment Info
     * @param uri
     */
    @Override
    public void onFragmentInteractionInfo(Uri uri) {

    }

    /**
     * Callback Fragment Pallete. Called when click in Spinner to select layerMode
     * @param layerName MAP_TYPE_NORMAL, MAP_TYPE_HYBRID, MAP_TYPE_SATELLITE,
     *                  MAP_TYPE_TERRAIN, MAP_TYPE_NONE
     */
    @Override
    public void onFragmentInteractionPalette(String layerName) {
        MapsUtils.setLayerMap(getApplicationContext(), mMap, layerName);
    }

    /**
     * Implementation of {@link LocationListener}.
     */
    @Override
    public void onLocationChanged(Location location) {
        // TODO: POSITION ACTUAL
        Toast.makeText(getApplicationContext(),location.toString(),Toast.LENGTH_SHORT).show();
    }

    /**
     * Callback called when connected to GCore. Implementation of {@link GoogleApiClient.ConnectionCallbacks}.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                MapsUtils.REQUEST,
                this);  // LocationListener
    }

    /**
     * Callback called when disconnected from GCore. Implementation of {@link GoogleApiClient.ConnectionCallbacks}.
     */
    @Override
    public void onConnectionSuspended(int cause) {
        // Do nothing
    }

    /**
     * Implementation of {@link GoogleApiClient.OnConnectionFailedListener}.
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Do nothing
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        // TODO: qUE HACE?
        // This causes the marker at Perth to bounce into position when it is clicked.
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 2 * t);

                if (t > 0.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
            marker.showInfoWindow();
        return false;
    }
}
