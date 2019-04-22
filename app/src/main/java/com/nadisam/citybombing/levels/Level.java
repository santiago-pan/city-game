package com.nadisam.citybombing.levels;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nadisam.citybombing.config.SharedPreferencesHelper;
import com.nadisam.citybombing.gui.Congrats;
import com.nadisam.citybombing.json.JsonHelper;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.levels.utils.GameTime;
import com.nadisam.citybombing.levels.utils.LostGames;
import com.nadisam.citybombing.levels.utils.ShotSpeed;
import com.nadisam.citybombing.levels.utils.Utils;
import com.nadisam.citybombing.levels.utils.WrongTarget;
import com.nadisam.citybombing.log.Logger;
import com.nadisam.citybombing.pro.R;

public class Level extends FragmentActivity implements SurfaceHolder.Callback, android.view.View.OnClickListener, OnClickListener
{
    public static final int                     GAME_ON                   = 0;
    public static final int                     GAME_OVER                 = 1;
    public static final int                     GAME_WIN                  = 2;
    public static final int                     GAME_PAUSED               = 3;

    private com.nadisam.citybombing.levels.Game mGameSurface              = null;
    private boolean                             mPloting                  = true;
    private boolean                             mPause                    = false;
    private Handler                             mHandler                  = new Handler();
    private int                                 mHandlerTimer             = 1000 / Defines.FRAME_RATE;
    private com.nadisam.citybombing.json.Level  mLevel                    = null;

    private City                                mCity                     = null;
    private Plane                               mPlane                    = null;
    private Bombs                               mBombs                    = null;
    private Explosions                          mExplosions               = null;
    private Extras                              mExtras                   = null;
    private Enemies                             mEnemies                  = null;
    private Bullets                             mBullets                  = null;
    private GreenBuildings                      mGreenBuildings           = null;

    private int                                 mShots                    = 0;
    private int                                 mPoints                   = 0;
    private int                                 mTotalPoints              = 0;
    private int                                 mFirePowerRelease         = 0;
    private int                                 mImpacts                  = 0;
    private int                                 mCurrentLevel             = 1;
    private int                                 mDifficulty               = Defines.MIN_DIFFICULTY;

    // Level info
    private TextView                            textViewCurrentLevelLabel = null;
    private TextView                            textViewCurrentLevel      = null;
    private TextView                            textViewPointsLabel       = null;
    private TextView                            textViewPoints            = null;
    private TextView                            textViewShotsLabel        = null;
    private TextView                            textViewShots             = null;
    private TextView                            textViewLevel             = null;

    // Extras
    private TextView                            textViewSpecialGun        = null;
    private TextView                            textViewMoreTime          = null;

    // GAME MENU
    private RelativeLayout                      gameInfoPanel             = null;
    private Button                              btnA                      = null;
    private Button                              btnB                      = null;
    private Button                              btnC                      = null;

    private int                                 mGameStatus               = GAME_ON;

    private Typeface                            font                      = null;

