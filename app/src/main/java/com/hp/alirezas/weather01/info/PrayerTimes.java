package com.hp.alirezas.weather01.info;

/**
 * Created by alireza s on 6/24/2016.
 */
public class PrayerTimes {
    private String morning;
    private String sunrise;
    private String noon;
    private String sunset;
    private String west;
    private String midnight;

    public String getMorning() {
        return morning;
    }

    public PrayerTimes setMorning(String morning) {
        this.morning = morning;
        return this;
    }

    public String getSunrise() {
        return sunrise;
    }

    public PrayerTimes setSunrise(String sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public String getNoon() {
        return noon;
    }

    public PrayerTimes setNoon(String noon) {
        this.noon = noon;

        return this;
    }

    public String getSunset() {
        return sunset;
    }

    public PrayerTimes setSunset(String sunset) {
        this.sunset = sunset;
        return this;
    }

    public String getWest() {
        return west;
    }

    public PrayerTimes setWest(String west) {
        this.west = west;
        return this;
    }

    public String getMidnight() {
        return midnight;
    }

    public PrayerTimes setMidnight(String midnight) {
        this.midnight = midnight;
        return this;
    }
}
