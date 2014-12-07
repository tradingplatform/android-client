package com.infusion.apollo.framework.provider.watch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.infusion.apollo.framework.provider.watch.model.InstrumentPriceSummary;
import com.infusion.apollo.framework.provider.watch.model.Stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;

public class SignalRWatchListService extends Service implements IWatchListService, MessageReceivedHandler, ErrorCallback
{
    private final ArrayList<IWatchListServiceListener> mListeners;
    private final ArrayList<InstrumentPriceSummary> mPricesSummaries;

    private final Comparator<InstrumentPriceSummary> mComparator;
    private final Binder binder = new Binder();

    private HubConnection mConnection;
    private boolean mIsActive;

    public SignalRWatchListService() {
        mListeners = new ArrayList<IWatchListServiceListener>();
        mPricesSummaries = new ArrayList<InstrumentPriceSummary>();

        mComparator = new InstrumentPriceSummaryComparator();
    }

    @Override
    public void subscribe(IWatchListServiceListener listener) {
        if (listener == null || mListeners.contains(listener)) {
            return;
        }

        mListeners.add(listener);
    }

    @Override
    public void unsubscribe(IWatchListServiceListener listener) {
        if (listener == null || !mListeners.contains(listener)) {
            return;
        }

        mListeners.remove(listener);
    }

    @Override
    public ArrayList<InstrumentPriceSummary> getPricesSummaries() {
        return mPricesSummaries;
    }

    @Override
    public IBinder onBind(Intent intent) {
        startService(intent);

        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        mIsActive = true;

        final SignalRWatchListService service = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.loadPlatformComponent(new AndroidPlatformComponent());

                String host = "http://trading.infusiondevlab.com/signalr";
                mConnection = new HubConnection( host );
                mConnection.received(service);
                mConnection.error(service);

                HubProxy stockTickerHub = mConnection.createHubProxy( "stockTicker" );
                stockTickerHub.subscribe(service);

                try {
                    mConnection.start().get();
                } catch (InterruptedException e) {
                    // Handle ...
                    e.printStackTrace();
                    Log.e("SignalRWatchListService", e.getMessage());
                } catch (ExecutionException e) {
                    // Handle ...
                    e.printStackTrace();
                    Log.e("SignalRWatchListService", e.getMessage());
                }
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mIsActive = false;

        if (mConnection != null) {
            mConnection.disconnect();
        }
    }

    @Override
    public void onMessageReceived(JsonElement jsonElement) {
    }

    @Override
    public void onError(Throwable throwable) {
    }

    public synchronized void updateStockPrice(Stock stock) {
        if (stock.Symbol == null) {
            return;
        }

        InstrumentPriceSummary match = null;
        for (int i = 0; i < mPricesSummaries.size(); i++) {
            if (mPricesSummaries.get(i).getInstrumentId().equalsIgnoreCase(stock.Symbol)) {
                match = mPricesSummaries.get(i);
                break;
            }
        }

        if (match == null) {
            match = new InstrumentPriceSummary(stock.Symbol, stock.Change);
            match.setLastChange(stock.LastChange);
            mPricesSummaries.add(match);
        }
        else {
            match.setPrice(stock.Change);
            match.setLastChange(stock.LastChange);
        }

        Collections.sort(mPricesSummaries, mComparator);

        notifyPricesChanged();
    }

    private void notifyPricesChanged() {
        for (IWatchListServiceListener listener : mListeners) {
            listener.onPricesChanged(mPricesSummaries);
        }
    }

    private void sleepSafely(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // failed to sleep - ignore
        }
    }

    public class Binder extends android.os.Binder {
        public SignalRWatchListService getService() {
            return SignalRWatchListService.this;
        }
    }

    private class InstrumentPriceSummaryComparator implements Comparator<InstrumentPriceSummary> {
        @Override
        public int compare(InstrumentPriceSummary arg0,
                           InstrumentPriceSummary arg1) {
            return -1 * Double.compare(arg0.getPrice(), arg1.getPrice());
        }
    }
}
