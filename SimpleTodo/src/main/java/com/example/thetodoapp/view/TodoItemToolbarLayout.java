/**
 * Created by Cory on 11/22/13.
 */

package com.example.thetodoapp.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thetodoapp.R;
import com.example.thetodoapp.data.TodoItem;

public class TodoItemToolbarLayout extends RelativeLayout {

    private ImageView mDelete;
    private ImageView mEdit;
    private ImageView mAddAlarm;
    private ImageView mEditAlarm;
    private TextView mAlarmText;

    private long mAlarm;

    public TodoItemToolbarLayout(final Context c, final AttributeSet attrs) {
        super(c, attrs);
        final TypedArray a = c.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TodoItemLayout, 0, 0);

        mAlarm = TodoItem.NO_ALARM;
        try {
            mAlarm = a.getInteger(R.styleable.TodoItemToolbarLayout_alarm, (int)TodoItem.NO_ALARM);
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
        mAddAlarm = (ImageView) findViewById(R.id.toolbar_icon_add_alarm);
        mEditAlarm = (ImageView) findViewById(R.id.toolbar_icon_edit_alarm);
        mAlarmText = (TextView) findViewById(R.id.toolbar_alarm_text);

        setAlarm(mAlarm);
    }

    public void show(final boolean show) {
        setVisibility((show) ? VISIBLE : GONE);
    }

    public void setAlarm(final long alarm) {
        mAlarm = alarm;
        final boolean hasAlarm = (mAlarm == TodoItem.NO_ALARM);
        mAddAlarm.setVisibility((hasAlarm) ? GONE : VISIBLE);
        mEditAlarm.setVisibility((hasAlarm) ? VISIBLE : GONE);
        mAlarmText.setVisibility((hasAlarm) ? VISIBLE : GONE);
        mAlarmText.setText(Long.toString(alarm));
        mAlarmText.setText("Saturday, November 23");//Long.toString(alarm));

    }
}
