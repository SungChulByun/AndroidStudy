package com.example.practice_0507_recyclerview_databinding;

import java.util.List;

import android.util.Log;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Binding {
	private static final String TAG = "sungchul";

	@BindingAdapter("app:bindGirdMemberList")
	public static void bindGridMemberList(BaseRecyclerView recyclerView, List<Object> sampleList) {
		LinearLayoutManager linearLayoutManager =
			new LinearLayoutManager(recyclerView.getContext());
		linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
		recyclerView.setLayoutManager(linearLayoutManager);
		Log.d("TAG", "bindGridMemberList: current time : " + System.currentTimeMillis());

		if(recyclerView.getAdapter() == null){
			Log.d(TAG, "bindGridMemberList: adapter = null");
			return;
		}
		else{
			Log.d(TAG, "bindGridMemberList: adapter = not null");
			recyclerView.getAdapter().setItemList(sampleList);
			Log.d(TAG, "bindGridMemberList: list size : " + recyclerView.getAdapter().getItemCount());
			for(Object object : recyclerView.getAdapter().getItemList()){
				Sample sample = (Sample) object;
				Log.d(TAG, "sample : " + sample.getSampleName() + ", " + sample.getSampleEmail());
			}
		}
	}
}
