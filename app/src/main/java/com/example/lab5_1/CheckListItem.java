package com.example.lab5_1;

import java.io.Serializable;

/**
 * Class representing a single item inside a CheckListNote.
 */
public class CheckListItem implements Serializable {
    private String text;
    private boolean isChecked;

    public CheckListItem(String text, boolean isChecked) {
        this.text = text;
        this.isChecked = isChecked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
