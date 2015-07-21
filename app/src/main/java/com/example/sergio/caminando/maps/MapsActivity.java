package com.example.sergio.caminando.maps;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.sergio.caminando.R;
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
        RouteInfoFragment.OnFragmentInteractionListener {

    private RouteInfoFragment mInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mInfoFragment = (RouteInfoFragment) getSupportFragmentManager().findFragmentById(R.id.information_fragment);
    }

    @Override
    public void onMapReady(GoogleMap map) {
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
