package com.hp.alirezas.weather01.utility;

import android.content.Context;
import android.content.res.Resources;

import com.hp.alirezas.weather01.R;

/**
 * Created by alireza s on 6/26/2016.
 */
public class ConverTool {

    static Context context;

    public ConverTool(Context context) {
        this.context = context;
    }
    public static String convertCityName(String cityName) {
        String name = "";

        Resources resources =context.getResources();
        switch (cityName) {
            case ("babol"):
               name = resources.getString(R.string.بابل);
                break;
            case ("tehran"):
                name = resources.getString(R.string.تهران);
                break;
            case ("amol"):
                name = resources.getString(R.string.امل);
                break;


            //// TODO: 6/26/2016 add all citys name to convret all

        }
        return name;

    }

    public int convertCode(String code) {
        int i = 0;
        switch (code) {
            case ("1"):
                i =R.drawable.storm;
                break;
            case ("2"):
                i =R.drawable.storm;
                break;
            case ("3"):
                 i = R.drawable.storm;
                break;
            case ("4"):
                i = R.drawable.thunder_vector;
                break;
            case ("5"):
                i = R.drawable.rain_snow;
                break;
            case ("6"):
                i = R.drawable.rain_wind;
                break;
            case ("7"):
                i = R.drawable.shower_snow;
                break;
            case ("8"):
                i = R.drawable.small_mist;
                break;
            case ("9"):
                i = R.drawable.small_mist;
                break;
            case ("10"):
                i = R.drawable.rain_snow;
                break;
            case ("11"):
                i = R.drawable.shower;
                break;
            case ("12"):
                i = R.drawable.shower;
                break;
            case ("13"):
                i = R.drawable.snow_wind;
                break;
            case ("14"):
                i = R.drawable.shower_snow;
                break;
            case ("15"):
                i = R.drawable.shower_snow;
                break;
            case ("16"):
                i = R.drawable.snow;
                break;
            case ("17"):
                i = R.drawable.hail;
                break;
            case ("18"):
                i = R.drawable.rain_snow;
                break;
            case ("19"):
                i = R.drawable.wind_dust;
                break;
            case ("20"):
                i = R.drawable.fog;
                break;
            case ("21"):
                i = R.drawable.fog;
                break;
            case ("22"):
                i = R.drawable.fog;
                break;
            case ("23"):
                i = R.drawable.wind;
                break;
            case ("24"):
                i = R.drawable.wind;
                break;
            case ("25"):
                i = R.drawable.snow_vector;
                break;
            case ("26"):
                i = R.drawable.cloud;
                break;
            case ("27"):
                i = R.drawable.moon_big_cloud;
                break;
            case ("28"):
                i = R.drawable.cloud;
                break;
            case ("29"):
                i = R.drawable.moon_small_cloud;
                break;
            case ("30"):
                i = R.drawable.partly_cloudy_day;
                break;
            case ("31"):
                i = R.drawable.moon;
                break;
            case ("32"):
                i = R.drawable.sun;
                break;
            case ("33"):
                i = R.drawable.moon;
                break;
            case ("34"):
                i = R.drawable.sun;
                break;
            case ("35"):
                i = R.drawable.rain_hail;
                break;
            case ("36"):
                i = R.drawable.sun;
                break;
            case ("37"):
                i = R.drawable.thunder_vector;
                break;
            case ("38"):
                i = R.drawable.thunder_vector;
                break;
            case ("39"):
                i = R.drawable.thunder_vector;
                break;
            case ("40"):
                i = R.drawable.shower;
                break;
            case ("41"):
                i = R.drawable.snow_vector;
                break;
            case ("42"):
                i = R.drawable.shower_snow;
                break;
            case ("43"):
                i = R.drawable.snow_vector;
                break;
            case ("44"):
                i = R.drawable.partly_cloudy_day_1;
                break;
            case ("45"):
                i = R.drawable.shower_thunder;
                break;
            case ("46"):
                i = R.drawable.shower_snow;
                break;
            case ("47"):
                i = R.drawable.thunder_vector;
                break;
            case ("3200"):
                i = 0;
                break;

        }
        // TODO: 6/27/2016 add all code state with icons
        return i;
    }

