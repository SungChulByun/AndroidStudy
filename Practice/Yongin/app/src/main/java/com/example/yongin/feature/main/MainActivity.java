package com.example.yongin.feature.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.yongin.R;
import com.example.yongin.databinding.ActivityMainBinding;
import com.example.yongin.feature.onProgress.ProgressActivity;

import static com.example.yongin.helper.Values.LEXIO;
import static com.example.yongin.helper.Values.POKER;
import static com.example.yongin.helper.Values.TYPE;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setFragment();
        getLiveDataForAction();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void setFragment() {
        MainFragment fragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commit();
    }

    private void getLiveDataForAction() {
        viewModel.getLiveDataForButtonClick().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String type) {
                switch (type) {
                    case LEXIO:
                        startProgressActivity(LEXIO);
                        break;

                    case POKER:
                        startProgressActivity(POKER);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void startProgressActivity(String type){
        Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
        intent.putExtra(TYPE, type);
        startActivity(intent);
    }
}
