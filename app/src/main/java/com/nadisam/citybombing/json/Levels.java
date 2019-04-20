package com.nadisam.citybombing.json;

import java.util.ArrayList;
import java.util.List;

public class Levels
{
    private List<Level> levels = new ArrayList<Level>();

    public List<Level> getLevels()
    {
        return levels;
    }

    public void setLevels(List<Level> levels)
    {
        this.levels = levels;
    }
    
    public void add(Level level)
    {
        levels.add(level);
    }
}
