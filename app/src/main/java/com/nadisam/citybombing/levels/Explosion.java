package com.nadisam.citybombing.levels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.nadisam.citybombing.levels.interfaces.DrawableItemInterface;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.Utils;

public class Explosion implements DrawableItemInterface
{

    private Bitmap  mExplosionBmp;  // the animation sequence
    private Rect    sourceRect;     // the rectangle to be drawn from the animation bitmap
    private int     frameNr;        // number of frames in animation
    private int     currentFrame;   // the current frame

    private int     spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int     spriteHeight;   // the height of the sprite

    private int     x;              // the X coordinate of the object (top left of the image)
    private int     y;              // the Y coordinate of the object (top left of the image)

    private boolean mDraw   = false;

    public Explosion(int bombType)
    {
        if (bombType == Defines.BOMB_TYPE_A)
        {
            this.mExplosionBmp = Utils.mExplosionA;
            this.frameNr = 43;
        }
        else if (bombType == Defines.BOMB_TYPE_B)
        {
            this.mExplosionBmp = Utils.mExplosionB;
            this.frameNr = 41;
        }
        else
        {
            this.mExplosionBmp = Utils.mExplosionC;
            this.frameNr = 92;
        }

        this.currentFrame = 0;
        this.spriteWidth = mExplosionBmp.getWidth() / this.frameNr;
        this.spriteHeight = mExplosionBmp.getHeight();
        this.sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
    }

    public void update()
    {
        // increment the frame
        currentFrame++;
        if (currentFrame >= frameNr)
        {
            mDraw = false;
            currentFrame = 0;
        }

        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;
    }

    public Explosion activate(int x, int y)
    {
        this.x = x;
        this.y = y;

        this.currentFrame = 0;
        this.update();
        this.mDraw = true;
        return this;
    }

    public boolean isActive()
    {
        return mDraw;
    }

    @Override
    public void draw(Canvas canvas, long time)
    {
        // where to draw the sprite
        if (mDraw)
        {
            int x = this.x - spriteWidth/2;
            int y = this.y - spriteHeight/2;
            
            Rect destRect = new Rect(x, y, x + spriteWidth, y + spriteHeight);
            canvas.drawBitmap(mExplosionBmp, sourceRect, destRect, null);
            update();
        }
    }
}
