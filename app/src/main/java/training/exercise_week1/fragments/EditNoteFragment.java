package training.exercise_week1.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import training.exercise_week1.DAO.NotesDb;
import training.exercise_week1.model.Note;
import training.exercise_week1.R;
import training.exercise_week1.SomeFunctions;

public class EditNoteFragment extends Fragment{

    private SomeFunctions sf = new SomeFunctions();

    private EditText subject_EditText;
    private EditText content_EditText;

    NotesDb notesDb;

    EditNoteFragmentListener editNoteFragmentListener;
    public interface EditNoteFragmentListener {
        void newNoteButtonClicked(Note note);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        subject_EditText = (EditText) view.findViewById(R.id.subject_EditText);
        content_EditText = (EditText) view.findViewById(R.id.content_EditText);

        Button cancelButton = (Button) view.findViewById(R.id.cancel_Button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNoteFragmentListener.newNoteButtonClicked(null);
            }
        });

        Button saveButton = (Button) view.findViewById(R.id.save_Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getSubject() == null || getContent() == null)) {
                    editNoteFragmentListener.newNoteButtonClicked(new Note(getSubject(), getContent()));
                }
            }
        });

        Button dbTester = (Button) view.findViewById(R.id.dbTester);
        dbTester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note newNote;
                notesDb = new NotesDb(getContext());
                if (!(getSubject() == null || getContent() == null)) {
                    newNote = new Note(getSubject(), getContent());
                } else {
                    String str = sf.getCurrentDateAndTime();
                    newNote = new Note("S=" + str, "C=" + str);
                    sf.showToastMessage(getContext(), "Insert data", false);
                }
                notesDb.createNote(newNote);

            }
        });

        Button dbPrint = (Button) view.findViewById(R.id.dbPrint);
        dbPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesDb = new NotesDb(getContext());
                ArrayList<Note> notes = notesDb.getAllNotes();
                for (Note n : notes) {
                    Log.d("DB_LIST", n.getId() + " " + n.getSubject() + " " + n.getContent());
                }
            }
        });


        Bundle args = getArguments();
        if (args != null) {
            Log.d("LOG", "savedInstanceState is NOT null");
            Note note = (Note) args.getSerializable("Note");
            subject_EditText.setText(note.getSubject());
            content_EditText.setText(note.getContent());
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            editNoteFragmentListener = (EditNoteFragmentListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(e.toString());
        }
    }

    private String getSubject() {
        if (subject_EditText.getText().toString().equals("")) {
            sf.showToastMessage(this.getContext(), "Subject cannot be empty!", false);
        } else {
            return subject_EditText.getText().toString();
        }
        return null;
    }

    private String getContent() {
        if (content_EditText.getText().toString().equals("")) {
            sf.showToastMessage(this.getContext(), "Content cannot be empty!", false);
        } else {
            return content_EditText.getText().toString();
        }
        return null;
    }

}
