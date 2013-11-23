/**
 * Created by Cory on 11/22/13.
 */

package com.example.thetodoapp.data;

/**
 * Container class defining a TodoItem
 */
public class TodoItem {

    /**
     * Sentinal value for no alarm
     * Keep in sync with R.integer.no_alarm
     */
    public static final long NO_ALARM = -1;

    /** The text of this to-do item */
    private String mText;

    /** The alarm for this to-do item */
    private long mAlarm;

    /**
     * Constructs a new to-do item with the given text
     * @param text The text of the to-do item
     */
    public TodoItem(final String text) {
        this(text, NO_ALARM);
    }

    public TodoItem(final String text, final long alarm) {
        mText = text;
        mAlarm = alarm;
    }

    /** Returns the text of the to-do item */
    public String getText() { return mText; }

    /** Returns the text of the to-do item */
    public long getAlarm() { return mAlarm; }

    /** Returns whether this to-do item has an alarm */
    public boolean hasAlarm() {
        return (mAlarm != NO_ALARM);
    }
}
