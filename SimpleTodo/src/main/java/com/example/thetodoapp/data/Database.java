/**
 * Created by Cory on 11/19/13.
 */

package com.example.thetodoapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * The interface for the database
 */
public class Database extends ContentProvider {

    /** The database helper reference */
    private DatabaseHelper mDbHelper;
    /** UriMatcher to help Uri lookup */
    private static final UriMatcher sUriMatcher;

    /** The provider authority string */
    public static final String AUTHORITY = "com.example.thetodoapp.data.database";
    /** The content uri */
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    /** The name of the database file */
    private static final String NAME = "database.db";
    /** The database version */
    public static final int VERSION = 1;

    /** The URI code for the to_do table */
    private static final int URI_CODE_TABLE_TODO = 1;

    /** Setup expected table Uris */
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, Table.TODO.getName(), URI_CODE_TABLE_TODO);
    }

    @Override
    public boolean onCreate () {
        mDbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public int delete(final Uri uri, final String where, final String[] whereArgs) {
        final int numRowsAffected;
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        switch(sUriMatcher.match(uri)) {
            case URI_CODE_TABLE_TODO:
                numRowsAffected = db.delete(Table.TODO.getName(), where, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numRowsAffected;
    }

    @Override
    public Cursor query(final Uri uri, final String[] projection, final String where,
                        final String[] whereArgs, final String sortOrder) {
        final Cursor c;
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        switch(sUriMatcher.match(uri)) {
            case URI_CODE_TABLE_TODO:
                c = db.query(Table.TODO.getName(), projection, where, whereArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        final ContentValues insertValues;
        if (values == null) {
            insertValues = new ContentValues();
        } else {
            insertValues = new ContentValues(values);
        }

        final Table table;
        final long newRowId;
        switch (sUriMatcher.match(uri)) {
            case URI_CODE_TABLE_TODO:
                table = Table.TODO;
                newRowId = db.insert(table.getName(), Column.TODO_ITEM.getName(), insertValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
        if (newRowId == -1) {
            return null;
        }

        final Uri newRowUri = ContentUris.withAppendedId(table.getUri(), newRowId);
        getContext().getContentResolver().notifyChange(newRowUri, null);
        return newRowUri;

    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String where,
                      final String[] whereArgs) {
        final int numRowsAffected;
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case URI_CODE_TABLE_TODO:
                numRowsAffected = db.update(Table.TODO.getName(), values, where, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return numRowsAffected;
    }


    @Override
    public String getType(final Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case URI_CODE_TABLE_TODO:
                return Table.TODO.getType();
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
    }

    /** A Database Helper class */
    private class DatabaseHelper extends SQLiteOpenHelper {
        /**
         * Instantiates an open helper for the provider's SQLite data repository
         * @param context
         */
        private DatabaseHelper(final Context context) {
            super(context, NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Table.TODO.getCreateStatement());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            switch (oldVersion) {
                case 1:
                    // do upgrade to version 2 from version 1
                case 2:
                    // do upgrade to version 3 from version 2
                default:
                    // no more upgrade needed
                    break;
            }
        }
    }
}