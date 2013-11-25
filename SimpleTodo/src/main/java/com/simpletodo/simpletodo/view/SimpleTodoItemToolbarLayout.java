/**
 * Created by Cory on 11/22/13.
 */

package com.simpletodo.simpletodo.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simpletodo.simpletodo.R;
import com.simpletodo.simpletodo.data.SimpleTodoItem;
import com.simpletodo.simpletodo.util.SimpleTodoLogger;

import java.text.SimpleDateFormat;

/**
 * A custom componenet for the to-do item of a SimpleTodoItemLayout
 */
public class SimpleTodoItemToolbarLayout extends RelativeLayout {

    /** A reference to the context */
    private Context mContext;

    /** A reference to the parent layout */
    private SimpleTodoItemLayout mSimpleTodoItemLayout;

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
    private SimpleTodoItem mSimpleTodoItem;

    /**
     * Constructs a new SimpleTodoItemToolbarLayout from the provided attributes
     * @param c
     * @param attrs
     */
    public SimpleTodoItemToolbarLayout(final Context c, final AttributeSet attrs) {
        super(c, attrs);
        final TypedArray a = c.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.SimpleTodoItemLayout, 0, 0);

        try {
            mHasAlarm = a.getBoolean(R.styleable.SimpleTodoItemToolbarLayout_hasAlarm, false);
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

        setAlarm(SimpleTodoItem.NO_ALARM); // alarm will be set when the layout is attached to a to-do item
    }

    /**
     * Attaches this {@link SimpleTodoItemTextView} to the given {@link SimpleTodoItemLayout} and initializes
     * listeners
     * @param til to attach to
     */
    public void attachParent(final SimpleTodoItemLayout til) {
        mSimpleTodoItemLayout = til;
        mEdit.setOnClickListener( mSimpleTodoItemLayout.new OnEditTextListener()) ;
    }

    /**
     * Bind the given simpleTodoItem to this toolbar
     * @param simpleTodoItem
     */
    public void bind(final SimpleTodoItem simpleTodoItem) {
        if (mSimpleTodoItem != null) {
            SimpleTodoLogger.e("UI| Attempt to bind SimpleTodoItemToolbarLayout to simpleTodoItem " + simpleTodoItem +
                    " when already bound to " + mSimpleTodoItem);
            return;
        }
        mSimpleTodoItem = simpleTodoItem;
        setAlarm(simpleTodoItem.getAlarm());
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
        final boolean mHasAlarm = (alarm == SimpleTodoItem.NO_ALARM);
        mEditAlarm.setImageResource((mHasAlarm) ?
                R.drawable.ic_action_alarms : R.drawable.ic_action_add_alarm);
        mAlarmText.setVisibility((mHasAlarm) ? VISIBLE : GONE);
        if (mHasAlarm) {
            mAlarmText.setText( toAlarmText(alarm) );
        }
    }

    private boolean verifyAttached(final String msg) {
        final boolean isAttached = (mSimpleTodoItem != null);
        if (!isAttached) {
            SimpleTodoLogger.e(msg);
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
            setAlarm((mHasAlarm) ? System.currentTimeMillis() : SimpleTodoItem.NO_ALARM);
        }
    }
}
