package com.hp.alirezas.weather01.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hp.alirezas.weather01.info.CityInfo;
import com.hp.alirezas.weather01.utility.MyApp;

import java.util.List;

/**
 * Created by alireza s on 6/26/2016.
 */
public class FragmentManager  extends FragmentStatePagerAdapter {


    List<CityInfo> list;

    public FragmentManager(android.support.v4.app.FragmentManager fm, List<CityInfo> list) {

        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {

        CityInfo cityInfo = list.get(position);
        int cityId = cityInfo.getCityId();


        Fragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putInt(CityFragment.ID_ARG,cityId);

        fragment.setArguments(args);

        return fragment;
    }
    public int getItemPosition(Object item) {

            return POSITION_NONE;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}

