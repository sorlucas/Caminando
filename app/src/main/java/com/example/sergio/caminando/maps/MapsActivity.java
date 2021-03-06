package com.example.sergio.caminando.maps;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.util.MapsUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
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
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback,
        RouteInfoFragment.OnFragmentInfoListener,
        RoutePaletteFragment.OnFragmentPaletteListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = makeLogTag(MapsActivity.class);

    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;

    private RouteInfoFragment mInfoFragment;

    private Marker mMarkerInit;
    private Marker mMarketFinal;

    private FloatingActionButton mFloatButtonOK;
    
    private Location mActualLocation;

    private String mCityNameInit;
    private String mDistanceRoute;
    private String mElevationRoute;
    private String mCityNameFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mInfoFragment = (RouteInfoFragment) getSupportFragmentManager().findFragmentById(R.id.information_fragment);
        mFloatButtonOK = (FloatingActionButton) findViewById(R.id.btnFloatingActionCreate);

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

            // TODO: Implement real CityName
            mCityNameInit = "Implement";

            mInfoFragment.setText(mCityNameInit, 0);
        } else if (mMarketFinal == null) {
            mMarketFinal = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title("End")
                    .snippet("Descripcion")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_flag))
                    .draggable(true));

            // TODO: Implement real city name final
            mCityNameFinal = "implement";
            mInfoFragment.setText(mCityNameFinal, 3);

            mDistanceRoute = MapsUtils.getDistanceRouteNoReal(mMarkerInit.getPosition(), mMarketFinal.getPosition());
            mInfoFragment.setText(mDistanceRoute, 1);

            // TODO: Implement real elevation.
            mElevationRoute = "En construcion";
            mInfoFragment.setText(mElevationRoute, 2);

            checkButtonOk();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Already created Init and finish route",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onMapLongClick(LatLng point) {
        //TODO: Implement onMapLongClick
    }

    @Override
    public void onCameraChange(final CameraPosition position) {
        //TODO: Implement onCameraChange
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

    @Override
    public void onChangeCameraView(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    /**
     * Implementation of {@link LocationListener}.
     */
    @Override
    public void onLocationChanged(Location location) {
        // TODO: POSITION ACTUAL
        // To go in mapReady de first place
        if (mActualLocation == null)    {
            mActualLocation = location;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mActualLocation.getLatitude(), mActualLocation.getLongitude())
                    , 15));
            ;
            getSupportFragmentManager().beginTransaction().add(R.id.palette_fragment,
                    RoutePaletteFragment.newInstance(location.getLatitude(),location.getLongitude())).commit();
        }


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
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
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

    private void checkButtonOk () {
        if (mMarkerInit.getPosition() != null && mMarketFinal.getPosition() != null) {
                mFloatButtonOK.setVisibility(View.VISIBLE);
        } else {
            mFloatButtonOK.setVisibility(View.GONE);
        }
    }

    public void cancel(View view) {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    public void createRoute (View view) {
        Double lat_init = mMarkerInit.getPosition().latitude;
        Double lon_init = mMarkerInit.getPosition().longitude;

        Double lat_final = mMarketFinal.getPosition().latitude;
        Double lon_final = mMarketFinal.getPosition().longitude;

        // Create intent to result data points to Parent activity
        Intent returnIntent = new Intent();
        // TODO: Change to Routecontract
        returnIntent.putExtra("city_name_init", mCityNameInit);
        returnIntent.putExtra("lat_init", lat_init);
        returnIntent.putExtra("lon_init", lon_init);
        returnIntent.putExtra("city_name_final", mCityNameFinal);
        returnIntent.putExtra("lat_final", lat_final);
        returnIntent.putExtra("lon_final", lon_final);
        returnIntent.putExtra("distance", mDistanceRoute);
        returnIntent.putExtra("elevation", mElevationRoute);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
