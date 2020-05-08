package com.example.practice_0507_recyclerview_databinding;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {
	private static final String TAG = "sungchul";

	protected List<Object> itemList;
	protected boolean[] isSelected;

	public BaseAdapter() {
		itemList = new ArrayList<>();
	}

	@NonNull
	@Override
	public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return null;
	}

	@Override
	public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return 0;
	}

	public List<Object> getItemList() {
		return itemList;
	}

	public void setItemList(List<Object> itemList) {
		this.itemList = itemList;
		isSelected = new boolean[itemList.size()];
		notifyDataSetChanged();
		Log.d(TAG, "onChanged: notifyChanged");
	}

}
