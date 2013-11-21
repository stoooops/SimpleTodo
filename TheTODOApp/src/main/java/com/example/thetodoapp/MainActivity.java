package com.example.thetodoapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thetodoapp.data.Column;
import com.example.thetodoapp.data.Table;
import com.example.thetodoapp.view.ListsFragment;
import com.example.thetodoapp.view.NavigationDrawerFragment;
import com.example.thetodoapp.view.TodoFragment;

/** The main Activity. Switches between To-Do, Lists, and Settings */
public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /** Container for section ids */
    public class Sections {
        public static final int TODO = 0;
        public static final int LISTS = 1;
        public static final int SETTINGS = 2;
    }

    /** Fragment managing the behaviors, interactions and presentation of the navigation drawer. */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /** Used to store the last screen title. For use in {@link #restoreActionBar()}. */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getString(R.string.title_todo);


        // Set up the drawer.
        // Shows drawer if first time
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if (!App.opened) {
            App.opened = true;
            final int times = 3;
            for (int time = 1; time <= times; time++) {
                final ContentValues cv = new ContentValues();

                String s;
                switch (time % 3) {
                    case 1:
                        s = "This is a simple todo statement.";
                        break;
                    case 2:
                        s = "This is the todo that never ends. It just goes on and on my friends. " +
                                "Somebody started typing it not knowing what it was, and they'll " +
                                "continue typing it forever just because..."+
                                "This is the todo that never ends. It just goes on and on my friends. " +
                                "Somebody started typing it not knowing what it was, and they'll " +
                                "continue typing it forever just because. end.";
                        break;
                    case 3:
                    default:
                        s = "The next todo was deleted. But first lets type a longer todo on " +
                                "two lines.";
                        break;
                }

                cv.put(Column.TODO_ITEM.getName(), s);
                getContentResolver().insert(Table.TODO.getUri(), cv);
            }
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int pos) {
        // update the main content by replacing fragments
        final FragmentManager fragmentManager = getFragmentManager();

        switch (pos) {
            case Sections.TODO:
                fragmentManager.beginTransaction()
                            .replace(R.id.container, new TodoFragment())
                            .commit();
                break;
            case Sections.LISTS:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ListsFragment())
                        .commit();
                break;
            case Sections.SETTINGS:
                fragmentManager.beginTransaction()
                //        .replace(R.id.container, new SettingsFragment())
                        .commit();
                break;
        }
        onSectionAttached(pos);
    }

    public void onSectionAttached(int num) {
        switch (num) {
            case Sections.TODO:
                mTitle = getString(R.string.title_todo);
                break;
            case Sections.LISTS:
                mTitle = getString(R.string.title_lists);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                getContentResolver().delete(Table.TODO.getUri(), null, null);
                return true;
            case R.id.action_new_todo:
                return true; //TODO what should this be
               // "false to allow normal menu processing to proceed, true to consume it here."
        }
        return super.onOptionsItemSelected(item);
    }
}
