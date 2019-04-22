package com.nadisam.citybombing.gui;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nadisam.citybombing.AppCore;
import com.nadisam.citybombing.config.SharedPreferencesHelper;
import com.nadisam.citybombing.levels.utils.GameTime;
import com.nadisam.citybombing.levels.utils.LayoutToBmp;
import com.nadisam.citybombing.levels.utils.LostGames;
import com.nadisam.citybombing.levels.utils.Share;
import com.nadisam.citybombing.levels.utils.ShotSpeed;
import com.nadisam.citybombing.levels.utils.Utils;
import com.nadisam.citybombing.levels.utils.WrongTarget;
import com.nadisam.citybombing.pro.R;

public class Stats extends FragmentActivity implements OnClickListener
{
    protected TextView tvStats          = null;

    private TextView tvLevelLabel     = null;
    private TextView tvLevel          = null;
    private TextView tvPointsLabel    = null;
    private TextView tvPoints         = null;
    private TextView tvBombsLabel     = null;
    private TextView tvBombs          = null;
    private TextView tvAccuracyLabel  = null;
    private TextView tvAccuracy       = null;
    private TextView tvGameTimeLabel  = null;
    private TextView tvGameTime       = null;
    private TextView tvShotSpeedLabel = null;
    private TextView tvShotSpeed      = null;
    private TextView tvGreenLabel     = null;
    private TextView tvGreen          = null;
    private TextView tvLostLabel      = null;
    private TextView tvLost           = null;

    private Button   btOk             = null;
    private Button   btShare          = null;
    private Typeface font             = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_stats);

        font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.base_font));

        tvStats = findViewById(R.id.textViewStatsTitle);
        tvStats.setTypeface(font);

        tvLevelLabel = findViewById(R.id.textViewLevelLabel);
        tvLevel = findViewById(R.id.textViewLevel);

        tvPointsLabel = findViewById(R.id.textViewPointsLabel);
        tvPoints = findViewById(R.id.textViewPoints);

        tvBombsLabel = findViewById(R.id.textViewShotsLabel);
        tvBombs = findViewById(R.id.textViewShots);

        tvAccuracyLabel = findViewById(R.id.textViewAcruracyLabel);
        tvAccuracy = findViewById(R.id.textViewAccuracy);

        tvGameTimeLabel = findViewById(R.id.textViewGameTimeLabel);
        tvGameTime = findViewById(R.id.textViewGameTime);

        tvShotSpeedLabel = findViewById(R.id.textViewSpeedLabel);
        tvShotSpeed = findViewById(R.id.textViewSpeed);

        tvGreenLabel = findViewById(R.id.textViewGreenLabel);
        tvGreen = findViewById(R.id.textViewGreen);

        tvLostLabel = findViewById(R.id.textViewLostGamesLabel);
        tvLost = findViewById(R.id.textViewLostGames);

        tvLevelLabel.setTypeface(font);
        tvLevel.setTypeface(font);
        tvPointsLabel.setTypeface(font);
        tvPoints.setTypeface(font);
        tvBombsLabel.setTypeface(font);
        tvBombs.setTypeface(font);
        tvAccuracyLabel.setTypeface(font);
        tvAccuracy.setTypeface(font);
        tvGameTimeLabel.setTypeface(font);
        tvGameTime.setTypeface(font);
        tvShotSpeedLabel.setTypeface(font);
        tvShotSpeed.setTypeface(font);
        tvGreenLabel.setTypeface(font);
        tvGreen.setTypeface(font);
        tvLostLabel.setTypeface(font);
        tvLost.setTypeface(font);

        btShare = findViewById(R.id.btShare);
        btShare.setOnClickListener(this);
        btShare.setTypeface(font);
        
        btOk = findViewById(R.id.btOk);
        btOk.setOnClickListener(this);
        btOk.setTypeface(font);

        // Calculate stats
        calculateStats ();
    }

    private void calculateStats ()
    {
        int mLevel;
        int mPoints;
        int mFirePower;
        int mAccuracy;

        // Get level
        mLevel = SharedPreferencesHelper.getLevel();
        this.tvLevel.setText(String.valueOf(mLevel));
        
        // Get points
        mPoints = SharedPreferencesHelper.getPoints();
        this.tvPoints.setText(String.valueOf(mPoints));
        
        // Get fire-power
        mFirePower = SharedPreferencesHelper.getShots();
        this.tvBombs.setText(String.valueOf(mFirePower));
        
        // Calculate accuracy
        int impacts = SharedPreferencesHelper.getImpacts();
        if (0 != mFirePower)
        {
            mAccuracy = (int)((((double)impacts)/mFirePower)*100);
        }
        else
        {
            mAccuracy = 0;
        }
        
        this.tvAccuracy.setText(mAccuracy + "%");
        
        // GameTime
        String gameTime = GameTime.getGameTime();
        this.tvGameTime.setText(gameTime);
        
        // Shot Speed
        int shotSpeed = ShotSpeed.getShotSpeed();
        this.tvShotSpeed.setText(String.valueOf(shotSpeed));
        
        // Wrong targets
        int wrong = WrongTarget.getWrongTarget();
        this.tvGreen.setText(String.valueOf(wrong));
        
        // Lost games
        int lostGames = LostGames.getLostGames();
        this.tvLost.setText(String.valueOf(lostGames));
    }
    
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btShare)
        {
            Bitmap bmp = LayoutToBmp.generateBmp((RelativeLayout) findViewById(R.id.statsRelativeLayout));
            Share.share(bmp, getString(R.string.app_name), this);
        }
        if (v.getId() == R.id.btOk)
        {
            finish();
        }
    }
}
