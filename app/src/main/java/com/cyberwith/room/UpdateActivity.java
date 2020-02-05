package com.cyberwith.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cyberwith.room.database.NoteEntity;
import com.cyberwith.room.viewModel.NoteViewModel;
import com.cyberwith.room.viewModel.UpdateViewModel;

public class UpdateActivity extends AppCompatActivity {

    public static final String NOTE_ID = "note_id";
    public static final String UPDATE_NOTE = "update_note";
    private EditText editText;
    private Button updateButton, cancelButton;
    private UpdateViewModel noteViewModel;

    private Bundle bundle;
    private String noteID;
    private LiveData<NoteEntity> noteEntityLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        bundle = getIntent().getExtras();
        if (bundle != null){
            noteID = bundle.getString("note_id");
        }

        editText = findViewById(R.id.editText2);
        updateButton = findViewById(R.id.updateButtonID);
        cancelButton = findViewById(R.id.cancelButtonID);

        noteViewModel = ViewModelProviders.of(this).get(UpdateViewModel.class);
        //for view note data
        noteEntityLiveData = noteViewModel.getNote(noteID);
        noteEntityLiveData.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
                editText.setText(noteEntity.getNote());
            }
        });

        setOnClick();
    }

    private void setOnClick() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateNote = editText.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra(NOTE_ID, noteID);
                resultIntent.putExtra(UPDATE_NOTE, updateNote);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
