package com.agusgarcia.geonotes;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngZoom;
import com.mapbox.mapboxsdk.views.MapView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private static final String TAG = "MapFragment";

    MapView mapView = null;

    public MapFragment() {
        // Required empty public constructor
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

}
