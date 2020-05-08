package com.example.practice_0507_recyclerview_databinding;

import java.util.ArrayList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import com.example.practice_0507_recyclerview_databinding.databinding.HolderSampleBinding;

public class SampleAdapter extends BaseAdapter {
	private static final String TAG = "sungchul";

	public SampleAdapter() {
		itemList = new ArrayList<>();
	}

	@NonNull
	@Override
	public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		HolderSampleBinding binding =
			DataBindingUtil.inflate(layoutInflater, R.layout.holder_sample, parent, false);

		return new SampleHolder(binding);
	}

	@Override
	public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
		SampleHolder holder = (SampleHolder)baseViewHolder;
		Sample sample = (Sample)itemList.get(position);
		Log.d(TAG, "Sample : position : " + position + ", name : " + sample.getSampleName() + ", email : " + sample.getSampleEmail());
		holder.bind(sample);
	}
}

class SampleHolder extends BaseViewHolder {
	HolderSampleBinding binding;

	public SampleHolder(@NonNull HolderSampleBinding binding) {
		super(binding.getRoot());
		this.binding = binding;
	}

	public void bind(Sample sample) {
		binding.setSample(sample);
		binding.executePendingBindings();
	}
}
