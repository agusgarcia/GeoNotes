
package com.agusgarcia.geonotes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.agusgarcia.geonotes.Notes.DataManager;
import com.agusgarcia.geonotes.Notes.Note;
import com.agusgarcia.geonotes.Notes.NoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewNoteActivity extends AppCompatActivity {

    private List<Note> mNotes = new ArrayList<>();
    NoteAdapter mNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("New note");

    }

    public void createNote(View view) {
        String title;
        EditText editTitle = (EditText) findViewById(R.id.edit_title);
        title = editTitle.getText().toString();


        Log.d("new title", title);
        Note note;
        note = new Note("Title: " + title, "Description..........", "Date", "Location");
        note.save();

    }


}
