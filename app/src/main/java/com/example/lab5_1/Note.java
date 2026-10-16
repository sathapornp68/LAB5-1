package com.example.lab5_1;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Abstract base class for Note.
 */
public abstract class Note implements Serializable {
    private String title;
    private User user;
    private String createdAt;

    public Note(String title, User user) {
        this.title = title;
        this.user = user;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        this.createdAt = sdf.format(new Date());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public abstract String getNoteType();

    public abstract String getContentDisplay();
}
