package com.infusion.apollo.app;

import com.google.inject.Inject;
import com.infusion.apollo.framework.log.ILog;

import roboguice.fragment.RoboFragment;

/**
 * Created by ali on 2014-05-18.
 */
public class BaseFragment extends RoboFragment {
    @Inject
    protected ILog mLogger;
}
