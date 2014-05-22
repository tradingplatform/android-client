package com.infusion.apollo.framework.provider.watch;

import com.infusion.apollo.framework.provider.watch.model.InstrumentPriceSummary;

import java.util.ArrayList;

public interface IWatchListServiceListener {
    void onPricesChanged(ArrayList<InstrumentPriceSummary> prices);
}