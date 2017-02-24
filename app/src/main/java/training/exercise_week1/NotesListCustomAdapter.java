package training.exercise_week1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import training.exercise_week1.model.Note;

/**
 * Created by usr on 2/8/2017.
 */

public class NotesListCustomAdapter extends ArrayAdapter<Note> {
    private final Context context;
    private final ArrayList<Note> notes;


    public NotesListCustomAdapter(Context context, ArrayList<Note> notes) {
        super(context, R.layout.listview_custom_row, notes);
        this.context = context;
        this.notes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_custom_row, parent, false);

        TextView noteSubject = (TextView) rowView.findViewById(R.id.noteSubjectTextView);
        TextView deleteButton = (TextView) rowView.findViewById(R.id.DeleteTextView);

//        field to display in the ListView
        noteSubject.setText(notes.get(position).getSubject());

        return rowView;
    }

}