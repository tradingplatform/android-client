package com.infusion.apollo.framework.provider.stock.model;

import java.util.Date;

/**
 * Created by ali on 2014-05-18.
 */
public class StockData {
    public String Market;
    public String Symbol;

    public double Last;
    public double Change;
    public double ChangePercent;

    public int Volume;

    public String Level;

    public double Bid, BidFactor,
            Ask, AskFactor;

    public double DayHi,
            DayLo,
            FiftyTwoWeekHi,
            FiftyTwoWeekLo,
            MarketCap,
            PricePerEarnings,
            EarningsPerShare;

    public Date FiftyTwoWeekHiDate, FiftyTwoWeekLoDate;

    public Date NextEarnings;

    public StockData(String market, String symbol) {
        Symbol = symbol;
        Market = market;
    }
}
