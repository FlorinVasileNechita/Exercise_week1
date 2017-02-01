package training.exercise_week1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by florinnechita on 31/01/17.
 */

// https://www.learn2crack.com/2014/06/android-sliding-navigation-drawer-example.html
public class LeftDrawer extends AppCompatActivity {

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

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
                Toast.makeText(view.getContext(), menu[position], Toast.LENGTH_SHORT).show();
                if(menu[position].equals("Notes list")){
//                    TODO: change to load the proper fragments
//                    Intent i = new Intent(LeftDrawer.this, ListOfNotes.class);
//                    startActivity(i);
                }
            }
        });

    }
}