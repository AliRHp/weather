package com.hp.alirezas.weather01.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.hp.alirezas.weather01.db.Constants;
import com.hp.alirezas.weather01.info.City;
import com.hp.alirezas.weather01.info.CityInfo;
import com.hp.alirezas.weather01.info.DateInfo;
import com.hp.alirezas.weather01.info.Forecast;
import com.hp.alirezas.weather01.info.PrayerTimes;
import com.hp.alirezas.weather01.info.Status;
import com.hp.alirezas.weather01.utility.Helper;
import com.hp.alirezas.weather01.utility.MyApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UpdateInfoService extends Service {


    public String coordinatesUrl;
    private String mainUrl = "http://malipour.ir/api/?";
    private String listUrl = "http://malipour.ir/api//?type=cities";
    DateInfo dateInfo;
    CityInfo cityInfo;
    Forecast forecast;
    PrayerTimes prayerTimes;
    Status status;
    List<Forecast> forecastList;
    private String downloadedJson;
    String serviceMode;


    int downloadCount;
    int savedListSize;

    boolean addMode;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadCount = 0;
        addMode = false;

        waitingThread.start();


        serviceMode = "";

        if (intent.hasExtra(Constants.SERVICE_MODE)) {
            //get list of Cities
            serviceMode = intent.getExtras().getString(Constants.SERVICE_MODE);
            if (serviceMode.equals(Constants.MODE_GET_CITY_LIST)) {
                downloadInfo.execute(listUrl);

            } else if (serviceMode.equals(Constants.MODE_ADD_CITY)) {
                addMode = true;
                Double lat = intent.getExtras().getDouble(Constants.CITY_LAT);
                Double lon = intent.getExtras().getDouble(Constants.CITY_LON);
                String url = makeCoordinatesUrl(lat, lon);
                downloadInfo.execute(url);
            }
        } else {
            try {
                coordinatesUrl = makeCoordinatesUrl(getCurrentLat(), getCurrentLon());
            } catch (Exception e) {
                Helper.showToast(UpdateInfoService.this,"دسترسی به موقعیت  مکانی شما امکان پذیر نیست");
                Helper.showToast(UpdateInfoService.this,"لطفا تنطیمات  موقعیت مکانی خود را چک کنید ");
            }
            List<CityInfo> list = MyApp.getOperatin().getSavedCites();
            downloadInfo.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, coordinatesUrl);
            if ((savedListSize = list.size()) > 1) {
                for (int i = 1; i < savedListSize; i++) {
                    try {
                        List<City> list1 = MyApp.getOperatin().searchCites(list.get(i).getCityName());
                        City city = list1.get(0);
                        double lat = Double.parseDouble(city.getCityLat());
                        double lon = Double.parseDouble(city.getCityLon());
                        downloadInfo = new DownloadInfo();
                        downloadInfo.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, makeCoordinatesUrl(lat, lon));
                    } catch (Exception e) {
                        MyApp.getOperatin().deleteCityInfo(list.get(i).getCityId());
                        MyApp.getOperatin().deleteDateInfo(list.get(i).getCityId());
                        MyApp.getOperatin().deleteForecast(list.get(i).getCityId());
                        MyApp.getOperatin().deletePrayerTimes(list.get(i).getCityId());
                        MyApp.getOperatin().deleteStatus(list.get(i).getCityId());
                    }
                }
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!waitingThread.currentThread().isInterrupted()) {
            waitingThread.currentThread().interrupt();
            waitingThread = null;
        }
    }

    public class DownloadInfo extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            String urlS = params[0];
            String json = "";


            try {
                URL url = new URL(urlS);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                int responseCode = connection.getResponseCode();
                Log.d("responseCode", responseCode + "");

                if (connection.getResponseCode() != 200) {

                } else {
                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuilder builder = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    json = builder.toString();
                }
                if ((serviceMode.compareTo(Constants.MODE_GET_CITY_LIST) == 0)) {
                    List<City> list = parsListJson(json);
                    putListIntoDb(list);

                }


                Log.d("resultJson", json);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json) {


            super.onPostExecute(json);
            try {
                waitingThread.currentThread().interrupt();
                Helper.showToast(UpdateInfoService.this, "thread stop");
                waitingThread = null;
                Thread.currentThread().sleep(20);

            } catch (InterruptedException e) {
                e.printStackTrace();
//                Helper.showToast(UpdateInfoService.this, "thread  not stop");
            }

            if (json != null) {
                Log.d("=====", json);
                if ((serviceMode.compareTo(Constants.MODE_GET_CITY_LIST) == 0)) {
                    sendBroadcast(new Intent(Constants.ACTION_FILTER_FINISH_DOWNLOAD));

                } else if (addMode) {
                    parsJson(json);
                    int size;
                    if ((size = MyApp.getOperatin().getSavedCites().size()) > 1) {
                        CityInfo info = MyApp.getOperatin().getCityInfo(MyApp.getOperatin().getSavedCites().get(size - 1).getCityId());
                        MyApp.getOperatin().deleteCityInfo(info.getCityId());
                        MyApp.getOperatin().deleteDateInfo(info.getCityId());
                        MyApp.getOperatin().deleteForecast(info.getCityId());
                        MyApp.getOperatin().deletePrayerTimes(info.getCityId());
                        MyApp.getOperatin().deleteStatus(info.getCityId());
                    }
                    addTodb();
                    sendBroadcast(new Intent(Constants.ACTION_FILTER_FINISH_ADD_CITY));

                } else {
                    parsJson(json);
                    if (MyApp.getOperatin().getSavedCites().size() == 0) {
                        addTodb();
                        sendBroadcast(new Intent(Constants.ACTION_FILTER_FINISH_DOWNLOAD));
                    } else {
                        int id = MyApp.getOperatin().getSavedCites().get(downloadCount).getCityId();
                        downloadCount++;
                        updateDb(id);

                        if (downloadCount == savedListSize) {
                            sendBroadcast(new Intent(Constants.ACTION_FILTER_FINISH_DOWNLOAD));
                        }
                    }

                }
            } else {
                Helper.showToast(UpdateInfoService.this, "no response from server ");
            }


        }


    }

    public String makeCoordinatesUrl(double lat, double lon) {

        String url = (mainUrl + "lat=" + lat + "&lon=" + lon);
        return url;
    }

    public void parsJson(String json) {
        dateInfo = new DateInfo();
        cityInfo = new CityInfo();
        status = new Status();
        forecastList = new ArrayList<>();
        prayerTimes = new PrayerTimes();

        JSONObject root = null;
        try {
            root = new JSONObject(json);

//            cityInfo.setCurrentDate(root.getString(Constants.DATE));

            JSONObject dateObject = root.getJSONObject(Constants.DATE);
            dateInfo.setDate(dateObject.getString(Constants.DATE));
            dateInfo.setYear(dateObject.getString(Constants.YEAR));
            dateInfo.setMonthName(dateObject.getString(Constants.MONTH_NAME));
            dateInfo.setDayName(dateObject.getString(Constants.DAY_NAME));
            dateInfo.setTime(dateObject.getString(Constants.TIME));
            dateInfo.setDay(dateObject.getString(Constants.DAY));


            JSONObject cityObject = root.getJSONObject(Constants.CITY_INFO);
            cityInfo.setCityName(cityObject.getString(Constants.CITY_NAME));
            cityInfo.setRegion(cityObject.getString(Constants.REGION));
            cityInfo.setCityPicUrl(cityObject.getString(Constants.CITY_PIC_ADRESS));

            JSONObject weatherObject = root.getJSONObject(Constants.WEATHER);

            JSONObject statusObject = weatherObject.getJSONObject(Constants.WEATHER_STATUS);
            status.setDate(statusObject.getString(Constants.DATE));
            status.setTemp(statusObject.getString(Constants.TEMPERATURE));
            status.setMain(statusObject.getString(Constants.MAIN));

            JSONObject atmospherObject = weatherObject.getJSONObject(Constants.ATMOSPHERE);
            status.setHumidity(atmospherObject.getString(Constants.HUMIDITY));
            status.setPresure(atmospherObject.getString(Constants.PRESSURE));

            JSONObject windObject = weatherObject.getJSONObject(Constants.WIND);
            status.setChill(windObject.getString(Constants.CHILL));
            status.setDirection(windObject.getString(Constants.DIRECTION));
            status.setSpeed(windObject.getString(Constants.SPEED));


            JSONArray forecastArray = weatherObject.getJSONArray(Constants.FORECAST);
            for (int i = 0; i < forecastArray.length(); i++) {
                forecast = new Forecast();

                JSONObject forecastObject = forecastArray.getJSONObject(i);
                forecast.setDate(forecastObject.getString(Constants.PERSION_DATE)).setDay(forecastObject.getString(Constants.DAY)).setHigh(forecastObject.getString(Constants.HIGH_TEMP)).setLow(forecastObject.getString(Constants.LOW_TEMP)).setMain(forecastObject.getString(Constants.MAIN));
                forecastList.add(forecast);
            }

            JSONObject prayerObject = root.getJSONObject(Constants.PRAYER);
            prayerTimes.setMorning(prayerObject.getString(Constants.MORNING)).setSunrise(prayerObject.getString(Constants.SUNRISE)).setNoon(prayerObject.getString(Constants.NOON)).setSunset(prayerObject.getString(Constants.SUNSET)).setMidnight(prayerObject.getString(Constants.MIDNIGHT)).setWest(prayerObject.getString(Constants.WEST));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addTodb() {
        MyApp.getOperatin().addCityInfo(cityInfo);
        MyApp.getOperatin().addStatus(status);
        MyApp.getOperatin().addPrayerTimes(prayerTimes);
        MyApp.getOperatin().addDateInfo(dateInfo);

        List<CityInfo> infoList = MyApp.getOperatin().getSavedCites();
        CityInfo cityInfo1;
//        if (infoList.size() > 0) {
        cityInfo1 = infoList.get(infoList.size() - 1);

        int city_id = cityInfo1.getCityId();
        int i = 0;
        for (int j = 0; j < forecastList.size() - 1; j++) {
            MyApp.getOperatin().addForecast(forecastList.get(j + 1), city_id);
            i = j;
        }
        MyApp.getOperatin().addForecast(forecastList.get(i + 1), city_id);

    }


    public void updateDb(int id) {
        MyApp.getOperatin().updateCityInfo(cityInfo, id);
        MyApp.getOperatin().updateStatus(status, id);
        MyApp.getOperatin().updateDateInfo(dateInfo, id);
        MyApp.getOperatin().updatePrayerTimes(prayerTimes, id);
        MyApp.getOperatin().updateDateInfo(dateInfo, id);

        for (int i = 0; i < forecastList.size(); i++) {
            MyApp.getOperatin().updateForecast(id, i, forecastList.get(i));
        }


    }

    public double getCurrentLat() {
        double lat = 0.0;
        boolean isGpsEnabled;
        boolean isNetWorkEnabled;


        LocationManager manager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        isGpsEnabled = manager.isProviderEnabled(manager.GPS_PROVIDER);
        isNetWorkEnabled = manager.isProviderEnabled(manager.NETWORK_PROVIDER);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        if (isNetWorkEnabled) {

            Location lastKnownLocation = manager.getLastKnownLocation(manager.NETWORK_PROVIDER);
            try {
                lat = lastKnownLocation.getLatitude();
            } catch (Exception e) {

            }

        }
        if (isGpsEnabled) {

            Location lastKnownLocation = manager.getLastKnownLocation(manager.GPS_PROVIDER);
            lat = lastKnownLocation.getLatitude();
        }
        return lat;

    }

    public double getCurrentLon() {
        double lon = 0.0;
        boolean isGpsEnabled;
        boolean isNetWorkEnabled;


        LocationManager manager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        isGpsEnabled = manager.isProviderEnabled(manager.GPS_PROVIDER);
        isNetWorkEnabled = manager.isProviderEnabled(manager.NETWORK_PROVIDER);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        if (isNetWorkEnabled) {

            Location lastKnownLocation = manager.getLastKnownLocation(manager.NETWORK_PROVIDER);
            lon = lastKnownLocation.getLongitude();

        }
        if (isGpsEnabled) {

            Location lastKnownLocation = manager.getLastKnownLocation(manager.GPS_PROVIDER);
            lon = lastKnownLocation.getLongitude();
        }
        return lon;
    }

    public List<City> parsListJson(String json) {
        List<City> list = new ArrayList<>();
        City city;

        try {
            JSONArray rootArray = new JSONArray(json);
            for (int i = 0; i < rootArray.length(); i++) {
                JSONObject cityObject = rootArray.getJSONObject(i);

                city = new City();
                city.setCityFaName(cityObject.getString(Constants.CITY_FA_NAME));
                city.setCityLat(cityObject.getString(Constants.CITY_LAT));
                city.setCityLon(cityObject.getString(Constants.CITY_LON));
                city.setCityPicUrl(cityObject.getString(Constants.CITY_PIC));

                list.add(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }


    public void putListIntoDb(List<City> list) {

        MyApp.getOperatin().deleteAllCityList();

        for (int i = 0; i < list.size(); i++) {
            MyApp.getOperatin().addCityList(list.get(i));
        }
    }


    Thread waitingThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {

                for (int i = 0; i < 3000; i++) {
                    if (!waitingThread.isInterrupted()) {

                        try {
                            Thread.sleep(10);
                            Log.d("======", String.valueOf(i));
                            sendBroadcast(new Intent(Constants.ACTION_FILTER_WAIT_DOWNLOAD));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                sendBroadcast(new Intent(Constants.ACTION_FILTER_DOWNLOAD_FAILED));
//                Helper.showToast(UpdateInfoService.this, "no response from server ");

            } catch (Exception e) {
                waitingThread.currentThread().interrupt();

            }

        }
    });
}
