package com.example.practice_0507_recyclerview_databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

public class BaseActivity extends AppCompatActivity {
	protected ViewModelProvider.AndroidViewModelFactory viewModelFactory;
	protected ViewModelStore viewModelStore = new ViewModelStore();

	protected <T extends ViewModel> BaseViewModel getBaseViewModel(Class<T> modelClass){
		if (viewModelFactory == null) {
			viewModelFactory =
				ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
		}
		BaseViewModel viewModel = (BaseViewModel)new ViewModelProvider(this, viewModelFactory).get(modelClass);
		return viewModel;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(viewModelStore!=null){
			viewModelStore.clear();
		}
	}
}
