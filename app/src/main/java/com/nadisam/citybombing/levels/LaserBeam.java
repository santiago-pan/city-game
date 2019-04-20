package com.nadisam.citybombing.levels;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.nadisam.citybombing.AppCore;
import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.Utils;
import com.nadisam.citybombing.pro.R;

public class LaserBeam implements DrawableItemInterface
{
    private int mX      = 0;
    private int mHeight = 0;
    private int mCnt    = Defines.FRAME_RATE/10;
    private Paint mPaint = null;

    public LaserBeam(int x, int height)
    {
        this.mX = x;
        this.mHeight = height;
        this.mPaint = new Paint();
        this.mPaint.setStrokeWidth(5);
        this.mPaint.setColor(AppCore.getContext().getResources().getColor(R.color.red));
    }

    @Override
    public void draw(Canvas canvas, long time)
    {
        if (this.mCnt > 0)
        {
            this.mCnt--;
            int startX = this.mX;
            int startY = this.mHeight;
            int stopX = this.mX;
            int stopY = Utils.mScreenHeight;
            canvas.drawLine(startX, startY, stopX, stopY, mPaint);
        }
    }
}
