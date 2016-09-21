package com.hp.alirezas.weather01.info;

/**
 * Created by alireza s on 6/24/2016.
 */
public class Forecast
{
    private String date;
    private String day;
    private String high;
    private String low;
    private String main;


    public String getDate() {
        return date;
    }

    public Forecast setDate(String date) {
        this.date = date;
        return this;
    }

    public String getDay() {
        return day;
    }

    public Forecast setDay(String day) {
        this.day = day;
        return this;
    }

    public String getHigh() {
        return high;
    }

    public Forecast setHigh(String high) {
        this.high = high;
        return this;
    }

    public String getLow() {
        return low;
    }

    public Forecast setLow(String low) {
        this.low = low;
        return this;
    }

    public String getMain() {
        return main;
    }

    public Forecast setMain(String main) {
        this.main = main;
        return this;
    }
}
