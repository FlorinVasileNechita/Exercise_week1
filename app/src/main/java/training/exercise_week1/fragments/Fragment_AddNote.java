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

import training.exercise_week1.DAO.NotesDataSource;
import training.exercise_week1.Note;
import training.exercise_week1.R;
import training.exercise_week1.SomeFunctions;

public class Fragment_AddNote extends Fragment {

    private SomeFunctions sf = new SomeFunctions();

    private EditText subject_EditText;
    private EditText content_EditText;

    InterfaceAddNote interfaceAddNote;
    NotesDataSource notesDataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        subject_EditText = (EditText) view.findViewById(R.id.subject_EditText);
        content_EditText = (EditText) view.findViewById(R.id.content_EditText);

        Button cancelButton = (Button) view.findViewById(R.id.cancel_Button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceAddNote.newNoteButtonClicked(null);
            }
        });

        Button saveButton = (Button) view.findViewById(R.id.save_Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getSubject() == null || getContent() == null)) {
                    interfaceAddNote.newNoteButtonClicked(new Note(getSubject(), getContent()));
                }
            }
        });

        Button dbTester = (Button) view.findViewById(R.id.dbTester);
        dbTester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = sf.getCurrentDateAndTime();
                Note n = new Note("S=" + str, "C=" + str);
                notesDataSource = new NotesDataSource(getContext());
                notesDataSource.createNote(n);
            }
        });

        Button dbPrint = (Button) view.findViewById(R.id.dbPrint);
        dbPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesDataSource = new NotesDataSource(getContext());
                ArrayList<Note> notes = notesDataSource.getAllNotes();
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
            interfaceAddNote = (InterfaceAddNote) activity;
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

    public interface InterfaceAddNote {
        void newNoteButtonClicked(Note note);
    }

}
