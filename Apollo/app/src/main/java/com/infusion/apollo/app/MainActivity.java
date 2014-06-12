package com.infusion.apollo.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboFragmentActivity implements WatchListFragment.IWatchListFragmentListener,
                                                                  StockDataFragment.IStockDataFragmentListener,
                                                                  MarketOverView.onDowVisibilityEventListener,
                                                                  MarketOverView.onNasdaqVisibilityEventListener,
                                                                  MarketOverView.onSP500VisibilityEventListener  {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_watch_list:
                openWatchList();
                return true;
            case R.id.action_place_order:
                openTradeEntry(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onWatchListItemSelected(String market, String symbol) {
        // load stock data for symbol
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_pane_container, StockDataFragment.newInstance(market, symbol))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEnterTradeRequested(String symbol) {
        openTradeEntry(symbol);
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

    private void openWatchList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_pane_container, WatchListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void openTradeEntry(String symbol) {
        // load trade entry for symbol
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_pane_container, TradeEntryFragment.newInstance(symbol))
                .addToBackStack(null)
                .commit();
    }}
