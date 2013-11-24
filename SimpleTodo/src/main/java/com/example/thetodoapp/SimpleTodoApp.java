/**
 * Created by Cory on 11/20/13.
 */
package com.example.thetodoapp;

import android.app.Application;

/** Global application state store */
public class SimpleTodoApp extends Application {

    /** Application tag */
    public static String TAG = "com.example.thetodoapp";

    public static final String SHARED_PREFERENCES_FILENAME = TAG+"_prefences.xml";

    public static boolean opened = false;

//    /**
//    private static Context mContext;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mContext = this;
//    }
//
//    public static Context getContext(){
//        return mContext;
//    }
}