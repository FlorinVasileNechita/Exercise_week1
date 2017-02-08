package training.exercise_week1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import training.exercise_week1.model.Note;

public class NotesDb {

    private SQLiteDatabase database;
    private NotesSqlLiteHelper notesSqlLiteHelper;
    private String[] allColumns = {notesSqlLiteHelper.COLUMN_ID, notesSqlLiteHelper.COLUMN_SUBJECT, notesSqlLiteHelper.COLUMN_CONTENT};

    public NotesDb(Context context) {
        notesSqlLiteHelper = new NotesSqlLiteHelper(context);
    }

    private void openDb() {
        database = notesSqlLiteHelper.getWritableDatabase();
    }

    public void closeDb() {
        notesSqlLiteHelper.close();
    }

    public Note createNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(NotesSqlLiteHelper.COLUMN_SUBJECT, note.getSubject());
        values.put(NotesSqlLiteHelper.COLUMN_CONTENT, note.getContent());

        openDb();
        long insertId = database.insert(NotesSqlLiteHelper.TABLE_NOTES, null, values);

        Cursor cursor = database.query(NotesSqlLiteHelper.TABLE_NOTES,
                allColumns, NotesSqlLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        closeDb();
        return newNote;
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();

        openDb();
        Cursor cursor = database.query(NotesSqlLiteHelper.TABLE_NOTES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeDb();
        return notes;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setSubject(cursor.getString(1));
        note.setContent(cursor.getString(2));
        return note;
    }

}


















