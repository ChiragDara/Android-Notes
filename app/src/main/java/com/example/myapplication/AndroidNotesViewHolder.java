package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AndroidNotesViewHolder extends RecyclerView.ViewHolder{
    TextView notesTitle, noteDate , noteText;

    public AndroidNotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notesTitle = itemView.findViewById(R.id.notesTitle);
        noteText = itemView.findViewById(R.id.noteText);
        noteDate = itemView.findViewById(R.id.noteDate);
    }
}