    public int convertCode(String code,boolean vector) {
        int i = 0;
        switch (code) {
            case ("1"):
                i =R.drawable.tornado_season;
                break;
            case ("2"):
                i =R.drawable.tornado_season;
                break;
            case ("3"):
                i = R.drawable.tornado_season;
                break;
            case ("4"):
                i = R.drawable.thunder_vector;
                break;
            case ("5"):
                i = R.drawable. rain_snow_sleet;
                break;
            case ("6"):
                i = R.drawable.rain_snow_sleet;
                break;
            case ("7"):
                i = R.drawable.rain_snow_sleet;
                break;
            case ("8"):
                i = R.drawable.drizzle1;
                break;
            case ("9"):
                i = R.drawable. drizzle1;
                break;
            case ("10"):
                i = R.drawable.drizzle1;
                break;
            case ("11"):
                i = R.drawable.rain_vector;
                break;
            case ("12"):
                i = R.drawable.rain_vector;
                break;
            case ("13"):
                i = R.drawable.snow_cloud ;
                break;
            case ("14"):
                i = R.drawable.snow_cloud ;
                break;
            case ("15"):
                i = R.drawable.snow_cloud ;
                break;
            case ("16"):
                i = R.drawable.snow_vector;
                break;
            case ("17"):
                i = R.drawable.hail_vector;
                break;
            case ("18"):
                i = R.drawable.rain_snow_sleet;
                break;
            case ("19"):
                i = R.drawable.foggy_vector;
                break;
            case ("20"):
                i = R.drawable.foggy_vector;
                break;
            case ("21"):
                i = R.drawable.foggy_vector;
                break;
            case ("22"):
                i = R.drawable.foggy_vector;
                break;
            case ("23"):
                i = R.drawable.wind_vector;
                break;
            case ("24"):
                i = R.drawable.wind_vector ;
                break;
            case ("25"):
                i = R.drawable.cold;
                break;
            case ("26"):
                i = R.drawable. cloudy;
                break;
            case ("27"):
                i = R.drawable.mostly_cloudy_night;
                break;
            case ("28"):
                i = R.drawable.mostly_cloudy_day;
                break;
            case ("29"):
                i = R.drawable.mostly_cloudy_night;
                break;
            case ("30"):
                i = R.drawable.partly_cloudy_day_1;
                break;
            case ("31"):
                i = R.drawable.clear_night;
                break;
            case ("32"):
                i = R.drawable.sunny_vector;
                break;
            case ("33"):
                i = R.drawable.clear_night;
                break;
            case ("34"):
                i = R.drawable.sunny_vector;
                break;
            case ("35"):
                i = R.drawable.hail_vector;
                break;
            case ("36"):
                i = R.drawable.hot;
                break;
            case ("37"):
                i = R.drawable.thunder_vector;
                break;
            case ("38"):
                i = R.drawable.thunder_vector;
                break;
            case ("39"):
                i = R.drawable.thunder_vector;
                break;
            case ("40"):
                i = R.drawable.rain_vector;
                break;
            case ("41"):
                i = R.drawable.snow_vector;
                break;
            case ("42"):
                i = R.drawable.snow_cloud;
                break;
            case ("43"):
                i = R.drawable.snow_cloud;
                break;
            case ("44"):
                i = R.drawable.partly_cloudy_day_1;
                break;
            case ("45"):
                i = R.drawable.thunder_vector;
                break;
            case ("46"):
                i = R.drawable.snow_vector;
                break;
            case ("47"):
                i = R.drawable.thunder_vector;
                break;
            case ("3200"):
                i = 0;
                break;

        }
        // TODO: 6/27/2016 add all code state with icons
        return i;
    }

    public static String convertDayName(String enDay) {
        String dayFa = "";

        switch (enDay) {
            case("Sun"):
                dayFa = "یکشنبه";
            break;
            case ("Mon"):
                dayFa = "دوشنبه";
            break;
            case ("Tue"):
                dayFa = "سه شنبه";
            break;
            case ("Wed"):
                dayFa = "چهارشنبه";
            break;
            case ("Thu"):
                dayFa = "پنج شنبه";
            break;
            case ("Fri"):
                dayFa = "جمعه";
                break;
            case ("Sat"):
                dayFa = "شنبه";
            break;
            }
        return dayFa;

        }

    public static String convertMonthName() {
        return "";
    }
    }


