package com.hp.alirezas.weather01.fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hp.alirezas.weather01.MainActivity;
import com.hp.alirezas.weather01.R;
import com.hp.alirezas.weather01.adapters.CityListRecyclerAdapter;
import com.hp.alirezas.weather01.db.Constants;
import com.hp.alirezas.weather01.info.City;
import com.hp.alirezas.weather01.service.UpdateInfoService;
import com.hp.alirezas.weather01.utility.Helper;
import com.hp.alirezas.weather01.utility.MyApp;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityListFragment extends Fragment implements TextWatcher {
    IntentFilter filter;
    Intent intentService;

    RecyclerView listRecyclerView;
    CityListRecyclerAdapter listRecyclerAdapter;
    GridLayoutManager layoutManager;
    ProgressDialog dLProgressDialog;
    List<City> list;

    EditText searchEditText;

    boolean serviceFlag;
    boolean serviceIsRun;

    public CityListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city_list, container, false);

        serviceFlag = false;
        serviceIsRun = false;
        searchEditText = (EditText) v.findViewById(R.id.search_city_text);
        searchEditText.addTextChangedListener(this);

        //start download list of cites
        intentService = new Intent(getActivity(), UpdateInfoService.class);
        intentService.putExtra(Constants.SERVICE_MODE, Constants.MODE_GET_CITY_LIST);
        getActivity().startService(intentService);
        serviceFlag = true;

        //set recycler configuration
        listRecyclerView = (RecyclerView) v.findViewById(R.id.city_list_recycle_view);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        listRecyclerView.setLayoutManager(layoutManager);

        //start show progress for download list
        dLProgressDialog = new ProgressDialog(getActivity());
        dLProgressDialog.setMessage("لطفا کمی صبر کنید ...");
        dLProgressDialog.setCancelable(false);
        dLProgressDialog.show();
        //init recyclerView

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        filter = new IntentFilter();
        filter.addAction(Constants.ACTION_FILTER_FINISH_DOWNLOAD);
        filter.addAction(Constants.ACTION_FILTER_FINISH_ADD_CITY);
        filter.addAction(Constants.ACTION_FILTER_DOWNLOAD_FAILED);
        getActivity().registerReceiver(myReciver, filter);

        getActivity().registerReceiver(myReciver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (serviceFlag) {
            if (serviceIsRun) {
                getActivity().stopService(intentService);
                getActivity().unregisterReceiver(myReciver);
            }
        }
    }


    private BroadcastReceiver myReciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            getActivity().unregisterReceiver(myReciver);
            getActivity().stopService(intentService);

            if (dLProgressDialog.isShowing()) {
                dLProgressDialog.dismiss();
            }

            if (intent.getAction() == Constants.ACTION_FILTER_FINISH_DOWNLOAD) {
                list = MyApp.getOperatin().getAllCityList();
                listRecyclerAdapter = new CityListRecyclerAdapter(list, new CityListRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        intentService = new Intent(getActivity(), UpdateInfoService.class);
                        City city = list.get(position);
                        double lat = Double.parseDouble(city.getCityLat());
                        double lon = Double.parseDouble(city.getCityLon());
                        intentService.putExtra(Constants.CITY_LAT, lat);
                        intentService.putExtra(Constants.CITY_LON, lon);
                        intentService.putExtra(Constants.SERVICE_MODE, Constants.MODE_ADD_CITY);

                        getActivity().startService(intentService);
                        getActivity().registerReceiver(myReciver, filter);
                        dLProgressDialog.setCancelable(true);
                        dLProgressDialog.show();

                    }
                });
                listRecyclerView.setVisibility(View.GONE);
                listRecyclerView.setAdapter(listRecyclerAdapter);
                serviceIsRun = false;

            } else if (intent.getAction() == Constants.ACTION_FILTER_FINISH_ADD_CITY) {
                Intent intent1 = new Intent(getActivity(), MainActivity.class);
                intent1.putExtra(Constants.MODE_ADD_CITY, true);
                startActivity(intent1);
                serviceIsRun = false;
            } else if (intent.getAction().equals(Constants.ACTION_FILTER_DOWNLOAD_FAILED)) {
                Helper.showToast(getActivity(), "ارتباط با سرور برقرار نشد ");
                onDestroy();
            } else if (intent.getAction() == Constants.ACTION_FILTER_WAIT_DOWNLOAD) {
                serviceIsRun = true;
            }


        }
    };

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (count > 1) {
            List<City> resultCities = MyApp.getOperatin().searchCites(s.toString());
            list.clear();
            list.addAll(resultCities);
            listRecyclerView.setVisibility(View.VISIBLE);
            listRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}

