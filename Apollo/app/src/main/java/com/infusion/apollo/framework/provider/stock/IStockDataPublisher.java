package com.infusion.apollo.framework.provider.stock;

/**
 * Created by ali on 2014-05-18.
 */
public interface IStockDataPublisher {
    void subscribe(IStockDataSubscriber subscriber);

    void unsubscribe(IStockDataSubscriber subscriber);
}
