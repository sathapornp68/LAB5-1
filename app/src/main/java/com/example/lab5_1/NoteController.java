package com.example.lab5_1;

import android.text.TextUtils;

import java.util.List;

/**
 * Controller class in MVC Pattern acting as mediator between Views (AddNoteActivity, MainActivity)
 * and Models (User, Note, TextNote, CheckListNote, NoteRepository).
 */
public class NoteController {

    private static NoteController instance;
    private final NoteRepository repository;

    private NoteController() {
        this.repository = NoteRepository.getInstance();
    }

    public static synchronized NoteController getInstance() {
        if (instance == null) {
            instance = new NoteController();
        }
        return instance;
    }

    /**
     * Gets all notes from Model.
     */
    public List<Note> getAllNotes() {
        return repository.getNotes();
    }

    /**
     * Creates User and TextNote models and adds to repository.
     * @return true if successful, false if input validation fails.
     */
    public boolean createAndAddTextNote(String userName, String userEmail, String title, String content) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(title)) {
            return false;
        }

        // Create Model instances
        User user = new User(userName, userEmail);
        TextNote textNote = new TextNote(title, user, content != null ? content : "");

        // Delegate persistence to Repository Model
        repository.addNote(textNote);
        return true;
    }

    /**
     * Creates User and CheckListNote models and adds to repository.
     * @return true if successful, false if input validation fails.
     */
    public boolean createAndAddCheckListNote(String userName, String userEmail, String title, List<CheckListItem> items) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(title)) {
            return false;
        }

        // Create Model instances
        User user = new User(userName, userEmail);
        CheckListNote checkListNote = new CheckListNote(title, user, items);

        // Delegate persistence to Repository Model
        repository.addNote(checkListNote);
        return true;
    }

    /**
     * Register Event Listener for Model updates.
     */
    public void registerNoteAddedListener(OnNoteAddedListener listener) {
        repository.addOnNoteAddedListener(listener);
    }

    /**
     * Unregister Event Listener.
     */
    public void unregisterNoteAddedListener(OnNoteAddedListener listener) {
        repository.removeOnNoteAddedListener(listener);
    }
}
