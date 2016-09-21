package com.hp.alirezas.weather01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hp.alirezas.weather01.db.Constants;
import com.hp.alirezas.weather01.service.UpdateInfoService;
import com.hp.alirezas.weather01.utility.Helper;
import com.hp.alirezas.weather01.utility.NetCheck;

public class Splash extends AppCompatActivity {



    IntentFilter filter;
    Intent intentService;
    boolean serviceIsRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splsh);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        final TextView textView = (TextView) findViewById(R.id.text);
        Typeface tp = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/omid_regular.ttf");
        textView.setTypeface(tp);

        serviceIsRun = false;


        initViews();
        new Thread(new Runnable() {
            @Override
            public void run() {
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      imageView.animate().alpha(1).translationYBy(-50).setDuration(1000);
                      textView.animate().scaleX(1.1f).scaleY(1.1f).setDuration(1000);

                  }
              });
            }
        }).start();





    }

    @Override
    protected void onResume() {
        super.onResume();


        if (!NetCheck.checkNetworkIsOn(this)) {
            Helper.showToast(this, "please check your network connection");
            senToMainActivity();

        } else {
            intentService = new Intent(this, UpdateInfoService.class);
            startService(intentService);
            serviceIsRun = true;
        }
        filter = new IntentFilter();
        filter.addAction(Constants.ACTION_FILTER_DOWNLOAD_FAILED);
        filter.addAction(Constants.ACTION_FILTER_FINISH_DOWNLOAD);


        registerReceiver(myReceiver, filter);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().compareTo(Constants.ACTION_FILTER_DOWNLOAD_FAILED) == 0) {
                Helper.showToast(Splash.this,"ارتباط با سرور برقرار نشد...");
            }
            unregisterReceiver(myReceiver);
            stopService(intentService);
            serviceIsRun = false;
            senToMainActivity();
        }
    };

    public void senToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    public void initViews() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceIsRun) {
            stopService(intentService);
        }

    }
}

