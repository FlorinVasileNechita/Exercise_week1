package training.exercise_week1.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import training.exercise_week1.NotesListCustomAdapter;
import training.exercise_week1.contentProvider.NotesContentProvider;
import training.exercise_week1.database.NotesTable;
import training.exercise_week1.model.Note;
import training.exercise_week1.R;
import training.exercise_week1.SomeFunctions;

public class NotesListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ArrayList<Note> notesArrayList = new ArrayList<>();

    private ListAdapter adapter;
    private ListView listView;

    private SomeFunctions sf = new SomeFunctions();

    private boolean deleteActivated = false;

//    NotesDb notesDb;

    NotesListFragmentListener notesListFragmentListener;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {NotesTable.COLUMN_ID, NotesTable.COLUMN_SUBJECT, NotesTable.COLUMN_CONTENT};
        CursorLoader cursorLoader = new CursorLoader(getContext(), NotesContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface NotesListFragmentListener {
        void updateNote(Note note);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

//        notesDb = new NotesDb(getContext());

        listViewHandler(view);
        refreshList();

        addDateAndTimeButtonHandler(view);

        clickToDeleteTextViewHandler(view);

        addNoteButtonHandler(view);

        deleteNoteButtonHandler(view);

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

    private void listViewHandler(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Note note = notesArrayList.get(position);
                        if (deleteActivated) {
                            deleteNote(note);
                        } else {
                            notesListFragmentListener.updateNote(note);
                        }
                    }
                }
        );
    }

    private void addDateAndTimeButtonHandler(View view) {
        Button addDateAndTimeButton = (Button) view.findViewById(R.id.currentDate);
        addDateAndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = sf.getCurrentDateAndTime();
                addNote(new Note("S" + str, "C" + str));
            }
        });
    }


    private void addNoteButtonHandler(View view) {
        Button addNoteButton = (Button) view.findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesListFragmentListener.updateNote(null);
            }
        });
    }

    private void deleteNoteButtonHandler(View view) {
        Button deleteNotesButton = (Button) view.findViewById(R.id.deleteNotes);
        deleteNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sf.showToastMessage(getContext(), "Delete_btn clicked", false);
                deleteActivated = !deleteActivated;
                clickToDeleteTextViewHandler(getView());
            }
        });
    }

    private void clickToDeleteTextViewHandler(View view) {
        TextView clickRowToDelete = (TextView) view.findViewById(R.id.textView_clickToDelete);

        if (!deleteActivated) {
            clickRowToDelete.setVisibility(View.GONE);
        } else {
            clickRowToDelete.setVisibility(View.VISIBLE);
        }
    }

    private void deleteNote(Note note) {
        sf.showToastMessage(getContext(), "ToDelete= " + note.getSubject(), false);
//        notesDb.deleteNote(note);
//        refreshList();
    }

    public void addNote(Note note) {
//        notesDb.addNote(note);
//        refreshList();
    }

    public void updateNote(Note note) {
        Log.v("TAG", "ToUpdate=" + note.getId() + " " + note.getSubject());
        sf.showToastMessage(getContext(), "ToUpdate=" + note.getId() + " " + note.getSubject(), true);
//        notesDb.updateNote(note);
//        refreshList();
    }

    private void refreshList() {
//        notesArrayList = notesDb.getNotesOrderById(true);
//        adapter = new NotesListCustomAdapter(getContext(), notesArrayList);
//        listView.setAdapter(null);
//        listView.setAdapter(adapter);
    }
}