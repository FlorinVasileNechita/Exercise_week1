package training.exercise_week1.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import training.exercise_week1.DAO.NotesDb;
import training.exercise_week1.NotesListCustomAdapter;
import training.exercise_week1.model.Note;
import training.exercise_week1.R;
import training.exercise_week1.SomeFunctions;

public class NotesListFragment extends Fragment {

    private ArrayList<Note> notesArrayList = new ArrayList<>();

    private ListAdapter adapter;
    private ListView listView;

    private SomeFunctions sf = new SomeFunctions();

    private boolean deleteActivated = false;

    private TextView clickRowToDelete;

    private Button addDateAndTimeButton;
    private Button addNoteButton;
    private Button deleteNotesButton;

    NotesDb notesDb;

    NotesListFragmentListener notesListFragmentListener;

    public interface NotesListFragmentListener {
        void updateNote(Note note);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        notesDb = new NotesDb(getContext());
        notesArrayList = notesDb.getNotesOrderById(true);

        handleListView(view);

        handleAddDateAndTimeButton(view);

        handleClickToDeleteTextView(view);

        handleAddNoteButton(view);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            notesListFragmentListener = (NotesListFragmentListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(e.toString());
        }
    }

    private void handleListView(View view) {
        adapter = new NotesListCustomAdapter(view.getContext(), notesArrayList);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Note note = notesArrayList.get(position);
                        if (deleteActivated) {
                            deleteNote(note);
                        } else {
                            itemClicked(note);
                        }
                    }
                }
        );
    }

    private void handleAddDateAndTimeButton(View view) {
        addDateAndTimeButton = (Button) view.findViewById(R.id.currentDate);
        addDateAndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = sf.getCurrentDateAndTime();
                addElementInList("S" + str, "C" + str);
            }
        });
    }

    private void handleAddNoteButton(View view) {
        addNoteButton = (Button) view.findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesListFragmentListener.updateNote(null);
            }
        });
    }

    private void handleClickToDeleteTextView(View view) {
        clickRowToDelete = (TextView) view.findViewById(R.id.textView_clickToDelete);
        if (!deleteActivated) {
            clickRowToDelete.setVisibility(View.GONE);
        } else {
            clickRowToDelete.setVisibility(View.VISIBLE);
        }
    }

    private void handleDeleteButton(View view) {
        deleteNotesButton = (Button) view.findViewById(R.id.deleteNotes);
        deleteNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void itemClicked(Note note) {
        notesListFragmentListener.updateNote(note);
    }

    private void deleteNote(Note note) {
//        TODO: implement delete
        refreshList();
    }

    public void addElementInList(String noteSubject, String noteContent) {
        notesDb.addNote(new Note(noteSubject, noteContent));
        refreshList();
    }

    public void updateElementInList(Note note, Note oldNote) {
//        int pos = 0;
//        for (int i = 0; i < subjects.size(); i++) {
//            if (subjects.get(i).equals(oldNote.getSubject())) {
//                pos = i;
//                break;
//            }
//        }
//        subjects.set(pos, note.getSubject());
//        contents.set(pos, note.getContent());
//
//        refreshList();

        int pos = 0;
        for (int i = 0; i < notesArrayList.size(); i++) {
            if (notesArrayList.get(i).equals(oldNote.getSubject())) {
                pos = i;
                break;
            }
        }
        notesArrayList.set(pos, note);

        refreshList();
    }

    private void refreshList() {
        notesArrayList = notesDb.getNotesOrderById(true);
        listView.setAdapter(null);
        listView.setAdapter(adapter);
    }
}
