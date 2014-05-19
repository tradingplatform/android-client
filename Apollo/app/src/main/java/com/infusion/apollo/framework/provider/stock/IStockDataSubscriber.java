package com.infusion.apollo.framework.provider.stock;

import com.infusion.apollo.framework.provider.stock.model.StockData;
import com.infusion.apollo.framework.provider.stock.model.StockDataCriteria;

/**
 * Created by ali on 2014-05-18.
 */
public interface IStockDataSubscriber {
    StockDataCriteria getCriteria();

    void onStockDataReceived(StockData data);
}
