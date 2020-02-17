package com.example.viewpager_practice_byun.CustomData;

public class dataHistory extends customData{
    private final int TYPE_IMAGE = 9999;
    private final int TYPE_BLOG = 9998;
    private final int TYPE_NEWS = 9997;
    private final int TYPE_HISTORY = 9996;

    public dataHistory(String t, String des, String date, String name, String link, String showAll, int type) {
        super(t, des, date, name, link, showAll, type);
    }

    public dataHistory(String title, String nickname, String link){
        super();
        this.setTitle(title);
        this.setType(TYPE_HISTORY);
        this.setLink(link);
        this.setName(nickname);
    }
}
