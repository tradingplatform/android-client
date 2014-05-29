package com.infusion.apollo.framework.provider.watch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.infusion.apollo.framework.provider.watch.model.InstrumentPriceSummary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by ali on 2014-05-21.
 */
public class WatchListService extends Service implements IWatchListService {
    private final ArrayList<IWatchListServiceListener> mListeners;
    private final ArrayList<InstrumentPriceSummary> mPricesSummaries;

    private final Comparator<InstrumentPriceSummary> mComparator;
    private final Binder binder = new Binder();

    private boolean mIsActive;

    public WatchListService() {
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mIsActive) {
                    updateSymbols();
                    notifyPricesChanged();

                    sleepSafely(2 * 1000);
                }
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mIsActive = false;
    }

    private void updateSymbols() {
        updateSymbolsWithMockData();
    }

    private void updateSymbolsWithMockData() {
        if (mPricesSummaries.isEmpty()) {
            // initialize with sample instruments
            mPricesSummaries.add(new InstrumentPriceSummary("CD", 10.34));
            mPricesSummaries.add(new InstrumentPriceSummary("JPHX", 10.05));
            mPricesSummaries.add(new InstrumentPriceSummary("ECK", 9.02));
            mPricesSummaries.add(new InstrumentPriceSummary("KD", 7.13));
            mPricesSummaries.add(new InstrumentPriceSummary("ELF", 4.37));
            mPricesSummaries.add(new InstrumentPriceSummary("IJHJ", 2.58));
            mPricesSummaries.add(new InstrumentPriceSummary("I", 1.30));
            mPricesSummaries.add(new InstrumentPriceSummary("ZECH", 1.22));
            mPricesSummaries.add(new InstrumentPriceSummary("DGJ", 1.08));
            mPricesSummaries.add(new InstrumentPriceSummary("OPIS", 0.95));
            mPricesSummaries.add(new InstrumentPriceSummary("GIU", 0.09));
            mPricesSummaries.add(new InstrumentPriceSummary("JTWA", -0.62));
            mPricesSummaries.add(new InstrumentPriceSummary("Q", -1.05));
            mPricesSummaries.add(new InstrumentPriceSummary("ZE", -1.56));
            mPricesSummaries.add(new InstrumentPriceSummary("AZ", -1.90));
            mPricesSummaries.add(new InstrumentPriceSummary("RTPI", -2.05));
            mPricesSummaries.add(new InstrumentPriceSummary("WNGE", -2.95));
            mPricesSummaries.add(new InstrumentPriceSummary("VS", -3.34));
            mPricesSummaries.add(new InstrumentPriceSummary("MC", -6.82));
            mPricesSummaries.add(new InstrumentPriceSummary("PLDES", -5.05));
        }

        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < mPricesSummaries.size(); i++) {
            int sign = -1;
            int remainder = random.nextInt() % 3;
            if (remainder == 0) {
                sign = 0;
            } else if (remainder == 1) {
                sign = 1;
            }

            double increment = (double) (Math.round(sign * random.nextGaussian() * 10000) / 10000) / (double) 10;

            InstrumentPriceSummary summary = mPricesSummaries.get(i);
            summary.setPrice(summary.getPrice() + increment);
            summary.setLastChange(increment);
        }

        Collections.sort(mPricesSummaries, mComparator);
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
        public WatchListService getService() {
            return WatchListService.this;
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
