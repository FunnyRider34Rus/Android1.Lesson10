package com.example.notes.presenter;

import com.example.notes.model.Note;
import com.example.notes.model.NotesRepository;
import com.example.notes.view.recycler.NotesListView;

import java.util.List;

public class NotesPresenter {

    private NotesListView view;
    private NotesRepository repository;

    public NotesPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {
        List<Note> notes = repository.getAllNotes();
        view.showNotes(notes);
    }
}
