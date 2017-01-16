package training.exercise_week1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    private SomeFunctions sf = new SomeFunctions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_notes);

        addNoteButton = (Button) findViewById(R.id.addNoteButton);
        listView = (ListView) findViewById(R.id.listView);

        if (adapter == null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
            Log.d("ADAPTER", "New adapter was created!");
        } else {
            Log.d("ADAPTER", "The adapter was already created");
        }

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String
                    }
                }
        );

    }

    //    http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Note note = (Note) data.getSerializableExtra("Note");
                sf.showToastMessage(this, note.getSubject(), true);
                addElementInList(note.getSubject(), note.getContent());
            }
        }
    }

    public void addNoteClicked(View view) {
        Intent i = new Intent(this, AddNote.class);
        startActivityForResult(i, 1);
    }

    public void dateAndTimeClicked(View view) {
        String str = sf.getCurrentDateAndTime();
        addElementInList(str, str);
    }

    public void addElementInList(String noteSubject, String noteContent) {
        listItems.add(0, new Note(noteSubject, noteContent));
        refreshList();
    }

    private void refreshList() {
        listView.setAdapter(null);
        listView.setAdapter(adapter);
    }



}
