package com.example.notes.presenter;

import com.example.notes.model.Note;
import com.example.notes.model.NotesRepository;
import com.example.notes.view.recycler.NotesListView;

import java.util.ArrayList;
import java.util.List;

public class NotesPresenter {

    private NotesListView view;
    private NotesRepository repository;

    public NotesPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {
        ArrayList<AdapterItem> adapterItems = new ArrayList<>();
        view.showNotes(adapterItems);
    }

    public void onNoteAdded(Note note) {
        NoteAdapterItem adapterItem = new NoteAdapterItem(note, note.getTitle(), note.getMessage(), note.getCreatedAt());

        view.onNoteAdded(adapterItem);
    }

    public void removeNote(Note selectedNote) {
        repository.delete(selectedNote);
        view.onNoteRemoved(selectedNote);
    }
}
