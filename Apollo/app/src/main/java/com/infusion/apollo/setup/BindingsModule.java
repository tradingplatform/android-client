package com.infusion.apollo.setup;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.infusion.apollo.framework.log.ILog;
import com.infusion.apollo.framework.log.LogWrapper;
import com.infusion.apollo.framework.provider.stock.IStockDataPublisher;
import com.infusion.apollo.framework.provider.stock.mock.MockStockDataPublisher;

/**
 * Created by ali on 13/04/14.
 */
public class BindingsModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(ILog.class).to(LogWrapper.class).in(Singleton.class);
        binder.bind(IStockDataPublisher.class).to(MockStockDataPublisher.class).in(Singleton.class);

        // scope options: Singleton, ContextSingleton. Singleton is application wide, ContextSingleton is only for the lifetime of the Context
    }
}
