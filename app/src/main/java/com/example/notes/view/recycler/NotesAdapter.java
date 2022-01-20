package com.example.notes.view.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.model.Note;
import com.example.notes.presenter.AdapterItem;
import com.example.notes.presenter.NoteAdapterItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private ArrayList<AdapterItem> data = new ArrayList<>();

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    private OnClick onClick;
    private Fragment fragment;



    public void setData(Collection<AdapterItem> notes) {
        data.clear();
        data.addAll(notes);
    }

    public int addItem(NoteAdapterItem note) {
        data.add(note);
        return data.size();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        NoteViewHolder noteViewHolder = (NoteViewHolder) holder;

        NoteAdapterItem note = (NoteAdapterItem) data.get(position);

        noteViewHolder.getTitle().setText(note.getTitle());
        noteViewHolder.getDate().setText(dateFormat.format(note.getDate()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int removeItem(Note selectedNote) {
        int index = 0;
        for (int i=0; i < data.size(); i++) {
            if (data.get(i) instanceof NoteAdapterItem && ((NoteAdapterItem) data.get(i)).getNote().getId().equals(selectedNote.getId())) {
                index =i;
                break;
            }
        }
        data.remove(index);
        return index;
    }

    interface OnClick {
        void onClick(Note note);

        void onLongClick(Note note);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private TextView date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            CardView card = itemView.findViewById(R.id.card_view);

            fragment.registerForContextMenu(card);

            title = itemView.findViewById(R.id.title);

            date = itemView.findViewById(R.id.date);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AdapterItem item = data.get(getAdapterPosition());

                    if (item instanceof NoteAdapterItem) {
                        if (getOnClick() != null) {
                            getOnClick().onClick(((NoteAdapterItem) item).getNote());
                        }
                    }
                }
            });

            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    card.showContextMenu();

                    AdapterItem item = data.get(getAdapterPosition());

                    if (item instanceof NoteAdapterItem) {
                        if (getOnClick() != null) {
                            getOnClick().onLongClick(((NoteAdapterItem) item).getNote());
                        }
                    }

                    return true;
                }
            });

        }

        public TextView getTitle() {
            return title;
        }

        public TextView getDate() {
            return date;
        }
    }
}
