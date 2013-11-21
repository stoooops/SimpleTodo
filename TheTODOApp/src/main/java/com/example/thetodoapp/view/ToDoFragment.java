/**
 * Created by Cory Gabrielsen on 11/16/13.
 */
package com.example.thetodoapp.view;

import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.thetodoapp.data.Column;
import com.example.thetodoapp.data.Table;

/**
 * A fragment for the TO-DO view
 */
public class ToDoFragment extends ListFragment {

    private final Context mContext;

    public ToDoFragment(final Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final Cursor c = mContext.getContentResolver().query(Table.TODO.getUri(), null, null, null, null);

        final CursorAdapter adapter = new TodoCursorAdapter(getActivity(), c,
                                                      CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class TodoCursorAdapter extends CursorAdapter {

        public TodoCursorAdapter(final Context context, final Cursor c, final int flags) {
            super(context, c, flags);
        }

        @Override
        public void bindView(final View view, final Context context, final Cursor cursor) {
            final String id = cursor.getString(0);
            final String todoItem = cursor.getString(
                    cursor.getColumnIndex(Column.TODO_ITEM.getName()));

            final TextView tv = (TextView) view;
            tv.setText(id+": "+todoItem);
        }

        @Override
        public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
            final TextView view = new TextView(context);
            return view;
        }
    }

}