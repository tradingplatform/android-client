package com.infusion.apollo.framework.provider.watch;

import com.infusion.apollo.framework.provider.watch.model.InstrumentPriceSummary;

import java.util.ArrayList;

/**
 * Created by ali on 2014-05-21.
 */
public interface IWatchListService {
    void subscribe(IWatchListServiceListener listener);

    void unsubscribe(IWatchListServiceListener listener);

    ArrayList<InstrumentPriceSummary> getPricesSummaries();
}
