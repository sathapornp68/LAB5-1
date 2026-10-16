package com.example.lab5_1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a CheckList Note, extending abstract Note.
 */
public class CheckListNote extends Note {
    private List<CheckListItem> items;

    public CheckListNote(String title, User user, List<CheckListItem> items) {
        super(title, user);
        this.items = items != null ? items : new ArrayList<>();
    }

    public List<CheckListItem> getItems() {
        return items;
    }

    public void addItem(CheckListItem item) {
        this.items.add(item);
    }

    @Override
    public String getNoteType() {
        return "CheckListNote";
    }

    @Override
    public String getContentDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            CheckListItem item = items.get(i);
            sb.append(item.isChecked() ? "[✓] " : "[ ] ")
              .append(item.getText());
            if (i < items.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
