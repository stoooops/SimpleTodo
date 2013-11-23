/**
 * Created by Cory on 11/21/13.
 */
package com.example.thetodoapp.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.thetodoapp.R;
import com.example.thetodoapp.data.Column;
import com.example.thetodoapp.data.Table;
import com.example.thetodoapp.util.Logger;
import com.example.thetodoapp.util.Utils;

/**
 * A custom layout for a To-Do Item
 */
public class TodoItemLayout extends RelativeLayout {

    /** A reference to the {@link TodoItemTextView} */
    private TodoItemTextView mTodoItemTextView;

    /** A reference to the {@link com.example.thetodoapp.view.TodoItemToolbarLayout} */
    private TodoItemToolbarLayout mToolbar;

    /** A reference to the context */
    private final Context mContext;

    /** Whether the to-do item is currently editable */
    private boolean mEditable;

    /** Whether the to-do item is currently expanded */
    private boolean mExpanded;

    /**
     * Constructs a new {@link TodoItemLayout}
     * @param c
     * @param editable whether the new to-do item is editable
     * @param expanded whether the new to-do item view is expanded
     */
    public TodoItemLayout(final Context c, final boolean editable,
                          final boolean expanded) {
        super(c);
        mContext = c;
        mEditable = editable;
        mExpanded = expanded;

        init(c);
    }

    /**
     * Constructs a new {@link TodoItemLayout} from the specified attributes.
     * @param c
     * @param attrs the attributes to set
     */
    public TodoItemLayout(final Context c, final AttributeSet attrs) {
        super(c, attrs);
        mContext = c;
        final TypedArray a = c.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TodoItemLayout, 0, 0);
        try {
            mEditable = a.getBoolean(R.styleable.TodoItemLayout_editable, false);
            mExpanded = a.getBoolean(R.styleable.TodoItemLayout_expanded, false);
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
        LayoutInflater.from(c).inflate(R.layout.todo_item, this, true);
        // TODO can't set background resource in xml due to merge tag or something
        setBackgroundResource(R.drawable.background_todo_item);
        final Resources resources = getResources();
        setPadding((int) resources.getDimension(R.dimen.todo_item_border_left),
                (int) resources.getDimension(R.dimen.todo_item_border_top),
                (int) resources.getDimension(R.dimen.todo_item_border_right),
                (int) resources.getDimension(R.dimen.todo_item_border_bottom));

        mTodoItemTextView = (TodoItemTextView) findViewById(R.id.todo_item_text);
        mTodoItemTextView.attachToTodoItemView(this);

        mToolbar = (TodoItemToolbarLayout) findViewById(R.id.todo_item_toolbar);

        setEditable(mEditable);
        setExpanded(mExpanded);
    }

    /**
     * Sets the text of this To-Do item
     * @param text
     */
    public void setText(final String text) {
        mTodoItemTextView.setText(text);
    }

    /** Add a new to-do item from this EditTodoText */
    void doAddTodo() {
        Utils.closeKeyboard(mContext);

        final String text = mTodoItemTextView.getText().toString();
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
        mTodoItemTextView.setEditable(editable);
    }

    /**
     * Sets the To-Do Item expanded or not
     * @param expanded whether to set expanded
     */
    private void setExpanded(final boolean expanded) {
        mExpanded = expanded;
        mTodoItemTextView.setExpanded(expanded);
        mToolbar.show(expanded);
    }

    /** Handle the tap event for this {@link TodoItemLayout} */
    private void doTapEvent() {
        setExpanded(!mExpanded);
    }

    //TODO eventually implement getView to cache lookup
    public class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            Logger.v("TodoItemLayout.onClick()");
            doTapEvent();
        }
    }

    /**
     * Expands or collapses the layout depending on new focus state
     */
    public class OnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(final View v, final boolean hasFocus) {
            Logger.v("TodoItemLayout.onFocusChange()");
            setExpanded(hasFocus);
        }
    }
}