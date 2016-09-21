package com.hp.alirezas.weather01.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hp.alirezas.weather01.R;
import com.hp.alirezas.weather01.info.Forecast;
import com.hp.alirezas.weather01.utility.ConverTool;

import java.util.List;

/**
 * Created by alireza s on 7/2/2016.
 */
public class ForecastAdapetrRecycle extends RecyclerView.Adapter<ForecastAdapetrRecycle.ForecastVieHolder> {

    private List<Forecast> list;
    Context context;

    public ForecastAdapetrRecycle(List<Forecast> list) {
        this.list = list;

    }


    @Override
    public ForecastVieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_row_view, parent, false);
        ForecastVieHolder holder = new ForecastVieHolder(v);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ForecastVieHolder holder, int position) {

        ConverTool tool = new ConverTool(context);
        Forecast forecast = list.get(position);

        holder.textViewForecastDate.setText(forecast.getDate());
        holder.textViewForecastDayName.setText(tool.convertDayName(forecast.getDay()));
        holder.textViewForecastHigh.setText(forecast.getHigh());
        holder.textViewForecastlow.setText(forecast.getLow());
        holder.imageForecastmain.setImageResource(tool.convertCode(forecast.getMain(), true));

    }



    @Override
    public int getItemCount() {
        return list.size()-1;
    }

    public class ForecastVieHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView textViewForecastDayName;
        TextView textViewForecastDate;
        TextView textViewForecastHigh;
        TextView textViewForecastlow;
        ImageView imageForecastmain;



        public ForecastVieHolder(View v) {
            super(v);

            textViewForecastDate = (TextView) v.findViewById(R.id.forecast_date);
            textViewForecastDayName = (TextView) v.findViewById(R.id.forecast_day_name);
            textViewForecastHigh = (TextView) v.findViewById(R.id.high);
            textViewForecastlow = (TextView) v.findViewById(R.id.low);
            imageForecastmain = (ImageView) v.findViewById(R.id.main_forecast);

        }
    }

}
