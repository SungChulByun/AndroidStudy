package com.example.yongin.feature.onProgress.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.yongin.R;
import com.example.yongin.databinding.HolderLexioScoreBinding;
import com.example.yongin.model.LexioScore;
import com.example.yongin.recyclerView.BaseViewHolder;
import com.example.yongin.recyclerView.MyAdapter;
import com.example.yongin.recyclerView.MyRecyclerView;

public class LexioScoreAdapter extends MyAdapter<LexioScoreHolder> {
    private MyRecyclerView recyclerView;

    @NonNull
    @Override
    public LexioScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == 0){
            HolderLexioScoreBinding binding = DataBindingUtil.inflate(inflater, R.layout.holder_lexio_score, parent, false);
            return new LexioScoreHolder(binding);
        }
        else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LexioScoreHolder holder, int position) {
        recyclerView = holder.binding.eachRoundScoreRecyclerView;
        LexioScoreDetailAdapter adapter = new LexioScoreDetailAdapter();
        recyclerView.setAdapter(adapter);

        holder.bind((LexioScore) itemList.get(position));

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}

class LexioScoreHolder extends BaseViewHolder{
    HolderLexioScoreBinding binding;
    public LexioScoreHolder(@NonNull HolderLexioScoreBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(LexioScore lexioScore){
        binding.setScoreList(lexioScore);
        binding.executePendingBindings();
    }
}