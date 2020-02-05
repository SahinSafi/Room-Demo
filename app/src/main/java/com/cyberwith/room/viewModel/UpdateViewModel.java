package com.cyberwith.room.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cyberwith.room.database.NoteDao;
import com.cyberwith.room.database.NoteDatabase;
import com.cyberwith.room.database.NoteEntity;

public class UpdateViewModel extends AndroidViewModel {

    private NoteDao noteDao;
    private NoteDatabase noteDatabase;

    public UpdateViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getDatabase(application);
        noteDao = noteDatabase.noteDao();
    }

    public LiveData<NoteEntity> getNote(String noteID){
        return noteDao.getNote(noteID);
    }
}
