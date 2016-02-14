package com.agusgarcia.geonotes;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agusgarcia.geonotes.Notes.DataManager;
import com.agusgarcia.geonotes.Notes.Note;
import com.agusgarcia.geonotes.Notes.NoteAdapter;
import com.mapbox.mapboxsdk.geometry.LatLng;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements DataManager.NotesListener {

    private static final String TAG = "ListFragment";
    NoteAdapter mNoteAdapter;
    List<Note> mNotes;
    RecyclerView recyclerView;

     static boolean seeNote = false;
     static LatLng seeNotePos;

    public ListFragment() {
        DataManager.loadAll(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mNoteAdapter = new NoteAdapter(mNotes);
        recyclerView.setAdapter(mNoteAdapter);

        mNoteAdapter.setNoteDeleteClickListener(new NoteAdapter.NoteDeleteClickListener() {

            @Override
            public void deleteNoteOnClickHandler(int position) {

                Note note = mNotes.get(position);
                Long noteId = note.getId();
                Note noteToDelete = Note.findById(Note.class, noteId);

                mNoteAdapter.delete(noteToDelete);
                mNotes.remove(position);

                Snackbar.make(getView(), "Your note has been deleted.", Snackbar.LENGTH_SHORT).show();

            }
        });

        mNoteAdapter.setNoteSeeListener(new NoteAdapter.NoteSeeListener() {

            @Override
            public void seeNoteHandler(int position) {

                Note note = mNotes.get(position);
                Long noteId = note.getId();
                Note noteToSee = Note.findById(Note.class, noteId);

                MapFragment.seeNoteLocation = true;
                seeNote = true;
                seeNotePos = new LatLng(noteToSee.getLat(), noteToSee.getLng());

                Context context = view.getContext();
                context.startActivity(new Intent(context, MapActivity.class));
            }
        });

        return view;
    }

    public void updateView() {
        DataManager.loadAll(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    public void onAllNotesLoaded(List<Note> notes) {
        mNotes = notes;
        mNoteAdapter.notifyDataSetChanged();
    }
}
