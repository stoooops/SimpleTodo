/**
 * Created by Cory on 11/22/13.
 */

package com.example.thetodoapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.thetodoapp.R;

public class TodoItemToolbarLayout extends LinearLayout {

    public TodoItemToolbarLayout(final Context c, final AttributeSet attrs) {
        super(c, attrs);
        init(c);
    }

    public void init(final Context c) {
        LayoutInflater.from(c).inflate(R.layout.todo_item_toolbar, this, true);
    }
}
