/**
 * Created by Cory on 11/21/13.
 */
package com.example.thetodoapp.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thetodoapp.R;
import com.example.thetodoapp.data.Column;
import com.example.thetodoapp.data.Table;
import com.example.thetodoapp.util.Logger;
import com.example.thetodoapp.util.Utils;

/** A custom view for a possibly editable To-do Item */
public class TodoItemView extends RelativeLayout {

    /** The maximum number of lines shown when a TodoText is collapsed */
    private static final int COLLAPSED_MAX_LINES = 2;

    /** The TextView for this View */
    private TextView mTextView;

    /** A reference to the context */
    private final Context mContext;

    /** Whether the to-do item is currently editable */
    private boolean mEditable;

    /** Whether the to-do item is currently expanded */
    private boolean mExpanded;

    /**
     * Constructs a new {@link TodoItemView}
     * @param c
     * @param editable whether the new to-do item is editable
     * @param expanded whether the new to-do item view is expanded
     * @param text the text of the new to-do item
     */
    public TodoItemView(final Context c, final boolean editable,
                        final boolean expanded, final String text) {
        super(c);
        mContext = c;
        mEditable = editable;
        mExpanded = expanded;
        this.build(text);
    }

    /**
     * Constructs a new {@link TodoItemView}
     * @param c
     * @param editable whether the new to-do item is editable
     * @param expanded whether the new to-do item view is expanded
     */
    public TodoItemView(final Context c, final boolean editable,
                         final boolean expanded) {
        this(c, editable, expanded, null);
    }


