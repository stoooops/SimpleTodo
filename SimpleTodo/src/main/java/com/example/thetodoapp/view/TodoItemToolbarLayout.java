/**
 * Created by Cory on 11/22/13.
 */

package com.example.thetodoapp.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thetodoapp.R;
import com.example.thetodoapp.data.TodoItem;
import com.example.thetodoapp.util.Logger;

import java.text.SimpleDateFormat;

/**
 * A custom componenet for the to-do item of a TodoItemLayout
 */
public class TodoItemToolbarLayout extends RelativeLayout {

    /** A reference to the context */
    private Context mContext;

    /** A reference to the parent layout */
    private TodoItemLayout mTodoItemLayout;

    /** A reference to the delete button */
    private ImageButton mDelete;
    /** A reference to the edit text button */
    private ImageButton mEdit;
    /** A reference to the edit alarm button */
    private ImageButton mEditAlarm;
    /** A reference to the alarm text */
    private TextView mAlarmText;

    /** Whether the toolbar has an alarm set */
    private boolean mHasAlarm;

    /** A reference to the associated to-do item */
    private TodoItem mTodoItem;

    /**
     * Constructs a new TodoItemToolbarLayout from the provided attributes
     * @param c
     * @param attrs
     */
    public TodoItemToolbarLayout(final Context c, final AttributeSet attrs) {
        super(c, attrs);
        final TypedArray a = c.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TodoItemLayout, 0, 0);

        try {
            mHasAlarm = a.getBoolean(R.styleable.TodoItemToolbarLayout_hasAlarm, false);
        } finally {
            a.recycle();
        }

        init(c);
    }

    /**
     * Initializes the toolbar with no alarm set
     * @param c
     */
    public void init(final Context c) {
        LayoutInflater.from(c).inflate(R.layout.todo_item_toolbar, this, true);

        final Resources resources = getResources();
        setPadding((int)resources.getDimension(R.dimen.toolbar_padding_horizontal),
                (int)resources.getDimension(R.dimen.toolbar_padding_top),
                (int)resources.getDimension(R.dimen.toolbar_padding_horizontal),
                (int)resources.getDimension(R.dimen.toolbar_padding_bottom));

        mDelete = (ImageButton) findViewById(R.id.toolbar_icon_delete);
        mEdit = (ImageButton) findViewById(R.id.toolbar_icon_edit);
        mEditAlarm = (ImageButton) findViewById(R.id.toolbar_icon_edit_alarm);
        mAlarmText = (TextView) findViewById(R.id.toolbar_alarm_text);

        setAlarm(TodoItem.NO_ALARM); // alarm will be set when the layout is attached to a to-do item
    }

    /**
     * Attaches this {@link TodoItemTextView} to the given {@link TodoItemLayout} and initializes
     * listeners
     * @param til to attach to
     */
    public void attachParent(final TodoItemLayout til) {
        mTodoItemLayout = til;
        mEdit.setOnClickListener( mTodoItemLayout.new OnEditTextListener()) ;
    }

    /**
     * Bind the given todoItem to this toolbar
     * @param todoItem
     */
    public void bind(final TodoItem todoItem) {
        if (mTodoItem != null) {
            Logger.e("UI| Attempt to bind TodoItemToolbarLayout to todoItem " + todoItem +
                    " when already bound to " + mTodoItem);
            return;
        }
        mTodoItem = todoItem;
        setAlarm(todoItem.getAlarm());
        mEditAlarm.setOnClickListener(new OnAlarmChangeListener());
    }

    /**
     * Show or hide this toolbar
     * @param show
     */
    public void show(final boolean show) {
        setVisibility((show) ? VISIBLE : GONE);
    }

    /**
     * Set the alarm for this toolbar
     * @param alarm to set
     */
    public void setAlarm(final long alarm) {
        final boolean mHasAlarm = (alarm == TodoItem.NO_ALARM);
        mEditAlarm.setImageResource((mHasAlarm) ?
                R.drawable.ic_action_alarms : R.drawable.ic_action_add_alarm);
        mAlarmText.setVisibility((mHasAlarm) ? VISIBLE : GONE);
        if (mHasAlarm) {
            mAlarmText.setText( toAlarmText(alarm) );
        }
    }

    private boolean verifyAttached(final String msg) {
        final boolean isAttached = (mTodoItem != null);
        if (!isAttached) {
            Logger.e(msg);
        }
        return isAttached;
    }

    /**
     * Returns the date format of this alarm
     * @param alarm to get date format of
     * @return alarm text
     */
    private String toAlarmText(final long alarm) {
        return new SimpleDateFormat("HH:mm:ss.SSS dd MMM").format(System.currentTimeMillis());
    }

    /**
     * Listener to handle taps to the alarm button and change the alarm
     */
    private class OnAlarmChangeListener implements OnClickListener {
        @Override
        public void onClick(final View v) {
            final String unAttachedMsg = "Received OnAlarmListener.onClick() with no attached " +
                                        "to-do item? Listener should not be attached.";
            if (!verifyAttached(unAttachedMsg)) {
                return;
            }
            // for now, just swap the alarm
            mHasAlarm = !mHasAlarm;
            setAlarm((mHasAlarm) ? System.currentTimeMillis() : TodoItem.NO_ALARM);
        }
    }
}
