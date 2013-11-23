/**
 * Created by Cory on 11/22/13.
 */

package com.example.thetodoapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thetodoapp.R;
import com.example.thetodoapp.util.Logger;

/**
 * A wrapper class to make a TextView toggle editable and expandable
 */
public class TodoItemTextView extends LinearLayout {

    /** The maximum number of lines shown when a TodoText is collapsed */
    private static final int COLLAPSED_MAX_LINES = 2;

    /** A reference to the {@link TodoItemLayout} */
    private TodoItemLayout mTodoItemLayout;

    /** A reference to the {@link TextView} */
    private TextView mTextView;

    /** A reference to the {@link EditText} */
    private EditText mEditText;

    /** Whether the to-do item is currently editable */
    private boolean mEditable;

    /** Whether the to-do item is currently expanded */
    private boolean mExpanded;

    private OnClickListener mOnClickListener;
    private OnFocusChangeListener mOnFocusChangeListener;
    private TextView.OnEditorActionListener mOnEditorActionListener;

    /**
     * Constructs a new {@link TodoItemTextView}
     * @param c
     * @param editable whether the new to-do item text is editable
     * @param expanded whether the new to-do item text is expanded
     */
    public TodoItemTextView(final Context c, final boolean editable,
                            final boolean expanded) {
        super(c);
        mEditable = editable;
        mExpanded = expanded;

        init(c);
    }

    /**
     * Constructs a new {@link TodoItemTextView} from the specified attributes.
     * @param c
     * @param attrs the attributes to set
     */
    public TodoItemTextView(final Context c, final AttributeSet attrs) {
        super(c, attrs);
        final TypedArray a = c.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TodoItemTextView, 0, 0);
        try {
            mEditable = a.getBoolean(R.styleable.TodoItemTextView_editable, false);
            mExpanded = a.getBoolean(R.styleable.TodoItemTextView_expanded, false);
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
        LayoutInflater.from(c).inflate(R.layout.view_todo_item_text, this, true);

        mTextView = (TextView) findViewById(R.id.todo_text);

        mEditText = (EditText) findViewById(R.id.todo_edit);
        // TODO wtf why doesn't xml declaration work
        // xml layout doesnt seem to work for these attributes
        mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        mEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditText.setHorizontallyScrolling(false);
        mEditText.setMaxLines(Integer.MAX_VALUE);

        setEditable(mEditable);
        setExpanded(mExpanded);
    }

    /**
     * Attaches this {@link TodoItemTextView} to the given {@link TodoItemLayout} and initializes
     * listeners
     * @param til to attach to
     */
    public void attachToTodoItemView(final TodoItemLayout til) {
        mTodoItemLayout = til;
        mOnClickListener = til.new OnClickListener();
        mOnFocusChangeListener = til.new OnFocusChangeListener();
        mOnEditorActionListener = new OnEditorActionListener();
    }

    /**
     * Sets the To-Do Item test editable or not
     * @param editable whether to set editable
     */
    public void setEditable(final boolean editable) {
        mEditable = editable;

        mEditText.setVisibility((editable) ? VISIBLE : GONE);
        mTextView.setVisibility((editable) ? GONE: VISIBLE);

        mEditText.setOnEditorActionListener((editable) ? mOnEditorActionListener : null);
        mTextView.setOnClickListener((editable) ? null : mOnClickListener);
        mTextView.setOnFocusChangeListener((editable) ? null : mOnFocusChangeListener);

        // update possibly changed text to displayed view
        final TextView newTextView = (editable) ? mEditText : mTextView;
        final TextView oldTextView = (editable) ?  mTextView : mEditText;
        // calling toString() on getText() removes the underline formatting
        // of the last word in the EditText
        newTextView.setText(oldTextView.getText());
    }

    /**
     * Sets the To-Do Item text expanded or not
     * @param expanded whether to set expanded
     */
    public void setExpanded(final boolean expanded) {
        mExpanded = expanded;
        mTextView.setMaxLines((expanded) ? Integer.MAX_VALUE : COLLAPSED_MAX_LINES);
    }

    /**
     * Returns the text of this To-Do item
     * @return the To-Do Item item
     */
    public CharSequence getText() {
        return (mEditable) ? mEditText.getText() : mTextView.getText();
    }

    /**
     * Sets the text of this To-Do item
     * @param s to set
     */
    public void setText(final CharSequence s) {
        mTextView.setText(s);
    }

    /** OnEditorActionListener for the EditText of an EditTextTodo */
    private class OnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
            Logger.v("TodoItemLayout.onEditionAction()");
            if (event != null) {
                // if shift key is down, then we want to insert the '\n' char in the TextView;
                // otherwise, the default action is to send the message.
                if (!event.isShiftPressed()) {
                    mTodoItemLayout.doAddTodo();
                    return true;
                }
                return false;
            }
            mTodoItemLayout.doAddTodo();
            return true;
        }
    }
}
