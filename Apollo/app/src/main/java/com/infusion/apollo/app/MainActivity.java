package com.infusion.apollo.app;

import android.os.Bundle;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboFragmentActivity implements WatchListFragment.IWatchListFragmentListener, MarketOverView.onDowVisibilityEventListener, MarketOverView.onNasdaqVisibilityEventListener, MarketOverView.onSP500VisibilityEventListener  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // load market watcher and watch list information
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.left_pane_container, MarketWatcherFragment.newInstance())
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

    @Override
    public void changeDowVisibility(int visibility) {

    }

    @Override
    public void changeNasdaqVisibility(int visibility) {

    }

    @Override
    public void changeSP500Visibility(int visibility) {

    }
}
