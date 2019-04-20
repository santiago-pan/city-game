package com.nadisam.citybombing.levels.utils;

import com.nadisam.citybombing.config.SharedPreferencesHelper;

public class LostGames
{
    private final static int ON     = 0;
    private final static int OFF    = 1;
    private static int       status = OFF;
    private static int       lost  = 0;

    public static void start()
    {
        if (status == OFF)
        {
            status = ON;
        }
        lost = SharedPreferencesHelper.getLostGames(); // Convert seconds to milliseconds
    }

    public static void update()
    {
        if (status == OFF)
        {
            start();
        }
        lost++;
    }

    public static void stop()
    {
        if (status == ON)
        {
            status = OFF;
            SharedPreferencesHelper.saveLostGames(lost);
        }
    }

    public static int getLostGames()
    {
        return SharedPreferencesHelper.getLostGames();
    }
}
