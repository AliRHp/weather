package com.hp.alirezas.weather01.fragment;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hp.alirezas.weather01.MainActivity;
import com.hp.alirezas.weather01.R;
import com.hp.alirezas.weather01.adapters.ForecastAdapetrRecycle;
import com.hp.alirezas.weather01.db.Constants;
import com.hp.alirezas.weather01.info.CityInfo;
import com.hp.alirezas.weather01.info.DateInfo;
import com.hp.alirezas.weather01.info.Forecast;
import com.hp.alirezas.weather01.info.PrayerTimes;
import com.hp.alirezas.weather01.info.Status;
import com.hp.alirezas.weather01.service.UpdateInfoService;
import com.hp.alirezas.weather01.utility.ConverTool;
import com.hp.alirezas.weather01.utility.Helper;
import com.hp.alirezas.weather01.utility.MyApp;
import com.hp.alirezas.weather01.utility.NetCheck;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGBuilder;
import com.telly.mrvector.MrVector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    LinearLayout linearLayout;

    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerView;
    ScrollView scrollView;

    TextView textViewDayName;
    TextView textViewDay;
    TextView textViewMonthNam;
    TextView textViewYear;
    TextView textViewTemp;
    ImageView imageViewMain;
    TextView textViewhumidity;
    TextView textViewPressure;
    TextView textViewChill;
    TextView textViewSpeed;
    TextView textViewMorning;
    TextView textViewSunrise;
    TextView textViewNoon;
    TextView textViewSunset;
    TextView textViewWest;
    TextView textVieMidNight;
    TextView textViewdate;
    TextView textViewTime;


    CityInfo cityInfo;
    DateInfo dateInfo;
    PrayerTimes prayerTimes;

    List<Forecast> forecastList;
    Status status;

    int cityId;
    SwipeRefreshLayout swipeRefreshLayout;

    public static String ID_ARG;

    IntentFilter filter;
    Intent intentService;

    boolean serviceFlag;
    boolean serviceIsRun;


    public CityFragment() {
    }


    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city, container, false);

        serviceFlag = false;
        serviceIsRun = false;


        linearLayout = (LinearLayout) v.findViewById(R.id.main_state_layout);
        //set swipe to refresh
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetCheck.checkNetworkIsOn(getActivity())) {
                    intentService = new Intent(getActivity(), UpdateInfoService.class);
                    serviceFlag = true;
                    getActivity().startService(intentService);
                } else {
                    Helper.showToast(getActivity(), "عدم  ارتباط با سرور ...");
                    Helper.showToast(getActivity(), "لطفا تنظیمات  اینترنت خود را چک کنید ");
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        Bundle args = getArguments();
        cityId = args.getInt(ID_ARG);


//get info from db
        cityInfo = MyApp.getOperatin().getCityInfo(cityId);
        prayerTimes = MyApp.getOperatin().getPrayerTimes(cityId);
        dateInfo = MyApp.getOperatin().getDateInfo(cityId);
        forecastList = MyApp.getOperatin().getAllForecast(cityId);
        status = MyApp.getOperatin().getStatus(cityId);


        //init views
        textViewTemp = (TextView) v.findViewById(R.id.main_temp);
        imageViewMain = (ImageView) v.findViewById(R.id.main);
        scrollView = (ScrollView) v.findViewById(R.id.scrollView);

        textViewMorning = (TextView) v.findViewById(R.id.morning);
        textViewSunrise = (TextView) v.findViewById(R.id.sunrise);
        textViewNoon = (TextView) v.findViewById(R.id.noon);
        textViewSunset = (TextView) v.findViewById(R.id.sunset);
        textViewWest = (TextView) v.findViewById(R.id.west);
        textVieMidNight = (TextView) v.findViewById(R.id.midnight);


        textViewDayName = (TextView) v.findViewById(R.id.day_name);
        textViewDay = (TextView) v.findViewById(R.id.day_num);
        textViewMonthNam = (TextView) v.findViewById(R.id.month_name);
        textViewYear = (TextView) v.findViewById(R.id.year);


        textViewhumidity = (TextView) v.findViewById(R.id.humidity);
        textViewPressure = (TextView) v.findViewById(R.id.pressure);
        textViewChill = (TextView) v.findViewById(R.id.chill);
        textViewSpeed = (TextView) v.findViewById(R.id.speed);


//        TextView textViewdate = (TextView) findViewById(R.id.latest_update_date);
//        TextView textViewTime = (TextView) findViewById(R.id.latest_update_time);
//

        appBarLayout = (AppBarLayout) v.findViewById(R.id.appbar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle);

        appBarLayout.addOnOffsetChangedListener(this);


        LinearLayoutManager forecastLayoutManager = new LinearLayoutManager(getContext());

        //init forecast recycler
        recyclerView.setLayoutManager(forecastLayoutManager);
        final ForecastAdapetrRecycle adapter = new ForecastAdapetrRecycle(forecastList);
        recyclerView.setAdapter(adapter);


        //init collapse toolbar
        collapsingToolbarLayout = (CollapsingToolbarLayout) v.findViewById(R.id.htab_collapse_toolbar);
        collapsingToolbarLayout.setTitle(cityInfo.getCityName());


        // download city image for toolbar header
        if (!serviceIsRun) {
            imageDownload imageDownload = new imageDownload();
            imageDownload.execute(cityInfo.getCityPicUrl());
        }


        ConverTool tool = new ConverTool(getActivity());


        //set textViews from db
        textViewTemp.setText(status.getTemp());
        Resources res = getActivity().getResources();


        //todo check this vector set style in higher api level (21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = res.getDrawable(R.drawable.sunny, null);
            VectorDrawable vectorDrawable = (VectorDrawable) drawable;
            imageViewMain.setImageDrawable(vectorDrawable);

        } else {
            try {
                imageViewMain.setImageResource(tool.convertCode(status.getMain(), true));
            } catch (Exception e) {
                
            }
        }


        textViewMorning.setText(prayerTimes.getMorning());
        textViewSunrise.setText(prayerTimes.getSunrise());
        textViewNoon.setText(prayerTimes.getNoon());
        textViewSunset.setText(prayerTimes.getSunset());
        textViewWest.setText(prayerTimes.getWest());
        textVieMidNight.setText(prayerTimes.getMidnight());


        textViewDayName.setText(dateInfo.getDayName());
        textViewDay.setText(dateInfo.getDay());
        textViewMonthNam.setText(dateInfo.getMonthName());
        textViewYear.setText(dateInfo.getYear());

        textViewhumidity.setText(status.getHumidity());
        textViewPressure.setText(status.getPresure());
        textViewChill.setText(status.getChill());
        textViewSpeed.setText(status.getSpeed());

        //currentBackground
        //todo put into a thread
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");
//        String currentTime = dateFormat.format(calendar.getTime());
//
//        if ((currentTime.compareTo(prayerTimes.getMorning()) > 0 && (currentTime.compareTo("10:00:00")) < 0)) {
//            scrollView.setBackgroundResource(R.drawable.back_morning);
//
//        } else if (currentTime.compareTo("10:00:00") > 0 && currentTime.compareTo(prayerTimes.getSunset()) < 0) {
//            scrollView.setBackgroundResource(R.drawable.back_noon);
//        } else if (currentTime.compareTo(prayerTimes.getSunset()) > 0 && currentTime.compareTo(prayerTimes.getSunset()) > 0) {
//            scrollView.setBackgroundResource(R.drawable.back_night);
//        } else if (currentTime.compareTo(prayerTimes.getMidnight()) > 0 && currentTime.compareTo(prayerTimes.getMorning()) > 0) {
//            scrollView.setBackgroundResource(R.drawable.back_night);
//        }

        return v;
    }


    //task for download city image
    private class imageDownload extends AsyncTask<String, Void, BitmapDrawable> {

        BitmapDrawable drawable;

        @Override
        protected BitmapDrawable doInBackground(String... params) {

            String link = params[0];
            File file = null;
            try {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), cityInfo.getCityName() + "image.jpeg");
                if (file.exists()) {
                    Log.d("fileExists", file.getAbsolutePath());
                } else {
                    URL url = new URL(link);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    int responseCode = httpURLConnection.getResponseCode();
                    Log.d("responseCode", "" + responseCode);

                    InputStream inputStream = httpURLConnection.getInputStream();

                    file.createNewFile();

                    FileOutputStream outputStream = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];
                    int numberOfBytes = 0;
                    while ((numberOfBytes = inputStream.read(buffer)) != -1) {

                        outputStream.write(buffer, 0, numberOfBytes);
                        Log.d("=====>", "" + numberOfBytes);
                    }
                }

                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                drawable = new BitmapDrawable(getResources(), bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return drawable;
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            super.onPostExecute(drawable);

            appBarLayout.setBackground(drawable);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if (collapsingToolbarLayout.getHeight() + verticalOffset >= (254)) {
            swipeRefreshLayout.setEnabled(true);
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
        if (collapsingToolbarLayout.getHeight() + verticalOffset <= (ViewCompat.getMinimumHeight(collapsingToolbarLayout))) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


        filter = new IntentFilter();
        filter.addAction(Constants.ACTION_FILTER_FINISH_DOWNLOAD);
        filter.addAction(Constants.ACTION_FILTER_WAIT_DOWNLOAD);
        filter.addAction(Constants.ACTION_FILTER_DOWNLOAD_FAILED);


        getActivity().registerReceiver(receiver, filter);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.ACTION_FILTER_WAIT_DOWNLOAD)) {
                serviceIsRun = true;
                swipeRefreshLayout.setRefreshing(true);

            }
            if (intent.getAction().equals(Constants.ACTION_FILTER_DOWNLOAD_FAILED)) {
                serviceIsRun = false;
                Helper.showToast(getActivity(), "ارتباط با سرور برقرار نشد...");
                if (serviceFlag == true) {
                    getActivity().stopService(intentService);
                    swipeRefreshLayout.setRefreshing(false);
                    serviceFlag = false;
                }
                swipeRefreshLayout.setRefreshing(false);

            } else if (intent.getAction().equals(Constants.ACTION_FILTER_FINISH_DOWNLOAD)) {
                swipeRefreshLayout.setRefreshing(false);
                serviceIsRun = false;
                if (serviceFlag == true) {
                    Helper.showToast(getActivity(), "بروز رسانی انجام شد");
                    getActivity().stopService(intentService);
                    getActivity().sendBroadcast(new Intent(Constants.ACTION_FILTER_REFRESH_VIEW_PAGER));
                    serviceFlag = false;
                }
            }


        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if (serviceFlag) {
            if (serviceIsRun) {
                getActivity().stopService(intentService);
                serviceIsRun = false;
                serviceFlag = false;
            }
        }

//        getActivity().unregisterReceiver(receiver);
    }


    public boolean isServiceRunning(Class<?> service) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceClass : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.getName().equals(serviceClass.service.getClassName())) {
                return true;
            }

        }
        return false;

    }

    public void enableDisableSwipeRefresh(boolean b) {
//        if (swipeRefreshLayout.isRefreshing()) {
//            swipeRefreshLayout.setEnabled(false);
//
//        } else {
//            swipeRefreshLayout.setEnabled(true);
//        }

    }


}



