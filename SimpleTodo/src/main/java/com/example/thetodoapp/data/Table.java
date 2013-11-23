/**
 * Created by Cory on 11/20/13.
 */

package com.example.thetodoapp.data;

import android.net.Uri;

/** Container and utility class for table and associated data */
public class Table {

    /** THe to_do table */
    public static final Table TODO = new Table(
            "to_do",
            new String[]{Column.TODO_ITEM.getName()},
            new String[]{"text not null"});

    /** The table name */
    private final String mName;

    /** The table uri */
    private final Uri mUri;

    /** The table MIME type */
    private final String mType;

    /** The table create statement */
    private final String mCreateString;

    /** Construct a new Table with given name and columns */
    public Table(final String name,
                  final String[] columnNames,
                  final String[] columnDataTypes) {
        // verify input
        if (columnNames.length != columnDataTypes.length) {
            throw new IllegalArgumentException("columnNames.length != columnDataTypes.length");
        }

        mName = name;
        mUri = Uri.withAppendedPath(Database.CONTENT_URI, name);
        mType = "vnd.android.cursor.dir/vnd.com.example.data.database."+name;

        // create create-statement string
        final StringBuilder sb = new StringBuilder();
        sb.append("create table "+name+" ( "+
                Column._ID.getName()+" integer primary key autoincrement,");
        for (int i = 0; i < columnNames.length; i++) {
            sb.append(" "+columnNames[i]+" "+columnDataTypes[i]);
        }
        sb.append(");");
        mCreateString = sb.toString();
    }

    /** Returns the uri of this table */
    public Uri getUri() {
        return mUri;
    }

    /** Returns the name of this table */
    public String getName() {
        return mName;
    }

    /** Returns the type of the content in this table */
    public String getType() {
        return mType;
    }

    /** Returns the create statement for this table */
    public String getCreateStatement() {
        return mCreateString;
    }

    @Override
    public String toString() {
        return mName.toString();
    }
}