    public TodoItemView(final Context c, final AttributeSet attrs) {
        super(c, attrs);
        mContext = c;
        final TypedArray a = c.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TodoItemView, 0, 0);
        try {
            mEditable = a.getBoolean(R.styleable.TodoItemView_editable, false);
            mExpanded = a.getBoolean(R.styleable.TodoItemView_expanded, false);
        } finally {
            a.recycle();
        }
        this.build(null);
    }

    /**
     * Builds and adds the elements of the {@link TodoItemView} to the {@link TodoItemView}
     * @param text
     */
    private void build(final String text) {
        mTextView = createTextView(mContext);

        if (text != null) {
            mTextView.setText(text);
        }

        this.setBackgroundResource(R.drawable.todo_item_shadow);
        this.setLayoutParams(new ListView.LayoutParams(
                ListView.LayoutParams.MATCH_PARENT,
                ListView.LayoutParams.WRAP_CONTENT));

        this.addView(mTextView);
    }

    /** Helper method to create a TextView for this {@link TodoItemView} from its properties */
    private TextView createTextView(final Context c) {
        return (mEditable) ? createEditTodoText(c) : createTodoTextView(c, !mExpanded);
    }

    /** Create a TextView for an {@link TodoItemView} */
    private EditText createEditTodoText(final Context c) {
        final EditText res = new EditText(c);

        final Resources resources = getResources();
        setGenericTodoTextViewAttributes(res, resources);

        res.setInputType(InputType.TYPE_CLASS_TEXT);
        res.setImeOptions(EditorInfo.IME_ACTION_DONE);
        res.setHint(resources.getString(R.string.edit_todo_prompt));
        res.setHintTextColor(resources.getColor(R.color.edit_todo_prompt));
        res.setCursorVisible(true);

        res.setOnEditorActionListener(new EditTodoOnEditorActionListener());

        return res;
    }

    /**
     * Create a TextView for an {@link TodoItemView}
     * @param expanded whether the TextView is collapsed
     */
    private TextView createTodoTextView(final Context c, final boolean expanded) {
        final TextView res = new TextView(c);

        setGenericTodoTextViewAttributes(res, getResources());

        res.setHorizontallyScrolling(false);
        res.setEllipsize(TextUtils.TruncateAt.END);

        res.setClickable(true);
        res.setOnClickListener(new TodoViewOnClickListener());

        res.setFocusable(true);
        res.setFocusableInTouchMode(true);
        res.setOnFocusChangeListener(new TodoViewOnFocusChangeListener());

        if (expanded) {
            res.setMaxLines(Integer.MAX_VALUE);
        } else {
            res.setMaxLines(COLLAPSED_MAX_LINES);
        }
        return res;
    }

    /** Set the common properties of TextView and EditText for an {@link TodoItemView} */
    private void setGenericTodoTextViewAttributes(final TextView tv, final Resources resources) {
        tv.setTypeface(Typeface.SANS_SERIF);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, resources.getDimension(R.dimen.text_small));
        tv.setBackgroundColor(Color.TRANSPARENT);

        tv.setLayoutParams(new ListView.LayoutParams(
                ListView.LayoutParams.MATCH_PARENT,
                ListView.LayoutParams.WRAP_CONTENT));

        tv.setPadding((int) resources.getDimension(R.dimen.todo_item_padding_left),
                (int) resources.getDimension(R.dimen.todo_item_padding_top),
                (int) resources.getDimension(R.dimen.todo_item_padding_right),
                (int) resources.getDimension(R.dimen.todo_item_padding_bottom));
    }

    /** OnClickListener for a EditTodoText */
    private class TodoViewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            Logger.v("EditTodoText.onClick()");
            doTapEvent();
        }
    }

    /** OnClickListener for a EditTodoText */
    private class TodoViewOnFocusChangeListener implements OnFocusChangeListener {
        @Override
        public void onFocusChange(final View v, final boolean hasFocus) {
            Logger.v("EditTodoText.onFocusChange()");
            if (hasFocus) {
                setExpanded();
            } else {
                setCollapsed();
            }
        }
    }

    /** OnEditorActionListener for the EditText of an EditTextTodo */
    private class EditTodoOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
            if (event != null) {
                // if shift key is down, then we want to insert the '\n' char in the TextView;
                // otherwise, the default action is to send the message.
                if (!event.isShiftPressed()) {
                    doAddTodo();
                    return true;
                }
                return false;
            }
            doAddTodo();
            return true;
        }
    }

    /** Add a new to-do item from this EditTodoText */
    private void doAddTodo() {
        Logger.v("doAddTodo()");

        // close keyboard
        Utils.closeKeyboard(mContext);

        final String text = mTextView.getText().toString();
        mTextView = createTodoTextView(mContext, true);
        mTextView.setText(text);

        this.removeViewAt(0);
        this.addView(mTextView, 0);

        // add to-do item to db
        final ContentValues cv = new ContentValues();
        cv.put(Column.TODO_ITEM.getName(), mTextView.getText().toString());
        mContext.getContentResolver().insert(Table.TODO.getUri(), cv);
    }

    /**
     * Expand the EditTodoText
     * @return whether it was already expanded
     */
    private boolean expand() {
        final boolean wasExpanded = mExpanded;
        if (!wasExpanded) {
            setExpanded();
        }
        return wasExpanded;
    }

    /** Expands the view */
    private void setExpanded() {
        mExpanded = true;
        mTextView.setMaxLines(Integer.MAX_VALUE);
    }

    /**
     * Collapses the EditTodoText
     * @return whether it was already collapsed */
    private boolean collapse() {
        final boolean wasCollapsed = !mExpanded;
        if (!wasCollapsed) {
            setCollapsed();
        }
        return wasCollapsed;
    }

    /** Collapses the view */
    private void setCollapsed() {
        mExpanded = false;
        mTextView.setMaxLines(COLLAPSED_MAX_LINES);
    }

    /** Handle the tap event for this {@link TodoItemView} */
    private void doTapEvent() {
        if (mExpanded) {
            collapse();
        } else {
            expand();
        }
        requestFocusFromTouch();
    }

    /** Returns the TextView for this {@link TodoItemView} */
    public TextView getTextView() {
        return mTextView;
    }
}