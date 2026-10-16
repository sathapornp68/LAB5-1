package com.example.lab5_1;

/**
 * Class representing a Text Note, extending abstract Note.
 */
public class TextNote extends Note {
    private String textContent;

    public TextNote(String title, User user, String textContent) {
        super(title, user);
        this.textContent = textContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public String getNoteType() {
        return "TextNote";
    }

    @Override
    public String getContentDisplay() {
        return textContent != null ? textContent : "";
    }
}
