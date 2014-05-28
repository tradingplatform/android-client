package com.infusion.apollo.app;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MarketOverView extends Fragment {

    int visibilityDow = View.VISIBLE;
    int visibilitNasdaq = View.VISIBLE;
    int visibilitSP500 = View.VISIBLE;

    TextView dowMain;
    TextView dowIncrease;
    TextView dowChange;
    TextView dowVolume;

    TextView nasMain;
    TextView nasIncrease;
    TextView nasChange;
    TextView nasVolume;

    TextView sp500Main;
    TextView sp500Increase;
    TextView sp500Change;
    TextView sp500Volume;
    onDowVisibilityEventListener dowVisibilityEventListener;
    onNasdaqVisibilityEventListener nasdaqVisibilityEventListener;
    onSP500VisibilityEventListener sP500VisibilityEventListener;
    DecimalFormat df = new DecimalFormat("#,###.00");
    DecimalFormat de = new DecimalFormat("+##0.00");
    DecimalFormat dee = new DecimalFormat("##0.00");
    DecimalFormat dg = new DecimalFormat("+#0.00%");
    DecimalFormat dgg = new DecimalFormat("#0.00%");
    DecimalFormat dh = new DecimalFormat("#,###.00M");
    double initialDowMain = 16675.50;
    double dowMainValue = initialDowMain;
    double initialDowIncrease = 20.12;
    double dowChangeValue;
    double initialDowVol = 60.88;
    double initialNasMain = 4237.07;
    double nasMainValue = initialNasMain;
    double initialNasIncrease = -5.12;
    double nasChangeValue;
    double initialNasVol = 22.31;
    double initialSP500Main = 1911.91;
    double sp500MainValue = initialSP500Main;
    double initialSP500Increase = -15.12;
    double sp500ChangeValue;
    double initialSP500Vol = 5.66;

    public MarketOverView() {
        // Required empty public constructor
    }

    public static double random(int min, int max) {
        Random rand = new Random();
        double cc = rand.nextInt(max - min + 1) + min;
        double roundOff = Math.round(cc * 100.0) / 100.0;

        return roundOff;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            dowVisibilityEventListener = (onDowVisibilityEventListener) activity;
            nasdaqVisibilityEventListener = (onNasdaqVisibilityEventListener) activity;
            sP500VisibilityEventListener = (onSP500VisibilityEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_market_over_view, container, false);

        view.findViewById(R.id.dow).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                visibilityDow = visibilityDow == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
                dowVisibilityEventListener.changeDowVisibility(visibilityDow);
            }
        });

        view.findViewById(R.id.nasdaq).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                visibilitNasdaq = visibilitNasdaq == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
                nasdaqVisibilityEventListener.changeNasdaqVisibility(visibilitNasdaq);
            }
        });

        view.findViewById(R.id.sp500).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                visibilitSP500 = visibilitSP500 == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
                sP500VisibilityEventListener.changeSP500Visibility(visibilitSP500);
            }
        });

        dowMain = (TextView) view.findViewById(R.id.textView2);
        dowIncrease = (TextView) view.findViewById(R.id.textView4);
        dowChange = (TextView) view.findViewById(R.id.textView7);
        dowVolume = (TextView) view.findViewById(R.id.textView10);

        nasMain = (TextView) view.findViewById(R.id.textView3);
        nasIncrease = (TextView) view.findViewById(R.id.textView5);
        nasChange = (TextView) view.findViewById(R.id.textView8);
        nasVolume = (TextView) view.findViewById(R.id.textView11);

        sp500Main = (TextView) view.findViewById(R.id.textView);
        sp500Increase = (TextView) view.findViewById(R.id.textView6);
        sp500Change = (TextView) view.findViewById(R.id.textView9);
        sp500Volume = (TextView) view.findViewById(R.id.textView12);

        mockDowChange();
        mockNasdaqChange();
        mockSP500Change();

        // Inflate the layout for this fragment
        return view;
    }

    public void mockDowChange() {
        calculateDowChange();

        dowMain.setText(df.format(dowMainValue));
        if (initialDowIncrease > 0) {
            dowIncrease.setText(de.format(initialDowIncrease));
            dowIncrease.setTextColor(Color.parseColor("#ff5bcc2b"));
        } else {
            dowIncrease.setText(dee.format(initialDowIncrease));
            dowIncrease.setTextColor(Color.parseColor("#ffe5391f"));
        }

        if (dowChangeValue > 0) {
            dowChange.setText(dg.format(dowChangeValue));
            dowChange.setTextColor(Color.parseColor("#ff5bcc2b"));
        } else {
            dowChange.setText(dgg.format(dowChangeValue));
            dowChange.setTextColor(Color.parseColor("#ffe5391f"));
        }

        dowVolume.setText(dh.format(initialDowVol));
    }

    private void calculateDowChange() {
        double dowX = random(-30, 30);
        initialDowIncrease += dowX;
        dowMainValue += dowX;
        dowChangeValue = initialDowIncrease / initialDowMain;
        initialDowVol += random(1, 5);
    }

    public void mockNasdaqChange() {
        calculateNasChange();

        nasMain.setText(df.format(nasMainValue));
        if (initialNasIncrease > 0) {
            nasIncrease.setText(de.format(initialNasIncrease));
            nasIncrease.setTextColor(Color.parseColor("#ff5bcc2b"));
        } else {
            nasIncrease.setText(dee.format(initialNasIncrease));
            nasIncrease.setTextColor(Color.parseColor("#ffe5391f"));
        }

        if (nasChangeValue > 0) {
            nasChange.setText(dg.format(nasChangeValue));
            nasChange.setTextColor(Color.parseColor("#ff5bcc2b"));
        } else {
            nasChange.setText(dgg.format(nasChangeValue));
            nasChange.setTextColor(Color.parseColor("#ffe5391f"));
        }

        nasVolume.setText(dh.format(initialNasVol));
    }

    private void calculateNasChange() {
        double nasX = random(-20, 20);
        initialNasIncrease += nasX;
        nasMainValue += nasX;
        nasChangeValue = initialNasIncrease / initialNasMain;
        initialNasVol += random(1, 5);
    }

    public void mockSP500Change() {
        calculateSP500Change();

        sp500Main.setText(df.format(sp500MainValue));
        if (initialSP500Increase > 0) {
            sp500Increase.setText(de.format(initialSP500Increase));
            sp500Increase.setTextColor(Color.parseColor("#ff5bcc2b"));
        } else {
            sp500Increase.setText(dee.format(initialSP500Increase));
            sp500Increase.setTextColor(Color.parseColor("#ffe5391f"));
        }

        if (sp500ChangeValue > 0) {
            sp500Change.setText(dg.format(sp500ChangeValue));
            sp500Change.setTextColor(Color.parseColor("#ff5bcc2b"));
        } else {
            sp500Change.setText(dgg.format(sp500ChangeValue));
            sp500Change.setTextColor(Color.parseColor("#ffe5391f"));
        }

        sp500Volume.setText(dh.format(initialSP500Vol));
    }

    private void calculateSP500Change() {
        double sp500X = random(-10, 10);
        initialSP500Increase += sp500X;
        sp500MainValue += sp500X;
        sp500ChangeValue = initialSP500Increase / initialSP500Main;
        initialSP500Vol += random(1, 5);
    }

    public interface onDowVisibilityEventListener {
        public void changeDowVisibility(int visibility);
    }

    public interface onNasdaqVisibilityEventListener {
        public void changeNasdaqVisibility(int visibility);
    }

    public interface onSP500VisibilityEventListener {
        public void changeSP500Visibility(int visibility);
    }
}
