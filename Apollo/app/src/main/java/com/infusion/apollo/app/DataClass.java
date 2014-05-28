package com.infusion.apollo.app;

import java.util.Calendar;

public class DataClass {

    public String category;
    public double value;
    public double value2;
    public double value3;
    public double value4;
    public Calendar date;

    public DataClass(Calendar date) {
        this(date, 0, 0);
    }

    public DataClass(String category, double value) {
        this(category, value, 0);
    }

    public DataClass(Calendar date, double value) {
        this(date, value, 0);
    }

    public DataClass(Calendar date, double value, double value2) {
        this("", value, value2);
        this.date = date;
    }

    public DataClass(Calendar date, double value, double value2, double value3, double value4) {
        this(date, value, value2);
        this.value3 = value3;
        this.value4 = value4;
    }

    public DataClass(String category, double value, double value2) {
        this.category = category;
        this.value = value;
        this.value2 = value2;
    }
}
