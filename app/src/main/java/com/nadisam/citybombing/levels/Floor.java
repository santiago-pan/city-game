package com.nadisam.citybombing.levels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.Utils;

public class Floor implements FloorInterface, DrawableItemInterface
{
    private Bitmap  mFloorDrawable    = null;
    private Paint   mPaint            = null;
    private int     mFloorType        = Defines.FLOOR_TYPE_A;
    private int     mFloorStrength    = Defines.MIN_STRENGTH_LEVEL;

    private int     mX                = 0;
    private int     mY                = 0;
    private int     mHeight           = 0;
    private boolean mFloorStatus      = Defines.FLOOR_IS_INTACT;
    private int     mDestroyedCounter = 0;

    public Floor()
    {
        this(Defines.FLOOR_TYPE_A);
    }

    public Floor(int floorType)
    {
        this(floorType, Defines.MIN_STRENGTH_LEVEL);
    }

    public Floor(int floorType, int floorStrength)
    {
        this(floorType, floorStrength, 0, 0);
    }

    public Floor(int floorType, int floorStrength, int x, int y)
    {
        this.mX = x;
        this.mY = y;
        this.mFloorType = floorType;
        this.mFloorStrength = floorStrength;
        this.mFloorDrawable = Utils.loadFloor(mFloorType);
        if (null != this.mFloorDrawable)
        {
            this.mHeight = this.mFloorDrawable.getHeight();
        }
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(false);
    }
    @Override
    public int type()
    {
        return mFloorType;
    }
    @Override
    public int strength()
    {
        return mFloorStrength;
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
    @Override
    public int height()
    {
        return this.mHeight;
    }
    @Override
    public int width()
    {
        if (mFloorDrawable != null)
        {
            return mFloorDrawable.getWidth();
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

    @Override
    public void draw(Canvas canvas, long time)
    {
        if (canvas == null)
            return;
        if (mFloorDrawable == null)
            return;
        // If destroyed and counter == 0
        if ((mFloorStatus == Defines.FLOOR_DESTROYED)&&(0 == mDestroyedCounter))
            return;
        
        if (mDestroyedCounter > 0)
        {
            mDestroyedCounter-=1;
        }

        if (mFloorStatus == Defines.FLOOR_DESTROYED)
        {
            mPaint.setAlpha(mDestroyedCounter*8+127);
        }
        
        canvas.drawBitmap(this.mFloorDrawable, this.mX, Utils.mScreenHeight - this.mY - this.mFloorDrawable.getHeight(), mPaint);
    }
}
