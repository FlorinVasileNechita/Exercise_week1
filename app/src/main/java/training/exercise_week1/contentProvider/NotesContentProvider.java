package training.exercise_week1.contentProvider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import training.exercise_week1.database.NotesDatabaseHelper;

/**
 * Created by florinnechita on 16/02/17.
 */

public class NotesContentProvider extends ContentProvider {

    private NotesDatabaseHelper notesDatabaseHelper;

    private static final int NOTES = 10;
    private static final int NOTES_ID = 20;

    private static final String AUTHORITY = "training.exercise_week1.contentProvider";

    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONNTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/notes";
    public static final String CONTENT_ITEM_BASE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "note";


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
