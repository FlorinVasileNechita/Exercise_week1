package training.exercise_week1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import training.exercise_week1.fragments.Fragment_About;
import training.exercise_week1.fragments.Fragment_AddNote;
import training.exercise_week1.fragments.Fragment_NotesList;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

/**
 * Created by florinnechita on 31/01/17.
 */

// https://www.learn2crack.com/2014/06/android-sliding-navigation-drawer-example.html
public class LeftDrawer extends AppCompatActivity implements Fragment_NotesList.ListenerFragment_NotesList {

    String[] menu;

    DrawerLayout drawerLayout;
    ListView drawerListView;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_left_drawer);

        menu = new String[]{"Notes list", "New note", "About"};

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.left_drawer);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu);

        drawerListView.setAdapter(adapter);
        drawerListView.setSelector(android.R.color.holo_blue_bright);

        drawerLayout.openDrawer(GravityCompat.START);

        changeFragment(new Fragment_NotesList());

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
                Toast.makeText(view.getContext(), menu[position], Toast.LENGTH_SHORT).show();
                if (menu[position].equals("Notes list")) {
//                    TODO: change to load the proper fragments
                    Log.d("TAG", "Notes list button clicked");
                    changeFragment(new Fragment_NotesList());
                }

                if (menu[position].equals("New note")) {
                    menu_NewNoteButtonClicked();
                }

                if (menu[position].equals("About")) {
                    Log.d("TAG", "About button clicked");
                    changeFragment(new Fragment_About());
                }
            }
        });

    }

    private void menu_NewNoteButtonClicked() {
        Log.d("TAG", "New note button clicked");
        changeFragment(new Fragment_AddNote());
    }

    private void changeFragment(Fragment fragmentName) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.content_frame, fragmentName);
        fragmentTransaction.replace(R.id.fragment, fragmentName);
        fragmentTransaction.setTransition(TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }


    @Override
    public void addNote() {
        menu_NewNoteButtonClicked();
    }

    @Override
    public void updateNote(Note note) {
        menu_NewNoteButtonClicked();
        Log.d("Tag","Updating note");
        Fragment_AddNote fragment_addNote = (Fragment_AddNote) getSupportFragmentManager().findFragmentById(R.id.fragment);
//        Fragment_AddNote fragment_addNote = (Fragment_AddNote) getSupportFragmentManager().findFragmentByTag("Fragment_AddNote");
        fragment_addNote.setFields(note);
    }
}