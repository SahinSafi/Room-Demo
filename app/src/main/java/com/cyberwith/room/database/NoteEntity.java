package com.cyberwith.room.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class NoteEntity {

    public NoteEntity( String id, String note) {
        this.id = id;
        this.note = note;
    }

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    @ColumnInfo(name = "notes")
    private String note;

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getNote() {
        return note;
    }
}
