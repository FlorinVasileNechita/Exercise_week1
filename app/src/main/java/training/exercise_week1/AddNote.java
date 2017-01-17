package training.exercise_week1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddNote extends AppCompatActivity {

    private SomeFunctions sf = new SomeFunctions();

    private EditText subject_EditText;
    private EditText content_EditText;

    private boolean update = false;
    private Note oldNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        subject_EditText = (EditText) findViewById(R.id.subject_EditText);
        content_EditText = (EditText) findViewById(R.id.content_EditText);

        try {
            Intent data = getIntent();
            oldNote = (Note) data.getSerializableExtra("Note");
            subject_EditText.setText(oldNote.getSubject());
            content_EditText.setText(oldNote.getContent());
            update = true;
            Log.d("Note", "Note=" + oldNote.getSubject() + " " + oldNote.getContent());
        } catch (Exception e) {
        }
    }

    public void cancelButtonClicked(View view) {
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }

    public void saveButtonClicked(View view) {
        if (!(getSubject() == null || getContent() == null)) {
            Intent i = new Intent();
            i.putExtra("Note", new Note(getSubject(), getContent()));
            if (!update) {
                setResult(RESULT_OK, i);
            } else {
                i.putExtra("OldNote", oldNote);
                setResult(RESULT_FIRST_USER, i);
            }
            finish();
        }
    }

    private String getSubject() {
        if (subject_EditText.getText().toString().equals("")) {
            sf.showToastMessage(this, "Subject cannot be empty!", false);
        } else {
            return subject_EditText.getText().toString();
        }
        return null;
    }

    private String getContent() {
        if (content_EditText.getText().toString().equals("")) {
            sf.showToastMessage(this, "Content cannot be empty!", false);
        } else {
            return content_EditText.getText().toString();
        }
        return null;
    }
}
