package com.agusgarcia.geonotes.Notes;

import android.os.AsyncTask;

import java.util.List;

public class DataManager {

    public interface NotesListener {
        void onAllNotesLoaded(List<Note> notes);
    }

    public static void loadAll(final NotesListener mListener) {
        class NotesAsyncTask extends AsyncTask<Void, Void, Void> {
            List<Note> notes;

            @Override
            protected Void doInBackground(Void... params) {
                notes = Note.listAll(Note.class);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mListener.onAllNotesLoaded(notes);
            }
        }

        new NotesAsyncTask().execute();
    }
}
