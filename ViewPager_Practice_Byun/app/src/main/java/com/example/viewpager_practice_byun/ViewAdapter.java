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

public class ViewAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private String[] name = new String[] {"Luffy", "Nami"};

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
        EditText middle = view.findViewById(R.id.search_text);
        final ImageView search = view.findViewById(R.id.search_button);
        middle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if(text.length()>0){
                    search.setImageResource(R.drawable.search_button_green);
                }
                else{
                    search.setImageResource(R.drawable.search_button_normal);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        container.addView(view, position);
        return view;
    }


    @Override
    public int getCount() {
        return 2;
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
