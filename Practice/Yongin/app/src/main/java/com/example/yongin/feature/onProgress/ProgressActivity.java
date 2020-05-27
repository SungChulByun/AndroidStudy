package com.example.yongin.feature.onProgress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.yongin.R;
import com.example.yongin.databinding.ActivityProgressBinding;
import com.example.yongin.feature.onProgress.game.LexioFragment;
import com.example.yongin.feature.onProgress.memberSelect.MemberRegisterActivity;
import com.example.yongin.feature.onProgress.memberSelect.MemberSelectFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.yongin.helper.Values.ADD_MEMBER;
import static com.example.yongin.helper.Values.NEXT;
import static com.example.yongin.helper.Values.TYPE;

public class ProgressActivity extends AppCompatActivity {
    private ActivityProgressBinding binding;
    private ProgressViewModel viewModel;
    private ViewPager2 viewPager2;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initViewPager();
        getLiveDataForAction();
    }

    private void init(){
        type = getIntent().getStringExtra(TYPE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_progress);
        viewModel = new ViewModelProvider(this).get(ProgressViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void initViewPager(){
        viewPager2 = binding.progressViewPager;
        PageAdapter adapter = new PageAdapter(this);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(MemberSelectFragment.newInstance(type));
        fragmentList.add(LexioFragment.newInstance());
        adapter.setFragmentList(fragmentList);
        viewPager2.setAdapter(adapter);
        viewPager2.setCurrentItem(0);
    }

    private void getLiveDataForAction(){
        viewModel.getLiveDataForButtonClick().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String type) {
                switch (type){
                    case NEXT:
                        viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
                        break;

                    default:
                        break;
                }
            }
        });
    }
}
