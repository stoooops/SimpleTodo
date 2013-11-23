/**
 * Created by Cory on 11/20/13.
 */

package com.example.thetodoapp.data;

/** Container and utility class for column and associated data */
public class Column {

    /** The required _id column */
    public static final Column _ID = new Column("_id");
    /** The to_do item column */
    public static final Column TEXT = new Column("to_do_item");
    /** The alarm column */
    public static final Column ALARM = new Column("alarm");

    /** The column name */
    private final String mName;

    /** Construct a new column with the given name */
    public Column(final String name) {
        mName = name;
    }

    /** Returns the name of this column */
    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return mName;
    }
}