package com.nadisam.citybombing.levels;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.nadisam.citybombing.AppCore;
import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.Utils;
import com.nadisam.citybombing.log.Logger;

public class Plane implements DrawableItemInterface
{
    private Bitmap            mDrawable       = null;
    private Paint             mPaint          = null;
    private int               mType           = 0;
    private int               mBombType       = 0;
    private int               mGap            = 0;
    private float             mSpeed          = 0;
    private float             mRememberSpeed  = 0;          // To remember original speed
    private boolean           mCollision      = false;      // When collision happens
    private boolean           mCollisionEnd   = false;      // When collision explosion finished
    private Bomb              mBomb           = null;
    private City              mCity           = null;
    private float             mX              = 0;
    private int               mY              = 0;
    private int               mWidth          = 0;
    private int               mHeight         = 0;
    private int               mShots          = 0;
    private int               mTotalFirePower = 0;          // Fire power released
    private int               mExtraShots     = 0;
    private boolean           mExtraLowSpeed  = false;
    private Vector<Explosion> mExplosions     = null;
    private Timer             mCollisionTimer = new Timer();
    private int               COLLISION_TIME  = 2000;

    public Plane(City city, int type, int gap, int speed)
    {
        this.mCity = city;
        this.mType = type;
        this.mGap = (int) Utils.getNormSpeed(Utils.getDpi(), gap);
        this.mSpeed = (int) Utils.getNormSpeed(Utils.getDpi(), speed);
        this.mRememberSpeed = this.mSpeed;
        this.mDrawable = Utils.loadPlane(mType);
        this.mWidth = this.mDrawable.getWidth();
        this.mHeight = this.mDrawable.getHeight();
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(false);
        this.mY = (int) Utils.getNormDistance(Utils.getDpi(), 10f);// 1cm from top //Utils.mScreenHeight / 10;
        this.mX = 0;
        this.mExplosions = new Vector<Explosion>();

        if (this.mType == Defines.PLANE_TYPE_A)
        {
            this.mShots = Defines.PLANE_TYPE_A_MAX_SHOTS;
            this.mBombType = Defines.BOMB_TYPE_A;
        }
        else if (this.mType == Defines.PLANE_TYPE_B)
        {
            this.mShots = Defines.PLANE_TYPE_B_MAX_SHOTS;
            this.mBombType = Defines.BOMB_TYPE_B;
        }
        else if (this.mType == Defines.PLANE_TYPE_C)
        {
            this.mShots = Defines.PLANE_TYPE_C_MAX_SHOTS;
            this.mBombType = Defines.BOMB_TYPE_C;
        }
        else
        {
            // DEFAULT
        }
    }

    public int type()
    {
        return this.mType;
    }

    public Bomb fire()
    {
        // If you have bombs and you are not in a collision then you can keep firing.
        if ((this.mShots > 0) && (!this.mCollision))
        {
            mBomb = new Bomb(this.mBombType, this.xBomb(), this.yBomb());
            this.updateFirePower(mBomb);
            mShots -= 1;
            return mBomb;
        }
        else
        {
            return null;
        }
    }

    /**
     * Extra shot fire. Doesn't affect to plane bombs.
     * 
     * @param bombType
     * @return
     */
    public Bomb fire(int bombType)
    {
        if (!this.mCollision)
        {
            this.mBomb = new Bomb(bombType, this.xBomb(), this.yBomb());
            this.updateFirePower(mBomb);
            return mBomb;
        }
        else
        {
            return null;
        }
    }

    public void fireBlast(Bombs bombs)
    {
        int numBombs = 5;
        int iniIndex = 0;
        int endIndex = 0;
        int numBuildings = this.mCity.getNumBuildings();
        int buildingIndex = this.mCity.getBuildingLocation(this.x() - this.mWidth / 2);

        if (numBuildings < 0)
            return;
        if (buildingIndex < 0)
            return;

        if (buildingIndex > numBuildings - 3)
        {
            iniIndex = numBuildings - 4;
            endIndex = numBuildings;
        }
        else
        {
            iniIndex = Math.max(0, buildingIndex - 3);
            endIndex = iniIndex + numBombs - 1;
        }

        int cnt = numBombs - 1;
        for (int i = iniIndex; i < endIndex; i++)
        {
            Building building = this.mCity.getBuildingByLocation(i);
            if (!building.isGreen())
            {
                Bomb bomb = new Bomb(Defines.BOMB_TYPE_A, building.x(), this.yBomb() + (cnt * this.mGap));
                this.updateFirePower(bomb);
                bombs.add(bomb);
                cnt--;
            }
        }
    }

    private LaserBeam laserBeam = null;

    public void fireLaserBeam(Bombs bombs)
    {
        laserBeam = new LaserBeam(this.x(), this.yBomb());
        Building building = this.mCity.getBuilding(this.x());

        if (null != building)
        {
            int numFloors = building.floors();

            for (int i = 0; i < numFloors; i++)
            {
                FloorInterface floor = building.getFloor(i);
                Bomb bomb = new Bomb(Defines.BOMB_TYPE_A, building.x(), Utils.mScreenHeight-floor.y());
                this.updateFirePower(bomb);
                bombs.add(bomb);
            }
        }
    }

