package com.nadisam.citybombing.levels;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.Utils;

public class Enemy implements FloorInterface, DrawableItemInterface
{
    private Bitmap  mEnemyDrawable    = null;
    private Paint   mPaint            = null;
    private String  mEnemyType        = Defines.ENEMY_AIRCRAFT_BATTERY;
    private int     mFloorType        = 0;
    private int     mX                = 0;
    private int     mY                = 0;
    private int     mBulletX          = 0;
    private int     mBulletY          = 0;
    private boolean mFloorStatus      = Defines.FLOOR_IS_INTACT;
    private int     mDestroyedCounter = 0;
    private int     mShootTime        = Defines.ENEMY_BULLET_MIN_RELOAD + new Random().nextInt(Defines.ENEMY_BULLET_MAX_RELOAD);

    public Enemy(String type, int x, int y)
    {
        this.mEnemyType = type;
        this.mX = x;
        this.mY = y;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(false);

        if (0 == type.compareTo(Defines.ENEMY_AIRCRAFT_BATTERY))
        {
            mFloorType = Defines.FLOOR_ROOF_ENEMY_TYPE_A;
        }
        else if (0 == type.compareTo(Defines.ENEMY_BALOON_MINES))
        {
            mFloorType = Defines.FLOOR_ROOF_ENEMY_TYPE_B;
        }
    }

    public void setCoords(int x, int y)
    {
        this.mX = x;
        this.mY = y;

        // Left side
        if (Utils.mScreenWidth / 2 > this.mX)
        {
            this.mEnemyDrawable = Utils.loadEnemy(this.mEnemyType, true);
        }
        else
        {
            this.mEnemyDrawable = Utils.loadEnemy(this.mEnemyType, false);
        }
    }

    @Override
    public int type()
    {
        return mFloorType;
    }

    public String typeStr()
    {
        return mEnemyType;
    }

    @Override
    public int strength()
    {
        return 1;
    }

    @Override
    public int x()
    {
        return this.mX;
    }

    @Override
    public int y()
    {
        return this.mY;
    }

    public int getBulletX()
    {
        return this.mBulletX;
    }

    public int getBulletY()
    {
        return this.mBulletY;
    }

    @Override
    public int height()
    {
        if (mEnemyDrawable != null)
        {
            return mEnemyDrawable.getHeight();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int width()
    {
        if (mEnemyDrawable != null)
        {
            return mEnemyDrawable.getWidth();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public void destroy()
    {
        this.mFloorStatus = Defines.FLOOR_DESTROYED;
        this.mDestroyedCounter = Defines.DESTROYED_COUNTER;
    }

    /**
     * Returns true if a bullet must be shot
     * 
     * @return
     */
    public boolean shoot()
    {
        if (mFloorStatus != Defines.FLOOR_DESTROYED)
        {
            if (0 == mShootTime)
            {
                mShootTime = Defines.ENEMY_BULLET_MIN_RELOAD + new Random().nextInt(Defines.ENEMY_BULLET_MAX_RELOAD);
                return true;
            }
            else
            {
                mShootTime--;
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    public void draw(Canvas canvas, long time)
    {
        if (canvas == null)
            return;
        if (mEnemyDrawable == null)
            return;
        // If destroyed and counter == 0
        if ((mFloorStatus == Defines.FLOOR_DESTROYED) && (0 == mDestroyedCounter))
            return;

        if (mDestroyedCounter > 0)
        {
            mDestroyedCounter -= 1;
        }

        if (mFloorStatus == Defines.FLOOR_DESTROYED)
        {
            mPaint.setAlpha(mDestroyedCounter * 8 + 127);
        }

        canvas.drawBitmap(this.mEnemyDrawable, this.mX, Utils.mScreenHeight - this.mY - this.mEnemyDrawable.getHeight(), mPaint);
    }
}
