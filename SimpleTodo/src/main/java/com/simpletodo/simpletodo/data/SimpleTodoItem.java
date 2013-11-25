/**
 * Created by Cory on 11/22/13.
 */

package com.simpletodo.simpletodo.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.simpletodo.simpletodo.SimpleTodoApp;

/**
 * Container class defining a SimpleTodoItem
 */
public class SimpleTodoItem {
    /**
     * Sentinel value for null id
     */
    private static final long NULL_ID = -1;
    /**
     * Sentinel value for no alarm
     * Keep in sync with R.integer.no_alarm
     */
    public static final long NO_ALARM = -1;

    /**
     * {@link android.content.SharedPreferences} key for the next generated to-do id
     */
    private static final String ID_KEY = Column.TODO_ID.getName();

    /**
     * Seed for the to-do id generation
     */
    private static final long ID_SEED = 1;

    /** The id of this to-do item */
    private long mId;

    /** The text of this to-do item */
    private String mText;

    /** The alarm for this to-do item */
    private long mAlarm;

    /**
     * Constructs a new to-do item with the given text
     * @param text The text of the to-do item
     */
    public SimpleTodoItem(final long id, final String text) {
        this(id, text, NO_ALARM);
    }

    /**
     * Constructs a new to-do item with the given text and alarm
     * @param text The text of the to-do item
     * @param alarm The alarm for the to-do item
     */
    public SimpleTodoItem(final long id, final String text, final long alarm) {
        mId = id;
        mText = text;
        mAlarm = alarm;
    }

    /**
     +     * Constructs a to-do item from table row
     +     */
    public SimpleTodoItem(final Cursor c) {
        this(c.getInt(c.getColumnIndex(Column.TODO_ID.getName())),
             c.getString(c.getColumnIndex(Column.TEXT.getName())),
             c.getLong(c.getColumnIndex(Column.ALARM.getName())) );
    }

    /** Returns the id of the to-do item */
    public long getId() { return mId; }

    /** Returns the text of the to-do item */
    public String getText() { return mText; }

    /** Returns the text of the to-do item */
    public long getAlarm() { return mAlarm; }

    /** Returns whether this to-do item has an alarm */
    public boolean hasAlarm() {
        return (mAlarm != NO_ALARM);
    }

    @Override
    public String toString() {
        return "["+Long.toString(mId)+", \""+mText+"\", "+Long.toString(mAlarm)+"]";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return mId == ((SimpleTodoItem) o).mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Constructs a new to-do item with a newly generated di
     * @param c
     * @param text
     * @return
     */
    public static SimpleTodoItem newInstance(final Context c, final String text) {
        return new SimpleTodoItem(SimpleTodoItem.generateTodoId(c), text);
    }

    /**
     * Generates a new unique id for a to-do item
     * @param c
     * @return the to-do id
     */
    public static long generateTodoId(final Context c) {
        final SharedPreferences sharedPrefs = c.getSharedPreferences(SimpleTodoApp.SHARED_PREFERENCES_FILENAME,
                                                                     Context.MODE_PRIVATE);
        final long todoId = sharedPrefs.getLong(ID_KEY, ID_SEED);
        sharedPrefs.edit().putLong(ID_KEY, todoId + 1).commit();
        return todoId;
    }
}
