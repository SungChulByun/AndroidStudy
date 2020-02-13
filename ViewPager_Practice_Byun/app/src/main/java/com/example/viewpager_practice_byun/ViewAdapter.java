package com.example.viewpager_practice_byun;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewAdapter extends PagerAdapter {

    private LayoutInflater inflater;

    public ViewAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view;
        view = inflater.inflate(R.layout.child, null);
        ImageView img = view.findViewById(R.id.child_image);
        img.setImageResource(R.drawable.onepiece_01+position);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return 3;
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
