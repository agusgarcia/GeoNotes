package com.agusgarcia.geonotes;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationListener;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngZoom;
import com.mapbox.mapboxsdk.views.MapView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements LocationListener {

    private static final String TAG = "MapFragment";
    MapView mapView = null;

    protected Location mLastLocation;

    protected Double locationLat;
    protected Double locationLon;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            locationLat = getArguments().getDouble("locationLat");
            locationLon = getArguments().getDouble("locationLon");
        }
        Log.d("longLat", locationLat.toString() + " " + locationLon.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) view.findViewById(R.id.map_view);

        mapView.setAccessToken(BuildConfig.MAPBOX_ACCESS_TOKEN);

        mapView.setStyleUrl(Style.MAPBOX_STREETS);

        mapView.setLatLng(new LatLngZoom(2.13726, 11.57603, 5));

        mapView.onCreate(savedInstanceState);

        Log.d("onCreateMapFragment", "here");

        return view;
    }


    @Override
    public void onStart() {
        mapView.onStart();
        super.onStart();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        Log.d(TAG, "onLocationChanged" + mLastLocation.toString());
    }
}
