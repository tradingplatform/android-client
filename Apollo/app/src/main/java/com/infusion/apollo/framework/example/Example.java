package com.infusion.apollo.framework.example;

import javax.inject.Singleton;

/**
 * Created by ali on 13/04/14.
 */
@Singleton  // process wide singleton
// @ContextSingleton    different instance per Context
public class Example implements IExample {
    @Override
    public int getNumber() {
        return 1;
    }
}
