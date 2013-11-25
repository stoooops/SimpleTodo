/**
 * Created by Cory on 11/20/13.
 */

package com.simpletodo.simpletodo.data;

import android.net.Uri;

/** Container and utility class for table and associated data */
public enum Table {

    /** The to_do table */
    TODO("to_do", new Column[]{Column.TODO_ID,  Column.TEXT, Column.ALARM});//new Column[]{Column.TODO_ID});

    /** The table name */
    private final String mName;

    /** The table uri */
    private final Uri mUri;

    /** The table MIME type */
    private final String mType;

    /** The table create statement */
    private final String mCreateString;

    /** Construct a new Table with given name and columns */
    private Table(final String name, final Column[] columns){
        mName = name;
        mUri = Uri.withAppendedPath(Database.CONTENT_URI, name);
        mType = "vnd.android.cursor.dir/vnd.com.example.data.database."+name;

        // create create-statement string
        final StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+name+" ( "+
                columns[0].getName()+" "+columns[0].getDataType());
        for (int i = 1; i < columns.length; i++) {
            sb.append(", "+columns[i].getName()+" "+columns[i].getDataType());
        }

        sb.append(");");
        mCreateString = sb.toString();
    }

    /** Returns the uri of this table */
    public Uri getUri() { return mUri; }
    /** Returns the name of this table */
    public String getName() { return mName; }
    /** Returns the type of the content in this table */
    public String getType() { return mType; }
    /** Returns the create statement for this table */
    public String getCreateStatement() { return mCreateString; }

    @Override
    public String toString() { return mName; }
}