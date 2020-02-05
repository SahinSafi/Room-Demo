package com.cyberwith.room.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyberwith.room.MainActivity;
import com.cyberwith.room.R;
import com.cyberwith.room.UpdateActivity;
import com.cyberwith.room.database.NoteEntity;

import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.NoteViewHolder> {

    public interface OnNoteDeleteListener {
        void onDeleteListener(NoteEntity noteEntity);
    }

    private final LayoutInflater layoutInflater;
    private Context context;
    private List<NoteEntity> noteEntityList;
    private OnNoteDeleteListener onNoteDeleteListener;

    public NoteViewAdapter(Context context, OnNoteDeleteListener onNoteDeleteListener) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.onNoteDeleteListener = onNoteDeleteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_note_view_layout, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (noteEntityList != null){
            NoteEntity noteEntity = noteEntityList.get(position);
            holder.setData(noteEntity.getNote(), position);
            holder.setListeners();
        }else {
            holder.textView.setText("No data found");
        }
    }

    @Override
    public int getItemCount() {
        if (noteEntityList != null){
            return noteEntityList.size();
        }else return 0;
    }

    public void setNotes(List<NoteEntity> noteEntityList){
        this.noteEntityList = noteEntityList;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView editButton, deleteButton;
        private int mPosition;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.noteTextViewID);
            editButton = itemView.findViewById(R.id.editImageViewID);
            deleteButton = itemView.findViewById(R.id.deleteImageViewID);
        }

        public void setData(String note, int position){
            textView.setText(note);
            mPosition = position;
        }

        public void setListeners(){

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("note_id", noteEntityList.get(mPosition).getId());
                    ((Activity) context).startActivityForResult(intent, MainActivity.UPDATE_NOTE_REQUEST_CODE);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onNoteDeleteListener != null){
                        onNoteDeleteListener.onDeleteListener(noteEntityList.get(mPosition));
                    }
                }
            });
        }
    }
}
