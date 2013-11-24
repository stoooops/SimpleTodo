/**
 * Created by Cory on 11/21/13.
 */
package com.example.thetodoapp.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Static utility class
 */
public class Utils {

    /**
     * Closes the keyboard
     * @param c
     */
    public static void closeKeyboard(final Context c) {
        ((InputMethodManager)
                c.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0, 0);
    }
}
