package com.hp.alirezas.weather01.adapters;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hp.alirezas.weather01.R;

import java.util.List;

/**
 * Created by alireza s on 7/11/2016.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NavDrawerHolder> {


    OnItemClickListener listener;

    public NavigationDrawerAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }


    public interface OnItemClickListener  {
        void OnItemClick(View v,int position);


    }
    @Override
    public NavDrawerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_row_view, parent, false);
        NavDrawerHolder holder = new NavDrawerHolder(v,listener);
        return holder;
    }



    @Override
    public void onBindViewHolder(NavDrawerHolder holder, int position) {




        switch (position) {
            case (0):
                holder.itemNameTextView.setText("بروزرسانی");
                holder.itemIconImage.setImageResource(R.drawable.ic_menu_manage);
                break;
            case (1):
                holder.itemNameTextView.setText("لیست شهرها");
                holder.itemIconImage.setImageResource(R.drawable.ic_menu_slideshow);
                break;
            case (2):
                holder.itemNameTextView.setText("راهنما");
                holder.itemIconImage.setImageResource(R.drawable.ic_menu_share);
                break;
            case (3):
                holder.itemNameTextView.setText("درباره ما");
                holder.itemIconImage.setImageResource(R.drawable.ic_web);
                break;
            case (4):
                holder.itemNameTextView.setText("خروج");
                holder.itemIconImage.setImageResource(R.drawable.ic_web);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class NavDrawerHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        OnItemClickListener listener;
        TextView itemNameTextView;
        ImageView itemIconImage;
        public NavDrawerHolder(View itemView , OnItemClickListener listener) {
            super(itemView);
            NavDrawerHolder.this.listener = listener;
            itemNameTextView = (TextView) itemView.findViewById(R.id.nav_row_name);
            itemIconImage = (ImageView) itemView.findViewById(R.id.nav_row_icon);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.OnItemClick(v,getLayoutPosition());

        }
    }


}
