package com.infusion.apollo.framework.provider.stock.mock;

import com.infusion.apollo.framework.provider.stock.IStockDataPublisher;
import com.infusion.apollo.framework.provider.stock.IStockDataSubscriber;
import com.infusion.apollo.framework.provider.stock.model.StockData;
import com.infusion.apollo.framework.provider.stock.model.StockDataCriteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by ali on 2014-05-18.
 */
public class MockStockDataPublisher implements IStockDataPublisher {
    private final int PollFrequencyMilliseconds = 2500;
    private final Random mRandom = new Random();

    private ArrayList<IStockDataSubscriber> mSubscribers;
    private StockDataPoller mPoller;

    public MockStockDataPublisher() {
        mSubscribers = new ArrayList<IStockDataSubscriber>();
    }

    @Override
    public void subscribe(IStockDataSubscriber subscriber) {
        if (subscriber != null && !mSubscribers.contains(subscriber)) {
            mSubscribers.add(subscriber);

            if (mSubscribers.size() == 1) {
                // first subscriber added - kick off poller
                mPoller = new StockDataPoller();
                new Thread(mPoller).start();
            }
        }
    }

    @Override
    public void unsubscribe(IStockDataSubscriber subscriber) {
        if (mSubscribers.contains(subscriber)) {
            mSubscribers.remove(subscriber);

            if (mSubscribers.size() == 0) {
                // last subscriber removed - stop poller
                mPoller.AllowRun = false;
            }
        }
    }

    private class StockDataPoller implements Runnable {
        public boolean AllowRun;

        private StockDataPoller() {
            AllowRun = true;
        }

        @Override
        public void run() {
            while (AllowRun) {
                ArrayList<IStockDataSubscriber> subscribers = (ArrayList<IStockDataSubscriber>) mSubscribers.clone();
                for (IStockDataSubscriber subscriber : subscribers) {
                    // poll for stock data, then publish
                    StockData stockData = pollStockData(subscriber);
                    subscriber.onStockDataReceived(stockData);

                    if (!AllowRun) {
                        // user stopped service - stop iterating full set
                        break;
                    }
                }

                sleepSafely(PollFrequencyMilliseconds);
            }
        }

        private StockData pollStockData(IStockDataSubscriber subscriber) {
            StockDataCriteria critera = subscriber.getCriteria();

            StockData stockData = new StockData(critera.Market, critera.Symbol);
            stockData.Last = getRandomDouble(400, 406);
            stockData.Change = getRandomDouble(1, 5);
            stockData.ChangePercent = getRandomDouble(0, 2);
            stockData.Volume = getRandomInteger(4500000, 4570000);
            stockData.Bid = getRandomDouble(400, 405);
            stockData.BidFactor = 500;
            stockData.Ask = getRandomDouble(405, 408);
            stockData.AskFactor = 100;
            stockData.Level = "II";

            stockData.DayHi = getRandomDouble(400, 405);
            stockData.DayLo = getRandomDouble(395, 400);
            stockData.FiftyTwoWeekHi = getRandomDouble(500, 518);
            stockData.FiftyTwoWeekHiDate = new Date(2014, 12, 15);
            stockData.FiftyTwoWeekLo = getRandomDouble(300, 310);
            stockData.FiftyTwoWeekLoDate = new Date(2014, 12, 18);
            stockData.MarketCap = 583000000000.0;
            stockData.PricePerEarnings = getRandomDouble(12, 14);
            stockData.EarningsPerShare = getRandomDouble(40, 44);
            stockData.NextEarnings = new Date(2015, 9, 19);

            return stockData;
        }

        private double getRandomDouble(double lower, double upper) {
            double range = upper - lower;

            return lower + Math.random() * range;
        }

        private int getRandomInteger(int lower, int upper) {
            int range = upper - lower;
            return lower + mRandom.nextInt(range);
        }

        private void sleepSafely(int milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                // failed to sleep - ignore
            }
        }
    }
}
