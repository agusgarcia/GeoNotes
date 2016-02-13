package com.agusgarcia.geonotes.Notes;

import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.agusgarcia.geonotes.ListFragment;
import com.agusgarcia.geonotes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements DataManager.NotesListener {

    private static final String TAG = "NoteAdapter";
    public List<Note> mNotes = new ArrayList<>();
    private static NoteClickListener mListener;
    private static NoteDeleteClickListener mListenerDel;
    private static NoteAddListener mListenerAdd;

    public NoteAdapter() {
        DataManager.loadAll(this);
    }

    @Override
    public void onAllNotesLoaded(List<Note> notes) {
        mNotes = notes;

        for (final Note note : notes) {
            Log.d(TAG, note.toString());
        }

        notifyDataSetChanged();
        Log.d(TAG, "onAllNotesLoaded");
    }

    public void delete(Note note) {
        note.delete();
        // mNotes.remove(note);
        DataManager.loadAll(this);
    }

    public void add(Note note) {
        note.save();
        String toString = note.toString();
        Log.d(TAG, toString);
        //add(note);
        //  mNotes.add(getItemCount()-1, note);
        mListenerAdd.addNoteHandler(note);
        Log.d(TAG, "add note");
        DataManager.loadAll(this);

    }


    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mNoteTitle;
        private final TextView mNoteDescription;
        private final Button mButtonDelete;

        public NoteViewHolder(View itemView) {
            super(itemView);
            mNoteTitle = (TextView) itemView.findViewById(R.id.note_title);
            mNoteDescription = (TextView) itemView.findViewById(R.id.note_description);

            mButtonDelete = (Button) itemView.findViewById(R.id.note_delete);
            mButtonDelete.setTag(itemView.getId());

            mButtonDelete.setOnClickListener(this);

            itemView.setOnClickListener(this);
//            itemView.setOnCreateContextMenuListener(this);
        }


        public TextView getNoteTitle() {
            return mNoteTitle;
        }

        public TextView getNoteDescription() {
            return mNoteDescription;
        }


        @Override
        public void onClick(View v) {
            Log.d(TAG, "on click: " + v);
            if (mListener == null && mListenerDel == null) {
                return;
            }

            int id = v.getId();

            switch (id) {
                case R.id.note:
                    mListener.onClick(getAdapterPosition(), v);
                case R.id.note_delete:
                    mListenerDel.deleteNoteOnClickHandler(getAdapterPosition());

                    Log.d(TAG + " adptr pos:", Integer.toString(getAdapterPosition()));
                    // mListenerDel.deleteNoteOnClickHandler();
            }
        }

      /*  @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuItem myActionItem = menu.add("Some menu item");
            myActionItem.setOnMenuItemClickListener(this);

            menu.setHeaderTitle("Select The Action");
            menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
            menu.add(0, v.getId(), 0, "SMS");
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            // Menu Item Clicked!
            Log.d("TAG", "menu item clicked");
            return true;
        }*/
    }

    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NoteViewHolder holder;
        View itemView = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.fragment_note, parent, false);
        holder = new NoteViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.getNoteTitle().setText(note.getTitle());
        holder.getNoteDescription().setText(note.getDescription());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void setNoteClickListener(NoteClickListener listener) {
        mListener = listener;
    }

    public void setNoteDeleteClickListener(NoteDeleteClickListener listener) {
        mListenerDel = listener;
    }

    public void setNoteAddListener(NoteAddListener listener) {
        mListenerAdd = listener;
    }

    public interface NoteClickListener {
        void onClick(int position, View v);
    }

    public interface NoteDeleteClickListener {
        void deleteNoteOnClickHandler(int position);
    }

    public interface NoteAddListener {
        void addNoteHandler(Note note);
    }
}
