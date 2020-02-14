package com.example.viewpager_practice_byun.SearchType;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Blog_Result {
    @SerializedName("lastBuildDate")
    public String lastBuildDate;
    @SerializedName("total")
    public Integer total;
    @SerializedName("start")
    public Integer start;
    @SerializedName("display")
    public Integer display;
    @SerializedName("items")
    public List<blogDetail> items = new ArrayList();

    public class blogDetail {

        @SerializedName("title")
        public String title;
        @SerializedName("link")
        public String link;
        @SerializedName("description")
        public String description;
        @SerializedName("bloggername")
        public String bloggername;
        @SerializedName("bloggerlink")
        public String bloggerlink;
        @SerializedName("postdate")
        public String postdate;

    }
}