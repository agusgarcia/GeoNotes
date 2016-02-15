package com.agusgarcia.geonotes;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements LocationListener, DataManager.NotesListener {

    private static final String TAG = "MapFragment";

    protected MapView mapView;

    protected NoteAdapter mNoteAdapter;
    private List<Note> mNotes = new ArrayList<>();
    protected Location mLastLocation = new Location("");
    protected Double locationLat;
    protected Double locationLon;

    protected boolean isMapClickable;
    protected boolean markerSet = false;

    protected static boolean isMapLongClickable = false;
    protected static LatLng longClickLocation;
    protected boolean addNewMarker = false;

    protected LatLng seeNotePos = ListFragment.seeNotePos;
    public String city;

    protected Marker currentPositionMarker;

    protected static boolean seeCurrentLocation;
    protected static boolean seeLongClickLocation;
    protected static boolean seeNoteLocation;

    public MapFragment() {
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mNoteAdapter = new NoteAdapter(mNotes);

        mapView = (MapView) view.findViewById(R.id.map_view);

        mapView.setAccessToken(BuildConfig.MAPBOX_ACCESS_TOKEN);

        mapView.setStyleUrl(Style.MAPBOX_STREETS);

        if (addNewMarker) {
            markerSet = false;
            addMarker();
        }
        if (isMapLongClickable) {
            markerSet = true;
            addMarker();
        }

        mapView.setOnMapLongClickListener(new MapView.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng location) {

                isMapLongClickable = true;
                seeLongClickLocation = true;

                longClickLocation = location;

                Intent intent = new Intent(getActivity(), NewNoteActivity.class);
                startActivity(intent);
            }
        });

        mapView.onCreate(savedInstanceState);

        return view;
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        handleNewLocation(location);
    }

    private void addMarker() {
        //If map is clickable and we haven't added a marker yet, set click listener
        if (isMapClickable && !markerSet) {
            mapView.setOnMapClickListener(new MapView.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    if (!markerSet) {
                        addNewMarker(latLng);
                    }
                }
            });
        } else if (isMapLongClickable) {
            //If we have made a long click...
            if (longClickLocation == null) {
                return;
            }
            // ..and long click location is not null
            //We add a new marker

            addNewMarker(longClickLocation);
            isMapLongClickable = false;

        } else {
            //If click listener is not enabled and we haven't made a long click
            //We add a marker on current (last detected) position

            LatLng mLastLocationLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            addNewMarker(mLastLocationLatLng);
        }
    }

    private void addNewMarker(LatLng location) {
        //Get the current date
        final String date = new SimpleDateFormat("EEE, dd MMM yyyy").format(new Date());

        //Get the city of the location
        Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            city = addresses.get(0).getLocality();
        }

        LatLng latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());

        //Add the new marker on the map
        mapView.addMarker(new MarkerOptions()
                .position(latLngLocation)
                .title(NewNoteActivity.title)
                .snippet(NewNoteActivity.description + System.getProperty("line.separator") + date));

        //Create de note
        Note note = new Note(
                NewNoteActivity.title,
                NewNoteActivity.description,
                date,
                location.getLatitude(),
                location.getLongitude());

        note.setCity(city);

        if (getView() != null) {
            Snackbar.make(getView(), "Your note has been added.", Snackbar.LENGTH_SHORT).show();
        }

        note.save();

        mNoteAdapter.add(note);

        markerSet = true;
    }

    private void handleNewLocation(Location location) {
        //On position changes, update the marker position
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        if (currentPositionMarker != null) {
            mapView.removeMarker(currentPositionMarker);
        }

        IconFactory mIconFactory = IconFactory.getInstance(this.getContext());
        Drawable mIconDrawable = ContextCompat.getDrawable(this.getContext(), R.drawable.ic_place_black_36dp);
        Icon icon = mIconFactory.fromDrawable(mIconDrawable);

        currentPositionMarker = mapView.addMarker(new MarkerOptions()
                .icon(icon)
                .position(latLng)
                .title("You're here !"));
    }

    public void setPosition() {
        //If we have clicked on "see note on the map"
        // we center the view on the marker of the note
        if (seeNoteLocation) {
            mapView.setLatLng(new LatLngZoom(seeNotePos, 7));
            Log.d(TAG, "seeNoteLocation");
        } else if (seeLongClickLocation) {
            //if we have long clicked, we set the position of the map
            // on the position of the new note
            mapView.setLatLng(new LatLngZoom(longClickLocation, 7));
            Log.d(TAG, "seeLongClickLocation");
        } else {
            //otherwise, we set the view on current position
            mapView.setLatLng(new LatLngZoom(locationLat, locationLon, 7));
            Log.d(TAG, "else");
        }

        seeNoteLocation = false;
        seeLongClickLocation = false;
        seeCurrentLocation = false;
    }

    @Override
    public void onAllNotesLoaded(List<Note> notes) {

        //load all notes and markers
        mNotes = notes;

        for (Note note : mNotes) {
            String title = note.getTitle();
            String description = note.getDescription();
            String date = note.getDate();
            Double lat = note.getLat();
            Double lng = note.getLng();

            mapView.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title(title)
                    .snippet(description + System.getProperty("line.separator") + date));
        }
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
        setPosition();
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
}
