package com.hp.alirezas.weather01.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hp.alirezas.weather01.R;
import com.hp.alirezas.weather01.info.CityInfo;

import java.util.List;

/**
 * Created by alireza s on 7/22/2016.
 */
public class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.SavedListHolder> {


    SavedListAdapter.OnItemClickListener listener;
    List<CityInfo> list;


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public SavedListAdapter(List<CityInfo> list, SavedListAdapter.OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public SavedListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_saved_row, parent, false);
        SavedListHolder holder = new SavedListHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(SavedListHolder holder, int position) {
        holder.savedCityNameTextView.setText(list.get(position).getCityName());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SavedListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView savedCityNameTextView;
        OnItemClickListener listener;

        public SavedListHolder(View v, OnItemClickListener listener) {
            super(v);
            this.listener = listener;
            savedCityNameTextView = (TextView) v.findViewById(R.id.saved_city_name);
            savedCityNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getLayoutPosition());
        }
    }
}
