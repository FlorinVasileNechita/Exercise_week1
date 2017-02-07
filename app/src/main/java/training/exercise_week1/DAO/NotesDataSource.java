package training.exercise_week1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import training.exercise_week1.Note;

/**
 * Created by florinnechita on 07/02/17.
 */

public class NotesDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_SUBJECT, MySQLiteHelper.COLUMN_CONTENT};

    public NotesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Note createNote(Note note) {
        /**
         ContentValues values = new ContentValues();
         values.put(MySQLiteHelper.COLUMN_SUBJECT, note.getSubject());
         values.put(MySQLiteHelper.COLUMN_CONTENT, note.getContent());
         open();
         database.insert(MySQLiteHelper.TABLE_NOTES, null, values);
         close();
         Log.d("DB_ADDED", note.getSubject() + " " + note.getContent());
         */
open();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_SUBJECT, note.getSubject());
        values.put(MySQLiteHelper.COLUMN_CONTENT, note.getContent());

        long insertId = database.insert(MySQLiteHelper.TABLE_NOTES, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }

    /**
     * public void deleteNote(Note note) {
     * open();
     * String deleteQuery = "DELETE FROM " + MySQLiteHelper.TABLE_NOTES + " WHERE " + MySQLiteHelper.COLUMN_ID + "=\"" + note.getId() + "\";";
     * Log.i("DB_QUERY", deleteQuery);
     * database.execSQL(deleteQuery);
     * close();
     * }
     * <p>
     * public void printDB() {
     * open();
     * String selectQuery = "SELECT * FROM " + MySQLiteHelper.TABLE_NOTES;
     * Log.i("DB_QUERY", selectQuery);
     * Cursor cursor = database.rawQuery(selectQuery, null);
     * cursor.moveToFirst();
     * <p>
     * while (!cursor.isAfterLast()) {
     * Log.i("BD", cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID) + "  " + cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID) + "  " + cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID));
     * cursor.moveToNext();
     * }
     * <p>
     * cursor.close();
     * Log.i("DB", "DONE");
     * close();
     * }
     */

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();

        open();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
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



















