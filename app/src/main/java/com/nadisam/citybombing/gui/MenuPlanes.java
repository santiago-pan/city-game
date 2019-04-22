package com.nadisam.citybombing.gui;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.nadisam.citybombing.AppCore;
import com.nadisam.citybombing.config.SharedPreferencesHelper;
import com.nadisam.citybombing.levels.Level;
import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.pro.R;

public class MenuPlanes extends FragmentActivity implements OnClickListener
{
    private Button   btPlaneA  = null;
    private Button   btPlaneB = null;
    private Button   btPlaneC = null;
    private Typeface font       = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planes);

        font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.base_font));

        btPlaneA = findViewById(R.id.btPlaneA);
        btPlaneB = findViewById(R.id.btPlaneB);
        btPlaneC = findViewById(R.id.btPlaneC);

        // Check points to enable/disable planes
        this.enablePlanesControl();
        
        btPlaneA.setOnClickListener(this);
        btPlaneB.setOnClickListener(this);
        btPlaneC.setOnClickListener(this);
        
        btPlaneA.setTypeface(font);
        btPlaneB.setTypeface(font);
        btPlaneC.setTypeface(font);
    }
    
    private void enablePlanesControl()
    {
        int points = SharedPreferencesHelper.getPoints();
        
        if (points > Defines.MIN_POINTS_FOR_PLANE_2)
        {
            btPlaneB.setEnabled(true);
        }
        else
        {
            btPlaneB.setEnabled(false);
        }
        if (points > Defines.MIN_POINTS_FOR_ENTERPRISE)
        {
            // Only in pro version
            int typeOfApplication = getResources().getInteger(R.integer.application_vestion_free_or_pro);
            if (1 == typeOfApplication)
            {
                btPlaneC.setEnabled(true);
            }
            else
            {
                btPlaneC.setEnabled(false);
            }
        }
        else
        {
            btPlaneC.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v)
    {
        // Stop music
        AppCore.getContext().stopMusic();
        
        int numLevel = this.getIntent().getIntExtra(Defines.NUM_LEVEL_EXTRA, 1);
        int planeType;
        
        if (v.getId() == R.id.btPlaneA)
        {
            planeType = Defines.PLANE_TYPE_A;
        }
        else if (v.getId() == R.id.btPlaneB)
        {
            planeType = Defines.PLANE_TYPE_B;
        }
        else
        {
            planeType = Defines.PLANE_TYPE_C;
        }
        
        Intent level = new Intent(this, Level.class);
        level.putExtra(Defines.NUM_LEVEL_EXTRA, numLevel);
        level.putExtra(Defines.PLANE_TYPE_EXTRA, planeType);
        startActivity(level);
        finish();
    }
}