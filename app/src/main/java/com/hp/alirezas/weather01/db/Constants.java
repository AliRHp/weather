package com.hp.alirezas.weather01.db;

/**
 * Created by alireza s on 6/25/2016.
 */
public class Constants {


    public static String ID = "_id";


    public static String CITY_LIST = "cityList";
    public static String CITY_FA_NAME = "title";
    public static String CITY_LAT = "Latitude";
    public static String CITY_LON = "Longitude";
    public static String CITY_PIC = "pic";


    public static String DATE = "date";
    public static String MONTH_NAME = "monthName";
    public static String MONTH_DAY = "day";
    public static String YEAR = "year";
    public static String TIME = "time";
    public static String DAY_NAME = "weekDayName";


    public static String CITY_INFO = "cityInfo";
    public static String CITY_NAME = "title_fa";
    public static String REGION = "region";
    public static String CITY_PIC_ADRESS = "CityPicure";

    public static String WEATHER = "weather";
    public static String WEATHER_STATUS = "status";
    public static String TEMPERATURE = "temp";
    public static String MAIN = "code";

    public static String ATMOSPHERE = "atmosphere";
    public static String HUMIDITY = "humidity";
    public static String PRESSURE = "pressure";


    public static String WIND = "wind";
    public static String CHILL = "chill";
    public static String DIRECTION = "direction";
    public static String SPEED = "speed";


    public static String FORECAST = "forecast";
    public static String CITY_ID = "city_id";
    public static String DAY = "day";
    public static String HIGH_TEMP = "high";
    public static String LOW_TEMP = "low";
    public static String PERSION_DATE = "fDate";

    public static String PRAYER = "prayerTimes";
    public static String MORNING = "morning";
    public static String SUNRISE = "sunrise";
    public static String NOON = "Noon";
    public static String SUNSET = "sunset";
    public static String WEST = "west";
    public static String MIDNIGHT = "midnight";


    public static String DATABASE_NAME = "weatherPrayer.db";
    public static int DATAbASE_VERSION = 1;


    public static String CREATE_TABLE_CITY_INFO = "  create table  "+ CITY_INFO + " ( " +ID+"  integer primary key autoincrement,"+
            CITY_NAME + " text ," + REGION + " text," + DATE + " text,"+CITY_PIC_ADRESS+" text);";


    public static String CREATE_TABLE_STATUS = "create table " + WEATHER_STATUS + " ( " +ID+"  integer primary key autoincrement,"+
            DATE + " text ," + TEMPERATURE + " text ," + MAIN + " text," + CHILL + " text," + DIRECTION + " text," + SPEED +
            " text," + HUMIDITY + " text," +
            PRESSURE + " text);";

    public static String CREATE_TABLE_FORECAST = "create table " + FORECAST + "(" + ID + "  integer primary key autoincrement," +
            PERSION_DATE + "  text," + DAY + " text," + HIGH_TEMP + " text," + LOW_TEMP + " text," + MAIN + " text,"+CITY_ID+"  text);";

    public static String CREATE_TABLE_PRAYER = "create table " + PRAYER + "( " + ID + "  integer primary key autoincrement," +
            MORNING + " text," + SUNRISE + " text, " + NOON + " text ," + SUNSET + " text," + WEST + " text," + MIDNIGHT + " text) ;";

    public static String CREATE_TABLE_DATE_INFO = "create table " + DATE + "(" + ID + " integer primary key autoincrement," + DATE +
            " text ," + YEAR + " text," + MONTH_NAME + " text," + DAY_NAME + " text," + TIME + " text,"+DAY+" text);";


    public static String CREATE_TABLE_CITY_LIST = "create table  " + CITY_LIST + "(" + ID + " integer primary key autoincrement," +
            CITY_FA_NAME + "  text , " + CITY_LAT + " text , " + CITY_LON + " text, " + CITY_PIC + "  text); ";



    public static String ACTION_FILTER_FINISH_DOWNLOAD = "com.hp.alirezas.weather01.download.finish";
    public static String ACTION_FILTER_FINISH_ADD_CITY = "com.hp.alirezas.weather01.add.city.finish";
    public static String ACTION_FILTER_WAIT_DOWNLOAD = "com.hp.alirezas.weather01.download.wait";
    public static String ACTION_FILTER_DOWNLOAD_FAILED = "com.hp.alirezas.weather01.download.failed";
    public static String ACTION_FILTER_REFRESH_VIEW_PAGER = "com.hp.alirezas.weather01. refresh_view_pager";



    public static String MODE_GET_CITY_LIST = "getLIst";
    public static String SERVICE_MODE = "mode";
    public static String MODE_ADD_CITY = "add city";
}
