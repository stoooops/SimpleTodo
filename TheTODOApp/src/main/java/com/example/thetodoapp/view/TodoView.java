/**
 * Created by Cory on 11/20/13.
 */
package com.example.thetodoapp.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.thetodoapp.App;
import com.example.thetodoapp.R;

/** The custom view for a to-do item */
public class TodoView extends TextView {

    /** The maximum number of lines shown when a TodoView is collapsed */
    private static final int COLLAPSED_MAX_LINES = 2;

    /** Whether the TodoView is expanded */
    private boolean expanded;

    /** Constructs a TodoView from the given Context */
    public TodoView(final Context c) {
        super(c);
        expanded = false;

        final Resources resources = getResources();

        this.setTypeface(Typeface.SANS_SERIF);
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, resources.getDimension(R.dimen.text_small));
        this.setBackgroundColor(resources.getColor(R.color.todo_item_color));
        this.setBackgroundResource(R.drawable.todo_item_shadow_collapsed);

        // setting padding after background resource is set because doesnt seem to work otherwise
        this.setPadding((int) resources.getDimension(R.dimen.todo_item_padding_left),
                (int) resources.getDimension(R.dimen.todo_item_padding_top),
                (int) resources.getDimension(R.dimen.todo_item_padding_right),
                (int) resources.getDimension(R.dimen.todo_item_padding_bottom));

        this.setMaxLines(COLLAPSED_MAX_LINES);
        this.setEllipsize(TextUtils.TruncateAt.END);

        this.setClickable(true);
        this.setOnClickListener(new TodoViewOnClickListener());

        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setOnFocusChangeListener(new TodoViewOnFocusChangeListener());
    }

    /** OnClickListener for a TodoView */
    private class TodoViewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            Log.v(App.TAG, "TodoView.onClick()");
            final TodoView tv = (TodoView) v;
            if (expanded) {
                tv.collapse();
            } else {
                tv.expand();
            }
            requestFocusFromTouch();
        }
    }

    /** OnFocusChangeListener for a TodoView */
    private class TodoViewOnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(final View v, final boolean hasFocus) {
            Log.v(App.TAG, "TodoView.onFocusChange()");
            final TodoView tv = (TodoView) v;
            if (hasFocus) {
                tv.expand();
            } else {
                tv.collapse();
            }
        }
    }

    /** Expand the TodoView */
    private boolean expand() {
        final boolean wasExpanded = expanded;
        if (!expanded) {
            expanded = true;
            this.setMaxLines(Integer.MAX_VALUE);
            this.setBackgroundResource(R.drawable.todo_item_shadow_expanded);
            this.setPadding(getResources());
        }
        return wasExpanded;
    }

    /**
     * Collapses the TodoView
     * @return whether it was already collapsed */
    private boolean collapse() {
        final boolean wasCollapsed = !expanded;
        if (expanded) {
            expanded = false;
            this.setMaxLines(COLLAPSED_MAX_LINES);
            this.setBackgroundResource(R.drawable.todo_item_shadow_collapsed);
            this.setPadding(getResources());
        }
        return wasCollapsed;
    }

    /** Helper method to reset padding after background is set */
    private void setPadding(final Resources resources) {
        this.setPadding((int) resources.getDimension(R.dimen.todo_item_padding_left),
                (int) resources.getDimension(R.dimen.todo_item_padding_top),
                (int) resources.getDimension(R.dimen.todo_item_padding_right),
                (int) resources.getDimension(R.dimen.todo_item_padding_bottom));

    }
}
