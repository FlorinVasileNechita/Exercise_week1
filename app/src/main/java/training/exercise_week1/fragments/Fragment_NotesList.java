package training.exercise_week1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import training.exercise_week1.AddNote;
import training.exercise_week1.CustomAdapter;
import training.exercise_week1.Note;
import training.exercise_week1.R;
import training.exercise_week1.SomeFunctions;

import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;

/**
 * Created by zbarciog on 01/02/17.
 */

public class Fragment_NotesList extends Fragment {

    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayList<String> contents = new ArrayList<>();

    private ListAdapter adapter;
    private ListView listView;

    private SomeFunctions sf = new SomeFunctions();

    private boolean deleteActivated = false;
    private TextView clickRowToDelete;

    private Button addDateAndTime_Button;
    private Button addNoteButton_Button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        handleListView(view);

        handleAddDateAndTimeButton(view);

        handleClickToDeleteTextView(view);

        handleAddNoteButton(view);


        return view;
    }

    private void handleListView(View view) {
        adapter = new CustomAdapter(view.getContext(), subjects);
        listView = (ListView) view.findViewById(R.id.listView);
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
    }

    private void handleAddDateAndTimeButton(View view) {
        addDateAndTime_Button = (Button) view.findViewById(R.id.currentDate);
        addDateAndTime_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = sf.getCurrentDateAndTime();
                addElementInList("S" + str, "C" + str);
            }
        });
    }

    private void handleAddNoteButton(View view){
        addNoteButton_Button = (Button) view.findViewById(R.id.addNoteButton);
        addNoteButton_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddNoteActivity(null);
            }
        });
    }

    private void handleClickToDeleteTextView(View view) {
        clickRowToDelete = (TextView) view.findViewById(R.id.textView_clickToDelete);
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

    private void goToAddNoteActivity(Note note) {
//        Intent i = new Intent(this, AddNote.class);
        Intent i = new Intent(this.getContext(), AddNote.class);
        i.putExtra("Note", note);
        startActivityForResult(i, 1);
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
}
