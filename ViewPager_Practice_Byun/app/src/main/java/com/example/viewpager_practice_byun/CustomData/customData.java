package com.example.viewpager_practice_byun.CustomData;

public class customData {
    private String title;
    private String description;
    private String date;
    private String name;
    private String link;
    private String showAll;
    private int type;

    public customData(String t, String des, String date, String name, String link, String showAll, int type){
        this.title=t;
        this.description=des;
        this.date=date;
        this.name=name;
        this.link=link;
        this.showAll=showAll;
        this.type=type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getShowAll() {
        return showAll;
    }

    public void setShowAll(String showAll) {
        this.showAll = showAll;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
