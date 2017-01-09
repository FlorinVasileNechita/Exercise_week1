package training.exercise_week1;

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

    private ArrayList<String> listItems;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_notes);

        addNoteButton = (Button) findViewById(R.id.addNoteButton);
        listView = (ListView) findViewById(R.id.listView);
        listItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);

        listView.setAdapter(adapter);
        addElementInList("testPrim123-1");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void addElementInList(String noteSubject) {
        listItems.add(0, noteSubject);
        listView.setAdapter(null);
        listView.setAdapter(adapter);
        Toast.makeText(this, noteSubject, Toast.LENGTH_SHORT).show();
    }

    public void addNoteClicked(View view) {
        addElementInList("test 0");
    }


}
