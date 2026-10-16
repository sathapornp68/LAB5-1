package com.example.lab5_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * MainActivity displaying note list and receiving Note addition events via OnNoteAddedListener.
 */
public class MainActivity extends AppCompatActivity implements OnNoteAddedListener {

    private RecyclerView rvNotes;
    private TextView tvEmptyState;
    private Button btnAddNote;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupRecyclerView();

        // Register custom event listener to listen for newly added notes
        NoteRepository.getInstance().addOnNoteAddedListener(this);

        // Event-driven OnClickListener: Navigate to AddNoteActivity
        btnAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        rvNotes = findViewById(R.id.rvNotes);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        btnAddNote = findViewById(R.id.btn_add_note);
    }

    private void setupRecyclerView() {
        List<Note> notesList = NoteRepository.getInstance().getNotes();
        adapter = new NoteAdapter(this, notesList);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setAdapter(adapter);
        updateEmptyState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNotesList();
    }

    private void refreshNotesList() {
        if (adapter != null) {
            List<Note> notes = NoteRepository.getInstance().getNotes();
            adapter.updateNotes(notes);
            updateEmptyState();
        }
    }

    private void updateEmptyState() {
        if (adapter != null && adapter.getItemCount() > 0) {
            rvNotes.setVisibility(View.VISIBLE);
            tvEmptyState.setVisibility(View.GONE);
        } else {
            rvNotes.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
        }
    }

    // Event callback triggered when a Note is added anywhere in the app
    @Override
    public void onNoteAdded(Note note) {
        refreshNotesList();
        Toast.makeText(this, "Event Received: New Note added (" + note.getTitle() + " by " + note.getUser().getName() + ")", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up event listener registration
        NoteRepository.getInstance().removeOnNoteAddedListener(this);
    }
}
