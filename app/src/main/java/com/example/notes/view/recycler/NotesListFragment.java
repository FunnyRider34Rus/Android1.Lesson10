package com.example.notes.view.recycler;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.model.Note;
import com.example.notes.model.NotesDatabase;
import com.example.notes.presenter.NotesPresenter;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    private RecyclerView notesList;

    private NotesAdapter adapter;

    private NotesPresenter presenter;

    public NotesListFragment() {
        super(R.layout.fragment_note_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesPresenter(this, NotesDatabase.INSTANCE);
        adapter = new NotesAdapter();

        adapter.setOnClick(new NotesAdapter.OnClick() {
            @Override
            public void onClick(Note note) {
                //TODO обработка нажатия на заметку
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notesList = view.findViewById(R.id.notes_list);

        notesList.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false));
        notesList.setAdapter(adapter);

        presenter.requestNotes();
    }

    @Override
    public void showNotes(List<Note> notes) {
        adapter.setData(notes);
        adapter.notifyDataSetChanged();
    }
}
