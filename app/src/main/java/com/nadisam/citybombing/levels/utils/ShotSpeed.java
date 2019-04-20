package com.nadisam.citybombing.levels.utils;

import com.nadisam.citybombing.config.SharedPreferencesHelper;

public class ShotSpeed
{
    private static long shotSpeed = 0;
    private static long currentBestTime = 10000;
    private static long prevTime = 0;
    private static long newTime = 0;
    
    public static void update()
    {
        newTime = System.currentTimeMillis();
        shotSpeed = newTime - prevTime;
        
        if (shotSpeed < currentBestTime)
        {
            currentBestTime = shotSpeed;
        }
        prevTime = newTime;
    }
    public static void stop()
    {
        int bestTime = SharedPreferencesHelper.getShotSpeed();
        if (bestTime == 0)
        {
            bestTime = 10000;
        }
        if (currentBestTime < bestTime)
        {
            SharedPreferencesHelper.saveShotSpeed((int)currentBestTime);
        }
    }
    public static int getShotSpeed ()
    {
        return SharedPreferencesHelper.getShotSpeed();
    }
}
