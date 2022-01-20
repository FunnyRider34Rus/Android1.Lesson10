package com.example.notes.view.recycler;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.notes.R;
import com.example.notes.model.Note;
import com.example.notes.model.NotesDatabase;
import com.example.notes.presenter.AdapterItem;
import com.example.notes.presenter.NoteAdapterItem;
import com.example.notes.presenter.NotesPresenter;
import com.example.notes.view.edit.AddNoteBottomSheetDialogFragment;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    private RecyclerView notesList;

    private NotesAdapter adapter;

    private NotesPresenter presenter;

    private Note selectedNote;

    private SwipeRefreshLayout mSwipeRefresh;

    public NotesListFragment() {
        super(R.layout.fragment_note_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesPresenter(this, NotesDatabase.INSTANCE);
        adapter = new NotesAdapter(this);

        adapter.setOnClick(new NotesAdapter.OnClick() {
            @Override
            public void onClick(Note note) {
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(Note note) {
                selectedNote = note;

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notesList = view.findViewById(R.id.notes_list);
        mSwipeRefresh = view.findViewById(R.id.swipe_to_refresh);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestNotes();
            }
        });

        notesList.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false));
        notesList.setAdapter(adapter);

        presenter.requestNotes();

        getParentFragmentManager()
                .setFragmentResultListener(AddNoteBottomSheetDialogFragment.KEY, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);
                        presenter.onNoteAdded(note);
                    }
                });

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteBottomSheetDialogFragment.newInstance()
                        .show(getParentFragmentManager(), AddNoteBottomSheetDialogFragment.TAG);
            }
        });
    }

    @Override
    public void showNotes(List<AdapterItem> notes) {
        adapter.setData(notes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteAdded(NoteAdapterItem adapterItem) {
        int index = adapter.addItem(adapterItem);

        adapter.notifyItemInserted(index - 1);

        notesList.smoothScrollToPosition(index - 1);
    }

    @Override
    public void onNoteRemoved(Note selectedNote) {
        int index = adapter.removeItem(selectedNote);
        adapter.notifyItemRemoved(index);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.notes_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            presenter.removeNote(selectedNote);
            return true;
        }
        if (item.getItemId() == R.id.action_update) {
            Toast.makeText(requireContext(), "Update " + selectedNote.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
