package com.example.lab5_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private final Context context;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    public void updateNotes(List<Note> newNotes) {
        this.notes = newNotes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvNoteTitle.setText(note.getTitle());

        // Display User info stored in User class
        if (note.getUser() != null) {
            holder.tvNoteUser.setText("👤 " + note.getUser().getDisplayInfo());
        } else {
            holder.tvNoteUser.setText("👤 Anonymous");
        }

        holder.tvNoteDate.setText("🕒 " + note.getCreatedAt());

        if (note instanceof TextNote) {
            holder.tvNoteTypeBadge.setText("TextNote");
            holder.tvNoteTypeBadge.setBackgroundColor(ContextCompat.getColor(context, R.color.text_note_badge));
            holder.tvNoteTypeBadge.setTextColor(ContextCompat.getColor(context, R.color.text_note_badge_text));

            holder.tvNoteContent.setVisibility(View.VISIBLE);
            holder.tvNoteContent.setText(note.getContentDisplay());
            holder.llChecklistContainer.setVisibility(View.GONE);

        } else if (note instanceof CheckListNote) {
            CheckListNote checkListNote = (CheckListNote) note;
            holder.tvNoteTypeBadge.setText("CheckListNote");
            holder.tvNoteTypeBadge.setBackgroundColor(ContextCompat.getColor(context, R.color.checklist_badge));
            holder.tvNoteTypeBadge.setTextColor(ContextCompat.getColor(context, R.color.checklist_badge_text));

            holder.tvNoteContent.setVisibility(View.GONE);
            holder.llChecklistContainer.setVisibility(View.VISIBLE);
            holder.llChecklistContainer.removeAllViews();

            // Populate checklist items with interactive CheckBoxes (Event-driven CheckChangeListener)
            for (CheckListItem item : checkListNote.getItems()) {
                CheckBox checkBox = new CheckBox(context);
                checkBox.setText(item.getText());
                checkBox.setChecked(item.isChecked());
                checkBox.setTextSize(14);
                checkBox.setPadding(8, 4, 8, 4);

                // Event-driven listener for CheckBox toggle event
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> item.setChecked(isChecked));

                holder.llChecklistContainer.addView(checkBox);
            }
        }
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteTitle, tvNoteTypeBadge, tvNoteUser, tvNoteDate, tvNoteContent;
        LinearLayout llChecklistContainer;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteTypeBadge = itemView.findViewById(R.id.tvNoteTypeBadge);
            tvNoteUser = itemView.findViewById(R.id.tvNoteUser);
            tvNoteDate = itemView.findViewById(R.id.tvNoteDate);
            tvNoteContent = itemView.findViewById(R.id.tvNoteContent);
            llChecklistContainer = itemView.findViewById(R.id.llChecklistContainer);
        }
    }
}
