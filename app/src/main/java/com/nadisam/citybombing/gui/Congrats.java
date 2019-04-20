package com.nadisam.citybombing.gui;

import com.nadisam.citybombing.pro.R;

import android.os.Bundle;

public class Congrats extends Stats
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.tvStats.setText(getResources().getString(R.string.congrats_title));
    }
}
