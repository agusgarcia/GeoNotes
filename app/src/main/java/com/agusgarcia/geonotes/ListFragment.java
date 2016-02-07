package com.agusgarcia.geonotes;


import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.agusgarcia.geonotes.Notes.DataManager;
import com.agusgarcia.geonotes.Notes.Note;
import com.agusgarcia.geonotes.Notes.NoteAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private static final String TAG = "ListFragment";
    NoteAdapter mNoteAdapter;
    List<Note> notes;
    RecyclerView recyclerView;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mNoteAdapter = new NoteAdapter();

        mNoteAdapter.setNoteClickListener(new NoteAdapter.NoteClickListener() {
            @Override
            public void onClick(int position, View v) {
                Log.d(TAG, "clicked");
            }
        });


        updateView();

        //Note note;

        // note = new Note("Title: " + "title", "Description..........", "Date", "Location");
        // note.save();

        //notes.add(new Note("Ttl", "Desc", Calendar.getInstance().getTime(), "location"));

        Log.d(TAG, "here");
        return view;
    }

    public void updateView() {
        recyclerView.setAdapter(new NoteAdapter());
    }


    @Override
    public void onResume() {
        super.onResume();
        updateView();
        Log.d(TAG, "onResume");
    }
}
