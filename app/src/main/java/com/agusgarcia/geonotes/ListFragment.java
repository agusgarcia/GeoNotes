package com.agusgarcia.geonotes;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    NoteAdapter mNoteAdapter;
    List<Note> notes;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Note note;

        for (int i = 0; i < 1; i++) {
            note = new Note("Title: " + i, "Description..........", "Date", "Location");
            note.save();
        }

        //notes.add(new Note("Ttl", "Desc", Calendar.getInstance().getTime(), "location"));

        recyclerView.setAdapter(new NoteAdapter());

        return view;
    }

}