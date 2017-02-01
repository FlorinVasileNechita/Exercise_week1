package training.exercise_week1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private boolean update = false;
    private Note oldNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        subject_EditText = (EditText) view.findViewById(R.id.subject_EditText);
        content_EditText = (EditText) view.findViewById(R.id.content_EditText);

//        try {
//            Intent data = getIntent();
//            oldNote = (Note) data.getSerializableExtra("Note");
//            subject_EditText.setText(oldNote.getSubject());
//            content_EditText.setText(oldNote.getContent());
//            update = true;
//            Log.d("Note", "Note=" + oldNote.getSubject() + " " + oldNote.getContent());
//        } catch (Exception e) {
//        }


        return view;
    }

    public void setFields(Note note){
        subject_EditText.setText(note.getSubject());
        content_EditText.setText(note.getContent());
    }

    public void cancelButtonClicked(View view) {
//        Intent i = new Intent();
//        setResult(RESULT_CANCELED, i);
//        finish();
    }

    public void saveButtonClicked(View view) {
//        if (!(getSubject() == null || getContent() == null)) {
//            Intent i = new Intent();
//            i.putExtra("Note", new Note(getSubject(), getContent()));
//            if (!update) {
//                setResult(RESULT_OK, i);
//            } else {
//                i.putExtra("OldNote", oldNote);
//                setResult(RESULT_FIRST_USER, i);
//            }
//            finish();
//        }
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
