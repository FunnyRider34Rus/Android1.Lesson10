package com.example.notes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotesDatabase implements NotesRepository {

    public static final NotesDatabase INSTANCE = new NotesDatabase();

    private ArrayList<Note> notes = new ArrayList<>();

    private NotesDatabase() {
        notes.add(new Note(UUID.randomUUID().toString(), "Title1", "Message1", new Date()));
        notes.add(new Note(UUID.randomUUID().toString(), "Title2", "Message2", new Date()));
        notes.add(new Note(UUID.randomUUID().toString(), "Title3", "Message3", new Date()));
        notes.add(new Note(UUID.randomUUID().toString(), "Title4", "Message4", new Date()));
        notes.add(new Note(UUID.randomUUID().toString(), "Title5", "Message5", new Date()));
    }


    @Override
    public List<Note> getAllNotes() {
        return notes;
    }
}
