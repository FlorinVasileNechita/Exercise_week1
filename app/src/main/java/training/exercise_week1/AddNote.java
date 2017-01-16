package training.exercise_week1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNote extends AppCompatActivity {

    private SomeFunctions sf = new SomeFunctions();

    private EditText subject_EditText;
    private EditText content_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        subject_EditText = (EditText) findViewById(R.id.subject_EditText);
        content_EditText = (EditText) findViewById(R.id.content_EditText);
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
            setResult(RESULT_OK, i);
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
