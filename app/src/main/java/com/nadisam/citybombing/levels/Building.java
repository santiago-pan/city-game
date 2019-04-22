package com.nadisam.citybombing.levels;

import java.util.Vector;

import android.graphics.Canvas;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.Utils;

public class Building implements DrawableItemInterface
{
    private Vector<Integer>        mFloorTypes        = null;

    private Vector<FloorInterface> mDestroyedBuilding = null;
    private Vector<FloorInterface> mBuilding          = null;
    private int                    mX                 = 0;
    private int                    mNumFloors         = 0;
    private int                    mNumDestroyFloors   = 0;
    private int                    mBuildingWidth     = 0;
    private int                    mBuildingHeight    = 0;
    private boolean                mIsGreenBuilding   = false;
    private boolean                mGreenDestroyed   = false;
    
    /**
     * Build building with numFloors and foorTypes
     * 
     * @param floorTypes Types of each floor [0...N] 0: low level, N: roof
     */
    public Building(Vector<Integer> floorTypes, Extra buildingExtra, Enemy buildingEnemy, GreenBuilding greenBuilding, int x)
    {
        int i = 0;
        int y = 0;

        this.mX = x;

        if (null == floorTypes)
            return;
        if (0 == floorTypes.size())
            return;

        this.mFloorTypes = floorTypes;
        this.mNumFloors = this.mFloorTypes.size();

        this.mBuilding = new Vector<FloorInterface>();
        this.mDestroyedBuilding = new Vector<FloorInterface>();

        for (i = 0; i < mNumFloors; i++)
        {
            Floor floor = null;
            // It's a extra floor
            if (this.mFloorTypes.get(i) == Defines.FLOOR_EXTRA_TYPE_A)
            {
                buildingExtra.setCoords(mX, y);
                mBuilding.add(buildingExtra);
                y = y + buildingExtra.height(); // increase the height;
            }
            // It's a enemy roof
            else if (this.mFloorTypes.get(i) == Defines.FLOOR_ROOF_ENEMY_TYPE_A)
            {
                buildingEnemy.setCoords(mX, y);
                mBuilding.add(buildingEnemy);
                y = y + buildingEnemy.height(); // increase the height;
            }
            // It's a church
            else if (this.mFloorTypes.get(i) == Defines.FLOOR_GREEN_CHURCH)
            {
                mIsGreenBuilding = true;
                greenBuilding.setCoords(mX, y);
                mBuilding.add(greenBuilding);
                y = y + greenBuilding.height(); // increase the height;
            }
            // It's a school
            else if (this.mFloorTypes.get(i) == Defines.FLOOR_GREEN_SCHOOL)
            {
                mIsGreenBuilding = true;
                greenBuilding.setCoords(mX, y);
                mBuilding.add(greenBuilding);
                y = y + greenBuilding.height(); // increase the height;
            }
            // It's a hospital
            else if (this.mFloorTypes.get(i) == Defines.FLOOR_GREEN_HOSPITAL)
            {
                mIsGreenBuilding = true;
                greenBuilding.setCoords(mX, y);
                mBuilding.add(greenBuilding);
                y = y + greenBuilding.height(); // increase the height;
            }
            else
            {
                floor = new Floor(this.mFloorTypes.get(i), 1, mX, y);
                mBuilding.add(floor);
                y = y + floor.height(); // increase the height;
            }
        }

        // Get width
        if (mNumFloors > 0)
        {
            mBuildingWidth = mBuilding.get(0).width();
        }
    }

    public int x()
    {
        return this.mX;
    }

    public int xEnd()
    {
        return this.mX + this.width();
    }

    public int width()
    {
        return mBuildingWidth;
    }

    public int height()
    {
        int i = 0;
        int height = 0;

        for (i = 0; i < mNumFloors; i++)
        {
            height += mBuilding.get(i).height();
        }
        this.mBuildingHeight = height;
        return this.mBuildingHeight;
    }

    public int floors()
    {
        return mNumFloors;
    }
    
    public FloorInterface getFloor (int index)
    {
        return this.mBuilding.get(index);
    }
    
    public boolean isGreen()
    {
        return mIsGreenBuilding;
    }

    /**
     * Check if there is a collision between a bomb and this building
     * 
     * @param bomb
     * @return
     */
    public int impact(Bomb bomb)
    {
        if (bomb.y() > Utils.mScreenHeight - this.height())
        {
            if (true == this.mIsGreenBuilding)
            {
                this.mGreenDestroyed = true;
            }
            return destroyBuilding(bomb);
        }
        else
        {
            return Defines.NO_FLOOR_IMPACT;
        }
    }

    /**
     * Check if there is a collision between the plane and this building
     * 
     * @param plane
     * @return
     */
    public boolean collision(Plane plane)
    {
        if (plane.y() > Utils.mScreenHeight - this.height())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Checks if a green building was destroyed.
     * @return
     */
    public boolean greenDestroyed()
    {
        return this.mGreenDestroyed;
    }

    /**
     * Decrease the power of the bomb. Destroys the floor and returns the type of floor destroyed.
     * 
     * @param bomb
     * @return What type of floor.
     */
    private int destroyBuilding(Bomb bomb)
    {
        synchronized (mBuilding)
        {
            if (bomb.power() > 0)
            {
                // Remove from building and add to destroyed floors
                if (mBuilding.size() > 0)
                {
                    FloorInterface floor = mBuilding.remove(mBuilding.size() - 1);
                    floor.destroy();
                    mNumFloors = mBuilding.size();

                    if (bomb.type() != Defines.BOMB_TYPE_A)
                    {
                        mDestroyedBuilding.add(floor);
                        mNumDestroyFloors += 1;
                    }

                    bomb.losePower();

                    return floor.type();
                }
                // If no more floors set bomb power level to 1
                else
                {
                    bomb.power(Defines.BOMB_TYPE_B_POWER - 1);
                    return Defines.NO_FLOOR;
                }
            }
            else
            {
                return 0;
            }
        }
    }

    @Override
    public void draw(Canvas canvas, long time)
    {
        int i = 0;

        for (i = 0; i < mNumFloors; i++)
        {
            mBuilding.get(i).draw(canvas, time);
        }
        for (i = 0; i < mNumDestroyFloors; i++)
        {
            mDestroyedBuilding.get(i).draw(canvas, time);
        }
    }
}
