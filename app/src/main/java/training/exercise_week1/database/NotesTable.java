package training.exercise_week1.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class NotesTable {

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_CONTENT = "content";

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;


    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "create table " + TABLE_NOTES
                    + "( "
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_SUBJECT + " text not null, "
                    + COLUMN_CONTENT + " text not null"
                    + ");";


    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(NotesTable.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }
}