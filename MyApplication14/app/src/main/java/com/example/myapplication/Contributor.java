package com.example.myapplication;

import android.os.AsyncTask;
import android.widget.TextView;

public class Contributor {
    String login;
    String html_url;

    int contributions;

    @Override
    public String toString() {
        return login + " (" + contributions + ")";
    }

}