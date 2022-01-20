package com.example.notes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotesDatabase implements NotesRepository {

    public static final NotesDatabase INSTANCE = new NotesDatabase();

    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    public List<Note> getAllNotes() {
        return notes;
    }

    @Override
    public void save(Note note) {
        notes.add(note);
    }

    @Override
    public void delete(Note note) {
        notes.remove(note);
    }
}
