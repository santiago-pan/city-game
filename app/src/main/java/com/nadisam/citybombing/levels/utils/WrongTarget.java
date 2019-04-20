package com.nadisam.citybombing.levels.utils;

import com.nadisam.citybombing.config.SharedPreferencesHelper;

public class WrongTarget
{
    private final static int ON     = 0;
    private final static int OFF    = 1;
    private static int       status = OFF;
    private static int       wrong  = 0;

    public static void start()
    {
        if (status == OFF)
        {
            status = ON;
        }
        wrong = SharedPreferencesHelper.getWrongTarget(); // Convert seconds to milliseconds
    }

    public static void update()
    {
        if (status == OFF)
        {
            start();
        }
        wrong++;
    }

    public static void stop()
    {
        if (status == ON)
        {
            status = OFF;
            SharedPreferencesHelper.saveWrongTarget(wrong);
        }
    }

    public static int getWrongTarget()
    {
        return SharedPreferencesHelper.getWrongTarget();
    }
}
