package com.cyberwith.room.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cyberwith.room.database.NoteDao;
import com.cyberwith.room.database.NoteDatabase;
import com.cyberwith.room.database.NoteEntity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteDao noteDao;
    private NoteDatabase noteDatabase;
    private LiveData<List<NoteEntity>> allNotes;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getDatabase(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNote();
    }

    public void insert(NoteEntity noteEntity){
        new InsertAsyncTask(noteDao).execute(noteEntity);
    }

    public LiveData<List<NoteEntity>> getAllNotes(){
        return allNotes;
    }

    public void update(NoteEntity noteEntity){
        new UpdateAsyncTask(noteDao).execute(noteEntity);
    }

    public void delete(NoteEntity noteEntity){
        new DeleteAsynctask(noteDao).execute(noteEntity);
    }

    private class InsertAsyncTask extends AsyncTask<NoteEntity, Void, Void> {

        private NoteDao noteDao;

        public InsertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.insertNote(noteEntities[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<NoteEntity, Void, Void>{
        private NoteDao noteDao;
        public UpdateAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.Update(noteEntities[0]);
            return null;
        }
    }

    private class DeleteAsynctask extends AsyncTask<NoteEntity, Void, Void>{

        private NoteDao noteDao;
        public DeleteAsynctask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.deleteNote(noteEntities[0]);
            return null;
        }
    }


}
