package com.nadisam.citybombing.levels;

import java.util.ArrayList;
import java.util.List;

import com.nadisam.citybombing.log.Logger;

public class Extras
{
    private List<Extra> floorExtras = new ArrayList<Extra>();

    public Extras(List<com.nadisam.citybombing.json.Extra> extras)
    {
        for (int i = 0; i < extras.size(); i++)
        {
            Extra floorExtra = new Extra(extras.get(i).getType(), 0, 0);
            floorExtras.add(floorExtra);
        }
    }

    public List<Extra> get()
    {
        return floorExtras;
    }

    public void set(List<Extra> extras)
    {
        this.floorExtras = extras;
    }

    public void add(Extra extra)
    {
        floorExtras.add(extra);
    }

    /**
     * Checks if some extra has been destroyed.
     * 
     * @return The Extra destroyed of null otherwise.
     */
    public Extra getExtraScore()
    {
        Extra buildingExtra = null;
        int num = this.floorExtras.size();
        for (int i = num - 1; i >= 0; i--)
        {
            try
            {
                if (this.floorExtras.get(i).getExtraScore())
                {
                    buildingExtra = this.floorExtras.remove(i);
                    break;
                }
            }
            catch (Exception e)
            {
                Logger.debug("EXTRA: Error deleting extra");
            }
        }
        return buildingExtra;
    }
}
