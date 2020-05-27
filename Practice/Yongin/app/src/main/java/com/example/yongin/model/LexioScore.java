package com.example.yongin.model;

import java.util.List;

public class LexioScore {
    private int[] scoreList;

    public LexioScore(int size) {
        scoreList = new int[size];
    }

    public int[] getScoreList() {
        return scoreList;
    }

    public void setScoreList(int[] scoreList) {
        this.scoreList = scoreList;
    }
}
