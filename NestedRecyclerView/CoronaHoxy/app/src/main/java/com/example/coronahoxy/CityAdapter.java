package com.example.coronahoxy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityHolder> {
    private ArrayList<String[]> cityList;

    public CityAdapter(){
        cityList = new ArrayList<>();
    }
    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, int position) {
        holder.setData(cityList.get(position));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public void addItem(String[] data){
        cityList.add(data);
    }
}


class CityHolder extends RecyclerView.ViewHolder{
    TextView name;
    TextView patient;
    TextView death;

    //Todo "완치" 도 넣어야 함

    public CityHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.city_name);
        patient = itemView.findViewById(R.id.city_patient);
        death = itemView.findViewById(R.id.city_death);
    }

    public void setData(String[] data){
        name.setText(data[0]);
        patient.setText(data[1]);
        death.setText(data[2]);
    }
}