package com.cyberwith.room;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cyberwith.room.adapter.NoteViewAdapter;
import com.cyberwith.room.database.NoteEntity;
import com.cyberwith.room.viewModel.NoteViewModel;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NoteViewAdapter.OnNoteDeleteListener {

    private NoteViewModel noteViewModel;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_REQUEST_CODE = 2;
    private RecyclerView recyclerView;
    private NoteViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getLifecycle().addObserver(new MainActivityObserver(MainActivity.this));
        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteViewAdapter(this, this);
        recyclerView.setAdapter(adapter);


        findViewById(R.id.addButtonID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        //get data from database/table
        noteViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                adapter.setNotes(noteEntities);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            String id = UUID.randomUUID().toString();
            NoteEntity noteEntity = new NoteEntity(id, data.getStringExtra(AddNoteActivity.NOTE_ADDED));
            noteViewModel.insert(noteEntity);
            Toast.makeText(getApplicationContext(), " Saved ", Toast.LENGTH_LONG).show();
        }
        else if (requestCode == UPDATE_NOTE_REQUEST_CODE && resultCode == RESULT_OK){
            NoteEntity noteEntity = new NoteEntity(data.getStringExtra(UpdateActivity.NOTE_ID),
                    data.getStringExtra(UpdateActivity.UPDATE_NOTE));
            noteViewModel.update(noteEntity);
            Toast.makeText(getApplicationContext(), " Updated ", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), " Not save ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteListener(NoteEntity noteEntity) {
        noteViewModel.delete(noteEntity);
        Toast.makeText(getApplicationContext(), " Not is delete ", Toast.LENGTH_LONG).show();
    }
}
