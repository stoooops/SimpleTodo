/**
 * Created by Cory on 11/21/13.
 */
package com.example.thetodoapp.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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
    private TextView mTodoText;

    private EditText mTodoEditText;

    /** A reference to the context */
    private final Context mContext;

    /** Whether the to-do item is currently editable */
    private boolean mEditable;

    /** Whether the to-do item is currently expanded */
    private boolean mExpanded;

    private TextView.OnEditorActionListener mL = new TodoItemOnEditorActionListener();

    /**
     * Constructs a new {@link TodoItemView}
     * @param c
     * @param editable whether the new to-do item is editable
     * @param expanded whether the new to-do item view is expanded
     */
    public TodoItemView(final Context c, final boolean editable,
                         final boolean expanded) {
        super(c);
        //Logger.v("TodoItemView(Context, boolean, boolean) in " + this);
        mContext = c;
        mEditable = editable;
        mExpanded = expanded;

        init(c);
    }


    public TodoItemView(final Context c, final AttributeSet attrs) {
        super(c, attrs);
        //Logger.v("TodoItemView(Context, AttributeSet) in " + this);
        mContext = c;
        final TypedArray a = c.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TodoItemView, 0, 0);
        try {
            mEditable = a.getBoolean(R.styleable.TodoItemView_editable, false);
            mExpanded = a.getBoolean(R.styleable.TodoItemView_expanded, false);
        } finally {
            a.recycle();
        }

        init(c);
    }

    private void init(final Context c) {
        LayoutInflater.from(c).inflate(R.layout.todo_item, this, true);
        setBackgroundResource(R.drawable.background_todo_item);

        mTodoText = (TextView) findViewById(R.id.todo_text);
        mTodoText.setClickable(true);
        mTodoText.setFocusable(true);
        mTodoText.setFocusableInTouchMode(true);
        mTodoText.setOnClickListener(new TodoItemOnClickListener());
        //mTodoText.setOnTouchListener(new TodoItemOnTouchListener());
        mTodoText.setOnFocusChangeListener(new TodoItemOnFocusChangeListener());

        mTodoEditText = (EditText) findViewById(R.id.todo_edit);
        mTodoEditText.setOnEditorActionListener(new TodoItemOnEditorActionListener());
//        mTodoText.setHorizontallyScrolling(false); // doesn't seem to work when just declared in xml
//        mTodoText.setEllipsize(TextUtils.TruncateAt.END); // doesn't seem to work when just declared in xml
//        mTodoText.setInputType(InputType.TYPE_CLASS_TEXT);

        setEditable(mEditable);
        setExpanded(mExpanded);
    }

    public void setText(final String text) {
        //Logger.v("setText(String) in "+this);
        mTodoText.setText(text);
    }

    public class TodoItemOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(final View v, final MotionEvent event) {
            Logger.v("TodoItemView.onTouch()");
            doTapEvent();
            return true;
        }
    }

    /** OnClickListener for a EditTodoText */
    public class TodoItemOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            Logger.v("TodoItemView.onClick()");
            doTapEvent();
        }
    }

    /** OnClickListener for a EditTodoText */
    public class TodoItemOnFocusChangeListener implements OnFocusChangeListener {
        @Override
        public void onFocusChange(final View v, final boolean hasFocus) {
            Logger.v("TodoItemView.onFocusChange()");
            setExpanded(hasFocus);
        }
    }

    /** OnEditorActionListener for the EditText of an EditTextTodo */
    public class TodoItemOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
            Logger.v("TodoItemView.onEditionAction()");
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

        final String text = mTodoText.getText().toString();
        setEditable(false);

        this.setOnClickListener(new TodoItemOnClickListener());
        this.setOnFocusChangeListener(new TodoItemOnFocusChangeListener());

        // add to-do item to db
        final ContentValues cv = new ContentValues();
        cv.put(Column.TODO_ITEM.getName(), mTodoText.getText().toString());
        mContext.getContentResolver().insert(Table.TODO.getUri(), cv);
    }

    /** Expands the view */
    private void setEditable(final boolean editable) {
       // Logger.v("setEditable("+editable+") in "+this);
        mEditable = editable;
//        mTodoText.setEnabled(editable);
//        mTodoText.setTextColor((editable) ?
//                getResources().getColor(R.color.edit_todo_prompt) : Color.DKGRAY);
        if (editable) {
            mTodoEditText.setVisibility(VISIBLE);
            mTodoText.setVisibility(GONE);
        } else {
            mTodoEditText.setVisibility(GONE);
            mTodoText.setVisibility(VISIBLE);
        }
    }
    /** Expands the view */
    private void setExpanded(final boolean expanded) {
        //Logger.v("setExpanded("+expanded+") in "+this);
        mExpanded = expanded;
        mTodoText.setMaxLines( (expanded) ? Integer.MAX_VALUE : COLLAPSED_MAX_LINES);
    }

    /** Handle the tap event for this {@link TodoItemView} */
    private void doTapEvent() {
        setExpanded(!mExpanded);
        setEditable(!mEditable);
    }

    /** Returns the TextView for this {@link TodoItemView} */
    public TextView getTodoText() {
        return mTodoText;
    }

    //TODO eventually implement getView to cache lookup
}