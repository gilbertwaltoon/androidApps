package com.example.clickablerecycler.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.clickablerecycler.models.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    long[] insertNotes(Note... notes);

    // "Select all from the table called notes"
    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

// Example custom query "Select all from notes where title is like..."
    @Query("SELECT * FROM notes where title LIKE :title")
    List<Note> getNoteWithCustomQuery(String title);


    @Delete
    int delete(Note... notes);


    @Update
    int update(Note... notes);

}
