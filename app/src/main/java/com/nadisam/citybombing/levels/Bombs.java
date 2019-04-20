package com.nadisam.citybombing.levels;

import java.util.Vector;
import android.graphics.Canvas;
import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.log.Logger;

public class Bombs implements DrawableItemInterface
{
    private Vector<Bomb> mBombs      = new Vector<Bomb>();
    private City         mCity       = null;
    private Explosions   mExplosions = null;
    private int          mPoints     = 0;
    private int          mImpacts    = 0;

    public Bombs(City city, Explosions explosions)
    {
        this.mCity = city;
        this.mExplosions = explosions;
    }

    public void add(Bomb bomb)
    {
        mBombs.add(bomb);
    }

    @Override
    public void draw(Canvas canvas, long time)
    {
        if (null == canvas)
            return;

        bombsManager(canvas, time);
    }

    public int points()
    {
        return mPoints;
    }

    public int impacts()
    {
        return mImpacts;
    }
    /******************* PRIVATE *****************/
    
    private void increasePoints()
    {
        mPoints += 1;
    }

    private void increaseImpacts()
    {
        mImpacts += 1;
    }

    private void addExplosion(Bomb bomb, Building building)
    {
        // If atomic bomb add only the first time
        if (bomb.type() == Defines.BOMB_TYPE_B)
        {
            if (bomb.power() == Defines.BOMB_TYPE_B_POWER - 1)
            {
                this.mExplosions.add(bomb, building);
            }
        }
        else
        {
            this.mExplosions.add(bomb, building);
        }
    }

    private void processBuilding(Building building, Bomb bomb)
    {
        if (null != building)
        {
            int impactType = building.impact(bomb);
            if (Defines.NO_FLOOR_IMPACT != impactType)
            {
                if ((impactType != Defines.FLOOR_GREEN_SCHOOL) && (impactType != Defines.FLOOR_GREEN_HOSPITAL) && (impactType != Defines.FLOOR_GREEN_CHURCH))
                {
                    // Add the bomb to the explosion vector
                    addExplosion(bomb, building);

                    // Increase game points only if the impact is in a building, not in the ground
                    if (impactType != Defines.NO_FLOOR)
                    {
                        // TODO It could include add points depending on the floor desroyect (like extras or enemies)
                        increasePoints();

                        // Increase impacts
                        increaseImpacts();
                    }
                }
                else
                {
                    // Add the bomb to the explosion vector
                    bomb.type(Defines.BOMB_TYPE_B);
                    bomb.power(Defines.BOMB_TYPE_B_POWER - 1);
                    addExplosion(bomb, building);
                }

                // If impact in building and no more power in the bomb
                if (bomb.power() == 0)
                {
                    bomb.blowUp();
                }
                // If bomb still has power but flat is already destroyed
                else if ((bomb.power() > 0) && (building.floors() == 0))
                {
                    bomb.blowUp();
                }
            }
        }
    }

    /**
     * Checks impacts of all bombs in all buildings
     * 
     * @param canvas
     */
    private void bombsManager(Canvas canvas, long time)
    {
        int i = 0;
        int numBombs = mBombs.size();

        for (i = numBombs - 1; i >= 0; i--)
        {
            Bomb bomb = mBombs.get(i);

            Building building = mCity.getBuilding(bomb.x(), bomb.width());

            // Process this building for this bomb
            processBuilding(building, bomb);

            if (bomb.visible())
            {
                bomb.draw(canvas, time);
            }
            else
            {
                Logger.debug("Remove bomb from vector: " + mBombs.size());
                mBombs.remove(i);
            }
        }
    }
}
