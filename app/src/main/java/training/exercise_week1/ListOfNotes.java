package training.exercise_week1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListOfNotes extends AppCompatActivity {

    private Button addNoteButton;
    private ListView listView;

    private ArrayList<Note> listItems = new ArrayList<>();
    private ListAdapter adapter;

    private Note noteReceived;

    private SomeFunctions sf = new SomeFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_notes);

        addNoteButton = (Button) findViewById(R.id.addNoteButton);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);

        handleReceivedData();

    }

    public void addNoteClicked(View view) {
        goToAddNote(null);
    }

    public void dateAndTimeClicked(View view) {
        String str = sf.getCurrentDateAndTime();
        addElementInList(str, str);
    }

    private void handleReceivedData() {
        Intent receivedIntent = getIntent();
        try {
            noteReceived = (Note) receivedIntent.getSerializableExtra("Note");
            addElementInList(noteReceived.getSubject(), noteReceived.getContent());
        } catch (Exception ex) {
            sf.showToastMessage(this, "No data received!", false);
        }
    }

    public void addElementInList(String noteSubject, String noteContent) {
        listItems.add(0, new Note(noteSubject, noteContent));
        refreshList();
    }

    private void refreshList() {
        listView.setAdapter(null);
        listView.setAdapter(adapter);
    }

    private void goToAddNote(Note note) {
        Intent intent = new Intent(this, AddNote.class);
        intent.putExtra("Note", note);
        startActivity(intent);
    }
}
