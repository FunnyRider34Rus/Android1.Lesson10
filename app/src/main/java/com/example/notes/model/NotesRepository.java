package com.example.notes.model;

import java.util.List;

public interface NotesRepository {

    List<Note> getAllNotes();

    void save(Note note);

    void delete(Note note);
}
