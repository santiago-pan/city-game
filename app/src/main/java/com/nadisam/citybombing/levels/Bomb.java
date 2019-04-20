package com.nadisam.citybombing.levels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.nadisam.citybombing.AppCore;
import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.Utils;

public class Bomb implements DrawableItemInterface
{
    private Bitmap  mDrawable     = null;
    private int     mBombWidth    = 0;
    private int     mBombHeight   = 0;
    private Paint   mPaint        = null;
    private int     mType         = Defines.BOMB_TYPE_A;
    private int     mAcceleration = (int) Utils.getNormSpeed(Utils.getDpi(), Defines.BOMB_ACCELERATION);
    private float   mTime         = 0;
    private float   mSpeed        = (int) Utils.getNormSpeed(Utils.getDpi(), Defines.BOMB_INITIAL_SPEED);
    private boolean mIsVisible    = true;                      // While power is on
    private boolean mIsIntact     = true;                      // While no impact
    private float   mX            = 0;
    private float   mY            = 0;
    private int     mPower        = 0;

    public Bomb(int type, int x, int y)
    {
        this.mType = type;
        this.mX = x;
        this.mY = y;
        if (this.mType == Defines.BOMB_TYPE_A)
        {
            this.mDrawable = Utils.mBoomTypeA;
            this.mPower = Defines.BOMB_TYPE_A_POWER;
        }
        else if (this.mType == Defines.BOMB_TYPE_B)
        {
            this.mDrawable = Utils.mBoomTypeB;
            this.mPower = Defines.BOMB_TYPE_B_POWER;
        }
        else if (this.mType == Defines.BOMB_TYPE_C)
        {
            this.mDrawable = Utils.mBoomTypeC;
            this.mPower = Defines.BOMB_TYPE_C_POWER;
        }
        else
        {
            this.mDrawable = Utils.mBoomTypeA;
            this.mPower = Defines.BOMB_TYPE_A_POWER;
        }

        this.mBombWidth = this.mDrawable.getWidth() / 2;
        this.mBombHeight = this.mDrawable.getHeight() / 2;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(false);
        this.mTime = 0;
    }

    // Ask if bomb is visible
    public boolean visible()
    {
        return mIsVisible;
    }

    // Bomb explosion, not visible anymore
    public void blowUp()
    {
        mIsVisible = false;
        AppCore.getContext().playSound(this.type());
    }

    public int type()
    {
        return mType;
    }

    public void type(int ftype)
    {
        this.mType = ftype;
    }
    
    public int power()
    {
        return this.mPower;
    }

    public void power(int fpower)
    {
        this.mPower = fpower;
    }
    public void losePower()
    {
        this.mIsIntact = false;
        this.mPower -= 1;
    }

    // Center of the bomb
    public float x()
    {
        return (this.mX + this.mBombWidth);
    }

    // Bottom side of the bomb
    public float y()
    {
        return (this.mY + this.mBombHeight);
    }

    // Width of the bomb
    public float width()
    {
        return this.mBombWidth * 2;
    }

    // Matrix rotator = new Matrix();
    private float  rotation = 0;
    private Matrix matrix   = new Matrix();

    @Override
    public void draw(Canvas canvas, long time)
    {
        if (canvas == null)
            return;
        if (this.mDrawable == null)
            return;

        // Increase position
        if (this.mY < Utils.mScreenHeight)
        {
            // Calculate speed
            mSpeed += (float) (mAcceleration * mTime * mTime) / 2;

            // Increase time
            mTime += Defines.BOMB_TIME_GAP;

            this.mY += mSpeed;

            // Plot with bomb rotation
            if (this.mIsIntact == true)
            {
                plotWithRotation(canvas);
                // plotWithNoRotation(canvas);
            }
        }
        else
        {
            // Bomb is out of the screen
            mIsVisible = false;
        }
    }

    private void plotWithRotation(Canvas canvas)
    {
        rotation += 1f;
        matrix.reset();
        matrix.postTranslate(-this.mBombWidth, -this.mBombHeight);
        matrix.postRotate(rotation);
        matrix.postTranslate(this.mX + this.mBombWidth, this.mY);

        canvas.drawBitmap(this.mDrawable, matrix, mPaint);
    }

    private void plotWithNoRotation(Canvas canvas)
    {
        canvas.drawBitmap(this.mDrawable, this.mX, this.mY, mPaint);
    }
}
