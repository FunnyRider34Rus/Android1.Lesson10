package com.example.notes.view.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.R;
import com.example.notes.model.Note;
import com.example.notes.model.NotesDatabase;
import com.example.notes.presenter.AddNotesPresenter;
import com.google.android.material.button.MaterialButton;

import java.util.Date;
import java.util.UUID;

public class AddNoteBottomSheetDialogFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment implements AddNoteView{

    public static final String TAG = "AddNoteBottomSheetDialogFragment";

    public static final String KEY = "AddoteBottomSheetDialogFragment";
    public static final String ARG_NOTE = "ARG_NOTE";

    public static AddNoteBottomSheetDialogFragment newInstance() {
        AddNoteBottomSheetDialogFragment fragment = new AddNoteBottomSheetDialogFragment();

        return fragment;
    }

    private MaterialButton buttonSave;

    private AddNotesPresenter presenter;

    private Note note;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AddNotesPresenter(this, NotesDatabase.INSTANCE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonSave = view.findViewById(R.id.button_save);
        EditText title = view.findViewById(R.id.input_title);
        EditText message = view.findViewById(R.id.input_message);

        note = new Note(UUID.randomUUID().toString(), title.getText().toString(), message.getText().toString(), new Date());

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveNote(note);
            }
        });
    }


    @Override
    public void noteSaved(Note note) {
        Bundle result = new Bundle();
        result.putParcelable(ARG_NOTE, note);
        getParentFragmentManager().setFragmentResult(KEY, result);
        dismiss();
    }
}
