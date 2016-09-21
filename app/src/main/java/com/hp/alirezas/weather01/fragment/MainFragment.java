package com.hp.alirezas.weather01.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hp.alirezas.weather01.MainActivity;
import com.hp.alirezas.weather01.R;
import com.hp.alirezas.weather01.adapters.SavedListAdapter;
import com.hp.alirezas.weather01.db.Constants;
import com.hp.alirezas.weather01.info.CityInfo;
import com.hp.alirezas.weather01.utility.MyApp;

import java.util.List;

/**
 */
public class MainFragment extends Fragment {
    SharedPreferences setting;
    ViewPager viewPager;
    FragmentManager fragManager;
    BottomSheetBehavior bottomSheetBehavior;
    RecyclerView savedListRecyclerView;
    SavedListAdapter listAdapter;
    LinearLayoutManager layoutManager;
    IntentFilter filter;


    boolean serviceIsRun;


    List<CityInfo> list;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        serviceIsRun = false;

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        list = MyApp.getOperatin().getSavedCites();


        //init bottom sheet
        final View bottomSheet = v.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(50);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        //set bottomSheet icon
        ImageView listIconImageView = (ImageView) v.findViewById(R.id.list_icon);
        listIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });


        //set bottom  sheet list in recycler view
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        savedListRecyclerView = (RecyclerView) v.findViewById(R.id.saved_city_list_recycler);
        savedListRecyclerView.setLayoutManager(layoutManager);

        listAdapter = new SavedListAdapter(list, new SavedListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                viewPager.setCurrentItem(position);
            }
        });
        savedListRecyclerView.setAdapter(listAdapter);


        //set swipe delete for bottom sheet list
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int pos = viewHolder.getAdapterPosition();
                if (pos > 0) {
                    CityInfo cityInfo = list.get(pos);
                    MyApp.getOperatin().deleteCityInfo(cityInfo.getCityId());
                    MyApp.getOperatin().deleteDateInfo(cityInfo.getCityId());
                    MyApp.getOperatin().deleteForecast(cityInfo.getCityId());
                    MyApp.getOperatin().deletePrayerTimes(cityInfo.getCityId());
                    MyApp.getOperatin().deleteStatus(cityInfo.getCityId());
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                } else {
                }


            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(savedListRecyclerView);


        //set view pager on frag manager
        fragManager = new FragmentManager(getActivity().getSupportFragmentManager(), list);
        viewPager = (ViewPager) v.findViewById(R.id.cityPager);
        viewPager.setAdapter(fragManager);



        //check for which page on pager
        if (getTag() != null) {
            if (getTag().equals("true")) {
                viewPager.setCurrentItem(MyApp.getOperatin().getSavedCites().size());
            }

        }
        return v;


    }


    @Override
    public void onStop() {
        super.onStop();
        filter = new IntentFilter();
        filter.addAction(Constants.ACTION_FILTER_REFRESH_VIEW_PAGER);
        getActivity().registerReceiver(receiver, filter);

    }




    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.ACTION_FILTER_REFRESH_VIEW_PAGER)) {
                fragManager.notifyDataSetChanged();
                viewPager.invalidate();
//                Helper.showToast(getContext(),"...بروزرسانی انجام شد...");
            } else if (intent.getAction().equals(Constants.ACTION_FILTER_WAIT_DOWNLOAD)) {
            } else if (intent.getAction().equals(Constants.ACTION_FILTER_FINISH_DOWNLOAD)) {
            }
        }
    };

}






