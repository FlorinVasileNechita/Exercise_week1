package training.exercise_week1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by usr on 2/8/2017.
 */

public class NotesListCustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;


    public NotesListCustomAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.listview_custom_row, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_custom_row, parent, false);

        TextView noteSubject = (TextView) rowView.findViewById(R.id.noteSubjectTextView);
//        Button deleteButton = (Button) rowView.findViewById(R.id.deleteNoteButton);
        TextView deleteButton = (TextView) rowView.findViewById(R.id.DeleteTextView);
        noteSubject.setText(values.get(position));

        // Change icon based on name

        return rowView;
    }

}