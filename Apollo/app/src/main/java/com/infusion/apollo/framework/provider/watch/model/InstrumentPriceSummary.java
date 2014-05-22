package com.infusion.apollo.framework.provider.watch.model;

public class InstrumentPriceSummary {
    private String instrumentId;
    private double price;
    private double lastChange;

    public InstrumentPriceSummary(String instrumentId, double price) {
        this.instrumentId = instrumentId;
        this.price = price;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double value) {
        price = value;
    }

    public double getLastChange() {
        return lastChange;
    }

    public void setLastChange(double value) {
        lastChange = value;
    }
}