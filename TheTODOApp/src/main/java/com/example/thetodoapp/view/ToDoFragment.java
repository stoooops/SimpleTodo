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
import android.widget.ListView;

import com.example.thetodoapp.R;
import com.example.thetodoapp.data.Column;
import com.example.thetodoapp.data.Table;

/** A fragment for the To-Do view  */
public class TodoFragment extends ListFragment {

    /** Constructs a new TodoFragment with reference to the given context */
    public TodoFragment() {
        super();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_todo, container, false); //TODO true?

        final ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        listView.addHeaderView(new TodoItemView(getActivity(), TodoItemView.State.EDITABLE));

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
            final String todoItem = cursor.getString(
                    cursor.getColumnIndex(Column.TODO_ITEM.getName()));

            final TodoItemView etv = (TodoItemView) view;
            etv.getTextView().setText(todoItem);
        }

        @Override
        public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
            final TodoItemView view = new TodoItemView(context, TodoItemView.State.COLLAPSED);
            return view;
        }
    }

}