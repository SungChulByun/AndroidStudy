package com.example.yongin.feature.custom;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yongin.model.Member;
import com.example.yongin.recyclerView.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BindAdapter {
    @BindingAdapter({"bind:bindList"})
    public static void bindSelectableListGrid(MyRecyclerView recyclerView, List<?> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 4){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.width = getWidth() / 5;
                lp.height = lp.width;
                return true;
            }
        };

        recyclerView.setLayoutManager(gridLayoutManager);
        if(recyclerView.getAdapter() == null){
            Log.d(TAG, "bind adapter is null ");
        }
        else{
            Log.d(TAG, "bind adapter is not null ");
            Log.d(TAG, "bind adapter each member");
            for(Object object : list){
                Member member = (Member) object;
                Log.d(TAG, "name : " + member.getName());
            }

            recyclerView.getAdapter().setItemList(list);

        }
    }

    @BindingAdapter({"bind:bindLexioMemberList"})
    public static void bindLexioMemberList(MyRecyclerView recyclerView, List<?> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        if(recyclerView.getAdapter() == null){
            Log.d(TAG, "bind adapter is null ");
        }
        else{
            Log.d(TAG, "bind adapter is not null ");
            Log.d(TAG, "bind adapter each member");
            for(Object object : list){
                Member member = (Member) object;
                Log.d(TAG, "name : " + member.getName());
            }

            recyclerView.getAdapter().setItemList(list);

        }
    }
    @BindingAdapter({"bind:bindLexioScoreDetailList"})
    public static void bindLexioScoreList(MyRecyclerView recyclerView, int[] list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        if(recyclerView.getAdapter() == null){
            Log.d(TAG, "bind adapter is null ");
        }
        else{
            Log.d(TAG, "bind adapter is not null ");

            List<Integer> arrayList = new ArrayList<>();
            for(int score : list){
                arrayList.add(score);
            }

            recyclerView.getAdapter().setItemList(arrayList);

        }
    }

    @BindingAdapter({"bind:bindLexioScoreList"})
    public static void bindLexioScoreList(MyRecyclerView recyclerView, List<?> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        if(recyclerView.getAdapter() == null){
            Log.d(TAG, "bind adapter is null ");
        }
        else{
            Log.d(TAG, "bind adapter is not null ");

            recyclerView.getAdapter().setItemList(list);

        }
    }
}
