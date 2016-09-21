//package com.hp.alirezas.weather01.fragment;
//
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Typeface;
//import android.graphics.drawable.AnimationDrawable;
//import android.graphics.drawable.BitmapDrawable;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutCompat;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.hp.alirezas.weather01.R;
//import com.hp.alirezas.weather01.info.CityInfo;
//import com.hp.alirezas.weather01.info.DateInfo;
//import com.hp.alirezas.weather01.info.PrayerTimes;
//import com.hp.alirezas.weather01.info.Status;
//import com.hp.alirezas.weather01.utility.MyApp;
//import com.hp.alirezas.weather01.utility.ConverTool;
//import com.koushikdutta.ion.Ion;
//import com.koushikdutta.ion.builder.Builders;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//
//public class WeatherFragment extends Fragment {
//    private String cityName;
//    LinearLayoutCompat Rl;
//
//
//    public WeatherFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_wehther, container, false);
//
//
//       Rl = (LinearLayoutCompat) rootView.findViewById(R.id.linearL);
////        Rl.setBackgroundResource(R.drawable.animation);
////        AnimationDrawable frameAnim = (AnimationDrawable) Rl.getBackground();
////        frameAnim.start();
//
//
//
//        Typeface tp = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(),"fonts/omid_regular.ttf");
//        Typeface tp1 = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(),"fonts/BNazanin.ttf");
//
//        CityInfo cityInfo = MyApp.getOperatin().getCityInfo();
//        Status status = MyApp.getOperatin().getStatus();
//        PrayerTimes prayerTimes = MyApp.getOperatin().getPrayerTimes();
//        DateInfo dateInfo = MyApp.getOperatin().getDateInfo();
//
//        TextView textViewDayName = (TextView) rootView.findViewById(R.id.day_name);
//        TextView textViewDay = (TextView) rootView.findViewById(R.id.day_num);
//        TextView textViewMonthName = (TextView) rootView.findViewById(R.id.month_name);
//        TextView textViewYear = (TextView) rootView.findViewById(R.id.year);
//
//
//
//        TextView textViewCity = (TextView) rootView.findViewById(R.id.city_name);
//        TextView textViewTemp = (TextView) rootView.findViewById(R.id.main_temp);
//        ImageView imageViewMain = (ImageView) rootView.findViewById(R.id.main);
//
//        TextView textViewhumidity = (TextView) rootView.findViewById(R.id.humidity);
//        TextView textViewPressure = (TextView) rootView.findViewById(R.id.pressure);
//        TextView textViewChill = (TextView) rootView.findViewById(R.id.chill);
//        TextView textViewSpeed = (TextView) rootView.findViewById(R.id.speed);
//
//        TextView textViewMorning = (TextView) rootView.findViewById(R.id.morning);
//        TextView textViewSunrise = (TextView) rootView.findViewById(R.id.sunrise);
//        TextView textViewNoon = (TextView) rootView.findViewById(R.id.noon);
//        TextView textViewSunset = (TextView) rootView.findViewById(R.id.sunset);
//        TextView textViewWest= (TextView) rootView.findViewById(R.id.west);
//        TextView textVieMidNight= (TextView) rootView.findViewById(R.id.midnight);
//
//
//        TextView textViewdate = (TextView) rootView.findViewById(R.id.latest_update_date);
//        TextView textViewTime = (TextView) rootView.findViewById(R.id.latest_update_time);
//
//
////        TextView textViewhumidityLabla = (TextView) rootView.findViewById(R.id.currentDate);
//
//
//
//        textViewCity.setTypeface(tp);
//        textViewTemp.setTypeface(tp1);
//        textViewdate.setTypeface(tp1);
//
//
//        ConverTool converTool = new ConverTool(getActivity());
//
//        textViewDayName.setText(dateInfo.getDayName());
//        textViewDay.setText(dateInfo.getDay());
//        textViewMonthName.setText(dateInfo.getMonthName());
//        textViewYear.setText(dateInfo.getYear());
//
//        cityName = cityInfo.getCityName();
//        textViewCity.setText(cityName);
//        textViewTemp.setText(status.getTemp());
//        imageViewMain.setImageResource(converTool.convertCode(status.getMain()));
//
//
//        textViewChill.setText(status.getChill());
//        textViewhumidity.setText(status.getHumidity());
//        textViewPressure.setText(status.getPresure());
//        textViewSpeed.setText(status.getSpeed());
//
//
//
//        textViewMorning.setText(prayerTimes.getMorning());
//        textViewSunrise.setText(prayerTimes.getSunrise());
//        textViewNoon.setText(prayerTimes.getNoon());
//        textViewSunset.setText(prayerTimes.getSunset());
//        textViewWest.setText(prayerTimes.getWest());
//        textVieMidNight.setText(prayerTimes.getMidnight());
//
//
//        textViewdate.setText(dateInfo.getDate());
//        textViewTime.setText(dateInfo.getTime());
//
//        cityInfo.getCityPicUrl();
//
//        String url = cityInfo.getCityPicUrl();
//        imageDownload download = new imageDownload();
//        download.execute(url);
//
//
//        // Inflate the layout for this fragment
//        return rootView;
//
//    }
//
//    private class imageDownload extends AsyncTask<String, Void, File> {
//
//        @Override
//        protected File doInBackground(String... params) {
//
//            String link = params[0];
//            Bitmap bitmap = null;
//            File file = null;
//            try {
//                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),cityName+ "image.jpeg");
//                if(file.exists()){
//                    Log.d("fileExists", file.getAbsolutePath());
//                }else {
//                    URL url = new URL(link);
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    int responseCode = httpURLConnection.getResponseCode();
//                    Log.d("responseCode", "" + responseCode);
//
//                    InputStream inputStream = httpURLConnection.getInputStream();
////                bitmap = BitmapFactory.decodeStream(inputStream);
//
//                    file.createNewFile();
//
//                    FileOutputStream outputStream = new FileOutputStream(file);
//
//                    byte[] buffer = new byte[1024];
//                    int numberOfBytes = 0;
//                    while ((numberOfBytes = inputStream.read(buffer)) != -1) {
//
//                        outputStream.write(buffer, 0, numberOfBytes);
//                        Log.d("=====>", "" + numberOfBytes);
//                    }
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return file;
//        }
//
//        @Override
//        protected void onPostExecute(File file) {
//            super.onPostExecute(file);
////            imageView.setImageURI(Uri.fromFile(file));
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
//            Rl.setBackground(drawable);
//
//        }
//
//    }
//}
