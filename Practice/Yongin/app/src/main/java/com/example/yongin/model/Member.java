package com.example.yongin.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.yongin.BR;

public class Member extends BaseObservable {
    private String name;
    private boolean selected;

    public Member(String name) {
        this.name = name;
        selected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    public void changeSelected(){
        selected = !selected;
        notifyPropertyChanged(BR.selected);
    }
}
