package com.example.clickablerecycler.persistance;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.clickablerecycler.async.InsertAsyncTask;
import com.example.clickablerecycler.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context){
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note));
    }

    public void updateNote(Note note){
    }

    public LiveData<List<Note>> retrieveNotesTask(){
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote(Note note){
    }

}
