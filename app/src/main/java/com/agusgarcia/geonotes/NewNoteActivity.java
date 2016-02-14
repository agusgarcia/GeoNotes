
package com.agusgarcia.geonotes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.agusgarcia.geonotes.Notes.Note;
import com.agusgarcia.geonotes.Notes.NoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewNoteActivity extends AppCompatActivity {

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
        toolbar.setTitle("Add new note");

    }

    public void createNote(View view) {

        EditText editTitle = (EditText) findViewById(R.id.edit_title);
        title = editTitle.getText().toString();

        EditText editDescription = (EditText) findViewById(R.id.edit_description);
        description = editDescription.getText().toString();

        if (MapFragment.isMapLongClickable) {
            launchMapActivity();

            //If we have made a long click, the position is already set
            //So we don't show the chooseLocation dialog
            return;
        }

        chooseLocation();
    }

    private void chooseLocation() {

        //Show a dialog to choose the location of the new note
        new MaterialDialog.Builder(this)
                .title("Choose location")
                .items("On the map", "Current location")
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        switch (which) {
                            case 0:
                                isMapClickable = true;
                                addNewMarker = true;
                                launchMapActivity();

                                break;
                            case 1:
                                isMapClickable = false;
                                addNewMarker = true;
                                launchMapActivity();

                                break;
                        }

                        return true;
                    }
                })
                .positiveText("Choose")
                .show();
    }

    public void launchMapActivity() {
        Intent intent = new Intent(NewNoteActivity.this, MapActivity.class);
        NewNoteActivity.this.startActivity(intent);
    }


}
