package com.agusgarcia.geonotes;


import android.location.Location;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agusgarcia.geonotes.Notes.DataManager;
import com.agusgarcia.geonotes.Notes.Note;
import com.google.android.gms.location.LocationListener;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngZoom;
import com.mapbox.mapboxsdk.views.MapView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements LocationListener, DataManager.NotesListener {

    private static final String TAG = "MapFragment";
    MapView mapView;

    private List<Note> mNotes = new ArrayList<>();

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

        DataManager.loadAll(this);

        mapView = (MapView) view.findViewById(R.id.map_view);

        mapView.setAccessToken(BuildConfig.MAPBOX_ACCESS_TOKEN);

        mapView.setStyleUrl(Style.MAPBOX_STREETS);

        if (mLastLocation != null) {
            locationLat = mLastLocation.getLatitude();
            locationLon = mLastLocation.getLongitude();
        }
        mapView.setLatLng(new LatLngZoom(locationLat, locationLon, 7));

        mapView.onCreate(savedInstanceState);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        Log.d(TAG, "onLocationChanged" + mLastLocation.toString());

        handleNewLocation(location);

    }

    private void handleNewLocation(Location location) {

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        mapView.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Hello World! :)")
                .snippet("I'm here!"));

        mapView.setLatLng(latLng);

    }

    @Override
    public void onAllNotesLoaded(List<Note> notes) {
        mNotes = notes;
        //notifyDataSetChanged();
    }

}
