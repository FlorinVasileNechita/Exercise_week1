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

import training.exercise_week1.model.Note;
import training.exercise_week1.R;
import training.exercise_week1.SomeFunctions;

public class EditNoteFragment extends Fragment {

    private SomeFunctions sf = new SomeFunctions();

    private EditText subject_EditText;
    private EditText content_EditText;

    Note receivedNote = new Note();

    EditNoteFragmentListener editNoteFragmentListener;

    public interface EditNoteFragmentListener {
        void saveChanges(Note note);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        subject_EditText = (EditText) view.findViewById(R.id.subject_EditText);
        content_EditText = (EditText) view.findViewById(R.id.content_EditText);

        Bundle args = getArguments();
        if (args != null) {
            Log.d("LOG", "savedInstanceState is NOT null");
            receivedNote = (Note) args.getSerializable("Note");
            subject_EditText.setText(receivedNote.getSubject());
            content_EditText.setText(receivedNote.getContent());
        }

        Button cancelButton = (Button) view.findViewById(R.id.cancel_Button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNoteFragmentListener.saveChanges(null);
            }
        });

        Button saveButton = (Button) view.findViewById(R.id.save_Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getSubject() == null || getContent() == null)) {
                    receivedNote.setSubject(getSubject());
                    receivedNote.setContent(getContent());
                    editNoteFragmentListener.saveChanges(receivedNote);
                }
            }
        });

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
