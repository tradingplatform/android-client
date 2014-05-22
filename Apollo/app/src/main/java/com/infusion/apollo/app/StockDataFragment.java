package com.infusion.apollo.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.inject.Inject;
import com.infusion.apollo.framework.provider.stock.IStockDataPublisher;
import com.infusion.apollo.framework.provider.stock.IStockDataSubscriber;
import com.infusion.apollo.framework.provider.stock.model.StockData;
import com.infusion.apollo.framework.provider.stock.model.StockDataCriteria;

import java.text.SimpleDateFormat;

import roboguice.inject.InjectView;

public class StockDataFragment extends BaseFragment implements IStockDataSubscriber {
    private static final String ARG_MARKET = "MARKET";
    private static final String ARG_SYMBOL = "SYMBOL";

    private static final SimpleDateFormat mSimpleDataFormat = new SimpleDateFormat("(MM/dd/yy)");

    @Inject
    private IStockDataPublisher mStockDataPublisher;

    @InjectView(R.id.last_text_view)
    private TextView mLastView;

    @InjectView(R.id.change_text_view)
    private TextView mChangeView;

    @InjectView(R.id.volume_text_view)
    private TextView mVolumeView;

    @InjectView(R.id.bid_text_view)
    private TextView mBidView;

    @InjectView(R.id.level_text_view)
    private TextView mLevelView;

    @InjectView(R.id.ask_text_view)
    private TextView mAskView;

    @InjectView(R.id.day_hi_text_view)
    private TextView mDayHiView;

    @InjectView(R.id.day_lo_text_view)
    private TextView mDayLoView;

    @InjectView(R.id.fifty_two_week_hi_text_view)
    private TextView mFiftyTwoWeekHiView;

    @InjectView(R.id.fifty_two_week_hi_date_text_view)
    private TextView mFiftyTwoWeekHiDateView;

    @InjectView(R.id.fifty_two_week_lo_text_view)
    private TextView mFiftyTwoWeekLoView;

    @InjectView(R.id.fifty_two_week_lo_date_text_view)
    private TextView mFiftyTwoWeekLoDateView;

    @InjectView(R.id.market_cap_text_view)
    private TextView mMarketCapView;

    @InjectView(R.id.price_per_earnings_text_view)
    private TextView mPricePerEarningsView;

    @InjectView(R.id.earnings_per_share_text_view)
    private TextView mEarningsPerShareView;

    private String mMarket;
    private String mSymbol;

    public StockDataFragment() {
        // solve data provider dependencies
    }

    public static StockDataFragment newInstance(String market, String symbol) {
        StockDataFragment fragment = new StockDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MARKET, market);
        args.putString(ARG_SYMBOL, symbol);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMarket = getArguments().getString(ARG_MARKET);
            mSymbol = getArguments().getString(ARG_SYMBOL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_stock_data, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        mStockDataPublisher.subscribe(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        mStockDataPublisher.unsubscribe(this);
    }

    @Override
    public StockDataCriteria getCriteria() {
        return new StockDataCriteria(mMarket, mSymbol);
    }

    @Override
    public void onStockDataReceived(final StockData data) {
        getView().post(new Runnable() {
            @Override
            public void run() {
                mergeStockData(data);
            }
        });
    }

    private void mergeStockData(StockData data) {
        // handle the event where we receive stock data - adapt to view
        mLastView.setText(String.format("%.2f", data.Last));
        mChangeView.setText(String.format("%.2f (%.2f%%)", data.Change, data.ChangePercent));
        mVolumeView.setText(String.format("%.2fM", data.Volume / 1000000.0));
        mBidView.setText(String.format("%.2fx%d", data.Bid, (int) data.BidFactor));
        mLevelView.setText(data.Level);
        mAskView.setText(String.format("%.2fx%d", data.Ask, (int) data.AskFactor));

        mDayHiView.setText(String.format("%.2f", data.DayHi));
        mDayLoView.setText(String.format("%.2f", data.DayLo));

        mFiftyTwoWeekHiView.setText(String.format("%.2f", data.FiftyTwoWeekHi));
        mFiftyTwoWeekHiDateView.setText(mSimpleDataFormat.format(data.FiftyTwoWeekHiDate));
        mFiftyTwoWeekLoView.setText(String.format("%.2f", data.FiftyTwoWeekLo));
        mFiftyTwoWeekLoDateView.setText(mSimpleDataFormat.format(data.FiftyTwoWeekLoDate));

        mMarketCapView.setText(String.format("%.1fB", data.MarketCap / 1000000000));
        mPricePerEarningsView.setText(String.format("%.1fx", data.PricePerEarnings));
        mEarningsPerShareView.setText(String.format("%.2f", data.EarningsPerShare));
    }
}