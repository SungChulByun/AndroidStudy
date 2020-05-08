package com.example.pr_room_livedata_test;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BindAdapter {
	@BindingAdapter("bind:bindAdapter")
	public static void bindAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
		GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 3);
		recyclerView.setLayoutManager(gridLayoutManager);
		recyclerView.setAdapter(adapter);
	}
}
