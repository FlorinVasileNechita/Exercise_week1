package training.exercise_week1.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import training.exercise_week1.Note;
import training.exercise_week1.R;
import training.exercise_week1.fragments.Fragment_About;
import training.exercise_week1.fragments.Fragment_AddNote;
import training.exercise_week1.fragments.Fragment_NotesList;

/**
 * Created by florinnechita on 02/02/17.
 */

public class NavigationDrawerActivity extends AppCompatActivity implements Fragment_NotesList.ListenerFragment_NotesList {

    String[] navigationDrawerMenuItems;

    DrawerLayout navigationDrawerLayout;
    ListView navigationDrawerMenu;
    ArrayAdapter<String> navigationDrawerMenuAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_activity);

        navigationDrawerMenuItems = new String[]{"Notes list", "Edit note", "About"};

        navigationDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        navigationDrawerMenu = (ListView) findViewById(R.id.navigation_drawer_menu);
        navigationDrawerMenuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navigationDrawerMenuItems);

        navigationDrawerMenu.setAdapter(navigationDrawerMenuAdapter);
        navigationDrawerMenu.setSelector(android.R.color.holo_green_dark);
        navigationDrawerLayout.openDrawer(GravityCompat.START);

        navigationDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigationDrawerLayout.closeDrawers();
                if (position == 0) {
                    replaceFragment(new Fragment_NotesList());
                } else if (position == 1) {
                    replaceFragment(new Fragment_AddNote());
                } else if (position == 2) {
                    replaceFragment(new Fragment_About());
                }
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.navigation_drawer_content, fragment);
        fragmentTransaction.commitNow();
    }


    @Override
    public void updateNote(Note note) {
        if (note != null) {
            replaceFragment(new Fragment_AddNote());
            Fragment_AddNote fragment_addNote = (Fragment_AddNote) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_content);
            fragment_addNote.setFields(note);
//            TOOD: fragment_addNote.setArguments()
        } else {
            replaceFragment(new Fragment_AddNote());
        }
    }
}
