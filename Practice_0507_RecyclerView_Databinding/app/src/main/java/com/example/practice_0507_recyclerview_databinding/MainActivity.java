package com.example.practice_0507_recyclerview_databinding;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.practice_0507_recyclerview_databinding.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
	private static final String TAG = "sungchul";

	private BaseRecyclerView recyclerView;
	private MainViewModel viewModel;
	private ActivityMainBinding binding;
	private SampleAdapter adapter;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init(){

		viewModel = (MainViewModel) getBaseViewModel(MainViewModel.class);

		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		binding.setViewModel(viewModel);
		binding.setLifecycleOwner(this);

		adapter = new SampleAdapter();
		recyclerView = findViewById(R.id.sample_recyclerView);
		recyclerView.setAdapter(adapter);
		Log.d("TAG", "init 1 : current time : " + System.currentTimeMillis());
		Log.d("TAG", "init 2 : current time : " + System.currentTimeMillis());

		getLiveDataForAction();
	}

	private void getLiveDataForAction(){
		viewModel.getLiveDataForAction().observe(this, new Observer<Integer>() {
			@Override
			public void onChanged(Integer type) {
				if(type == 0){
					recyclerView.getAdapter().setItemList(viewModel.getSampleList());
				}
			}
		});
	}
}
