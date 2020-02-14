package com.example.viewpager_practice_byun.CustomData;


public class dataImage extends customData {

    private final int TYPE_IMAGE = 9999;
    private final int TYPE_BLOG = 9998;
    private final int TYPE_NEWS = 9997;

    public dataImage(String t, String des, String date, String name, String link, String showAll, int type) {
        super(t, des, date, name, link, showAll, type);
    }

    public dataImage(String link){
        super();
        this.setLink(link);
        this.setType(TYPE_IMAGE);
    }
}
