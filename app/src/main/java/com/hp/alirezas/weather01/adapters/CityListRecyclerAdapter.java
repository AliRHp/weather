package com.hp.alirezas.weather01.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hp.alirezas.weather01.R;
import com.hp.alirezas.weather01.info.City;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alireza s on 7/13/2016.
 */
public class CityListRecyclerAdapter extends RecyclerView.Adapter<CityListRecyclerAdapter.CityListViewHolder> {


    List<City> list;
    Context context;
    OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public CityListRecyclerAdapter(List<City> list, OnItemClickListener listener) {
        this.listener = listener;
        this.list = list;
    }

    @Override
    public CityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_row, parent, false);
        CityListViewHolder holder = new CityListViewHolder(view, listener);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(CityListViewHolder holder, int position) {

        holder.cityNameText.setText(list.get(position).getCityFaName());
        if (list.get(position).getCityPicUrl() != null) {
            String picUrl = "http://malipour.ir/api/manager/uploads/" + list.get(position).getCityPicUrl();
            Glide.with(context)
                    .load(picUrl)
                    .placeholder(R.drawable.city_defult_2)
                    .into(holder.cityImageView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CityListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cityNameText;
        ImageView cityImageView;
        OnItemClickListener listener;
        RelativeLayout layout ;

        public CityListViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            CityListViewHolder.this.listener = listener;
            cityNameText = (TextView) itemView.findViewById(R.id.city_name_text);
            cityImageView = (ImageView) itemView.findViewById(R.id.city_image_view);
            layout = (RelativeLayout) itemView.findViewById(R.id.cityLayout);

            layout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v,getLayoutPosition());
        }
    }
}
