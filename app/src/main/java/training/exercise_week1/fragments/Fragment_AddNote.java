package training.exercise_week1.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import training.exercise_week1.Note;
import training.exercise_week1.R;
import training.exercise_week1.SomeFunctions;

/**
 * Created by florinnechita on 01/02/17.
 */

public class Fragment_AddNote extends Fragment {

    private SomeFunctions sf = new SomeFunctions();

    private EditText subject_EditText;
    private EditText content_EditText;

    private Button cancelButton, saveButton;

    private boolean update = false;
    private Note oldNote;

    InterfaceAddNote interfaceAddNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        subject_EditText = (EditText) view.findViewById(R.id.subject_EditText);
        content_EditText = (EditText) view.findViewById(R.id.content_EditText);

        cancelButton = (Button) view.findViewById(R.id.cancel_Button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceAddNote.newNoteButtonClicked(null);
            }
        });

        saveButton = (Button) view.findViewById(R.id.save_Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getSubject() == null || getContent() == null)) {
                    interfaceAddNote.newNoteButtonClicked(new Note(getSubject(), getContent()));
                }
            }
        });


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

    public void setFields(Note note) {
        subject_EditText.setText(note.getSubject());
        content_EditText.setText(note.getContent());
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
