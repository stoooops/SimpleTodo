/**
 * Created by Cory on 11/21/13.
 */
package com.simpletodo.simpletodo.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.simpletodo.simpletodo.R;
import com.simpletodo.simpletodo.data.Column;
import com.simpletodo.simpletodo.data.Database;
import com.simpletodo.simpletodo.data.SimpleTodoItem;
import com.simpletodo.simpletodo.data.Table;
import com.simpletodo.simpletodo.util.SimpleTodoLogger;
import com.simpletodo.simpletodo.util.Utils;

/**
 * A custom layout for a To-Do Item
 */
public class SimpleTodoItemLayout extends RelativeLayout {

    /** A reference to the {@link SimpleTodoItemTextView} */
    private SimpleTodoItemTextView mSimpleTodoItemTextView;

    /** A reference to the {@link SimpleTodoItemToolbarLayout} */
    private SimpleTodoItemToolbarLayout mToolbar;

    /** A reference to the context */
    private Context mContext;

    /** Whether the to-do item is currently editable */
    private boolean mEditable;

    /** Whether the to-do item is currently expanded */
    private boolean mExpanded;

    /** A reference to the associated to-do item */
    private SimpleTodoItem mSimpleTodoItem;

    /**
     * Constructs a new {@link com.simpletodo.simpletodo.view.SimpleTodoItemLayout}
     * @param c
     * @param editable whether the new to-do item is editable
     * @param expanded whether the new to-do item view is expanded
     */
    public SimpleTodoItemLayout(final Context c, final boolean editable,
                                final boolean expanded) {
        super(c);
        mEditable = editable;
        mExpanded = expanded;

        init(c);
    }

    /**
     * Constructs a new {@link com.simpletodo.simpletodo.view.SimpleTodoItemLayout} from the specified attributes.
     * @param c
     * @param attrs the attributes to set
     */
    public SimpleTodoItemLayout(final Context c, final AttributeSet attrs) {
        super(c, attrs);
        final TypedArray a = c.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.SimpleTodoItemLayout, 0, 0);
        try {
            mEditable = a.getBoolean(R.styleable.SimpleTodoItemLayout_editable, false);
            mExpanded = a.getBoolean(R.styleable.SimpleTodoItemLayout_expanded, false);
        } finally {
            a.recycle();
        }

        init(c);
    }

    /**
     * Inflates the view, sets the background, attachs the text view, and initializes the
     * editable and expanded state
     * @param c
     */
    private void init(final Context c) {
        mContext = c;
        LayoutInflater.from(mContext).inflate(R.layout.todo_item, this, true);
        // TODO can't set background resource in xml due to merge tag or something
        setBackgroundResource(R.drawable.background_todo_item);
        final Resources resources = getResources();
        setPadding((int) resources.getDimension(R.dimen.todo_item_border_left),
                (int) resources.getDimension(R.dimen.todo_item_border_top),
                (int) resources.getDimension(R.dimen.todo_item_border_right),
                (int) resources.getDimension(R.dimen.todo_item_border_bottom));

        mSimpleTodoItemTextView = (SimpleTodoItemTextView) findViewById(R.id.todo_item_text);
        mSimpleTodoItemTextView.attachParent(this);

        mToolbar = (SimpleTodoItemToolbarLayout) findViewById(R.id.todo_item_toolbar);
        mToolbar.attachParent(this);

        mSimpleTodoItem = null;

        setEditable(mEditable);
        setExpanded(mExpanded);
    }

    /**
     * Sets the text of this To-Do item
     * @param simpleTodoItem to bind
     */
    public void bind(final SimpleTodoItem simpleTodoItem) {
        if (mSimpleTodoItem != null) {
            SimpleTodoLogger.e("Attempt to bind SimpleTodoItemLayout to simpleTodoItem " + simpleTodoItem +
                    " when already bound to " + mSimpleTodoItem);
            return;
        }
        SimpleTodoLogger.v("UI| bind simpleTodoItem " + simpleTodoItem + " to SimpleTodoItemLayout");
        mSimpleTodoItem = simpleTodoItem;
        mSimpleTodoItemTextView.bind(simpleTodoItem);//setText(mSimpleTodoItem.getText());
        mToolbar.bind(simpleTodoItem);//setAlarm(mSimpleTodoItem.getAlarm());

        setOnLongClickListener(new OnDeleteListener());
    }

    /** Add a new to-do item from this EditTodoText */
    void doAddTodo() {
        Utils.closeKeyboard(mContext);

        final String text = mSimpleTodoItemTextView.getText().toString();
        setEditable(false);

        // add to-do item to db
        final ContentValues cv = new ContentValues();
        cv.put(Column.TEXT.getName(), text);
        mContext.getContentResolver().insert(Table.TODO.getUri(), cv);
    }

    /**
     * Sets the To-Do Item editable or not
     * @param editable whether to set editable
     */
    private void setEditable(final boolean editable) {
        mEditable = editable;
        mSimpleTodoItemTextView.setEditable(editable);
    }

    /**
     * Sets the To-Do Item expanded or not
     * @param expanded whether to set expanded
     */
    private void setExpanded(final boolean expanded) {
        mExpanded = expanded;
        mSimpleTodoItemTextView.setExpanded(expanded);
        mToolbar.show(expanded);
    }

    /** Handle the tap event for this {@link com.simpletodo.simpletodo.view.SimpleTodoItemLayout} */
    private void doTapEvent() {
        setExpanded(!mExpanded);
    }

    /**
     * Deletes the to-do item
     */
    public void onDelete() {
        Database.delete(mContext.getContentResolver(), mSimpleTodoItem);
        setVisibility(GONE);
    }

    //TODO eventually implement getView to cache lookup
    public class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            SimpleTodoLogger.v("SimpleTodoItemLayout.onClick()");
            doTapEvent();
        }
    }

    /**
     * Expands or collapses the layout depending on new focus state
     */
    public class OnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(final View v, final boolean hasFocus) {
            SimpleTodoLogger.v("SimpleTodoItemLayout.onFocusChange()");
            doTapEvent();
        }
    }

    /**
     * Verifies that the todoItem is attached, else logs an error message
     * @param msg
     * @return whether the view is attached
     */
    private boolean verifyAttached(final String msg) {
        final boolean isAttached = (mSimpleTodoItem != null);
        if (!isAttached) {
            SimpleTodoLogger.e(msg);
        }
        return isAttached;
    }

    /**
     *
     */
    private void focusEditText() {
        requestFocusFromTouch();
    }

    private class OnDeleteListener implements OnLongClickListener {
        @Override
        public boolean onLongClick(final View v) {
            SimpleTodoLogger.v("onLongClick()");
            final String unAttachedMsg = "Received OnDeleteListener.onClick() with no attached " +
                    "to-do item? Listener should not be attached.";
            if (!verifyAttached(unAttachedMsg)) {
                return false;
            }
            // for now, just swap the alarm
            onDelete();
            mToolbar.setAlarm((mSimpleTodoItem.getAlarm() != SimpleTodoItem.NO_ALARM) ? System.currentTimeMillis() : SimpleTodoItem.NO_ALARM);
            return true;
        }
    }

    /**
     * Listener to handle taps to the alarm button and change the alarm
     */
    public class OnEditTextListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            SimpleTodoLogger.v("OnEdit();");
            final String unAttachedMsg = "Received OnClickListener.onClick() with no attached " +
                    "to-do item? Listener should not be attached.";
            if (!verifyAttached(unAttachedMsg)) {
                return;
            }
            setEditable(true);
            focusEditText();
        }
    }
}