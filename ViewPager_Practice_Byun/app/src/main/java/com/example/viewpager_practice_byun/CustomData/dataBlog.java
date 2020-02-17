package com.example.viewpager_practice_byun.CustomData;

public class dataBlog extends customData{
    private final int TYPE_IMAGE = 9999;
    private final int TYPE_BLOG = 9998;
    private final int TYPE_NEWS = 9997;
    private final int TYPE_HISTORY = 9996;

    public dataBlog(String t, String des, String date, String name, String link, String showAll, int type) {
        super(t, des, date, name, link, showAll, type);
    }

    public dataBlog(String title, String description, String blogname, String link){
        super();
        this.setTitle(title);
        this.setDescription(description);
        this.setName(blogname);
        this.setLink(link);
        this.setType(TYPE_BLOG);
    }
}
