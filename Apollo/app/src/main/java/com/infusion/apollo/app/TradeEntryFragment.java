package com.infusion.apollo.app;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TradeEntryFragment extends BaseFragment {
    private static final String ARG_SYMBOL = "SYMBOL";

    public static TradeEntryFragment newInstance(String symbol) {
        TradeEntryFragment fragment = new TradeEntryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SYMBOL, symbol);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trade_entry, container, false);
    }

}
