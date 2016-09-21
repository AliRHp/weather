package com.hp.alirezas.weather01.db;

import com.hp.alirezas.weather01.info.City;
import com.hp.alirezas.weather01.info.CityInfo;
import com.hp.alirezas.weather01.info.DateInfo;
import com.hp.alirezas.weather01.info.Forecast;
import com.hp.alirezas.weather01.info.PrayerTimes;
import com.hp.alirezas.weather01.info.Status;

import java.sql.Date;
import java.util.List;

/**
 * Created by alireza s on 6/22/2016.
 */
public interface DOA {
    public List<City> searchCites(String name);

    public boolean addCityList(City city);

    public boolean deleteAllCityList();

    public List<City> getAllCityList();

    public boolean addStatus(Status status);

    public boolean updateStatus(Status status,int id);

    public boolean deleteStatus(int id);


    public Status getStatus(int id);

    public boolean addCityInfo(CityInfo cityInfo);

    public boolean updateCityInfo(CityInfo cityInfo,int id);

    public boolean deleteCityInfo(int id);

    public CityInfo getCityInfo(int id);

    public List<CityInfo> getSavedCites();

    public boolean addForecast(Forecast forecast,int city_id);

    public boolean updateForecast(int city_id,int i,Forecast forecast);

    public boolean deleteForecast(int id);

    public List<Forecast> getAllForecast(int city_id);

    public boolean addPrayerTimes(PrayerTimes prayerTimes);

    public boolean updatePrayerTimes(PrayerTimes prayerTimes,int id);

    public boolean deletePrayerTimes(int id);


    public PrayerTimes getPrayerTimes(int id);

    public boolean addDateInfo(DateInfo dateInfo);

    public boolean updateDateInfo(DateInfo dateInfo,int id);

    public boolean deleteDateInfo(int id);

    public DateInfo getDateInfo(int id);






}
