package com.nadisam.citybombing.levels;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;

public class Game extends SurfaceView
{
    Canvas mCanvas = null;

    public Game(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setSurfaceHolderCallback(SurfaceHolder.Callback callback)
    {
        getHolder().addCallback(callback);
    }

    public void lockCanvas()
    {
        mCanvas = this.getHolder().lockCanvas();
        if (null != mCanvas)
        {
            mCanvas.drawColor(Color.rgb(255, 255, 255));
        }
    }

    public void plot(DrawableItemInterface drawable, long time)
    {
        if (null != mCanvas)
        {
            drawable.draw(mCanvas, time);
        }
    }

    public void unlockCanvas()
    {
        if (null != mCanvas)
        {
            this.getHolder().unlockCanvasAndPost(mCanvas);
        }
    }
}
