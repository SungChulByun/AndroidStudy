package com.example.yongin.feature.onProgress.memberSelect;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.yongin.R;
import com.example.yongin.databinding.ActivityMemberRegisterBinding;

import static com.example.yongin.helper.Values.DUPLICATED;
import static com.example.yongin.helper.Values.FINISH;
import static com.example.yongin.helper.Values.REGISTER;

public class MemberRegisterActivity extends AppCompatActivity {
    private ActivityMemberRegisterBinding binding;
    private MemberRegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getLiveDataForAction();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_member_register);
        viewModel = new ViewModelProvider(this).get(MemberRegisterViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void getLiveDataForAction() {
        viewModel.getLiveDataForAction().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String type) {
                switch (type) {
                    case FINISH:
                        finish();
                        break;

                    case REGISTER:
                        viewModel.duplicationCheck();
                        break;

                    case DUPLICATED:
                        Toast.makeText(MemberRegisterActivity.this, "중복된 이름입니다. 이름을 바꿔주세요", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        });
    }
}
