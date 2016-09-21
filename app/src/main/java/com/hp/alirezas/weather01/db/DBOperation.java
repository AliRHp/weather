package com.hp.alirezas.weather01.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hp.alirezas.weather01.info.City;
import com.hp.alirezas.weather01.info.CityInfo;
import com.hp.alirezas.weather01.info.DateInfo;
import com.hp.alirezas.weather01.info.Forecast;
import com.hp.alirezas.weather01.info.PrayerTimes;
import com.hp.alirezas.weather01.info.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alireza s on 6/25/2016.
 */
public class DBOperation implements DOA {

    SQLiteDatabase db;

    public DBOperation(Context context) {
        DbHelper helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    @Override
    public boolean addCityList(City city) {

        ContentValues values = new ContentValues();
        values.put(Constants.CITY_FA_NAME, city.getCityFaName());
        values.put(Constants.CITY_LAT, city.getCityLat());
        values.put(Constants.CITY_LON, city.getCityLon());
        values.put(Constants.CITY_PIC, city.getCityPicUrl());

        db.insert(Constants.CITY_LIST, null, values);


        return false;
    }

    @Override
    public boolean deleteAllCityList() {
        db.delete(Constants.CITY_LIST, null, null);
        return false;
    }


    @Override
    public List<City> searchCites(String name) {
        String selection = Constants.CITY_FA_NAME + " like  ?";
        String[] selectionArgs = {"%" + name + "%"};
        Cursor cursor = db.query(Constants.CITY_LIST, null, selection, selectionArgs, null, null, null);

        List<City> list = new ArrayList();


        while (cursor.moveToNext()) {
            City city = new City();
            city.setCityPicUrl(cursor.getString(cursor.getColumnIndex(Constants.CITY_PIC)));
            city.setCityLon(cursor.getString(cursor.getColumnIndex(Constants.CITY_LON)));
            city.setCityLat(cursor.getString(cursor.getColumnIndex(Constants.CITY_LAT)));
            city.setCityFaName(cursor.getString(cursor.getColumnIndex(Constants.CITY_FA_NAME)));

            list.add(city);
        }


        return list;
    }

    @Override
    public List<City> getAllCityList() {
        Cursor cursor = db.query(Constants.CITY_LIST, null, null, null, null, null, null);

        List<City> list = new ArrayList<>();
        City city;

        while (cursor.moveToNext()) {
            city = new City();

            city.setCityFaName(cursor.getString(cursor.getColumnIndex(Constants.CITY_FA_NAME)));
            city.setCityLat(cursor.getString(cursor.getColumnIndex(Constants.CITY_LAT)));
            city.setCityLon(cursor.getString(cursor.getColumnIndex(Constants.CITY_LON)));
            city.setCityPicUrl(cursor.getString(cursor.getColumnIndex(Constants.CITY_PIC)));

            list.add(city);
        }
        return list;
    }

    @Override
    public boolean addStatus(Status status) {
        ContentValues values = new ContentValues();

        values.put(Constants.DATE, status.getDate());
        values.put(Constants.TEMPERATURE, status.getTemp());
        values.put(Constants.MAIN, status.getMain());
        values.put(Constants.HUMIDITY, status.getHumidity());
        values.put(Constants.PRESSURE, status.getPresure());
        values.put(Constants.CHILL, status.getChill());
        values.put(Constants.SPEED, status.getSpeed());
        values.put(Constants.DIRECTION, status.getDirection());

        if ((db.insert(Constants.WEATHER_STATUS, null, values)) == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean updateStatus(Status status, int id) {
        ContentValues values = new ContentValues();

        values.put(Constants.DATE, status.getDate());
        values.put(Constants.TEMPERATURE, status.getTemp());
        values.put(Constants.MAIN, status.getMain());
        values.put(Constants.HUMIDITY, status.getHumidity());
        values.put(Constants.PRESSURE, status.getPresure());
        values.put(Constants.CHILL, status.getChill());
        values.put(Constants.SPEED, status.getSpeed());
        values.put(Constants.DIRECTION, status.getDirection());
        try {
            db.update(Constants.WEATHER_STATUS, values, Constants.ID + " = ?", new String[]{id + ""});
//            db.execSQL(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteStatus(int id) {
        db.delete(Constants.WEATHER_STATUS, Constants.ID + "= ?", new String[]{id+""});
        return false;
    }


    @Override
    public Status getStatus(int id) {
        Cursor query = db.query(Constants.WEATHER_STATUS, null, Constants.ID + " = ?", new String[]{id + ""}, null, null, null);

        Status status = new Status();
        if (query.moveToFirst()) {
            status.setDate(query.getString(query.getColumnIndex(Constants.DATE)));
            status.setTemp(query.getString(query.getColumnIndex(Constants.TEMPERATURE)));
            status.setMain(query.getString(query.getColumnIndex(Constants.MAIN)));
            status.setHumidity(query.getString(query.getColumnIndex(Constants.HUMIDITY)));
            status.setPresure(query.getString(query.getColumnIndex(Constants.PRESSURE)));
            status.setChill(query.getString(query.getColumnIndex(Constants.CHILL)));
            status.setSpeed(query.getString(query.getColumnIndex(Constants.SPEED)));
            status.setDirection(query.getString(query.getColumnIndex(Constants.DIRECTION)));
            return status;
        } else {
            return null;
        }
    }

    @Override
    public boolean addCityInfo(CityInfo cityInfo) {
        ContentValues values = new ContentValues();

        values.put(Constants.DATE, cityInfo.getCurrentDate());
        values.put(Constants.CITY_NAME, cityInfo.getCityName());
        values.put(Constants.REGION, cityInfo.getRegion());
        values.put(Constants.CITY_PIC_ADRESS, cityInfo.getCityPicUrl());

        if ((db.insert(Constants.CITY_INFO, null, values) == -1)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean updateCityInfo(CityInfo cityInfo, int id) {
        ContentValues values = new ContentValues();

        values.put(Constants.DATE, cityInfo.getCurrentDate());
        values.put(Constants.CITY_NAME, cityInfo.getCityName());
        values.put(Constants.REGION, cityInfo.getRegion());
        values.put(Constants.CITY_PIC_ADRESS, cityInfo.getCityPicUrl());

        try {
            db.update(Constants.CITY_INFO, values, Constants.ID + " = ?", new String[]{id + ""});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteCityInfo(int id) {
        db.delete(Constants.CITY_INFO, Constants.ID + "= ?", new String[]{id+""});

        return false;
    }

    @Override
    public CityInfo getCityInfo(int id) {

        CityInfo cityInfo = new CityInfo();

        Cursor query = db.query(Constants.CITY_INFO, null, Constants.ID + " = ?", new String[]{id + ""}, null, null, null);

        if (query.moveToFirst()) {
            cityInfo.setCityName(query.getString(query.getColumnIndex(Constants.CITY_NAME)));
            cityInfo.setRegion(query.getString(query.getColumnIndex(Constants.REGION)));
            cityInfo.setCurrentDate(query.getString(query.getColumnIndex(Constants.DATE)));
            cityInfo.setCityPicUrl(query.getString(query.getColumnIndex(Constants.CITY_PIC_ADRESS)));
            cityInfo.setCityId(query.getInt(query.getColumnIndex(Constants.ID)));
            return cityInfo;
        } else {
            return null;
        }
    }

    @Override
    public List<CityInfo> getSavedCites() {
        List<CityInfo> list = new ArrayList<>();

        Cursor cursor = db.query(Constants.CITY_INFO, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            CityInfo cityInfo = new CityInfo();
            cityInfo.setCityName(cursor.getString(cursor.getColumnIndex(Constants.CITY_NAME)));
            cityInfo.setRegion(cursor.getString(cursor.getColumnIndex(Constants.REGION)));
            cityInfo.setCurrentDate(cursor.getString(cursor.getColumnIndex(Constants.DATE)));
            cityInfo.setCityPicUrl(cursor.getString(cursor.getColumnIndex(Constants.CITY_PIC_ADRESS)));
            cityInfo.setCityId(cursor.getInt(cursor.getColumnIndex(Constants.ID)));

            list.add(cityInfo);
        }
        return list;

    }

    @Override
    public boolean addForecast(Forecast forecast, int city_id) {

        ContentValues values = new ContentValues();

        values.put(Constants.PERSION_DATE, forecast.getDate());
        values.put(Constants.DAY, forecast.getDay());
        values.put(Constants.HIGH_TEMP, forecast.getHigh());
        values.put(Constants.LOW_TEMP, forecast.getLow());
        values.put(Constants.MAIN, forecast.getMain());
        values.put(Constants.CITY_ID, city_id);

        if ((db.insert(Constants.FORECAST, null, values) == -1)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean updateForecast(int city_id, int i, Forecast forecast) {

        ContentValues values = new ContentValues();

        int id = ((city_id - 1) * 10) + i;
        Log.d("forecast_id", id + "");

        values.put(Constants.PERSION_DATE, forecast.getDate());
        values.put(Constants.DAY, forecast.getDay());
        values.put(Constants.HIGH_TEMP, forecast.getHigh());
        values.put(Constants.LOW_TEMP, forecast.getLow());
        values.put(Constants.MAIN, forecast.getMain());

        try {
            db.update(Constants.FORECAST, values, Constants.ID + " = ?", new String[]{id + ""});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteForecast(int city_id) {
        db.delete(Constants.FORECAST, Constants.CITY_ID + "= ?", new String[]{city_id+""});

        return false;
    }

    @Override
    public List<Forecast> getAllForecast(int city_id) {

        List<Forecast> list = new ArrayList<>();

        Cursor cursor = db.query(Constants.FORECAST, null, Constants.CITY_ID + " = ?", new String[]{city_id + ""}, null, null, null);

        while (cursor.moveToNext()) {

            Forecast forecast = new Forecast();

            forecast.setDate(cursor.getString(cursor.getColumnIndex(Constants.PERSION_DATE)));
            forecast.setDay(cursor.getString(cursor.getColumnIndex(Constants.DAY)));
            forecast.setMain(cursor.getString(cursor.getColumnIndex(Constants.MAIN)));
            forecast.setLow(cursor.getString(cursor.getColumnIndex(Constants.LOW_TEMP)));
            forecast.setHigh(cursor.getString(cursor.getColumnIndex(Constants.HIGH_TEMP)));

            list.add(forecast);
        }
        return list;

    }

    @Override
    public boolean addPrayerTimes(PrayerTimes prayerTimes) {

        ContentValues values = new ContentValues();

        values.put(Constants.MORNING, prayerTimes.getMorning());
        values.put(Constants.SUNRISE, prayerTimes.getSunrise());
        values.put(Constants.NOON, prayerTimes.getNoon());
        values.put(Constants.SUNSET, prayerTimes.getSunset());
        values.put(Constants.WEST, prayerTimes.getWest());
        values.put(Constants.MIDNIGHT, prayerTimes.getMidnight());

        if ((db.insert(Constants.PRAYER, null, values)) == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean updatePrayerTimes(PrayerTimes prayerTimes, int id) {
        ContentValues values = new ContentValues();

        values.put(Constants.MORNING, prayerTimes.getMorning());
        values.put(Constants.SUNRISE, prayerTimes.getSunrise());
        values.put(Constants.NOON, prayerTimes.getNoon());
        values.put(Constants.SUNSET, prayerTimes.getSunset());
        values.put(Constants.WEST, prayerTimes.getWest());
        values.put(Constants.MIDNIGHT, prayerTimes.getMidnight());

        try {

            db.update(Constants.PRAYER, values, Constants.ID + " = " + " ?", new String[]{id + ""});

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deletePrayerTimes(int id) {
        db.delete(Constants.PRAYER, Constants.ID + "= ?", new String[]{id+""});


        return false;
    }

    @Override
    public PrayerTimes getPrayerTimes(int id) {
        PrayerTimes prayerTimes = new PrayerTimes();

        Cursor cursor = db.query(Constants.PRAYER, null, Constants.ID + " = ?", new String[]{id + ""}, null, null, null);
        if (cursor.moveToFirst()) {
            prayerTimes.setMorning(cursor.getString(cursor.getColumnIndex(Constants.MORNING)));
            prayerTimes.setSunrise(cursor.getString(cursor.getColumnIndex(Constants.SUNRISE)));
            prayerTimes.setNoon(cursor.getString(cursor.getColumnIndex(Constants.NOON)));
            prayerTimes.setSunset(cursor.getString(cursor.getColumnIndex(Constants.SUNSET)));
            prayerTimes.setWest(cursor.getString(cursor.getColumnIndex(Constants.WEST)));
            prayerTimes.setMidnight(cursor.getString(cursor.getColumnIndex(Constants.MIDNIGHT)));
            String _id = cursor.getString(cursor.getColumnIndex(Constants.ID));
            Log.d("_id", _id);

            return prayerTimes;
        } else {
            return null;
        }
    }

    @Override
    public boolean addDateInfo(DateInfo dateInfo) {
        ContentValues values = new ContentValues();
        values.put(Constants.DATE, dateInfo.getDate());
        values.put(Constants.YEAR, dateInfo.getYear());
        values.put(Constants.MONTH_NAME, dateInfo.getMonthName());
        values.put(Constants.DAY_NAME, dateInfo.getDayName());
        values.put(Constants.TIME, dateInfo.getTime());
        values.put(Constants.DAY, dateInfo.getDay());

        try {
            db.insert(Constants.DATE, null, values);
            return true;
        } catch (Exception e) {

            return false;

        }
    }

    @Override
    public boolean updateDateInfo(DateInfo dateInfo, int id) {
        ContentValues values = new ContentValues();
        values.put(Constants.DATE, dateInfo.getDate());
        values.put(Constants.YEAR, dateInfo.getYear());
        values.put(Constants.MONTH_NAME, dateInfo.getMonthName());
        values.put(Constants.DAY_NAME, dateInfo.getDayName());
        values.put(Constants.TIME, dateInfo.getTime());
        values.put(Constants.DAY, dateInfo.getDay());


        try {
            db.update(Constants.DATE, values, Constants.ID + " = ?", new String[]{id + ""});
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean deleteDateInfo(int id) {
        db.delete(Constants.DATE, Constants.ID + "= ?", new String[]{id+""});

        return false;
    }

    @Override
    public DateInfo getDateInfo(int id) {

        Cursor cursor = db.query(Constants.DATE, null, Constants.ID + " = ?", new String[]{id + ""}, null, null, null, null);

        if (cursor.moveToFirst()) {

            DateInfo dateInfo = new DateInfo();
            dateInfo.setDayName(cursor.getString(cursor.getColumnIndex(Constants.DAY_NAME)));
            dateInfo.setDate(cursor.getString(cursor.getColumnIndex(Constants.DATE)));
            dateInfo.setYear(cursor.getString(cursor.getColumnIndex(Constants.YEAR)));
            dateInfo.setMonthName(cursor.getString(cursor.getColumnIndex(Constants.MONTH_NAME)));
            dateInfo.setTime(cursor.getString(cursor.getColumnIndex(Constants.TIME)));
            dateInfo.setDay(cursor.getString(cursor.getColumnIndex(Constants.DAY)));
            return dateInfo;
        } else {
            return null;

        }
    }


}

