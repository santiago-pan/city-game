package com.nadisam.citybombing.levels;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.graphics.Canvas;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.Utils;
import com.nadisam.citybombing.log.Logger;

public class City implements DrawableItemInterface
{
    private int              mNumBuildings      = 0;
    private int              mBuildingX         = Utils.buildingWidth() * 2;
    private Vector<Building> mBuildings         = null;
    private Random           mRandom            = null;
    private int              mDifficulty        = 0;
    private boolean          mAllCrear          = false;
    private boolean          mGreenDestroyed    = false;
    private boolean          mGreenDestroyedEnd = false;
    private boolean          mAllClearEnd       = false;
    private int              ALL_CLEAR_TIME     = 1000;
    private int              GREEN_DESTRO_TIME  = 1000;
    private Extras           mExtras            = null;
    private Enemies          mEnemies           = null;
    private GreenBuildings   mGreenBuildings    = null;

    public City(Extras extras, Enemies enemies, GreenBuildings greens, int difficulty)
    {
        if (difficulty > Defines.MAX_DIFFICULTY)
            difficulty = Defines.MAX_DIFFICULTY;
        this.mRandom = new Random();
        this.mBuildings = new Vector<Building>();
        this.mNumBuildings = (int) Math.floor((float) (Utils.mScreenWidth - Utils.buildingWidth() * 3) / Utils.buildingWidth());
        this.mDifficulty = Math.round(difficulty / this.mNumBuildings);
        this.mExtras = extras;
        this.mEnemies = enemies;
        this.mGreenBuildings = greens;
        buildCity();
    }

    public int getNumBuildings()
    {
        return this.mBuildings.size();
    }
    
    /**
     * Returns a building by its index in vector.
     * @param location of building in vector
     * @return
     */
    public Building getBuildingByLocation(int location)
    {
        if (location < this.mBuildings.size())
        {   
            return this.mBuildings.get(location);
        }
        return null;
    }

    /**
     * Returns the building in coordinate x
     * @param x
     * @return
     */
    public Building getBuilding(float x)
    {
        if (x < 0)
            return null;
        if (x > Utils.mScreenWidth)
            return null;

        for (int i = 0; i < mNumBuildings; i++)
        {
            Building building = mBuildings.get(i);
            if ((x >= building.x()) && (x < building.xEnd()))
            {
                return building;
            }
        }

        return null;
    }
    
