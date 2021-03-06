package com.infusion.apollo.app;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MarketGraphTabs extends Fragment {

    private FragmentTabHost mTabHost;

    public MarketGraphTabs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabcontent1);

        mTabHost.addTab(mTabHost.newTabSpec("1D").setIndicator(CreateTabIndicator(inflater, "1D")),
                OneDayFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("1W").setIndicator(CreateTabIndicator(inflater, "1W")),
                OneWeekFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("1M").setIndicator(CreateTabIndicator(inflater, "1M")),
                OneMonthFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("3M").setIndicator(CreateTabIndicator(inflater, "3M")),
                ThreeMonthFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("6M").setIndicator(CreateTabIndicator(inflater, "6M")),
                SixMonthFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("1Y").setIndicator(CreateTabIndicator(inflater, "1Y")),
                OneYearFragment.class, null);

        mTabHost.getTabWidget().setBackgroundColor(Color.parseColor("#E6E6E6"));

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);


        mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#2288E7"));


        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // TODO Auto-generated method stub
                for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                    mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#E6E6E6")); //unselected
                }
                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#2288E7")); // selected
            }
        });

        mTabHost.setCurrentTab(0);

        return mTabHost;
    }

    private View CreateTabIndicator(LayoutInflater inflater, String indicatorText)
    {
        View tabIndicator = inflater.inflate(R.layout.tab_indicator, null);

        TextView textView = (TextView)tabIndicator.findViewById(R.id.textView);
        textView.setText(indicatorText);

        return tabIndicator;
    }
}
