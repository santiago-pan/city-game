package com.nadisam.citybombing.json;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.nadisam.citybombing.log.Logger;
import com.nadisam.citybombing.pro.R;

public class JsonHelper
{
    public static Level getLevel(Context context, int numLevel)
    {
        List<Level> levels = getLevels(context).getLevels();
        int NUM_LEVELS = levels.size();
        
        if (numLevel < 1)
            return null;
        
        if (numLevel > NUM_LEVELS)
            return null;
            
        return levels.get(numLevel-1);
    }
    
    public static Levels getLevels (Context context)
    {
        String json = null;
        Levels levels = null;
        
        try
        {
            json = readLevels(context);
            levels = new Gson().fromJson(json, Levels.class);
        }
        catch (Exception e)
        {
            Logger.error("JSON ERROR",e);
        }
        
        return levels;
    }
    
    private static String readLevels(Context context) throws java.io.IOException
    {
        InputStream inputStream = null;
        inputStream = context.getResources().openRawResource(R.raw.levels);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null)
        {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }
}
