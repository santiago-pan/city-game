package com.nadisam.citybombing.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.nadisam.citybombing.AppCore;
import com.nadisam.citybombing.log.Logger;

public class SharedPreferencesHelper
{
    private static final String SETTINGS_SHARED_PREFERENCES = "com.nadisam.citybombing.configuration";
    private static final String CONFIGURATION_LEVEL         = "level";
    private static final String CONFIGURATION_POINTS        = "points";
    private static final String CONFIGURATION_SHOTS         = "shots";
    private static final String CONFIGURATION_IMPACTS       = "impacts";
    private static final String CONFIGURATION_ACCURACY      = "accuracy";
    private static final String CONFIGURATION_GAME_TIME     = "gametime";
    private static final String CONFIGURATION_SHOT_SPEED    = "shotspeed";
    private static final String CONFIGURATION_WRONG_TARGET  = "wrongtarget";
    private static final String CONFIGURATION_LOST_GAMES    = "lostgames";

    private static final String CONFIGURATION_SOUND         = "sound";

    public static void saveLevel(int level)
    {
        set(CONFIGURATION_LEVEL, level);
    }

    public static int getLevel()
    {
        return get(CONFIGURATION_LEVEL);
    }

    public static void savePoints(int points)
    {
        set(CONFIGURATION_POINTS, points);
    }

    public static int getPoints()
    {
        return get(CONFIGURATION_POINTS);
    }

    public static void saveImpacts(int impacts)
    {
        set(CONFIGURATION_IMPACTS, impacts);
    }

    public static int getImpacts()
    {
        return get(CONFIGURATION_IMPACTS);
    }

    public static void saveShots(int shots)
    {
        set(CONFIGURATION_SHOTS, shots);
    }

    public static int getShots()
    {
        return get(CONFIGURATION_SHOTS);
    }

    public static void saveGameTime(int time)
    {
        set(CONFIGURATION_GAME_TIME, time);
    }

    public static int getGameTime()
    {
        return get(CONFIGURATION_GAME_TIME);
    }

    public static void saveShotSpeed(int speed)
    {
        set(CONFIGURATION_SHOT_SPEED, speed);
    }

    public static int getShotSpeed()
    {
        return get(CONFIGURATION_SHOT_SPEED);
    }

    public static void saveWrongTarget(int wrong)
    {
        set(CONFIGURATION_WRONG_TARGET, wrong);
    }

    public static int getWrongTarget()
    {
        return get(CONFIGURATION_WRONG_TARGET);
    }

    public static void saveLostGames(int lost)
    {
        set(CONFIGURATION_LOST_GAMES, lost);
    }

    public static int getLostGames()
    {
        return get(CONFIGURATION_LOST_GAMES);
    }

    public static void saveSound(boolean sound)
    {
        set(CONFIGURATION_SOUND, sound);
    }

    public static boolean getSound()
    {
        return get(CONFIGURATION_SOUND, true);
    }

    private static void set(String id, int value)
    {
        try
        {
            SharedPreferences settings = AppCore.getContext().getSharedPreferences(SETTINGS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt(id, value);
            editor.apply();
        }
        catch (Exception e)
        {
            Logger.debug("Error updating shared preferences");
        }
    }

    private static void set(String id, boolean value)
    {
        try
        {
            SharedPreferences settings = AppCore.getContext().getSharedPreferences(SETTINGS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(id, value);
            editor.apply();
        }
        catch (Exception e)
        {
            Logger.debug("Error updating shared preferences");
        }
    }

    private static void set(String id, String value)
    {
        try
        {
            SharedPreferences settings = AppCore.getContext().getSharedPreferences(SETTINGS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(id, value);
            editor.apply();
        }
        catch (Exception e)
        {
            Logger.debug("Error updating shared preferences");
        }
    }

    private static String get(String id, String defaultValue)
    {
        String value = "";
        try
        {
            SharedPreferences settings = AppCore.getContext().getSharedPreferences(SETTINGS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
            value = settings.getString(id, defaultValue);
        }
        catch (Exception e)
        {
            Logger.debug("Error retrieving shared preferences");
        }
        return value;
    }

    private static boolean get(String id, boolean defaultValue)
    {
        boolean value = true;

        try
        {
            SharedPreferences settings = AppCore.getContext().getSharedPreferences(SETTINGS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
            value = settings.getBoolean(id, defaultValue);
        }
        catch (Exception e)
        {
            Logger.debug("Error retrieving shared preferences");
        }
        return value;
    }

    private static int get(String id)
    {
        int value = 0;
        try
        {
            SharedPreferences settings = AppCore.getContext().getSharedPreferences(SETTINGS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
            value = settings.getInt(id, 0);
        }
        catch (Exception e)
        {
            Logger.debug("Error retrieving shared preferences");
        }
        return value;
    }

    private static boolean check(String id)
    {
        SharedPreferences settings = AppCore.getContext().getSharedPreferences(SETTINGS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return settings.contains(id);
    }
}
