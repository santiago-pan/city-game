package com.nadisam.citybombing.gui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.nadisam.citybombing.config.SharedPreferencesHelper;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.pro.R;

public class MenuGame extends FragmentActivity implements OnClickListener
{
    private Button   btNewGame  = null;
    private Button   btContinue = null;
    private Typeface font       = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_1);

        font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.base_font));

        btNewGame = (Button) findViewById(R.id.btNewGame);
        btContinue = (Button) findViewById(R.id.btContinue);

        btNewGame.setOnClickListener(this);
        btContinue.setOnClickListener(this);

        btNewGame.setTypeface(font);
        btContinue.setTypeface(font);
        
        int numLevel = SharedPreferencesHelper.getLevel();
        
        if (0 == numLevel)
        {
            btContinue.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v)
    {
        int numLevel = 0;
        
        if (v.getId() == R.id.btContinue)
        {
            numLevel = SharedPreferencesHelper.getLevel();
        }
        if (numLevel == 0)
        {
            numLevel = 1;
        }
        
        Intent level = new Intent(this, MenuPlanes.class);
        level.putExtra(Defines.NUM_LEVEL_EXTRA, numLevel);
        startActivity(level);
        finish();
    }
}
