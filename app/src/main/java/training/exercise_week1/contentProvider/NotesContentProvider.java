package training.exercise_week1.contentProvider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashSet;

import training.exercise_week1.database.NotesDatabaseHelper;
import training.exercise_week1.database.NotesTable;
import training.exercise_week1.model.Note;

/**
 * Created by florinnechita on 16/02/17.
 */

public class NotesContentProvider extends ContentProvider {

    private NotesDatabaseHelper notesDatabaseHelper;

    private static final int NOTES = 10;
    private static final int NOTE_ID = 20;

    private static final String AUTHORITY = "training.exercise_week1.contentProvider";

    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/notes";
    public static final String CONTENT_ITEM_BASE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "note";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, NOTES);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTE_ID);
    }


    @Override
    public boolean onCreate() {
        notesDatabaseHelper = new NotesDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkNotesProjections(projection);

        // Set the table
        sqLiteQueryBuilder.setTables(NotesTable.TABLE_NOTES);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case NOTES:
                break;
            // adding the ID to the original query
            case NOTE_ID:
                sqLiteQueryBuilder.appendWhere(NotesTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase sqLiteDatabase = notesDatabaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = notesDatabaseHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case NOTES:
                id = sqLiteDatabase.insert(NotesTable.TABLE_NOTES, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = notesDatabaseHelper.getWritableDatabase();
        int rowsDeleted;
        switch (uriType) {
            case NOTES:
                rowsDeleted = sqLiteDatabase.delete(NotesTable.TABLE_NOTES, selection, selectionArgs);
                break;
            case NOTE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqLiteDatabase.delete(NotesTable.TABLE_NOTES, NotesTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqLiteDatabase.delete(NotesTable.TABLE_NOTES, NotesTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = notesDatabaseHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case NOTES:
                rowsUpdated = sqLiteDatabase.update(NotesTable.TABLE_NOTES, values, selection, selectionArgs);
                break;
            case NOTE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqLiteDatabase.update(NotesTable.TABLE_NOTES, values, NotesTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqLiteDatabase.update(NotesTable.TABLE_NOTES, values, NotesTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkNotesProjections(String[] projections) {
        String[] available = {NotesTable.COLUMN_SUBJECT, NotesTable.COLUMN_CONTENT};

        if (projections != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projections));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));
            if (!availableColumns.containsAll(requestedColumns)) {
                Log.d(NotesContentProvider.class.getName(), "Requested projection cannot be completed");
                throw new IllegalArgumentException("Unknown columns in projection!");
            }
        }

    }
}
