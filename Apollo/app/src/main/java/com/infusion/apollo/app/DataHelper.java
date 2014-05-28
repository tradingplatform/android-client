package com.infusion.apollo.app;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hpham on 2014-05-22.
 */
public class DataHelper {

    private static ArrayList<DataClass> dowJonesData;
    private static ArrayList<DataClass> todayData;

    public static ArrayList<DataClass> parseFinancialData(OneYearFragment fragment) {
        if (dowJonesData != null) {
            return dowJonesData;
        }

        return Load(new InputStreamReader(fragment.getResources().openRawResource(R.raw.dowjones)));
    }

    private static ArrayList<DataClass> Load(InputStreamReader inputStreamReader) {
        int i = 0;
        BufferedReader reader = new BufferedReader(inputStreamReader);
        dowJonesData = new ArrayList<DataClass>(365);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String line = reader.readLine();
            while (line != null) {
                String[] tokens = line.split("\t");

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, i);

                //String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

                // Date date = dateFormat.parse(timeStamp, new ParsePosition(0));
//                GregorianCalendar calendar = new GregorianCalendar();
//                calendar.setTime(date);
                DataClass data = new DataClass(cal, Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]));
                dowJonesData.add(data);
                line = reader.readLine();
                i--;
            }

            reader.close();

        } catch (IOException ex) {
            dowJonesData = null;
            throw new Error("Could not read raw resource dow jones data in the pan zoom example.");
        }

        return dowJonesData;
    }

    public static ArrayList<DataClass> parseFinancialData(SixMonthFragment sixMonthFragment) {
        if (dowJonesData != null) {
            return dowJonesData;
        }

        return Load(new InputStreamReader(sixMonthFragment.getResources().openRawResource(R.raw.dowjones)));
    }

    public static ArrayList<DataClass> parseFinancialData(ThreeMonthFragment threeMonthFragment) {
        if (dowJonesData != null) {
            return dowJonesData;
        }

        return Load(new InputStreamReader(threeMonthFragment.getResources().openRawResource(R.raw.dowjones)));
    }

    public static ArrayList<DataClass> parseFinancialData(OneWeekFragment oneWeekFragment) {
        if (dowJonesData != null) {
            return dowJonesData;
        }

        return Load(new InputStreamReader(oneWeekFragment.getResources().openRawResource(R.raw.dowjones)));
    }

    public static ArrayList<DataClass> parseFinancialData(OneDayFragment oneDayFragment) {
        if (todayData != null) {
            return todayData;
        }

        int h = 0;

        todayData = new ArrayList<DataClass>(20);


        for (int i = 0; i < 7; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, h);

            String timeStamp = new SimpleDateFormat("dd MMM HH:mm").format(cal.getTime());
            todayData.add(new DataClass(cal, random(-2.3, 2.4), random(-1.8, 1.4), random(-2.4, 2.0), random(-3.0, 3.0)));
            h--;
        }

        return todayData;
    }

    public static double random(double min, double max) {
        double diff = max - min;
        return min + Math.random() * diff;
    }

    public static ArrayList<DataClass> parseFinancialData(OneMonthFragment oneMonthFragment) {
        if (dowJonesData != null) {
            return dowJonesData;
        }

        return Load(new InputStreamReader(oneMonthFragment.getResources().openRawResource(R.raw.dowjones)));
    }
}
