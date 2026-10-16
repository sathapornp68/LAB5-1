package com.example.lab5_1;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing Notes and publishing Event-driven notifications to listeners.
 */
public class NoteRepository {
    private static NoteRepository instance;
    private final List<Note> notes = new ArrayList<>();
    private final List<OnNoteAddedListener> listeners = new ArrayList<>();

    private NoteRepository() {
        // Initial sample data to demonstrate both TextNote and CheckListNote with User information
        User demoUser = new User("John Doe", "john@example.com");
        notes.add(new TextNote("Welcome Note", demoUser, "Welcome to Lab 5 Note App! This is an example TextNote created by User John Doe."));

        List<CheckListItem> demoItems = new ArrayList<>();
        demoItems.add(new CheckListItem("Design classes (Lab 4)", true));
        demoItems.add(new CheckListItem("Implement Event-driven UI (Lab 5)", true));
        demoItems.add(new CheckListItem("Connect User with Note", true));
        demoItems.add(new CheckListItem("Test on Emulator", false));
        notes.add(new CheckListNote("Project Tasks", demoUser, demoItems));
    }

    public static synchronized NoteRepository getInstance() {
        if (instance == null) {
            instance = new NoteRepository();
        }
        return instance;
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void addNote(Note note) {
        notes.add(note);
        notifyNoteAdded(note);
    }

    public void addOnNoteAddedListener(OnNoteAddedListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeOnNoteAddedListener(OnNoteAddedListener listener) {
        listeners.remove(listener);
    }

    private void notifyNoteAdded(Note note) {
        for (OnNoteAddedListener listener : listeners) {
            listener.onNoteAdded(note);
        }
    }
}
