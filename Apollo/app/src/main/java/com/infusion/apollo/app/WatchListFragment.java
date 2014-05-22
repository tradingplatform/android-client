package com.infusion.apollo.app;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.infusion.apollo.adapter.InstrumentPriceSummaryAdapter;
import com.infusion.apollo.framework.provider.watch.IWatchListService;
import com.infusion.apollo.framework.provider.watch.IWatchListServiceListener;
import com.infusion.apollo.framework.provider.watch.WatchListService;
import com.infusion.apollo.framework.provider.watch.model.InstrumentPriceSummary;

import java.util.ArrayList;

public class WatchListFragment extends BaseFragment implements IWatchListServiceListener {
    private IWatchListService mWatchListService;
    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            WatchListService.Binder binder = (WatchListService.Binder) service;
            mWatchListService = binder.getService();
            mWatchListService.subscribe(WatchListFragment.this);
        }

        public void onServiceDisconnected(ComponentName className) {
            mWatchListService.unsubscribe(WatchListFragment.this);
        }
    };
    private IWatchListFragmentListener mListener;
    private GridView mGridView;
    private InstrumentPriceSummaryAdapter mGridViewAdapter;

    public static WatchListFragment newInstance() {
        return new WatchListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGridViewAdapter = new InstrumentPriceSummaryAdapter(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        // connect to the service for updates
        Intent intent = new Intent(getActivity(), WatchListService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();

        // disconnect from service for updates
        getActivity().unbindService(mConnection);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof IWatchListFragmentListener)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mListener = (IWatchListFragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watch_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mGridView = (GridView) getActivity().findViewById(R.id.gridView1);
        mGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        // user selected an item - notify
                        if (mListener != null) {
                            // grab the InstrumentPriceSummary
                            String instrumentId = ((InstrumentPriceSummary) mGridViewAdapter.getItem(position)).getInstrumentId();
                            mListener.onWatchListItemSelected("DOW", instrumentId);
                        }
                    }
                }
        );

        mGridView.setAdapter(mGridViewAdapter);
    }

    @Override
    public void onPricesChanged(final ArrayList<InstrumentPriceSummary> prices) {
        mGridView.post(new Runnable() {
            @Override
            public void run() {
                // refresh the prices on the ui thread
                mGridViewAdapter.setPrices(prices);
            }
        });
    }

    public interface IWatchListFragmentListener {
        public void onWatchListItemSelected(String market, String symbol);
    }
}
