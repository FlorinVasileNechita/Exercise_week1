package training.exercise_week1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {

    private Intent listOfNotesIntent;
    private Note noteReceived;

    private EditText subject_EditText;
    private EditText content_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        listOfNotesIntent = new Intent(this, ListOfNotes.class);
        Intent receivedIntent = getIntent();
        try {
            noteReceived = (Note) receivedIntent.getSerializableExtra("Note");
            showToastMessage(noteReceived.getSubject());
        } catch (Exception ex) {
//        no data is sent -> New note button was clicked
            showToastMessage("No data received!");
        }

        subject_EditText = (EditText) findViewById(R.id.subject_EditText);
        content_EditText = (EditText) findViewById(R.id.content_EditText);

    }

    public void cancelButtonClicked(View view) {
        startActivity(listOfNotesIntent);
    }

    public void saveButtonClicked(View view) {
        if (!(getSubject() == null || getContent() == null)) {
            listOfNotesIntent.putExtra("Note", new Note(getSubject(), getContent()));
            startActivity(listOfNotesIntent);
        }
    }

    private String getSubject() {
        if (subject_EditText.getText().toString().equals("")) {
            showToastMessage("Subject cannot be empty!");
        } else {
            return subject_EditText.getText().toString();
        }
        return null;
    }

    private String getContent() {
        if (content_EditText.getText().toString().equals("")) {
            showToastMessage("Content cannot be empty!");
        } else {
            return content_EditText.getText().toString();
        }
        return null;
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
