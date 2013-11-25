/**
 * Created by Cory Gabrielsen on 11/16/13.
 */
package com.simpletodo.simpletodo.view;

import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.simpletodo.simpletodo.R;
import com.simpletodo.simpletodo.data.Table;
import com.simpletodo.simpletodo.data.SimpleTodoItem;

/** A fragment for the To-Do view  */
public class SimpleTodoFragment extends ListFragment {

    /** Constructs a new SimpleTodoFragment with reference to the given context */
    public SimpleTodoFragment() {
        super();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_todo, container, false); //TODO true?

        final Cursor c = getActivity().getApplicationContext().getContentResolver()
                            .query(Table.TODO.getUri(), null, null, null, null);
        final CursorAdapter adapter = new TodoCursorAdapter(getActivity(), c,
                                                      CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);
        return rootView;
    }

    /** The cursor adapter for the to_do list view */
    private class TodoCursorAdapter extends CursorAdapter {

        /** Constructs a new CursorAdapter */
        public TodoCursorAdapter(final Context context, final Cursor c, final int flags) {
            super(context, c, flags);
        }

        @Override
        public void bindView(final View view, final Context context, final Cursor cursor) {
            final SimpleTodoItemLayout etv = (SimpleTodoItemLayout) view;
            etv.bind(new SimpleTodoItem(cursor));
        }

        @Override
        public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
            final SimpleTodoItemLayout view = new SimpleTodoItemLayout(context, false, false);
            return view;
        }
    }

}