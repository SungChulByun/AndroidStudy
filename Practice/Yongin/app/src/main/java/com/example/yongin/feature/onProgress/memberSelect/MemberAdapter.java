package com.example.yongin.feature.onProgress.memberSelect;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.yongin.R;
import com.example.yongin.databinding.HolderMemberBinding;
import com.example.yongin.model.Member;
import com.example.yongin.recyclerView.BaseViewHolder;
import com.example.yongin.recyclerView.MyAdapter;

import static com.example.yongin.helper.Values.POSITION_TAG;

public class MemberAdapter extends MyAdapter<MemberHolder> {

    @NonNull
    @Override
    public MemberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HolderMemberBinding binding = DataBindingUtil.inflate(inflater, R.layout.holder_member, parent, false);
        return new MemberHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberHolder holder, int position) {
        holder.bind((Member) itemList.get(position));
        HolderMemberBinding binding = holder.binding;
        binding.getRoot().setTag(POSITION_TAG, position);
        binding.getRoot().setOnClickListener(itemClickListener);
    }
}

class MemberHolder extends BaseViewHolder {
    public HolderMemberBinding binding;

    public MemberHolder(@NonNull HolderMemberBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Member member) {
        binding.setMember(member);
        binding.executePendingBindings();
    }
}

