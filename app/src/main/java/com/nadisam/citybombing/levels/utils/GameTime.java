package com.nadisam.citybombing.levels.utils;

import com.nadisam.citybombing.config.SharedPreferencesHelper;

public class GameTime
{
    private final static int ON = 0;
    private final static int OFF = 1;
    private final static int TOMILLIS = 1000;
    private static int status = OFF;
    private static long time = 0;
    
    public static void start()
    {
        if (status == OFF)
        {
            status = ON;
        }
        // Remember to multiply by 1000 after saving
        time = SharedPreferencesHelper.getGameTime()*TOMILLIS; // Convert seconds to milliseconds
    }
    
    public static void update (long millisec)
    {
        if (status == OFF)
        {
            start();
        }
        time += millisec;
    }
    public static void stop()
    {
        // Remember to divide by 1000 before saving
        if (status == ON)
        {
            status = OFF;
            SharedPreferencesHelper.saveGameTime(Math.round(time/TOMILLIS));
        }
    }
    
    public static String getGameTime()
    {
        String gameTimeStr = "";
        int totalSecs = SharedPreferencesHelper.getGameTime();
        
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        gameTimeStr = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        
        return gameTimeStr;
    }
}
