package com.infusion.apollo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infusion.apollo.app.R;
import com.infusion.apollo.framework.provider.watch.model.InstrumentPriceSummary;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class InstrumentPriceSummaryAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private ArrayList<InstrumentPriceSummary> mSummaries;

    public InstrumentPriceSummaryAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mSummaries = new ArrayList<InstrumentPriceSummary>();
    }

    @Override
    public int getCount() {
        return mSummaries.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mSummaries.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.instrument_price_summary, null);

        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.layout);

        InstrumentPriceSummary summary = mSummaries.get(position);

        if (summary.getLastChange() == 0) {
            layout.setBackgroundColor(Color.argb(255, 100, 150, 255));
        } else if (summary.getLastChange() > 0) {
            layout.setBackgroundColor(Color.argb(255, 100, 255, 150));
        } else {
            layout.setBackgroundColor(Color.argb(255, 255, 150, 100));
        }

        ((TextView) convertView.findViewById(R.id.instrumentIdTextView)).setText(summary.getInstrumentId());

        DecimalFormat format = new DecimalFormat("0.00");
        format.setPositivePrefix("+");
        format.setPositiveSuffix("%");
        format.setNegativeSuffix("%");
        ((TextView) convertView.findViewById(R.id.priceTextView)).setText(format.format(summary.getPrice()));
        ((TextView) convertView.findViewById(R.id.lastChangeTextView)).setText(format.format(summary.getLastChange()));

        return convertView;
    }

    public void setPrices(ArrayList<InstrumentPriceSummary> priceSummaries) {
        mSummaries = (priceSummaries != null) ? priceSummaries : new ArrayList<InstrumentPriceSummary>();
        notifyDataSetChanged();
    }
}