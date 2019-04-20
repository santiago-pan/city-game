package com.nadisam.citybombing.levels;

import java.util.Vector;

import android.graphics.Canvas;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.log.Logger;

public class Bullets implements DrawableItemInterface
{
    private Vector<Bullet> mBullets = new Vector<Bullet>();
    private Plane mPlane = null;

    public Bullets()
    {
    }
    
    public void setPlane (Plane plane)
    {
        this.mPlane = plane;
    }

    public void add(Bullet bullet)
    {
        mBullets.add(bullet);
    }
    
    private void bulletsManager(Canvas canvas, long time)
    {
        int i = 0;
        int numBullets = mBullets.size();

        for (i = numBullets - 1; i >= 0; i--)
        {
            Bullet bullet = mBullets.get(i);

            // Process this building for this bomb
            processBullet(mPlane, bullet);

            if (bullet.visible())
            {
                bullet.draw(canvas, time);
            }
            else
            {
                Logger.debug("Remove bomb from vector: " + mBullets.size());
                mBullets.remove(i);
            }
        }
    }
    
    private void processBullet (Plane plane, Bullet bullet)
    {
        int x = bullet.x();
        int y = bullet.y();
        
        int xEnd = plane.x();
        int xIni = plane.x() - plane.width();
        int yBottom = plane.y();
        int yTop = plane.y() - plane.height()/2;
        
        if ((x < xEnd)&&(x > xIni))
        {
            if ((y < yBottom)&&( y > yTop))
            {
                Logger.debug("Bullet impact!! " );
                plane.destroy();
                bullet.destroy();
            }
        }
    }

    @Override
    public void draw(Canvas canvas, long time)
    {
        bulletsManager(canvas, time);
    }
}
