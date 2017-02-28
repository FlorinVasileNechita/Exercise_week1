package training.exercise_week1.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import training.exercise_week1.model.Note;
import training.exercise_week1.R;
import training.exercise_week1.fragments.AboutFragment;
import training.exercise_week1.fragments.EditNoteFragment;
import training.exercise_week1.fragments.NotesListFragment;

public class NavigationDrawerActivity extends AppCompatActivity implements NotesListFragment.NotesListFragmentListener, EditNoteFragment.EditNoteFragmentListener {

    String[] navigationDrawerMenuItems;

    DrawerLayout navigationDrawerLayout;
    ListView navigationDrawerMenu;
    ArrayAdapter<String> navigationDrawerMenuAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_activity);

        navigationDrawerMenuItems = new String[]{"Notes list", "Edit note", "About", "Settings"};

        navigationDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        navigationDrawerMenu = (ListView) findViewById(R.id.navigation_drawer_menu);
        navigationDrawerMenuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navigationDrawerMenuItems);

        navigationDrawerMenu.setAdapter(navigationDrawerMenuAdapter);
        navigationDrawerMenu.setSelector(android.R.color.holo_green_dark);

        replaceFragment(new NotesListFragment(), null);
        navigationDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigationDrawerLayout.closeDrawers();
                if (position == 0) {
                    replaceFragment(new NotesListFragment(), null);
                } else if (position == 1) {
                    replaceFragment(new EditNoteFragment(), null);
                } else if (position == 2) {
                    replaceFragment(new AboutFragment(), null);
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment, Bundle args) {
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.navigation_drawer_content, fragment);
        fragmentTransaction.commitNow();
    }

    @Override
    public void updateNote(Note note) {
        Bundle args = new Bundle();
        if (note != null) {
            args.putSerializable("Note", note);
            replaceFragment(new EditNoteFragment(), args);
        } else {
            replaceFragment(new EditNoteFragment(), null);
        }
    }

    @Override
    public void saveChanges(Note note) {
        replaceFragment(new NotesListFragment(), null);
        if (note != null) {
            NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_content);
            if (note.getId() > 0) {
                notesListFragment.updateNote(note);
            } else {
                notesListFragment.addNote(note);
            }
        }
    }
}
