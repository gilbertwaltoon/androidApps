package com.example.clickablerecycler.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clickablerecycler.R;
import com.example.clickablerecycler.models.Note;

import java.util.ArrayList;

// template here passes in the view holder class below
public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {

    private ArrayList<Note> mNotes = new ArrayList<>();
    private OnNoteListener mOnNoteListener;

    // constructor
    public NotesRecyclerAdapter(ArrayList<Note> mNotes, OnNoteListener mOnNoteListener) {
        this.mNotes = mNotes;
        this.mOnNoteListener = mOnNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note_list_item, viewGroup, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.timestamp.setText(mNotes.get(position).getTimestamp());
        viewHolder.title.setText(mNotes.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, timestamp;
        OnNoteListener onNoteListener;

       public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
           super(itemView);
           title = itemView.findViewById(R.id.note_title);
           timestamp = itemView.findViewById(R.id.note_timestamp);
           this.onNoteListener = onNoteListener;
           itemView.setOnClickListener(this);
       }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAbsoluteAdapterPosition());
        }
    }

   public interface OnNoteListener{
        void onNoteClick(int position);
   }
}
