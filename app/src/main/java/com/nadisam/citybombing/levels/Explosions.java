package com.nadisam.citybombing.levels;

import java.util.Vector;

import android.graphics.Canvas;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;

public class Explosions implements DrawableItemInterface
{
    private Vector<Explosion> mExplosions = new Vector<Explosion>();

    public Explosions()
    {
    }

    public void add(Bomb bomb, Building building)
    {
        Explosion explosion = new Explosion(bomb.type());
        explosion.activate((int)building.x() + building.width()/2, (int)bomb.y());
        this.mExplosions.add(explosion);
    }

    @Override
    public void draw(Canvas canvas, long time)
    {
        int num = this.mExplosions.size();
        for (int i = num - 1; i >= 0; i--)
        {
            if (this.mExplosions.get(i).isActive())
            {
                this.mExplosions.get(i).draw(canvas, time);
            }
            else
            {
                this.mExplosions.remove(i);
            }
        }
    }
}
