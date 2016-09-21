package com.hp.alirezas.weather01.info;

/**
 * Created by alireza s on 6/24/2016.
 */
public class CityInfo {

    private String cityName;
    private String region;
    private String currentDate;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    private int cityId;


    public String getCityPicUrl() {
        return cityPicUrl;
    }

    public void setCityPicUrl(String cityPicUrl) {
        this.cityPicUrl = cityPicUrl;
    }

    private String cityPicUrl;
    public String getCurrentDate() {
        return currentDate;
    }

    public CityInfo setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public CityInfo setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public CityInfo setRegion(String region) {
        this.region = region;
        return this;
    }
}
