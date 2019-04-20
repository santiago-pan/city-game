package com.nadisam.citybombing.levels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.Utils;

public class Extra implements FloorInterface, DrawableItemInterface
{
    private Bitmap  mExtraDrawable    = null;
    private Paint   mPaint            = null;
    private String  mExtraType        = Defines.EXTRA_POWER_BOMBS;
    private int     mFloorType        = 0;
    private int     mX                = 0;
    private int     mY                = 0;
    private boolean mFloorStatus      = Defines.FLOOR_IS_INTACT;
    private int     mDestroyedCounter = 0;

    public Extra(String type, int x, int y)
    {
        this.mExtraType = type;
        this.mExtraDrawable = Utils.loadExtra(this.mExtraType);
        this.mX = x;
        this.mY = y;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(false);
        
        if (0 == type.compareTo(Defines.EXTRA_POWER_BOMBS))
        {
            mFloorType = Defines.FLOOR_EXTRA_TYPE_A;
        }
        else if (0 == type.compareTo(Defines.EXTRA_LOW_SPEED))
        {
            mFloorType = Defines.FLOOR_EXTRA_TYPE_B;
        }
        else if (0 == type.compareTo(Defines.EXTRA_MORE_SHOOTS))
        {
            mFloorType = Defines.FLOOR_EXTRA_TYPE_C;
        }
    }

    public void setCoords(int x, int y)
    {
        this.mX = x;
        this.mY = y;
    }

    @Override
    public int type()
    {
        return mFloorType;
    }
    
    public String typeStr()
    {
        return mExtraType;
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

    @Override
    public int height()
    {
        if (mExtraDrawable != null)
        {
            return mExtraDrawable.getHeight();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int width()
    {
        if (mExtraDrawable != null)
        {
            return mExtraDrawable.getWidth();
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
     * This function returns true when the Extra has been destroyed. It will be removed from the list
     * 
     * @return
     */
    public boolean getExtraScore()
    {
        if (mFloorStatus == Defines.FLOOR_DESTROYED)
        {
            return true;
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
        if (mExtraDrawable == null)
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

        canvas.drawBitmap(this.mExtraDrawable, this.mX, Utils.mScreenHeight - this.mY - this.mExtraDrawable.getHeight(), mPaint);
    }
}
