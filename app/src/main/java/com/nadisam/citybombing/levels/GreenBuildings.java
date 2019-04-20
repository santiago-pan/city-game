package com.nadisam.citybombing.levels;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;

public class GreenBuildings implements DrawableItemInterface
{

    private List<GreenBuilding> greenBuildings = new ArrayList<GreenBuilding>();

    public GreenBuildings(List<com.nadisam.citybombing.json.Green> greens)
    {
        for (int i = 0; i < greens.size(); i++)
        {
            GreenBuilding green = new GreenBuilding(greens.get(i).getType(),0,0);
            greenBuildings.add(green);
        }
    }

    public List<GreenBuilding> get()
    {
        return greenBuildings;
    }

    public void set(List<GreenBuilding> green)
    {
        this.greenBuildings = green;
    }

    public void add(GreenBuilding green)
    {
        greenBuildings.add(green);
    }

    @Override
    public void draw(Canvas canvas, long time)
    {}
}
