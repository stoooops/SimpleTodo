package com.simpletodo.simpletodo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.simpletodo.simpletodo.data.Database;
import com.simpletodo.simpletodo.data.Table;
import com.simpletodo.simpletodo.data.SimpleTodoItem;
import com.simpletodo.simpletodo.view.SimpleTodoFragment;

import java.util.Random;

/** The main Activity. Switches between To-Do, Lists, and Settings */
public class SimpleTodoActivity extends Activity {

    /** Container for section ids */
    public class Sections {
        public static final int TODO = 0;
        public static final int SETTINGS = 1;
    }

    /** Used to store the last screen title. For use in {@link #restoreActionBar()}. */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();

        mTitle = getString(R.string.app_name);

        loadTodoFragment();
        restoreActionBar();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        getMenuInflater().inflate(R.menu.main, menu);
        restoreActionBar();
        return true;
    }


    public void onSectionAttached(int num) {
        switch (num) {
            case Sections.TODO:
                mTitle = getString(R.string.app_name);
                break;
            case Sections.SETTINGS:
                mTitle = getString(R.string.title_settings);
                break;
        }
    }

    public void restoreActionBar() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.title_todo);
    }

    public void loadTodoFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SimpleTodoFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        switch (id) {
            case R.id.action_add_random_todo:
                addRandomTodo();
                loadTodoFragment();
                return true;
            case R.id.action_delete_todo_table:
                getContentResolver().delete(Table.TODO.getUri(), null, null);
                loadTodoFragment();
                return true;
            case R.id.action_settings:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment())
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addRandomTodo() {
        final ContentValues cv = new ContentValues();

        String s;

        final int num = 8;
        switch( new Random().nextInt(num)+ 1) {
            case 1:
                s = "todo";
                break;
            case 2:
                s= "This is the todo that never ends. It just goes on and on my friends. " +
                "Somebody started typing it not knowing what it was, and then they " +
                "continued typing it forever just because...this is the todo that "+
                "never ends. It just goes on and on my friends.  Somebody started "+
                "typing it not knowing what it was, and then they continued typing it "+
                "forever just because not.";
                break;
            case 3:
                s = "This is a totally real task that I have to do and should stretch over two lines";
                break;
            case 4:
                s = "This is a longer todo that should stretch over two lines but not a third";
                break;
            case 5:
                s = "Do short task";
                break;
            case 6:
                s = "I wonder how often people will type a period.";
                break;
            case 7:
                s = "I have to do a longer task";
                break;
            default:
                s = "";
                break;
        }

        int chance = 150;
        if (new Random().nextInt(chance) == 0) {
            s = "Its that time.";
        }

        final SimpleTodoItem todo = SimpleTodoItem.newInstance(this, s);
        Database.insert(getContentResolver(), todo);
    }
}
