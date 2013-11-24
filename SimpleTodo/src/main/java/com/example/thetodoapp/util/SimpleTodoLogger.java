/**
 * Created by Cory on 11/21/13.
 */

package com.example.thetodoapp.util;

import android.util.Log;

import com.example.thetodoapp.SimpleTodoApp;

/**
 * A thin wrapper around {@Link Log} to always log to {@link com.example.thetodoapp.SimpleTodoApp#TAG}
 */
public class SimpleTodoLogger {

    private static final String TAG = SimpleTodoApp.TAG;

    /**
     * Sends a {@link Log#DEBUG} message.
     * @param msg The message you would like logged
     */
    public static int d(final String msg) {
        return Log.d(TAG, msg);
    }

    /**
     * Sends a {@link Log#DEBUG} message and log the exception.
     * @param msg The message you would like logged
     * @param tr An exception to log
     */
    public static int d(final String msg, final Throwable tr) {
        return Log.d(TAG, msg, tr);
    }

    /**
     * Sends a {@link Log#ERROR} message.
     * @param msg The message you would like logged
     */
    public static int e(final String msg) {
        return Log.e(TAG, msg);
    }

    /**
     * Sends a {@link Log#ERROR} message and log the exception.
     * @param msg The message you would like logged
     * @param tr An exception to log
     */
    public static int e(final String msg, final Throwable tr) {
        return Log.e(TAG, msg, tr);
    }

    /**
     * Handy function to get a loggable stack trace from a {@link Throwable}.
     * @param tr An exception to log
     */
    public static String getStackTraceString(final Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    /**
     * Sends a {@link Log#INFO} message.
     * @param msg The message you would like logged
     */
    public static int i(final String msg) {
        return Log.i(TAG, msg);
    }

    /**
     * Sends a {@link Log#INFO} message and log the exception.
     * @param msg The message you would like logged
     * @param tr An exception to log
     */
    public static int i(final String msg, Throwable tr) {
        return Log.i(TAG, msg);
    }

    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     * The default level of any tag is set to {@link Log#INFO}. This means that any level above and
     * including {@link Log#INFO} will be logged. Before you make any calls to a logging method you
     * should check to see if your tag should be logged. You can change the default level by setting
     * a system property: 'setprop log.tag.<YOUR_LOG_TAG> <LEVEL>' Where level is either
     * {@link Log#VERBOSE}, {@link Log#DEBUG}, {@link Log#INFO}, {@link Log#WARN},
     * {@link Log#ERROR}, {@link Log#ASSERT}, or SUPPRESS. SUPPRESS will turn off all logging for
     * your tag. You can also create a local.prop file that with the following in it:
     * 'log.tag.<YOUR_LOG_TAG>=<LEVEL>' and place that in /data/local.prop.
     * @param level The level to check
     * @return Whether or not that this is allowed to be logged.
     * @throws java.lang.IllegalArgumentException is thrown if the {@link #TAG}.length() > 23
     */
    public static boolean isLoggable(final int level) {
        return Log.isLoggable(TAG, level);
    }

    /**
     * Low-level logging call.
     * @param priority The priority/type of this log message
     * @param msg The message you would like logged
     * @return The number of bytes written
     */
    public static int println(final int priority, final String msg) {
        return Log.println(priority, TAG, msg);
    }

    /**
     * Sends a {@link Log#VERBOSE} message.
     * @param msg The message you would like logged
     */
    public static int v(final String msg) {
        return Log.v(TAG, msg);
    }

    /**
     * Sends a {@link Log#VERBOSE} message and log the exception.
     * @param msg The message you would like logged
     * @param tr An exception to log
     */
    public static int v(final String msg, final Throwable tr) {
        return Log.v(TAG, msg, tr);
    }

    // Log API has no comment
    public static int w(final Throwable tr) {
        return Log.w(TAG, tr);
    }


    /**
     * Sends a {@link Log#WARN} message and log the exception.
     * @param msg The message you would like logged
     * @param tr An exception to log
     */
    public static int w(final String msg, final Throwable tr) {
        return Log.w(TAG, msg, tr);
    }

    /**
     * Sends a {@link Log#WARN} message.
     * @param msg The message you would like logged
     */
    public static int w(final String msg) {
        return Log.w(TAG, msg);
    }

    /**
     * What a Terrible Failure: Report an exception that should never happen.
     * Similar to {@link #wtf(String)} with an exception to log.
     * @param tr An exception to log
     */
    public static int wtf(final Throwable tr) {
        return Log.wtf(TAG, tr);
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The error will always
     * be logged at level {@link Log#ASSERT} with the call stack. Depending on system configuration,
     * a report may be added to the {@link android.os.DropBoxManager} and/or the process may be
     * terminated immediately with an error dialog.
     * @param msg The message you would like logged
     */
    public static int wtf(final String msg) {
        return Log.d(TAG, msg);
    }

    /**
     * What a Terrible Failure: Report an exception that should never happen.
     * Similar to {@link #wtf(Throwable)} with a nessage as well.
     * @param msg The message you would like logged.
     * @param tr An exception to log. May be null.
     */
    public static int wtf(final String msg, final Throwable tr) {
        return Log.wtf(TAG, msg, tr);
    }
}
