package com.example.test_recyclerview_databiinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.example.test_recyclerview_databiinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	private MemoViewModel viewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		init();
	}

	public void init() {
		viewModel = new MemoViewModel();
		viewModel.onCreate();

		binding.setViewmodel(viewModel);
		binding.setLifecycleOwner(this);
	}
}
