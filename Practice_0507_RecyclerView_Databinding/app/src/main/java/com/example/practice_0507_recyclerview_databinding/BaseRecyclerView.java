package com.example.practice_0507_recyclerview_databinding;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class BaseRecyclerView extends RecyclerView {
	private static final String TAG = "sungchul";
	private BaseAdapter adapter;

	public BaseRecyclerView(@NonNull Context context) {
		super(context);
		init();
	}

	public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init(){
		adapter = new BaseAdapter();
	}

	@Override
	public int getChildAdapterPosition(@NonNull View child) {
		return super.getChildAdapterPosition(child);
	}

	public BaseAdapter getAdapter() {
		return adapter;
	}

	@Override
	public void setAdapter(@Nullable Adapter adapter) {
		super.setAdapter(adapter);
	}

	public void notifyChanged(){
		adapter.notifyDataSetChanged();
		Log.d(TAG, "BaseRecyclerView: notifyChanged");
	}
}
