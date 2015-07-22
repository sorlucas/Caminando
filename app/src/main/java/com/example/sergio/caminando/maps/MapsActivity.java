package com.example.sergio.caminando.maps;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.util.MapsUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity
        implements
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnCameraChangeListener,
        OnMapReadyCallback,
        RouteInfoFragment.OnFragmentInfoListener,
        RoutePaletteFragment.OnFragmentPaletteListener {

    private GoogleMap mMap;

    private RouteInfoFragment mInfoFragment;
    private RoutePaletteFragment mPaletteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mInfoFragment = (RouteInfoFragment) getSupportFragmentManager().findFragmentById(R.id.information_fragment);
        mPaletteFragment = (RoutePaletteFragment) getSupportFragmentManager().findFragmentById(R.id.palette_fragment);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Update mTrafficCheckbox and setMyLocationEnabled. Initial functions
        mMap = MapsUtils.updateAllTypesOfViews(this, map);

        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnCameraChangeListener(this);
    }

    @Override
    public void onMapClick(LatLng point) {
        mInfoFragment.setText("tapped, point=" + point, 0);
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
        MapsUtils.setLayerMap(getApplicationContext(),mMap,layerName);
    }
}
