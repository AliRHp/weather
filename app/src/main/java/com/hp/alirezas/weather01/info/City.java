package com.hp.alirezas.weather01.info;

/**
 * Created by alireza s on 7/13/2016.
 */
public class City {

    private String CityFaName;
    private String cityLat;
    private String cityLon;
    private String cityPicUrl;

    public String getCityFaName() {
        return CityFaName;
    }

    public void setCityFaName(String cityFaName) {
        CityFaName = cityFaName;
    }

    public String getCityLat() {
        return cityLat;
    }

    public void setCityLat(String cityLat) {
        this.cityLat = cityLat;
    }

    public String getCityLon() {
        return cityLon;
    }

    public void setCityLon(String cityLon) {
        this.cityLon = cityLon;
    }

    public String getCityPicUrl() {
        return cityPicUrl;
    }

    public void setCityPicUrl(String cityPicUrl) {
        this.cityPicUrl = cityPicUrl;
    }
}
