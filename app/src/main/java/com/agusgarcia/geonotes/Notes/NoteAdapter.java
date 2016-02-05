package com.agusgarcia.geonotes.Notes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agusgarcia.geonotes.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private static final String TAG = "NoteAdapter";
    private List<Note> mNotes;


    //Constructor with arguments (a list of notes)
    public NoteAdapter(List<Note> notes) {
        mNotes = notes;
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

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNoteTitle;
        private final TextView mNoteDescription;

        public NoteViewHolder(View itemView) {
            super(itemView);
            mNoteTitle = (TextView) itemView.findViewById(R.id.note_title);
            mNoteDescription = (TextView) itemView.findViewById(R.id.note_description);
            //itemView.setOnClickListener(this);
        }

        public TextView getNoteTitle() {
            return mNoteTitle;
        }

        public TextView getNoteDescription() {
            return mNoteDescription;
        }
    }
}
