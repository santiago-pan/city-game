package com.nadisam.citybombing.levels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.Utils;

public class Bullet implements DrawableItemInterface
{
    private Bitmap  mBulletDrawable = null;
    private Paint   mPaint          = null;
    private int     mBulletX        = 0;
    private int     mBulletY        = 0;
    private int     mBulletSpeed    = 0;
    private boolean mLeftSide       = false;
    private boolean mIsVisible      = true;

    public Bullet(Enemy enemy)
    {
        this.mBulletSpeed = (int) Utils.getNormSpeed(Utils.getDpi(), Defines.ENEMY_BULLET_SPEED);
        
        // Left side
        if (Utils.mScreenWidth / 2 > enemy.x())
        {
            mLeftSide = true;
            mBulletDrawable = Utils.loadBullet(true);
            mBulletX = enemy.x() + enemy.width()/2 - enemy.width()/6;
            mBulletY = enemy.y() + enemy.height()/2 + enemy.width()/9;
        }
        else
        {
            mLeftSide = false;
            mBulletDrawable = Utils.loadBullet(false);
            mBulletX = enemy.x() + enemy.width()/2;
            mBulletY = enemy.y() + enemy.height()/2 + enemy.width()/9;
        }
    }
    
    
    public void destroy()
    {
        this.mIsVisible = false;
    }
    
    public boolean visible()
    {
        return mIsVisible;
    }

    public int x()
    {
        return this.mBulletX;
    }
    
    public int y()
    {
        return Utils.mScreenHeight - this.mBulletY;
    }
    
    @Override
    public void draw(Canvas canvas, long time)
    {
        bulletPosition();
        
        if (true == mIsVisible)
        {
            canvas.drawBitmap(this.mBulletDrawable, this.mBulletX, Utils.mScreenHeight - this.mBulletY, mPaint);
        }
    }

    private void bulletPosition()
    {
        // Left side
        if (true == mLeftSide)
        {
            mBulletX += mBulletSpeed;
        }
        // Right side
        else
        {
            mBulletX -= mBulletSpeed;
        }
        mBulletY += mBulletSpeed;

        if (mBulletX < 0)
        {
            mIsVisible = false;
        }
        if (mBulletX > Utils.mScreenWidth + 10)
        {
            mIsVisible = false;
        }
        if (mBulletY < 0)
        {
            mIsVisible = false;
        }
        if (mBulletY > Utils.mScreenHeight + 10)
        {
            mIsVisible = false;
        }
    }
}
