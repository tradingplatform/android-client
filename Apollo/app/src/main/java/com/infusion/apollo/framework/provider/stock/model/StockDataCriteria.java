package com.infusion.apollo.framework.provider.stock.model;

/**
 * Created by ali on 2014-05-18.
 */
public class StockDataCriteria {
    public String Market;
    public String Symbol;


    public StockDataCriteria(String market, String symbol) {
        Market = market;
        Symbol = symbol;
    }
}