    /**
     * Returns the building location in vector placed in coordinate x
     * @param x
     * @return
     */
    public int getBuildingLocation(float x)
    {
        if (x < 0)
            return -1;
        if (x > Utils.mScreenWidth)
            return -1;

        for (int i = 0; i < mNumBuildings; i++)
        {
            Building building = mBuildings.get(i);
            if ((x >= building.x()) && (x < building.xEnd()))
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * This method takes in count the width of the bomb.
     * 
     * @param x center coord of the bomb
     * @param width of the bomb
     * @return
     */
    public Building getBuilding(float x, float width)
    {
        Building tmpBuilding = null;

        if (x < 0)
            return null;
        if (x > Utils.mScreenWidth)
            return null;
        if (0 >= width)
            return null;

        int lSide = Math.round(x - width / 2);
        int rSide = Math.round(x + width / 2);

        for (int i = 0; i < mNumBuildings; i++)
        {
            Building building = mBuildings.get(i);
            if (((lSide >= building.x()) && (lSide < building.xEnd())) || ((rSide >= building.x()) && (rSide < building.xEnd())))
            {
                // First building
                if (null == tmpBuilding)
                {
                    tmpBuilding = building;
                }
                // If there is another match
                else
                {
                    // Check if this is toll
                    if (tmpBuilding.height() < building.height())
                    {
                        tmpBuilding = building;
                    }
                }
            }
            // If both sides are to the left of this building, then process has finished
            if ((lSide < building.xEnd()) && (rSide < building.xEnd()))
            {
                return tmpBuilding;
            }
        }

        return tmpBuilding;
    }

    // Returns true when no more buildings and floors are left
    public boolean allClear()
    {
        return this.mAllClearEnd;
    }

    public boolean greenDestroyed()
    {
        return this.mGreenDestroyedEnd;
    }

    private void checkGreenDestroyed()
    {
        if (true == this.mGreenDestroyed)
            return;

        for (int i = 0; i < mNumBuildings; i++)
        {
            Building building = mBuildings.get(i);
            if (true == building.greenDestroyed())
            {
                this.mGreenDestroyed = true;

                // Start timer to set allClearEnd = true after some time
                (new Timer()).schedule(new TimerTask() {

                    @Override
                    public void run()
                    {
                        City.this.mGreenDestroyedEnd = true;
                    }
                }, this.GREEN_DESTRO_TIME);
            }
        }
    }

    private void checkAllClear()
    {
        if (true == this.mAllCrear)
            return;

        int numFloors = 0;

        for (int i = 0; i < mNumBuildings; i++)
        {
            Building building = mBuildings.get(i);
            if (false == building.isGreen())
            {
                numFloors += building.floors();
            }
        }

        // If there is at least one floor
        if (numFloors > 0)
        {
            this.mAllCrear = false;
        }
        else
        {
            Logger.debug("All is clear");
            this.mAllCrear = true;

            // Start timer to set allClearEnd = true after some time
            (new Timer()).schedule(new TimerTask() {

                @Override
                public void run()
                {
                    City.this.mAllClearEnd = true;
                }
            }, this.ALL_CLEAR_TIME);
        }
    }

    private void buildCity()
    {
        Extra buildingExtra = null;
        Enemy buildingEnemy = null;
        GreenBuilding buildingGreen = null;

        // Get num extras
        int numExtras = 0;
        int numEnemies = 0;
        int numGreenBuildings = 0;

        List<Integer> extraPositionList = null;
        List<Integer> enemiesPositionList = null;
        List<Integer> greensPositionList = null;

        if (null != this.mExtras)
        {
            numExtras = this.mExtras.get().size();
        }
        if (null != this.mEnemies)
        {
            numEnemies = this.mEnemies.get().size();
        }
        if (null != this.mGreenBuildings)
        {
            numGreenBuildings = this.mGreenBuildings.get().size();
        }

        // Generate random position for all extras, enemies and green buildings
        if (numGreenBuildings > 0)
        {
            greensPositionList = Utils.getRandomItemsList(numGreenBuildings, this.mNumBuildings - 1, null);
        }
        if (numExtras > 0)
        {
            extraPositionList = Utils.getRandomItemsList(numExtras, this.mNumBuildings - 2, greensPositionList);
        }
        if (numEnemies > 0)
        {
            enemiesPositionList = Utils.getRandomItemsList(numEnemies, this.mNumBuildings - 2, greensPositionList);
        }

        // For all building
        for (int i = 0; i < mNumBuildings; i++)
        {
            Building building = null;
            Vector<Integer> floorTypes = new Vector<Integer>();

            // Check if this building is a green building
            if ((numGreenBuildings > 0) && (true == greensPositionList.contains(i)))
            {
                int indexOfGreen = greensPositionList.indexOf(i);
                buildingGreen = this.mGreenBuildings.get().get(indexOfGreen);
                if (buildingGreen.type() == Defines.FLOOR_GREEN_SCHOOL)
                {
                    mNumBuildings -= 2;
                }
                else if (buildingGreen.type() == Defines.FLOOR_GREEN_HOSPITAL)
                {
                    mNumBuildings -= 1;
                }
                else
                {
                    mNumBuildings -= 0;
                }
                floorTypes.add(buildingGreen.type());
                building = new Building(floorTypes, null, null, buildingGreen, mBuildingX);
            }
            // If normal building add floor, extras and enemies
            else
            {
                // Starts in 1 because of the low level
                int floorInit = 1;

                int extraFloorPosition = 0;

                // Random number of floors of this building
                int numFloors = this.mRandom.nextInt(Defines.FLOOR_MAX_FLOORS) + this.mDifficulty - Defines.FLOOR_MIN_FLOORS;

                // First floor
                floorTypes.add(mRandom.nextInt(Defines.NUM_BAJO_TYPES) + Defines.NUM_FLOOR_TYPES + Defines.NUM_ROOF_TYPES);

                // Check if there is some extra in this building
                if (numExtras > 0)
                {
                    if ((true == extraPositionList.contains(i)) && (numFloors > Defines.FLOOR_MIN_FLOORS))
                    {
                        // Get the index where it should be
                        int indexOfExtra = extraPositionList.indexOf(i);

                        buildingExtra = this.mExtras.get().get(indexOfExtra);
                        extraFloorPosition = this.mRandom.nextInt(numFloors - Defines.FLOOR_MIN_FLOORS) + floorInit;
                    }
                }

                // Create building floors
                for (int f = floorInit; f < numFloors - 1; f++)
                {
                    if (f == extraFloorPosition)
                    {
                        floorTypes.add(Defines.FLOOR_EXTRA_TYPE_A);
                    }
                    else
                    {
                        floorTypes.add(mRandom.nextInt(Defines.NUM_FLOOR_TYPES) + Defines.NUM_ROOF_TYPES);
                    }
                }

                // Roof

                // Check if there is some enemy in this building
                if (numEnemies > 0)
                {
                    if (true == enemiesPositionList.contains(i))
                    {
                        // Get the index where it should be
                        int indexOfEnemy = enemiesPositionList.indexOf(i);

                        buildingEnemy = this.mEnemies.get().get(indexOfEnemy);

                        floorTypes.add(Defines.FLOOR_ROOF_ENEMY_TYPE_A);
                    }
                    else
                    {
                        floorTypes.add(mRandom.nextInt(Defines.NUM_ROOF_TYPES));
                    }
                }
                else
                {
                    floorTypes.add(mRandom.nextInt(Defines.NUM_ROOF_TYPES));
                }

                // Create building
                building = new Building(floorTypes, buildingExtra, buildingEnemy, null, mBuildingX);
            }

            // Add building to the building list
            mBuildings.add(building);

            // Increase x position
            mBuildingX += building.width();
        }

        // Get again the number of buildings, just in case to avoid problems with green buildings.
        mNumBuildings = mBuildings.size();
    }

    @Override
    public void draw(Canvas canvas, long time)
    {
        // Check all clear
        checkAllClear();
        // Checks if a green building was destroyed
        checkGreenDestroyed();

        for (int b = 0; b < mNumBuildings; b++)
        {
            mBuildings.get(b).draw(canvas, time);
        }
    }
}
