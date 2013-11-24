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
import android.widget.ImageView;
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

    /** A reference to the delete button */
    private ImageView mDelete;
    /** A reference to the delete button */
    private ImageView mEdit;
    /** A reference to the edit alarm button */
    private ImageView mEditAlarm;
    private TextView mAlarmText;

    private boolean mHasAlarm;

    private TodoItem mTodoItem;

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

    public void init(final Context c) {
        LayoutInflater.from(c).inflate(R.layout.todo_item_toolbar, this, true);

        final Resources resources = getResources();
        setPadding((int)resources.getDimension(R.dimen.toolbar_padding_horizontal),
                (int)resources.getDimension(R.dimen.toolbar_padding_top),
                (int)resources.getDimension(R.dimen.toolbar_padding_horizontal),
                (int)resources.getDimension(R.dimen.toolbar_padding_bottom));

        mDelete = (ImageView) findViewById(R.id.toolbar_icon_delete);
        mEdit = (ImageView) findViewById(R.id.toolbar_icon_edit);
        mEditAlarm = (ImageView) findViewById(R.id.toolbar_icon_edit_alarm);
        mAlarmText = (TextView) findViewById(R.id.toolbar_alarm_text);

        setAlarm(TodoItem.NO_ALARM); // alarm will be set when the layout is attached to a to-do item
    }

    public void bind(final TodoItem todoItem) {
        if (mTodoItem != null) {
            Logger.e("UI| Attempt to bind TodoItemToolbarLayout to todoItem " + todoItem +
                    " when already bound to " + mTodoItem);
            return;
        }
        mTodoItem = todoItem;
        setAlarm(todoItem.getAlarm());
        setOnClickListener( new OnAlarmChangeListener() );
    }

    public void show(final boolean show) {
        setVisibility((show) ? VISIBLE : GONE);
    }

    public void setAlarm(final long alarm) {
        final boolean mHasAlarm = (alarm == TodoItem.NO_ALARM);
        mEditAlarm.setImageResource((mHasAlarm) ?
                R.drawable.ic_action_alarms : R.drawable.ic_action_add_alarm);
        mAlarmText.setVisibility((mHasAlarm) ? VISIBLE : GONE);
        if (mHasAlarm) {
            mAlarmText.setText( toAlarmText(alarm) );
        }
    }

    private String toAlarmText(final long alarm) {
        return new SimpleDateFormat("HH:mm:ss.SSS dd MMM").format(System.currentTimeMillis());
    }

    private class OnAlarmChangeListener implements OnClickListener {
        @Override
        public void onClick(final View v) {
            if (mTodoItem == null) {
                Logger.e("Received OnAlarmListener.onClick() with no attached to-do item? Listener "+
                         "should not be attached.");
            }
            // for now, just swap the alarm
            mHasAlarm = !mHasAlarm;
            setAlarm((mHasAlarm) ? System.currentTimeMillis() : TodoItem.NO_ALARM);
        }
    }
}
