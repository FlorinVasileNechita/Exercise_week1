package training.exercise_week1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;


    public CustomAdapter(Context context, ArrayList<String> values) {
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

