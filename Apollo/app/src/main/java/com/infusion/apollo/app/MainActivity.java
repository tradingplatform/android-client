package com.infusion.apollo.app;

import android.os.Bundle;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboFragmentActivity implements WatchListFragment.IWatchListFragmentListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // load watch list information
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.right_pane_container, WatchListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onWatchListItemSelected(String market, String symbol) {
        // load stock data for symbol
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.left_pane_container, StockDataFragment.newInstance(market, symbol))
                .addToBackStack(null)
                .commit();
    }
}
