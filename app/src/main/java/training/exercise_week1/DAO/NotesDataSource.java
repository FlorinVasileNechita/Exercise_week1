package training.exercise_week1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public void createNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_SUBJECT, note.getSubject());
        values.put(MySQLiteHelper.COLUMN_CONTENT, note.getContent());
        open();
        database.insert(MySQLiteHelper.TABLE_NOTES, null, values);
        close();


//        ContentValues values = new ContentValues();
//        values.put(MySQLiteHelper.COLUMN_SUBJECT, note.getSubject());
//        values.put(MySQLiteHelper.COLUMN_CONTENT, note.getContent());
//
//        long insertId = database.insert(MySQLiteHelper.TABLE_NOTES, null, values);
//
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTES,
//                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//                null, null, null);
//
//        cursor.moveToFirst();
//        Note note = cursorTo
    }

    public void deleteNote(Note note) {
        open();
        database.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_NOTES + " WHERE " + MySQLiteHelper.COLUMN_ID + "=\"" + note.getId() + "\";");
        close();
    }

    public void printDB() {
        open();
        String query = "SELECT * FROM " + MySQLiteHelper.TABLE_NOTES + " WHERE 1";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Log.i("BD", cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID) + "  " + cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID) + "  " + cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID));
            cursor.moveToNext();
        }

        cursor.close();
        Log.i("DB", "DONE");
        close();
    }


}



















