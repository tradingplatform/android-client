package com.infusion.apollo.setup;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.infusion.apollo.framework.example.Example;
import com.infusion.apollo.framework.example.IExample;
import com.infusion.apollo.framework.temp.ILog;
import com.infusion.apollo.framework.temp.LogWrapper;

/**
 * Created by ali on 13/04/14.
 */
public class BindingsModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(ILog.class).to(LogWrapper.class);
        binder.bind(IExample.class).to(Example.class);
    }
}
