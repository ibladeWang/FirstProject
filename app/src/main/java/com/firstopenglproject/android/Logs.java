package com.firstopenglproject.android;

import android.util.Log;

/**
 * Created by ylwang on 2017/5/12.
 */

public class Logs {
    public static boolean LOG_V = true;
    public static boolean LOG_D = true;
    public static boolean LOG_I = true;
    public static boolean LOG_E = true;

    public static int v(String TAG, String msg) {
        return Log.v(TAG, msg);
    }

    public static int d(String TAG, String msg) {
        return Log.d(TAG, msg);
    }

    public static int i(String TAG, String msg) {
        return Log.i(TAG, msg);
    }

    public static int e(String TAG, String msg) {
        return Log.e(TAG, msg);
    }
}
