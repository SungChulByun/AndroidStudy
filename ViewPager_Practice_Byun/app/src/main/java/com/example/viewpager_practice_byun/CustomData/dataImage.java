package com.example.viewpager_practice_byun.CustomData;


import com.example.viewpager_practice_byun.SearchType.Image_Result;

import java.util.ArrayList;
import java.util.List;

public class dataImage extends customData {

    private final int TYPE_IMAGE = 9999;
    private final int TYPE_BLOG = 9998;
    private final int TYPE_NEWS = 9997;
    private final int TYPE_HISTORY = 9996;

    private ArrayList<String> imageList;

    public ArrayList<String> getImageList(){
        return this.imageList;
    }

    public dataImage(String t, String des, String date, String name, String link, String showAll, int type) {
        super(t, des, date, name, link, showAll, type);
    }

    public dataImage(String link, String name, List<Image_Result.imageDetail> result){
        super();
        this.setLink(link);
        this.setName(name);
        this.setType(TYPE_IMAGE);
        imageList = new ArrayList<>();
        for(int i=0;i<result.size();i++){
            imageList.add(result.get(i).thumbnail);
        }
    }
}
