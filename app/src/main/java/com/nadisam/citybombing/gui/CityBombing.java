package com.nadisam.citybombing.gui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.nadisam.citybombing.AppCore;
import com.nadisam.citybombing.config.SharedPreferencesHelper;
import com.nadisam.citybombing.levels.utils.Utils;
import com.nadisam.citybombing.pro.R;

public class CityBombing extends FragmentActivity implements OnClickListener
{
    private Button   btSoundOnOff = null;
    private boolean  bSoundOn     = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button   btPlayGame;
        Button   btHelp;
        Typeface font;

        font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.base_font));

        btPlayGame = findViewById(R.id.btPlayGame);
        btSoundOnOff = findViewById(R.id.btSound);
        btHelp = findViewById(R.id.btHelp);

        btPlayGame.setOnClickListener(this);
        btSoundOnOff.setOnClickListener(this);
        btHelp.setOnClickListener(this);

        btPlayGame.setTypeface(font);
        btSoundOnOff.setTypeface(font);
        btHelp.setTypeface(font);

        // Calculate metrics
        Utils.calculateDisplayMetrics(this);

        // Start sound manager
        AppCore.getContext().startSoundPool(this);
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        
        // Play game music
        AppCore.getContext().stopMusic();
        
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        // Play game music
        if (SharedPreferencesHelper.getSound())
        {
            AppCore.getContext().playMusic();
            AppCore.getContext().soundOn();
            setHmiSoundOn();
        }
        else
        {
            setHmiSoundOff();
            AppCore.getContext().soundOff();
        }
    }

    @Override
    public void onClick(View v)
    {        
        if (v.getId() == R.id.btPlayGame)
        {
            Intent level = new Intent(this, MenuGame.class);
            startActivity(level);
        }
        else if (v.getId() == R.id.btSound)
        {
            if (bSoundOn)
            {
                setHmiSoundOff();
                AppCore.getContext().soundOff();
                SharedPreferencesHelper.saveSound(false);
            }
            else
            {
                setHmiSoundOn();
                AppCore.getContext().soundOn();
                SharedPreferencesHelper.saveSound(true);
            }
        }
        else if (v.getId() == R.id.btHelp)
        {
            Intent stats = new Intent(this, Stats.class);
            startActivity(stats);
        }
    }
    
    private void setHmiSoundOff()
    {
        btSoundOnOff.setText(getResources().getString(R.string.main_screen_sound_off));
        bSoundOn = false;
    }
    private void setHmiSoundOn()
    {
        btSoundOnOff.setText(getResources().getString(R.string.main_screen_sound_on));
        bSoundOn = true;
    }
}