    // Controls the time that really passes between frames to correct plane position
    long                                        prevTime                  = System.currentTimeMillis();
    long                                        newTime                   = System.currentTimeMillis();
    long                                        diffTime                  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);

        // Load current level
        mCurrentLevel = this.getIntent().getIntExtra(Defines.NUM_LEVEL_EXTRA, 1);

        mLevel = loadLevel(mCurrentLevel);
        mDifficulty = mLevel.getDifficulty();

        // Get current points
        mTotalPoints = SharedPreferencesHelper.getPoints();
        mPoints = 0;

        font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.base_font));

        mGameSurface = findViewById(R.id.gameSurfaceView);
        mGameSurface.setOnClickListener(this);
        mGameSurface.setSurfaceHolderCallback(this);

        textViewCurrentLevelLabel = findViewById(R.id.textViewCurrentLevelLabel);
        textViewCurrentLevel = findViewById(R.id.textViewCurrentLevel);
        textViewPointsLabel = findViewById(R.id.textViewPointsLabel);
        textViewPoints = findViewById(R.id.textViewPoints);
        textViewShotsLabel = findViewById(R.id.textViewShotsLabel);
        textViewShots = findViewById(R.id.textViewShots);
        textViewLevel = findViewById(R.id.textViewLevel);
        textViewSpecialGun = findViewById(R.id.specialGunTextView);
        textViewMoreTime = findViewById(R.id.specialMoreSlowTextView);

        gameInfoPanel = findViewById(R.id.gameInfoPanel);
        btnA = findViewById(R.id.btInfoButtonA);
        btnB = findViewById(R.id.btInfoButtonB);
        btnC = findViewById(R.id.btInfoButtonC);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);

        btnA.setTypeface(font);
        btnB.setTypeface(font);
        btnC.setTypeface(font);

        textViewCurrentLevelLabel.setTypeface(font);
        textViewCurrentLevel.setTypeface(font);
        textViewPointsLabel.setTypeface(font);
        textViewPoints.setTypeface(font);
        textViewShotsLabel.setTypeface(font);
        textViewShots.setTypeface(font);
        textViewLevel.setTypeface(font);
        textViewSpecialGun.setTypeface(font);
        textViewMoreTime.setTypeface(font);

        // Set level label
        textViewCurrentLevel.setText(String.valueOf(mLevel.getLevel()));

        // Add ad-mob
        Utils.initAdMob(this);
    }

    @Override
    public void onDestroy()
    {
        Utils.destroyAdView(this);
        super.onDestroy();
    }

    private com.nadisam.citybombing.json.Level loadLevel(int numLevel)
    {
        com.nadisam.citybombing.json.Level level = JsonHelper.getLevel(this, numLevel);
        return level;
    }

    private void createGame()
    {
        this.mGameStatus = GAME_ON;
        this.mBullets = new Bullets();
        this.mExtras = new Extras(this.mLevel.getExtras());
        this.mEnemies = new Enemies(this.mLevel.getEnemies(), this.mBullets);
        this.mGreenBuildings = new GreenBuildings(this.mLevel.getGreens());
        this.mCity = new City(this.mExtras, this.mEnemies, this.mGreenBuildings, this.mDifficulty);
        this.mPlane = new Plane(this.mCity, this.getIntent().getIntExtra(Defines.PLANE_TYPE_EXTRA, Defines.PLANE_TYPE_A), 120/* mLevel.getGap() */, 35/* mLevel.getSpeed() */);
        this.mBullets.setPlane(this.mPlane);
        this.mExplosions = new Explosions();
        this.mBombs = new Bombs(this.mCity, this.mExplosions);
        this.mShots = this.mPlane.shots();
        this.textViewShots.setText(String.valueOf(this.mShots));
        this.textViewPoints.setText(String.valueOf(mTotalPoints));
        this.reSetExtraLowSpeed();
        this.reSetExtraPowerBombs();
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run()
        {
            if (true == mPloting)
            {
                if (false == mPause)
                {
                    prevTime = newTime;
                    newTime = System.currentTimeMillis();
                    diffTime = newTime - prevTime;

                    synchronized (mHandler)
                    {
                        mHandler.postDelayed(mUpdateTimeTask, mHandlerTimer);
                        gameStatus();
                    }
                }
            }
            mydraw();
        }
    };

    private void mydraw()
    {
        mGameSurface.lockCanvas();
        mGameSurface.plot(mEnemies, diffTime);
        mGameSurface.plot(mBullets, diffTime);
        mGameSurface.plot(mCity, diffTime);
        mGameSurface.plot(mPlane, diffTime);
        mGameSurface.plot(mBombs, diffTime);
        mGameSurface.plot(mExplosions, diffTime);
        mGameSurface.unlockCanvas();
    }

    private void gameStatus()
    {
        Extra gameExtra = mExtras.getExtraScore();

        if (mPlane.collision())
        {
            gameOver();

            // Update lost games
            LostGames.update();
        }
        if (mCity.greenDestroyed())
        {
            gameOver();

            // Update lost games
            LostGames.update();
            // Update when there is a wrong target destroyed
            WrongTarget.update();
        }
        if (mCity.allClear())
        {
            gameWin();
        }
        if (null != gameExtra)
        {
            gameExtraScore(gameExtra);
        }

        // Set shots
        if (this.mShots != mPlane.shots())
        {
            this.mShots = mPlane.shots();
            this.textViewShots.setText(String.valueOf(this.mShots));
        }

        // Set points
        if (this.mPoints != mBombs.points())
        {
            this.mPoints = mBombs.points();
            this.textViewPoints.setText(String.valueOf(this.mTotalPoints + this.mPoints));
        }

        // Update game time
        GameTime.update(diffTime);
    }

    /**
     * When game over
     */
    private void gameOver()
    {
        mGameStatus = GAME_OVER;
        mPloting = false;
        textViewLevel.setText(getResources().getString(R.string.game_menu_msg_game_over));
        btnA.setText(getResources().getString(R.string.game_menu_btn_try_again));
        btnB.setText(getResources().getString(R.string.game_menu_btn_exit));
        btnA.setVisibility(View.VISIBLE);
        btnB.setVisibility(View.VISIBLE);
        btnC.setVisibility(View.INVISIBLE);
        gameInfoPanel.setVisibility(View.VISIBLE);
    }

    /**
     * When game win.
     */
    private void gameWin()
    {
        mGameStatus = GAME_WIN;
        mPloting = false;
        String msg = getResources().getString(R.string.game_menu_msg_game_win) + " " + String.valueOf(mCurrentLevel) + "\n" + getResources().getString(R.string.game_menu_msg_game_win2);
        textViewLevel.setText(msg);
        btnA.setText(getResources().getString(R.string.game_menu_btn_next_level));
        btnB.setText(getResources().getString(R.string.game_menu_btn_try_again));
        btnC.setText(getResources().getString(R.string.game_menu_btn_exit));
        btnA.setVisibility(View.VISIBLE);
        btnB.setVisibility(View.VISIBLE);
        btnC.setVisibility(View.VISIBLE);
        gameInfoPanel.setVisibility(View.VISIBLE);
    }

    private void gameExtraScore(Extra gameExtra)
    {
        if (0 == gameExtra.typeStr().compareTo(Defines.EXTRA_POWER_BOMBS))
        {
            setExtraPowerBombs();
        }
        else if (0 == gameExtra.typeStr().compareTo(Defines.EXTRA_LOW_SPEED))
        {
            setExtraLowSpeed();
        }
        else if (0 == gameExtra.typeStr().compareTo(Defines.EXTRA_MORE_SHOOTS))
        {
            setExtraMoreShoots();
        }

        Logger.debug("Level: Extra was destroyed: " + gameExtra.x());
    }

    private void setExtraPowerBombs()
    {
        this.textViewSpecialGun.setText("x6");
    }

    private void reSetExtraPowerBombs()
    {
        this.textViewSpecialGun.setText("x0");
    }

    private void setExtraLowSpeed()
    {
        this.textViewMoreTime.setText("ON");
        this.mPlane.enableLowSpeed();
        this.mPlane.setLowSpeed();
    }

    private void reSetExtraLowSpeed()
    {
        this.textViewMoreTime.setText("OFF");
    }

    private void setExtraMoreShoots()
    {
        this.mPlane.setExtraShoots();
    }

    private void pauseGame()
    {
        this.mPause = true;
        this.mPloting = false;
        this.mGameStatus = GAME_PAUSED;
        this.textViewLevel.setText(getResources().getString(R.string.game_menu_msg_game_paused));
        this.btnA.setText(getResources().getString(R.string.game_menu_btn_continue));
        this.btnB.setText(getResources().getString(R.string.game_menu_btn_exit));
        this.btnA.setVisibility(View.VISIBLE);
        this.btnB.setVisibility(View.VISIBLE);
        this.btnC.setVisibility(View.INVISIBLE);
        this.gameInfoPanel.setVisibility(View.VISIBLE);
    }

    private void resumeGame()
    {
        this.resetTime();
        this.gameInfoPanel.setVisibility(View.INVISIBLE);
        this.mGameStatus = GAME_ON;
        this.mPloting = true;
        this.mPause = false;
        this.mHandler.postDelayed(mUpdateTimeTask, this.mHandlerTimer);
    }

    private void stopGame()
    {
        this.mPloting = false;
        GameTime.stop();
        ShotSpeed.stop();
        WrongTarget.stop();
        LostGames.stop();
        finish();
    }

    private void restartLevel()
    {
        this.resetTime();
        this.gameInfoPanel.setVisibility(View.INVISIBLE);
        this.createGame();
        this.mPloting = true;
        this.mPause = false;
        this.mHandler.postDelayed(mUpdateTimeTask, this.mHandlerTimer);
    }

    private void nextLevel()
    {
        if (this.mCurrentLevel < Defines.NUM_LEVELS)
        {
            // Increase and save fire power
            this.mFirePowerRelease = SharedPreferencesHelper.getShots();
            this.mFirePowerRelease += this.mPlane.getFirePower();
            SharedPreferencesHelper.saveShots(this.mFirePowerRelease);

            // Increase and save impacts
            this.mImpacts = SharedPreferencesHelper.getImpacts();
            this.mImpacts += this.mBombs.impacts();
            SharedPreferencesHelper.saveImpacts(this.mImpacts);

            this.resetTime();
            this.gameInfoPanel.setVisibility(View.INVISIBLE);
            this.mCurrentLevel += 1;
            this.mDifficulty += 1;
            this.mLevel = loadLevel(mCurrentLevel);
            this.createGame();
            this.mPloting = true;
            this.mHandler.postDelayed(mUpdateTimeTask, mHandlerTimer);

            this.textViewCurrentLevel.setText(String.valueOf(mCurrentLevel));

            // Save new reached level
            SharedPreferencesHelper.saveLevel(mCurrentLevel);

            // Accumulated points
            this.mTotalPoints = this.mTotalPoints + this.mPoints;
            SharedPreferencesHelper.savePoints(this.mTotalPoints);
        }
        else
        {
            Intent congrats = new Intent(this, Congrats.class);
            startActivity(congrats);
            finish();
        }
    }

    private void resetTime()
    {
        this.prevTime = System.currentTimeMillis();
        this.newTime = System.currentTimeMillis();
        this.diffTime = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        this.createGame();
        this.mPloting = true;
        this.mHandler.postDelayed(mUpdateTimeTask, mHandlerTimer);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        this.mPloting = false;
    }

    @Override
    public void onClick(View v)
    {
        // "Next level" or "Try again"
        if (v.getId() == R.id.btInfoButtonA)
        {
            // GAME OVER and "Try again"
            if (GAME_OVER == mGameStatus)
            {
                restartLevel();
            }
            // GAME WING and "Next level"
            else if (GAME_WIN == mGameStatus)
            {
                nextLevel();
            }
            // GAME PAUSE and "Continue"
            else if (GAME_PAUSED == mGameStatus)
            {
                resumeGame();
            }
            else
            {}
        }
        // "Try again" or "Main Menu"
        else if (v.getId() == R.id.btInfoButtonB)
        {
            // GAME OVER and Main Menu
            if (GAME_OVER == mGameStatus)
            {
                stopGame();
            }
            // GAME WIN and Try again
            else if (GAME_WIN == mGameStatus)
            {
                restartLevel();
            }
            // GAME PAUSE and "Exit"
            else if (GAME_PAUSED == mGameStatus)
            {
                stopGame();
            }
            else
            {
                stopGame();
            }
        }
        // "Main menu"
        else if (v.getId() == R.id.btInfoButtonC)
        {
            stopGame();
        }
        else
        {
            if (this.mGameStatus == GAME_ON)
            {
                if (null != mPlane)
                {
                    Bomb bomb = mPlane.fire();
                    if (null != bomb)
                    {
                        mBombs.add(bomb);
                    }

                    // Update firing speed
                    ShotSpeed.update();
                }
            }
        }
    }

    public void onSpecialGunClick(View v)
    {
        if (View.VISIBLE != textViewSpecialGun.getVisibility())
            return;

        String text = textViewSpecialGun.getText().toString();
        int num = Integer.valueOf(text.substring(1, 2));

        // If there are shots left
        if (num > 0)
        {
            if (null != this.mPlane)
            {
                if (this.mPlane.type() == Defines.PLANE_TYPE_A)
                {
                    Bomb bomb = mPlane.fire(Defines.BOMB_TYPE_B);
                    if (null != bomb)
                    {
                        this.mBombs.add(bomb);
                    }
                }
                else if (mPlane.type() == Defines.PLANE_TYPE_B)
                {
                    this.mPlane.fireBlast(this.mBombs);
                }
                else if (mPlane.type() == Defines.PLANE_TYPE_C)
                {
                    this.mPlane.fireLaserBeam(this.mBombs);
                }
            }

            num--;

            // Update text
            textViewSpecialGun.setText("x" + num);
        }
    }

    public void onSpecialSlowTime(View v)
    {
        String text = textViewMoreTime.getText().toString();

        if (text.contains("ON"))
        {
            textViewMoreTime.setText("OFF");
            mPlane.resetSpeed();
        }
        else
        {
            // Only if it is enabled
            if (mPlane.setLowSpeed())
            {
                textViewMoreTime.setText("ON");
            }
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
        case KeyEvent.KEYCODE_BACK:

            pauseGame();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
