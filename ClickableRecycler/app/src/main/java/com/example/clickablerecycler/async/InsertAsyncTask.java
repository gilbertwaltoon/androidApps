package com.example.clickablerecycler.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.clickablerecycler.models.Note;
import com.example.clickablerecycler.persistance.NoteDAO;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void>{

    private NoteDAO mNoteDAO;

    private static final String TAG = "InsertAsyncTask";

    public InsertAsyncTask(NoteDAO dao) {
        mNoteDAO = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: thread: " + Thread.currentThread().getName());
        mNoteDAO.insertNotes(notes);
        return null;
    }


}
