package com.example.notes.presenter;

import com.example.notes.model.Note;

import java.util.Date;

public class NoteAdapterItem implements AdapterItem {
    private Note note;

    private String title;
    private String message;



    private Date date;

    public NoteAdapterItem(Note note, String title, String message, Date date) {
        this.note = note;
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public Note getNote() {
        return note;
    }
}
