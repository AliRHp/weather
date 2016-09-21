package com.hp.alirezas.weather01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.hp.alirezas.weather01.adapters.NavigationDrawerAdapter;
import com.hp.alirezas.weather01.db.Constants;
import com.hp.alirezas.weather01.fragment.CityListFragment;
import com.hp.alirezas.weather01.fragment.MainFragment;
import com.hp.alirezas.weather01.service.UpdateInfoService;
import com.hp.alirezas.weather01.utility.Helper;
import com.hp.alirezas.weather01.utility.NetCheck;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FragmentManager mainFragmentManager;
    NavigationDrawerAdapter navAdapter;

    Boolean addMode;

    IntentFilter filter;
    Intent intentService;

    boolean serviceIsRun;
    boolean serviceFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMode = false;
        serviceFlag = false;

        //check for return of add mode for view pager set count
        if (getIntent().hasExtra(Constants.MODE_ADD_CITY)) {
            addMode = getIntent().getExtras().getBoolean(Constants.MODE_ADD_CITY);
        }

        initViews();


    }

    @Override
    protected void onResume() {
        super.onResume();


        filter = new IntentFilter();
        filter.addAction(Constants.ACTION_FILTER_FINISH_DOWNLOAD);
        filter.addAction(Constants.ACTION_FILTER_WAIT_DOWNLOAD);
        filter.addAction(Constants.ACTION_FILTER_DOWNLOAD_FAILED);

        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceFlag == true) {
            if (serviceIsRun) {
                stopService(intentService);
            }
            unregisterReceiver(receiver);
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.ACTION_FILTER_WAIT_DOWNLOAD)) {
                serviceIsRun = true;


            } else if (intent.getAction().equals(Constants.ACTION_FILTER_DOWNLOAD_FAILED)) {
                serviceIsRun = false;
                if (serviceFlag == true) {
                    Helper.showToast(MainActivity.this, "ارتباط با سرور برقرار نشد...");
                    stopService(intentService);
                    serviceFlag = false;
                }
            } else if (intent.getAction().equals(Constants.ACTION_FILTER_FINISH_DOWNLOAD)) {
                serviceIsRun = false;
                if (serviceFlag == true) {
                    Helper.showToast(MainActivity.this, "بروز رسانی انجام شد");
                    stopService(intentService);
                    sendBroadcast(new Intent(Constants.ACTION_FILTER_REFRESH_VIEW_PAGER));
                    serviceFlag = false;
                }
            }


        }
    };

    private void initViews() {
        mainFragmentManager = getSupportFragmentManager();


        MainFragment mainFragment = new MainFragment();
        mainFragmentManager.beginTransaction().replace(R.id.mainFrame, mainFragment, addMode.toString()).commit();
        addMode = false;

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_main);
        RecyclerView navRecycleView = (RecyclerView) findViewById(R.id.nav_recycler_main);

        navAdapter = new NavigationDrawerAdapter(new NavigationDrawerAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(View v, int position) {

                switch (position) {
                    case (0):
                        MainFragment mainFragment = new MainFragment();
                        if (NetCheck.checkNetworkIsOn(MainActivity.this)) {
                            if (serviceIsRun != true) {
                                intentService = new Intent(MainActivity.this, UpdateInfoService.class);
                                startService(new Intent(intentService));
                                serviceFlag = true;
                                mainFragmentManager.beginTransaction().replace(R.id.mainFrame, mainFragment, null).commit();
                            }
                        } else {
                            Helper.showToast(MainActivity.this,"عدم ارتباط با سرور ...");
                            Helper.showToast(MainActivity.this,"لطفا تنظیمات اینترنت خود را چک کنید ");
                        }
                        break;
                    case (1):
                        if (NetCheck.checkNetworkIsOn(MainActivity.this)) {

                            if (serviceIsRun != true) {
                                if (NetCheck.checkNetworkIsOn(MainActivity.this)) {
                                    Fragment listFragment = new CityListFragment();
                                    mainFragmentManager.beginTransaction().replace(R.id.mainFrame, listFragment).commit();
                                }

                            } else {
                                Helper.showToast(MainActivity.this, "در حال بروز رسانی ");
                                Helper.showToast(MainActivity.this, "لطفا کمی صبر کنید...");
                            }
                        } else {
                            Helper.showToast(MainActivity.this, "عدم ارتباط با سرور ...");
                            Helper.showToast(MainActivity.this, "لطفا تنظیمات اینترنت خود را چک کنید ");
                        }

                }
                drawerLayout.closeDrawer(Gravity.RIGHT);


            }
        });
        LinearLayoutManager navLayoutManager = new LinearLayoutManager(MainActivity.this);
        navRecycleView.setLayoutManager(navLayoutManager);
        navRecycleView.setAdapter(navAdapter);


    }


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int j = 0; j < 1000; j++) {
                try {
                    Thread.sleep(1000);
                    Calendar c = Calendar.getInstance();
                    java.util.Date time = c.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm");
                    String date = format.format(time);

                    Log.d("=====>", date + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    });


}

