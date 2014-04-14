package com.infusion.apollo.setup;

import android.app.Application;

import roboguice.RoboGuice;

/**
 * Created by ali on 13/04/14.
 */
public class ApolloApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(this), new BindingsModule());
    }
}
