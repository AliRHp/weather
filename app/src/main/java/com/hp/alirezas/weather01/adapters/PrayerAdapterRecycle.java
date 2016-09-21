package com.hp.alirezas.weather01.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alireza s on 7/4/2016.
 */
public class PrayerAdapterRecycle extends RecyclerView.Adapter<PrayerAdapterRecycle.PrayerHolder> {
    @Override
    public PrayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PrayerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PrayerHolder extends RecyclerView.ViewHolder {
        public PrayerHolder(View itemView) {
            super(itemView);
        }
    }
}
