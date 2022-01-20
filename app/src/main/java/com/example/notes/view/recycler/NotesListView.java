package com.example.notes.view.recycler;

import com.example.notes.model.Note;
import com.example.notes.presenter.AdapterItem;
import com.example.notes.presenter.NoteAdapterItem;

import java.util.List;

public interface NotesListView {

    void showNotes(List<AdapterItem> notes);

    void onNoteAdded(NoteAdapterItem adapterItem);

    void onNoteRemoved(Note selectedNote);
}
