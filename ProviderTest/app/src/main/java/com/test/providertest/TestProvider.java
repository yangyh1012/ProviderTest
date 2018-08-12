package com.test.providertest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * @author Yangyihui
 * @create 2018/8/12
 * @Describe
 */
public class TestProvider extends ContentProvider {

    private TestDBHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new TestDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (buildUriMatcher().match(uri)) {
            case TEST:
                cursor = db.query(TestContract.ProviderEntry.TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        long _id;

        switch (buildUriMatcher().match(uri)) {
            case TEST:
            {
                Cursor cursor = query(TestContract.CONTENT_URI,new String[]{TestContract.ProviderEntry.COLUMN_NAME,TestContract.ProviderEntry.COLUMN_VALUE},TestContract.ProviderEntry.COLUMN_NAME+" =? ",
                        new String[]{(String)values.get(TestContract.ProviderEntry.COLUMN_NAME)},null);
                if (cursor != null && cursor.moveToFirst()) {

                    ContentValues updateValues = new ContentValues();
                    updateValues.put(TestContract.ProviderEntry.COLUMN_VALUE, values.get(TestContract.ProviderEntry.COLUMN_VALUE) + "");
                    update(uri, updateValues, TestContract.ProviderEntry.COLUMN_NAME+" =? ", new String[]{(String)values.get(TestContract.ProviderEntry.COLUMN_NAME)});
                    cursor.close();

                    return null;
                } else {
                    cursor.close();
                    _id = db.insert(TestContract.ProviderEntry.TABLE_NAME, null, values);
                    if ( _id > 0 ) {

                        returnUri = TestContract.ProviderEntry.buildUri(_id);
                    } else {

                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
            }
            break;
            default:
                throw new android.database.SQLException("Unknown uri: " + uri);
        }

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int row = db.update(TestContract.ProviderEntry.TABLE_NAME, values, selection, selectionArgs);
        return row;
    }

    private final static int TEST = 100;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TestContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, TestContract.PATH_TEST, TEST);

        return matcher;
    }
}
