package com.demo.weatherfy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.weatherfy.R;
import com.demo.weatherfy.activities.AllDetailsActivity;
import com.demo.weatherfy.activities.HomeScreenActivity;
import com.demo.weatherfy.models.CityWeatherDetails;
import com.demo.weatherfy.utils.LoadingBar;
import com.google.gson.Gson;

import java.util.List;
/*
 * Adapter to handle recycler view
 * */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    List<CityWeatherDetails> weatherList;
    CityWeatherDetails currentDetails;
    Context context;

    public WeatherAdapter(List<CityWeatherDetails> weatherList, CityWeatherDetails currentDetails, Context context) {
        this.weatherList = weatherList;
        this.currentDetails = currentDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.hourly_weather_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.hourlyFeelsLike.setText("Feels Like "+weatherList.get(position).getFeelsLike()+ " °C");
        holder.hourlyTemperature.setText("Tem "+weatherList.get(position).getCurrentTemp()+" °C");

        if(weatherList.get(position).getDetailedCurrentDescription().toLowerCase().contains("cloud"))
            holder.imageView.setBackgroundResource(R.drawable.ic_undraw_autumn_re_rwy0);
        else
            holder.imageView.setBackgroundResource(R.drawable.ic_undraw_sunny_day_re_gyxr);

        /*Setting Colour*/

       if(position%5==0){
           holder.constraintLayoutOfItems.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.purple_200)));
       }else if(position%4==0){
           holder.constraintLayoutOfItems.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.purple_500)));
       }else if(position%3==0){
           holder.constraintLayoutOfItems.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.teal_200)));
       }else{
           holder.constraintLayoutOfItems.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.teal_700)));
       }

        Boolean cloudContains = currentDetails.getDetailedCurrentDescription().toLowerCase().contains("cloud") ;
        Boolean exactNameFound = currentDetails.getExactNameFound();
        HomeScreenActivity.SetCurrentDetails(currentDetails.getCurrentTemp(),currentDetails.getFeelsLike(),currentDetails.getCityName(),cloudContains,
                currentDetails.getCurrentHumidity(),currentDetails.getWindSpeed(),exactNameFound);
        LoadingBar.LoadingTime(context,context.getResources(),false);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AllDetailsActivity.class);
                CityWeatherDetails passingDetails = weatherList.get(position);
                Gson gson = new Gson();
                String myJson = gson.toJson(passingDetails);
                intent.putExtra("PassDetailsAsJson",myJson);
                view.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView hourlyTemperature;
        TextView hourlyFeelsLike;
        ConstraintLayout constraintLayoutOfItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewWeather);
            hourlyTemperature = itemView.findViewById(R.id.hourly_DescriptionTextView);
            hourlyFeelsLike = itemView.findViewById(R.id.feelsLikeTextView);
            constraintLayoutOfItems=itemView.findViewById(R.id.layout_hourly_constraint);
        }

    }
}
