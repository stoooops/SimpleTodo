/**
 * Created by Cory on 11/20/13.
 */

package com.simpletodo.simpletodo.data;

/** Container and utility class for column and associated data */
public enum Column {

    TODO_ID("_id", " integer primary key "),
    TEXT("todo_text", " text not null "),
    ALARM("todo_alarm", " integer default "+ SimpleTodoItem.NO_ALARM+" ");

    /** The column name */
    private final String mName;

    /** The column data type */
    private final String mDataType;

    /** Construct a new column with the given name */
    private Column(final String name, final String dataType) {
        mName = name;
        mDataType = dataType;
    }

    /** Returns the name of this column */
    public String getName() { return mName; }
    /** Returns the data type of this column */
    public String getDataType() { return mDataType; }

    @Override
    public String toString() {
        return mName;
    }

}