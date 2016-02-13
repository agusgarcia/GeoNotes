package com.agusgarcia.geonotes;


import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agusgarcia.geonotes.Notes.DataManager;
import com.agusgarcia.geonotes.Notes.Note;
import com.agusgarcia.geonotes.Notes.NoteAdapter;
import com.google.android.gms.location.LocationListener;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngZoom;
import com.mapbox.mapboxsdk.views.MapView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements LocationListener, DataManager.NotesListener {

    private static final String TAG = "MapFragment";
    MapView mapView;

    private List<Note> mNotes = new ArrayList<>();

    protected Location mLastLocation = new Location("");

    protected Double locationLat;
    protected Double locationLon;

    protected boolean isMapClickable;
    protected boolean addNewMarker;

    protected boolean seeNote = ListFragment.seeNote;
    protected LatLng seeNotePos = ListFragment.seeNotePos;

    boolean markerSet = false;

    public String city;

    Marker currentPositionMarker;


    NoteAdapter mNoteAdapter;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataManager.loadAll(this);

        if (getArguments() != null) {
            locationLat = getArguments().getDouble("locationLat");
            locationLon = getArguments().getDouble("locationLon");
            isMapClickable = getArguments().getBoolean("clickableMap");
            addNewMarker = getArguments().getBoolean("newMarker");
        }

        mLastLocation.setLatitude(locationLat);
        mLastLocation.setLongitude(locationLon);

        Log.d("longLat", locationLat + " " + locationLon);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mNoteAdapter = new NoteAdapter();

        mapView = (MapView) view.findViewById(R.id.map_view);

        mapView.setAccessToken(BuildConfig.MAPBOX_ACCESS_TOKEN);

        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        /*
        if (mLastLocation != null) {
            locationLat = mLastLocation.getLatitude();
            locationLon = mLastLocation.getLongitude();
        }
        */
        mapView.setLatLng(new LatLngZoom(locationLat, locationLon, 7));
        if (addNewMarker) {
            addMarker();
        }

        if (seeNote) {
            Log.d(TAG, "see note true");
            Log.d(TAG, "see note" + seeNotePos);
            mapView.setLatLng(seeNotePos);
            ListFragment.seeNote = false;

        } else {
            Log.d(TAG, "see note false");
        }
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
        Log.d("onResume", locationLat + " " + locationLon);
        mapView.setLatLng(new LatLngZoom(locationLat, locationLon, 7));
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

    private void addMarker() {
        final String date = new SimpleDateFormat("EEE, dd MMM yyyy").format(new Date());

        if (isMapClickable) {

            mapView.setOnMapClickListener(new MapView.OnMapClickListener() {

                @Override
                public void onMapClick(@NonNull LatLng latLng) {

                    Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(latLng.getLatitude(), latLng.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses.size() > 0) {
                        Log.d(TAG, addresses.get(0).getLocality());
                        city = addresses.get(0).getLocality();
                    }

                    Log.d(TAG, latLng.getLatitude() + " " + latLng.getLongitude());
                    Log.d(TAG, NewNoteActivity.title);

                    if (!markerSet) {
                        mapView.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(NewNoteActivity.title)
                                .snippet(NewNoteActivity.description + System.getProperty("line.separator") + date));

                        Note note = new Note(
                                NewNoteActivity.title,
                                NewNoteActivity.description,
                                date,
                                latLng.getLatitude(),
                                latLng.getLongitude());
                        note.setCity(city);
                        mNoteAdapter.add(note);
                        markerSet = true;
                    }
                }
            });
        } else {

            Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses.size() > 0) {
                Log.d(TAG, addresses.get(0).getLocality());
                city = addresses.get(0).getLocality();
            }

            Log.d(TAG, "on current location");

            LatLng latLngLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            mapView.addMarker(new MarkerOptions()
                    .position(latLngLocation)
                    .title(NewNoteActivity.title)
                    .snippet(NewNoteActivity.description + System.getProperty("line.separator") + date ));

            Note note = new Note(
                    NewNoteActivity.title,
                    NewNoteActivity.description,
                    date,
                    mLastLocation.getLatitude(),
                    mLastLocation.getLongitude());

            note.setCity(city);

            mNoteAdapter.add(note);
            markerSet = true;

        }
    }

    private void handleNewLocation(Location location) {

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);


        if (currentPositionMarker != null) {
            mapView.removeMarker(currentPositionMarker);
        } else {
            if (!seeNote) {
                mapView.setLatLng(new LatLngZoom(currentLatitude, currentLongitude, 7));
            }
        }

        IconFactory mIconFactory = IconFactory.getInstance(this.getContext());
        Drawable mIconDrawable = ContextCompat.getDrawable(this.getContext(), R.drawable.ic_place);
        Icon icon = mIconFactory.fromDrawable(mIconDrawable);

        currentPositionMarker = mapView.addMarker(new MarkerOptions()
                .icon(icon)
                .position(latLng)
                .title("You're here !"));
    }

    @Override
    public void onAllNotesLoaded(List<Note> notes) {
        // mapView.removeAllAnnotations();
        mNotes = notes;
        int i = 1;
        for (Note note : mNotes) {
            String title = note.getTitle();
            String description = note.getDescription();
            Log.d("the notes", note.getLat().toString());
            Log.d("the notes", title);
            Log.d("the notes", description);
            Log.d("the notes", note.getLng().toString());
            Log.d("the notes", note.getDate() + "");
            String date = note.getDate();

            Double lat = note.getLat();
            Double lng = note.getLng();

            Log.d("noteLatLng", i + " " + title);
            Log.d("noteLatLng", lat + " " + lng);

          /*  mapView.addMarker(new MarkerOptions()
                    .position(new LatLng(0.5d * i, 3.8d * i))
                    // .position(noteLatLng)
                    .title(title)
                    .snippet(description)); */

            mapView.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    // .position(noteLatLng)
                    .title(title)
                    .snippet(description + System.getProperty("line.separator") + date));

            i++;

            mapView.setLatLng(new LatLng(lat, lng));

            //get each note and add the marker
        }

     /*   mapView.addMarker(new MarkerOptions()
                .position(new LatLng(0.5d, 3.8d))
                .title("title")
                .snippet("description"  ));
*/
        //notifyDataSetChanged();
    }

}
