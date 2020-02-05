package com.cyberwith.room.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertNote(NoteEntity noteEntity);

    @Query("select * from notes")
    LiveData<List<NoteEntity>> getAllNote(); //for view data

    @Query("select * from notes where id=:noteID")// for view single row
    LiveData<NoteEntity> getNote(String noteID);

    @Update
    void Update(NoteEntity noteEntity);// for update row

    @Delete
    int deleteNote(NoteEntity noteEntity);

}
