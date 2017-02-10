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

    private void closeDb() {
        notesSqlLiteHelper.close();
    }

    public Note addNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(NotesSqlLiteHelper.COLUMN_SUBJECT, note.getSubject());
        values.put(NotesSqlLiteHelper.COLUMN_CONTENT, note.getContent());

        openDb();
        long insertId = database.insert(NotesSqlLiteHelper.TABLE_NOTES, null, values);

        Cursor cursor = database.query(NotesSqlLiteHelper.TABLE_NOTES, allColumns, NotesSqlLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        closeDb();
        return newNote;
    }

    public ArrayList<Note> getNotesOrderById(boolean desc) {
        ArrayList<Note> notes = new ArrayList<>();

        openDb();
        Cursor cursor;
        if (desc) {
            cursor = database.query(NotesSqlLiteHelper.TABLE_NOTES, allColumns, null, null, null, null, allColumns[0] + " DESC");
        } else {
            cursor = database.query(NotesSqlLiteHelper.TABLE_NOTES, allColumns, null, null, null, null, allColumns[0] + " ASC");
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }

        cursor.close();
        closeDb();
        return notes;
    }

    public void deleteNote(Note note) {
        openDb();
        database.delete(NotesSqlLiteHelper.TABLE_NOTES, allColumns[0] + "=" + note.getId(), null);
        closeDb();
    }

    public void updateNote(Note note) {
        openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(allColumns[1], note.getSubject());
        contentValues.put(allColumns[2], note.getContent());
        database.update(NotesSqlLiteHelper.TABLE_NOTES, contentValues, allColumns[0] + "=" + note.getId(), null);
        closeDb();
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setSubject(cursor.getString(1));
        note.setContent(cursor.getString(2));
        return note;
    }

}



















