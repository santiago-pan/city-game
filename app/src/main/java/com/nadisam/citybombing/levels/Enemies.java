package com.nadisam.citybombing.levels;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;

public class Enemies implements DrawableItemInterface
{
    private List<Enemy> floorEnemies = new ArrayList<Enemy>();
    private Bullets     mBullets     = null;

    public Enemies(List<com.nadisam.citybombing.json.Enemy> enemies, Bullets bullets)
    {
        for (int i = 0; i < enemies.size(); i++)
        {
            Enemy floorEnemy = new Enemy(enemies.get(i).getType(), 0, 0);
            floorEnemies.add(floorEnemy);
        }

        this.mBullets = bullets;
    }

    public List<Enemy> get()
    {
        return floorEnemies;
    }

    public void set(List<Enemy> enemies)
    {
        this.floorEnemies = enemies;
    }

    public void add(Enemies enemies)
    {
        enemies.add(enemies);
    }

    /**
     * Checks impacts of all bombs in all buildings
     * 
     * @param canvas
     */
    private void enemiesManager(Canvas canvas)
    {
        int i = 0;
        int numEnemy = floorEnemies.size();

        // Check shoots
        for (i = 0; i < numEnemy; i++)
        {
            Enemy enemy = floorEnemies.get(i);
            if (true == enemy.shoot())
            {
                mBullets.add(new Bullet(enemy));
            }
        }
    }

    @Override
    public void draw(Canvas canvas, long time)
    {
        enemiesManager(canvas);
    }
}