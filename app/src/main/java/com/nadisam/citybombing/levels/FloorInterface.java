package com.nadisam.citybombing.levels;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;

public interface FloorInterface extends DrawableItemInterface
{
    int type();

    int strength();

    int x();

    int y();

    int height();

    int width();

    void destroy();
}
