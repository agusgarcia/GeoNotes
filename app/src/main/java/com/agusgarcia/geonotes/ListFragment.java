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

        mNoteAdapter = new NoteAdapter();
        recyclerView.setAdapter(mNoteAdapter);

        mNoteAdapter.setNoteClickListener(new NoteAdapter.NoteClickListener() {
            @Override
            public void onClick(int position, View v) {
                Log.d(TAG, "clicked pos :" + position);
            }
        });

        mNoteAdapter.setNoteAddListener(new NoteAdapter.NoteAddListener() {

            @Override
            public void addNoteHandler(Note note) {
                Log.d(TAG, "mNotes size 1" + mNotes.size());
                Log.d(TAG, "mNotes size 2" + mNotes.size());
                mNotes.add(note);

                //PAS COMME çA !
                mNoteAdapter.mNotes = mNotes;

                mNoteAdapter.notifyItemInserted(mNoteAdapter.getItemCount() - 1);
                // recyclerView.setAdapter(mNoteAdapter);
            }
        });

        mNoteAdapter.setNoteDeleteClickListener(new NoteAdapter.NoteDeleteClickListener() {

            @Override
            public void deleteNoteOnClickHandler(int position) {

                Log.d(TAG, "delete note");

                Note note = mNotes.get(position);
                Long noteId = note.getId();
                Note noteToDelete = Note.findById(Note.class, noteId);
                Log.d(TAG, "click on position " + position);
                Log.d(TAG, "click on id " + noteId);
                Log.d(TAG, "click on " + note);
                mNoteAdapter.delete(noteToDelete);

                mNotes.remove(position);
                Snackbar.make(getView(), "Your note has been deleted.", Snackbar.LENGTH_SHORT).show();

            }
        });

        mNoteAdapter.setNoteSeeListener(new NoteAdapter.NoteSeeListener() {


            @Override
            public void seeNoteHandler(int position) {

                Log.d(TAG, "see note");
                Note note = mNotes.get(position);
                Long noteId = note.getId();
                Note noteToSee = Note.findById(Note.class, noteId);
                Log.d(TAG, "click on position " + position);
                Log.d(TAG, "click on id " + noteId);
                Log.d(TAG, "click on " + note);

                MapFragment.seeNoteLocation = true;
                seeNote = true;
                seeNotePos = new LatLng(noteToSee.getLat(), noteToSee.getLng());

                Context context = view.getContext();
                context.startActivity(new Intent(context, MapActivity.class));
            }
        });

        Log.d(TAG, "here");
        return view;
    }

    public void updateView() {

        DataManager.loadAll(this);
        Log.d(TAG, "dataManager called");

        //recyclerView.setAdapter(mNoteAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateView();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onAllNotesLoaded(List<Note> notes) {
        Log.d(TAG, "on all notes");
        mNotes = notes;
        mNoteAdapter.notifyDataSetChanged();
    }
}
