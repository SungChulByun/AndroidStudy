package com.example.viewpager_practice_byun.SearchType;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Image_Result {
    @SerializedName("lastBuildDate")
    public String lastBuildDate;
    @SerializedName("total")
    public Integer total;
    @SerializedName("start")
    public Integer start;
    @SerializedName("display")
    public Integer display;
    @SerializedName("items")
    public List<imageDetail> items = new ArrayList();

    public class imageDetail {

        @SerializedName("title")
        public String title;
        @SerializedName("link")
        public String link;
        @SerializedName("thumbnail")
        public String thumbnail;
        @SerializedName("sizeheight")
        public String sizeheight;
        @SerializedName("sizewidth")
        public String sizewidth;
    }
}