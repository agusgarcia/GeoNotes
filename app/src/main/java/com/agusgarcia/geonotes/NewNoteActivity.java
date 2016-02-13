
package com.agusgarcia.geonotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.agusgarcia.geonotes.Notes.Note;
import com.agusgarcia.geonotes.Notes.NoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewNoteActivity extends AppCompatActivity {

    private List<Note> mNotes = new ArrayList<>();
    NoteAdapter mNoteAdapter;
    public static String title;
    public static String description;
    public static boolean isMapClickable = false;
    public static boolean addNewMarker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("New note");

    }

    public void createNote(View view) {

        EditText editTitle = (EditText) findViewById(R.id.edit_title);
        title = editTitle.getText().toString();

        EditText editDescription = (EditText) findViewById(R.id.edit_description);
        description = editDescription.getText().toString();

        /*
        Log.d("new title", title);
        Note note;
        note = new Note("Title: " + title, "Description..........", "Date", "Location");
        note.save();
        */


        chooseLocation();
    }

    private void chooseLocation() {

        new MaterialDialog.Builder(this)
                .title("Choose location")
                .items("On the map", "Current location")
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        switch (which) {
                            case 0:
                                Log.d("case", "On the map");
                                Log.d("newM4", Boolean.toString(addNewMarker));
                                isMapClickable = true;
                                addNewMarker = true;
                                Log.d("newM3", Boolean.toString(addNewMarker));
                                Intent mapIntent = new Intent(NewNoteActivity.this, MapActivity.class);
                                NewNoteActivity.this.startActivity(mapIntent);


                                break;
                            case 1:
                                Log.d("case", "Current location");
                                isMapClickable = false;
                                addNewMarker = true;
                                Intent locIntent = new Intent(NewNoteActivity.this, MapActivity.class);
                                NewNoteActivity.this.startActivity(locIntent);
                                break;
                        }

                        return true;
                    }
                })
                .positiveText("Choose")
                .show();
    }


}
