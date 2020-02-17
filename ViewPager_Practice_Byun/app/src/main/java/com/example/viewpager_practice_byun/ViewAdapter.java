package com.example.viewpager_practice_byun;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ViewAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> list;

    public ViewAdapter(LayoutInflater inflater, ArrayList<String> list){
        this.inflater = inflater;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view;
        view = inflater.inflate(R.layout.image_slide, null);
        ImageView img = view.findViewById(R.id.slide_image);
        Glide.with(view).load(list.get(position)).into(img);

        container.addView(view);
        return view;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
