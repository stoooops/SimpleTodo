package com.example.thetodoapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cory on 11/19/13.
 */
public class Database extends SQLiteOpenHelper {

    private static final String NAME = "database.db";
    public static final int VERSION = 1;

    public static final String TABLE_TODO = "[To-Do]";
//    public static final String TABLE_LISTS = "Lists";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TODO_ITEM = "to_do_item";

    private static final String TODO_CREATE = "create table "+TABLE_TODO+" ( "+
            COLUMN_ID+" integer primary key autoincrement, "+
            COLUMN_TODO_ITEM+" text not null );";

    public Database(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TODO_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // only one version so
        switch (newVersion) {
            case 1:
                break;
            default:
                break;
        }
    }
}
