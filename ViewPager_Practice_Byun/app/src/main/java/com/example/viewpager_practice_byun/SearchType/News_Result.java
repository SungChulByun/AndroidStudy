package com.example.viewpager_practice_byun.SearchType;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class News_Result {
    @SerializedName("lastBuildDate")
    public String lastBuildDate;
    @SerializedName("total")
    public Integer total;
    @SerializedName("start")
    public Integer start;
    @SerializedName("display")
    public Integer display;
    @SerializedName("items")
    public List<newsDetail> items = new ArrayList();

    public class newsDetail {

        @SerializedName("title")
        public String title;
        @SerializedName("originallink")
        public String originallink;
        @SerializedName("link")
        public String link;
        @SerializedName("description")
        public String description;
        @SerializedName("pubDate")
        public String pubDate;
    }
}
