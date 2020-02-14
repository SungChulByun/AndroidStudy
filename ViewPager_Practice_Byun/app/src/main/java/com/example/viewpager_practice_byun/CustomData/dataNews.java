package com.example.viewpager_practice_byun.CustomData;

public class dataNews extends customData{
    private final int TYPE_IMAGE = 9999;
    private final int TYPE_BLOG = 9998;
    private final int TYPE_NEWS = 9997;

    public dataNews(String t, String des, String date, String name, String link, String showAll, int type) {
        super(t, des, date, name, link, showAll, type);
    }

    public dataNews(String title, String description, String date, String link){
        super();
        this.setTitle(title);
        this.setDescription(description);
        this.setDate(date);
        this.setLink(link);
        this.setType(TYPE_NEWS);
    }
}
