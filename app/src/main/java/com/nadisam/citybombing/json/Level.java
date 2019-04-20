package com.nadisam.citybombing.json;

import java.util.ArrayList;
import java.util.List;

public class Level
{
    private int         level;                           // 1, 2, etc
    private int         difficulty;                      // area of the city (the heigh of the city is calculated = height = )area / num_of_buildings)
    private int         speed;                           // Speed of the plane
    private int         gap;                             // Vertical gap of the plane
    private List<Extra> extras  = new ArrayList<Extra>(); // extras distributed in the city
    private List<Enemy> enemies = new ArrayList<Enemy>(); // enemies distributed in the city
    private List<Green> greens  = new ArrayList<Green>(); // green buildings distributed in the city

    public Level()
    {}

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public int getGap()
    {
        return gap;
    }

    public void setGap(int gap)
    {
        this.gap = gap;
    }

    public List<Extra> getExtras()
    {
        return this.extras;
    }

    public void setExtras(List<Extra> extras)
    {
        this.extras = extras;
    }

    public List<Enemy> getEnemies()
    {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies)
    {
        this.enemies = enemies;
    }
    
    public List<Green> getGreens()
    {
        return this.greens;
    }

    public void setGreens(List<Green> greens)
    {
        this.greens = greens;
    }
    
    public void addEnemy(Enemy enemy)
    {
        this.enemies.add(enemy);
    }

    public void addExtra(Extra extra)
    {
        this.extras.add(extra);
    }
    
    public void addGreen(Green green)
    {
        this.greens.add(green);
    }
}