package com.example.viewpager_practice_byun.ImagePage;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.viewpager_practice_byun.R;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<GridImageHolder> {

    private List<String> mList;
    private LayoutInflater inflater;

    private static final int MY_KEY = 0xFFFFFFF1;

    public ImageAdapter(Context context){
        this.mList = new ArrayList<String>();
        this.inflater = LayoutInflater.from(context);
    }
    private OnGridItemClickListener listener;

    public interface OnGridItemClickListener{
        void OnGridItemClick(int position);
    }

    public void setOnGridItemClickListenr(OnGridItemClickListener listener){
        this.listener = listener;
    }



    private View.OnClickListener myGridItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(MY_KEY);
            listener.OnGridItemClick(position);
        }
    };


    @NonNull
    @Override
    public GridImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.grid_image, parent, false);
        return new GridImageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GridImageHolder holder, int position) {
        holder.setData(mList.get(position));
        holder.itemView.setTag(MY_KEY, position);
        holder.itemView.setOnClickListener(myGridItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addItem(String url){
        mList.add(url);
    }

    public void setList(ArrayList<String> list){
        this.mList = list;
    }
}

class GridImageHolder extends RecyclerView.ViewHolder{
    ImageView image;

    public GridImageHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.grid_image);
    }

    public void setData(String url){
        MultiTransformation multiOption = new MultiTransformation(new FitCenter(),new RoundedCorners(20) );
        Glide.with(this.itemView.getContext()).load(url).override(150, 150).apply(RequestOptions.bitmapTransform(multiOption)).fitCenter().into(image);
    }
}