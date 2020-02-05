package com.cyberwith.room.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = NoteEntity.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NoteDatabase noteDatabase;

    public static NoteDatabase getDatabase(final Context context){
        if (noteDatabase == null){
            synchronized (NoteDatabase.class){
                if (noteDatabase == null){
                    noteDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "note_database")
                            .build();
                }
            }
        }
        return noteDatabase;
    }

}
