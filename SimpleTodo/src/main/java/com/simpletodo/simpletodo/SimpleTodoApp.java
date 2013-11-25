/**
 * Created by Cory on 11/20/13.
 */
package com.simpletodo.simpletodo;

import android.app.Application;

/** Global application state store */
public class SimpleTodoApp extends Application {

    /** Application tag */
    public static String TAG = "com.example.thetodoapp";

    public static final String SHARED_PREFERENCES_FILENAME = TAG+"_prefences.xml";
}