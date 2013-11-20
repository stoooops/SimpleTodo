/**
 * Created by Cory Gabrielsen on 11/16/13.
 */
package com.example.thetodoapp.view;

import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.thetodoapp.data.Database;

/**
 * A fragment for the TO-DO view
 */
public class ToDoFragment extends ListFragment {

    private final SQLiteOpenHelper mDbHelper;


    public ToDoFragment(SQLiteOpenHelper dbHelper) {
        mDbHelper = dbHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Cursor c = mDbHelper.getReadableDatabase().query(Database.TABLE_TODO,
                                                               null, null, null, null, null, null);

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
                    cursor.getColumnIndex(Database.COLUMN_TODO_ITEM));

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