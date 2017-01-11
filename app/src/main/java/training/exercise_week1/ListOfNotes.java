package training.exercise_week1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListOfNotes extends AppCompatActivity {

    private Button addNoteButton;
    private ListView listView;

    private ArrayList<Note> listItems = new ArrayList<>();
    private ListAdapter adapter;

    private Note noteReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_notes);

        addNoteButton = (Button) findViewById(R.id.addNoteButton);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);

//        listView.setAdapter(adapter);
        addElementInList("testPrim123-1", "content");

        handleReceivedData();

    }

    private void handleReceivedData() {
        Intent receivedIntent = getIntent();
        try {
            noteReceived = (Note) receivedIntent.getSerializableExtra("Note");
            addElementInList(noteReceived.getSubject(), noteReceived.getContent());
        } catch (Exception ex) {
            showToastMessage("No data received!");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putSerializable("ArrayList<Note>",listItems);
        outState.putSerializable("listItems", listItems);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listItems = (ArrayList<Note>) savedInstanceState.getSerializable("listItems");
        refreshList();
    }

    public void addElementInList(String noteSubject, String noteContent) {
        listItems.add(0, new Note(noteSubject, noteContent));
        refreshList();
        Toast.makeText(this, noteSubject, Toast.LENGTH_SHORT).show();
    }

    private void refreshList() {
        listView.setAdapter(null);
        listView.setAdapter(adapter);

    }

    public void addNoteClicked(View view) {
        goToAddNote(null);
    }

    private void goToAddNote(Note note) {
        Intent intent = new Intent(this, AddNote.class);
        intent.putExtra("Note", note);
        startActivity(intent);
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
