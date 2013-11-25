/**
 * Created by Cory on 11/21/13.
 */

package com.simpletodo.simpletodo.util;

import android.util.Log;

import com.simpletodo.simpletodo.SimpleTodoApp;

/**
 * A thin wrapper around {@Link Log} to always log to {@link SimpleTodoApp#TAG}
 */
public class SimpleTodoLogger {

    /*
     * --------------------
     * Debug
     * --------------------
     */

    /**
     * Sends a {@link android.util.Log#DEBUG} message.
     * @param msg The message you would like logged
     */
    public static int d(final String msg) {
        return Log.d(SimpleTodoApp.TAG, msg);
    }

    /**
     * Sends a {@link android.util.Log#DEBUG} message and log the exception.
     * @param msg The message you would like logged
     * @param tr An exception to log
     */
    public static int d(final String msg, final Throwable tr) {
        return Log.d(SimpleTodoApp.TAG, msg, tr);
    }

    /*
     * --------------------
     * Error
     * --------------------
     */

    /**
     * Sends a {@link android.util.Log#ERROR} message.
     * @param msg The message you would like logged
     */
    public static int e(final String msg) {
        return Log.e(SimpleTodoApp.TAG, msg);
    }

    /**
     * Sends a {@link android.util.Log#ERROR} message and log the exception.
     * @param msg The message you would like logged
     * @param tr An exception to log
     */
    public static int e(final String msg, final Throwable tr) {
        return Log.e(SimpleTodoApp.TAG, msg, tr);
    }

    /*
     * --------------------
     * Info
     * --------------------
     */

    /**
     * Sends a {@link android.util.Log#INFO} message.
     * @param msg The message you would like logged
     */
    public static int i(final String msg) {
        return Log.i(SimpleTodoApp.TAG, msg);
    }

    /**
     * Sends a {@link android.util.Log#INFO} message and log the exception.
     * @param msg The message you would like logged
     * @param tr An exception to log
     */
    public static int i(final String msg, Throwable tr) {
        return Log.i(SimpleTodoApp.TAG, msg);
    }

    /*
     * --------------------
     * Verbose
     * --------------------
     */

    /**
     * Sends a {@link android.util.Log#VERBOSE} message.
     * @param msg The message you would like logged
     */
    public static int v(final String msg) {
        return Log.v(SimpleTodoApp.TAG, msg);
    }

    /**
     * Sends a {@link android.util.Log#VERBOSE} message and log the exception.
     * @param msg The message you would like logged
     * @param tr An exception to log
     */
    public static int v(final String msg, final Throwable tr) {
        return Log.v(SimpleTodoApp.TAG, msg, tr);
    }

    /*
     * --------------------
     * Warn
     * --------------------
     */

    // Log API has no comment
    public static int w(final Throwable tr) {
        return Log.w(SimpleTodoApp.TAG, tr);
    }


    /**
     * Sends a {@link android.util.Log#WARN} message and log the exception.
     * @param msg The message you would like logged
     * @param tr An exception to log
     */
    public static int w(final String msg, final Throwable tr) {
        return Log.w(SimpleTodoApp.TAG, msg, tr);
    }

    /**
     * Sends a {@link android.util.Log#WARN} message.
     * @param msg The message you would like logged
     */
    public static int w(final String msg) {
        return Log.w(SimpleTodoApp.TAG, msg);
    }

    /*
     * --------------------
     * Wtf
     * --------------------
     */

    /**
     * What a Terrible Failure: Report an exception that should never happen.
     * Similar to {@link #wtf(String)} with an exception to log.
     * @param tr An exception to log
     */
    public static int wtf(final Throwable tr) {
        return Log.wtf(SimpleTodoApp.TAG, tr);
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The error will always
     * be logged at level {@link android.util.Log#ASSERT} with the call stack. Depending on system configuration,
     * a report may be added to the {@link android.os.DropBoxManager} and/or the process may be
     * terminated immediately with an error dialog.
     * @param msg The message you would like logged
     */
    public static int wtf(final String msg) {
        return Log.d(SimpleTodoApp.TAG, msg);
    }

    /**
     * What a Terrible Failure: Report an exception that should never happen.
     * Similar to {@link #wtf(Throwable)} with a nessage as well.
     * @param msg The message you would like logged.
     * @param tr An exception to log. May be null.
     */
    public static int wtf(final String msg, final Throwable tr) {
        return Log.wtf(SimpleTodoApp.TAG, msg, tr);
    }

    /*
     * --------------------
     * Other android.util.Log methods
     * --------------------
     */
    /**
     * Handy function to get a loggable stack trace from a {@link Throwable}.
     * @param tr An exception to log
     */
    public static String getStackTraceString(final Throwable tr) {
        return Log.getStackTraceString(tr);
    }


    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     * The default level of any tag is set to {@link android.util.Log#INFO}. This means that any level above and
     * including {@link android.util.Log#INFO} will be logged. Before you make any calls to a logging method you
     * should check to see if your tag should be logged. You can change the default level by setting
     * a system property: 'setprop log.tag.<YOUR_LOG_SimpleTodoApp.TAG> <LEVEL>' Where level is either
     * {@link android.util.Log#VERBOSE}, {@link android.util.Log#DEBUG}, {@link android.util.Log#INFO}, {@link android.util.Log#WARN},
     * {@link android.util.Log#ERROR}, {@link android.util.Log#ASSERT}, or SUPPRESS. SUPPRESS will turn off all logging for
     * your tag. You can also create a local.prop file that with the following in it:
     * 'log.tag.<YOUR_LOG_SimpleTodoApp.TAG>=<LEVEL>' and place that in /data/local.prop.
     * @param level The level to check
     * @return Whether or not that this is allowed to be logged.
     * @throws IllegalArgumentException is thrown if the {@link SimpleTodoApp#TAG}.length() > 23
     */
    public static boolean isLoggable(final int level) {
        return Log.isLoggable(SimpleTodoApp.TAG, level);
    }

    /**
     * Low-level logging call.
     * @param priority The priority/type of this log message
     * @param msg The message you would like logged
     * @return The number of bytes written
     */
    public static int println(final int priority, final String msg) {
        return Log.println(priority, SimpleTodoApp.TAG, msg);
    }
}
