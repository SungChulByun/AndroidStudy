package com.example.coronahoxy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FlowAdapter extends RecyclerView.Adapter<FlowHolder> {
    public static final int LOCATION = 1111;
    public static final int DATE = 1112;
    private ArrayList<Flow> flowList;

    public FlowAdapter(){
        flowList = new ArrayList<>();
    }
    @NonNull
    @Override
    public FlowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == LOCATION){
            return new LocationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_holder, parent, false));
        }
        else{
            return new DateHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.date_holder, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FlowHolder holder, int position) {
        holder.setData(flowList.get(position));
    }

    @Override
    public int getItemCount() {
        return flowList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return flowList.get(position).type;
    }
}

abstract class FlowHolder extends RecyclerView.ViewHolder{
    public FlowHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void setData(Flow flow);
}

class LocationHolder extends FlowHolder{
    TextView date;
    TextView loc;

    public LocationHolder(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.flow_date);
        loc = itemView.findViewById(R.id.flow_location);
    }

    @Override
    public void setData(Flow flow) {
        date.setText(flow.date);
        loc.setText(flow.location);
    }
}

class DateHolder extends FlowHolder{
    TextView date;

    public DateHolder(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.date_date);
    }

    @Override
    public void setData(Flow flow) {
        date.setText(flow.date);
    }
}