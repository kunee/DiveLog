package de.kunee.divelog.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.text.TextUtils;

import static de.kunee.divelog.data.DiveLogContract.CONTENT_AUTHORITY;
import static de.kunee.divelog.data.DiveLogContract.Dives;

public class DiveLogContentProvider extends ContentProvider {

    private static final int DIVES = 1;
    private static final int DIVE_WITH_ID = 2;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = CONTENT_AUTHORITY;
        String path = Dives.PATH;
        uriMatcher.addURI(authority, path, DIVES);
        uriMatcher.addURI(authority, path + "/#", DIVE_WITH_ID);
        return uriMatcher;
    }

    private DatabaseOpenHelper db;

    @Override
    public boolean onCreate() {
        db = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        final int match = uriMatcher.match(uri);
        switch (match) {
            case DIVES:
                return Dives.CONTENT_DIR_TYPE;
            case DIVE_WITH_ID:
                return Dives.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor resultCursor;
        final int match = uriMatcher.match(uri);
        switch (match) {
            case DIVES:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = Dives._ID + " DESC";
                break;
            case DIVE_WITH_ID:
                if (selection == null) selection = "";
                if (!TextUtils.isEmpty(selection)) selection += " AND ";
                selection += Dives._ID + " = " + uri.getLastPathSegment();
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        resultCursor = db.getReadableDatabase().query(
                Dives.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        resultCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return resultCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri resultUri;
        final int match = uriMatcher.match(uri);
        switch (match) {
            case DIVES:
                long id = db.getWritableDatabase().insert(
                        Dives.TABLE_NAME,
                        null,
                        values
                );
                if (id > 0) {
                    resultUri = ContentUris.withAppendedId(Dives.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int rowsDeleted = 0;
        if (TextUtils.isEmpty(selection)) selection = "1";
        final int match = uriMatcher.match(uri);
        switch (match) {
            case DIVES:
                rowsDeleted += db.getWritableDatabase().delete(
                        Dives.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int rowsUpdated = 0;
        final int match = uriMatcher.match(uri);
        switch (match) {
            case DIVES:
                rowsUpdated += db.getWritableDatabase().update(
                        Dives.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
