package com.nadisam.citybombing.log;

import android.util.Log;

public class Logger
{

    private static final String TAG = "com.nadisam.citybombing.pro";

    public static void debug(String msg)
    {
        Log.d(TAG, "DEBUG: " + msg);
    }

    public static void error(String msg, Exception e)
    {
        Log.e(TAG, "ERROR: " + msg);
        e.printStackTrace();
//        ACRA.getErrorReporter().handleSilentException(e);
    }

    public static void warning(String msg)
    {
        Log.w(TAG, "WARNING: " + msg);
    }
}