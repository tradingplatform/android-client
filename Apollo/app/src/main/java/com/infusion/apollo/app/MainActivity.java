package com.infusion.apollo.app;

import android.os.Bundle;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.left_pane_container, StockDataFragment.newInstance("NASDAQ", "GOOGL"))
                    .commit();
        }
    }
}