    public void setExtraShoots()
    {
        if (this.mType == Defines.PLANE_TYPE_A)
        {
            this.mExtraShots += Defines.PLANE_TYPE_A_MAX_SHOTS;
        }
        else if (this.mType == Defines.PLANE_TYPE_B)
        {
            this.mExtraShots += Defines.PLANE_TYPE_B_MAX_SHOTS;
        }
        else if (this.mType == Defines.PLANE_TYPE_C)
        {
            this.mExtraShots += Defines.PLANE_TYPE_C_MAX_SHOTS;
        }
        else
        {
            // DEFAULT
        }

        reload();
    }

    public void enableLowSpeed()
    {
        this.mExtraLowSpeed = true;
    }

    /**
     * Tries to set low speed if it is enabled.
     * 
     * @return True or false
     */
    public boolean setLowSpeed()
    {
        if (mExtraLowSpeed)
        {
            mSpeed = mSpeed - mSpeed / 4;
            return true;
        }
        else
        {
            return false;
        }
    }

    public void resetSpeed()
    {
        this.mSpeed = this.mRememberSpeed;
    }

    public int getFirePower()
    {
        return this.mTotalFirePower;
    }

    /********** Private methods **************/

    private void reload()
    {
        if (this.mType == Defines.PLANE_TYPE_A)
        {
            this.mShots = mExtraShots + Defines.PLANE_TYPE_A_MAX_SHOTS;
        }
        else if (this.mType == Defines.PLANE_TYPE_B)
        {
            this.mShots = mExtraShots + Defines.PLANE_TYPE_B_MAX_SHOTS;
        }
        else if (this.mType == Defines.PLANE_TYPE_C)
        {
            this.mShots = mExtraShots + Defines.PLANE_TYPE_C_MAX_SHOTS;
        }
        else
        {
            // DEFAULT
        }
    }

    private void updateFirePower(Bomb bomb)
    {
        mTotalFirePower += bomb.power();
    }

    @Override
    public void draw(Canvas canvas, long time)
    {
        if (canvas == null)
            return;
        if (mDrawable == null)
            return;

        if (false == mCollision)
        {
            // Check where is the plane
            if (this.mX > Utils.mScreenWidth + this.mWidth)
            {
                this.mX = -this.mWidth;
                this.mY += mGap;
                this.reload(); // New set of shots
            }
            else
            {
                float newX = (time * mSpeed) / (1000 / Defines.FRAME_RATE);
                this.mX += newX;
            }

            canvas.drawBitmap(this.mDrawable, this.mX, this.mY, mPaint);

            // Check if there is a collision
            crashManagement();
        }
        else
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

        // If LaserBeam
        if (null != this.laserBeam)
        {
            laserBeam.draw(canvas, 0);
        }
    }

    // Bomb initial position
    private int xBomb()
    {
        return (int) (this.mX + mWidth / 2);
    }

    private int yBomb()
    {
        return (this.mY + mHeight);
    }

    // Right side of the plane
    public int x()
    {
        return (int) (this.mX + mWidth);
    }

    // Bottom side of the plane
    public int y()
    {
        return (this.mY + mHeight);
    }

    public int width()
    {
        return this.mWidth;
    }

    public int height()
    {
        return this.mHeight;
    }

    public int shots()
    {
        return mShots;
    }

    /**
     * Returns true when there was a collision with a building
     * 
     * @return
     */
    public boolean collision()
    {
        return this.mCollisionEnd;
    }

    public void destroy()
    {
        mCollision = true;

        // Create the explosion of the plane
        explosion();
    }

    private void crashManagement()
    {
        // If already in collision no more checking
        if (mCollision)
            return;

        // Building behind the plane
        Building building = mCity.getBuilding(this.x());

        if ((null != building) && (true == building.collision(this)))
        {
            Logger.debug("Plane: Collision start!!");

            mCollision = true;

            // Create the explosion of the plane
            explosion();
        }
    }

    private void explosion()
    {
        this.mExplosions.add(new Explosion(Defines.BOMB_TYPE_A).activate(this.x() - this.mWidth / 2, this.mY));
        this.mExplosions.add(new Explosion(Defines.BOMB_TYPE_B).activate(this.x(), this.mY + this.mHeight / 4));
        this.mExplosions.add(new Explosion(Defines.BOMB_TYPE_A).activate(this.x() + this.mWidth / 2, this.mY + this.mHeight / 2));

        this.mCollisionTimer.schedule(new TimerTask() {

            @Override
            public void run()
            {
                Logger.debug("Plane: Collision ends!!");
                Plane.this.mCollisionEnd = true;
            }
        }, this.COLLISION_TIME);

        AppCore.getContext().playSound(Defines.BOMB_TYPE_C);
    }
}
