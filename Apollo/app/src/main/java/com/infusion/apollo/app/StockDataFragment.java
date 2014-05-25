package com.infusion.apollo.app;

import android.graphics.Color;
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

    @InjectView(R.id.stock_text_view)
    private TextView mStockView;

    @InjectView(R.id.market_symbol_text_view)
    private TextView mMarketSymbolView;

    @InjectView(R.id.last_text_view)
    private TextView mLastView;

    @InjectView(R.id.change_text_view)
    private TextView mChangeView;

    @InjectView(R.id.change_percentage_text_view)
    private TextView mChangePercentageView;

    @InjectView(R.id.bid_text_view)
    private TextView mBidView;

    @InjectView(R.id.ask_text_view)
    private TextView mAskView;

    @InjectView(R.id.volume_text_view)
    private TextView mVolumeView;

    @InjectView(R.id.range_text_view)
    private TextView mDayRangeView;

    @InjectView(R.id.fifty_two_range_text_view)
    private TextView mFiftyTwoRangeView;

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
        mStockView.setText("Google Inc.");
        mMarketSymbolView.setText("(NASDAQ:GOOGL)");

        mLastView.setText(String.format("%.2f", data.Last));
        mChangeView.setText(String.format("+%.2f", data.Change));

        mChangePercentageView.setText(String.format("(%.2f%%)", data.ChangePercent));
        mChangePercentageView.setTextColor(Color.parseColor("#61C33B"));

        mBidView.setText(String.format("%.2fx%d", data.Bid, (int) data.BidFactor));
        mAskView.setText(String.format("%.2fx%d", data.Ask, (int) data.AskFactor));
        mVolumeView.setText(String.format("%.2fM", data.Volume / 1000000.0));

        mDayRangeView.setText(String.format("%.2f - %.2f", data.DayLo, data.DayHi));
        mFiftyTwoRangeView.setText(String.format("%.2f - %.2f", data.FiftyTwoWeekLo, data.FiftyTwoWeekHi));

        mMarketCapView.setText(String.format("%.1fB", data.MarketCap / 1000000000));
        mPricePerEarningsView.setText(String.format("%.1fx", data.PricePerEarnings));
        mEarningsPerShareView.setText(String.format("%.2f", data.EarningsPerShare));
    }
}
