package com.example.notes.presenter;

import com.example.notes.model.Note;
import com.example.notes.model.NotesRepository;
import com.example.notes.view.edit.AddNoteView;

public class AddNotesPresenter implements NotePresenter{

    private AddNoteView view;
    private NotesRepository repository;

    public AddNotesPresenter(AddNoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onActionPressed(Note note) {
        repository.save(note);
        view.noteSaved(note);
    }
}
