package training.exercise_week1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListOfNotes extends AppCompatActivity {

    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayList<String> contents = new ArrayList<>();

    private ListAdapter adapter;
    private ListView listView;

    private SomeFunctions sf = new SomeFunctions();

    private boolean deleteActivated = false;
    private TextView clickRowToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_notes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list_of_notes);
        setSupportActionBar(toolbar);

        adapter = new CustomAdapter(this, subjects);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Note note = new Note(subjects.get(position), contents.get(position));
                        if (deleteActivated) {
                            deleteNote(note);
                        } else {
                            itemClicked(note);
                        }
                    }
                }
        );

        clickRowToDelete = (TextView) findViewById(R.id.textView_clickToDelete);
        handleClickToDeleteTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.items_toolbar_list_of_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.editButton) {
            deleteActivated = !deleteActivated;
            handleClickToDeleteTextView();
            sf.showToastMessage(this, "Edit button", false);
        }
        return false;
    }

    private void handleClickToDeleteTextView() {
        if (!deleteActivated) {
            clickRowToDelete.setVisibility(View.GONE);
        } else {
            clickRowToDelete.setVisibility(View.VISIBLE);
        }
    }

    public void itemClicked(Note note) {
        goToAddNoteActivity(note);
    }

    private void deleteNote(Note note) {
        subjects.remove(note.getSubject());
        contents.remove(note.getContent());
        refreshList();
    }

    //    http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Note note = (Note) data.getSerializableExtra("Note");
                addElementInList(note.getSubject(), note.getContent());
            }
            if (resultCode == RESULT_FIRST_USER) {
                Note note = (Note) data.getSerializableExtra("Note");
                Note oldNote = (Note) data.getSerializableExtra("OldNote");
                updateElementInList(note, oldNote);
            }
        }
    }

    private void goToAddNoteActivity(Note note) {
        Intent i = new Intent(this, AddNote.class);
        i.putExtra("Note", note);
        startActivityForResult(i, 1);
    }

    public void addNoteClicked(View view) {
        goToAddNoteActivity(null);
    }

    public void dateAndTimeClicked(View view) {
        String str = sf.getCurrentDateAndTime();
        addElementInList("S" + str, "C" + str);
    }

    public void addElementInList(String noteSubject, String noteContent) {
//        listItems.add(0, new Note(noteSubject, noteContent));
        subjects.add(0, noteSubject);
        contents.add(0, noteContent);
        refreshList();
    }

    public void updateElementInList(Note note, Note oldNote) {
        int pos = 0;
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).equals(oldNote.getSubject())) {
                pos = i;
                break;
            }
        }
        subjects.set(pos, note.getSubject());
        contents.set(pos, note.getContent());

        refreshList();

    }

    private void refreshList() {
        listView.setAdapter(null);
        listView.setAdapter(adapter);
    }

}
