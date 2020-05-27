package com.example.yongin.feature.onProgress.memberSelect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.yongin.R;
import com.example.yongin.databinding.HolderMemberSquareBinding;
import com.example.yongin.model.Member;
import com.example.yongin.recyclerView.BaseViewHolder;
import com.example.yongin.recyclerView.MyAdapter;

import static com.example.yongin.helper.Values.POSITION_TAG;

public class MemberSquareAdapter extends MyAdapter<BaseViewHolder> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            HolderMemberSquareBinding binding = DataBindingUtil.inflate(inflater, R.layout.holder_member_square, parent, false);
            return new MemberSquareHolder(binding);
        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new BlankHolder(inflater.inflate(R.layout.holder_blank_for_round_count, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if(position!=0){
            MemberSquareHolder memberSquareHolder = (MemberSquareHolder) holder;
            memberSquareHolder.bind((Member) itemList.get(position));
            HolderMemberSquareBinding binding = memberSquareHolder.binding;
            binding.getRoot().setTag(POSITION_TAG, position);
            binding.getRoot().setOnClickListener(itemClickListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 0;
        }
    }
}

class MemberSquareHolder extends BaseViewHolder {
    public HolderMemberSquareBinding binding;

    public MemberSquareHolder(@NonNull HolderMemberSquareBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Member member) {
        binding.setMember(member);
        binding.executePendingBindings();
    }
}

class BlankHolder extends BaseViewHolder {
    public BlankHolder(@NonNull View itemView) {
        super(itemView);
    }
}

