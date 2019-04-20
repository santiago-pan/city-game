package com.nadisam.citybombing.levels;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;

public interface FloorInterface extends DrawableItemInterface
{
    public int type();

    public int strength();

    public int x();

    public int y();

    public int height();

    public int width();

    public void destroy();
}
