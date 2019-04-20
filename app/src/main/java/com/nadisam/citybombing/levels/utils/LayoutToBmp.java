package com.nadisam.citybombing.levels.utils;

import android.graphics.Bitmap;
import android.widget.RelativeLayout;

public class LayoutToBmp
{
    public static Bitmap generateBmp(RelativeLayout layout)
    {
        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        Bitmap bm = layout.getDrawingCache();
        return bm;
    }
}
