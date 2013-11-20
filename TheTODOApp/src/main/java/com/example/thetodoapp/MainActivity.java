package com.example.thetodoapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thetodoapp.data.Database;
import com.example.thetodoapp.view.ListsFragment;
import com.example.thetodoapp.view.NavigationDrawerFragment;
import com.example.thetodoapp.view.ToDoFragment;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final int TODO_SECTION = 0;
    public static final int LISTS_SECTION = 1;
    public static final int SETTINGS_SECTION = 2;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private SQLiteOpenHelper mDbHelper = new Database(this);

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

        //TODO delete
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        for (int i = 0; i < 2; i++) {
            final ContentValues todo = new ContentValues();
            todo.put(Database.COLUMN_TODO_ITEM, "todo "+i);
            db.insert(Database.TABLE_TODO, null, todo);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int pos) {
        // update the main content by replacing fragments
        final FragmentManager fragmentManager = getFragmentManager();

        switch (pos) {
            case TODO_SECTION:
                fragmentManager.beginTransaction()
                            .replace(R.id.container, new ToDoFragment(mDbHelper))
                            .commit();
                break;
            case LISTS_SECTION:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ListsFragment())
                        .commit();
                break;
            case SETTINGS_SECTION:
                fragmentManager.beginTransaction()
                //        .replace(R.id.container, new SettingsFragment())
                        .commit();
                break;
        }
        onSectionAttached(pos);
    }

    public void onSectionAttached(int num) {
        switch (num) {
            case TODO_SECTION:
                mTitle = getString(R.string.title_todo);
                break;
            case LISTS_SECTION:
                mTitle = getString(R.string.title_lists);
                break;
            case SETTINGS_SECTION:
                mTitle = getString(R.string.title_settings);
                break;
        }
    }

    public void restoreActionBar() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
