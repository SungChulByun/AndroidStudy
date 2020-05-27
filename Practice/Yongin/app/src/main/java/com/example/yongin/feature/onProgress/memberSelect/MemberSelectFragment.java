package com.example.yongin.feature.onProgress.memberSelect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.yongin.feature.custom.ProgressFragment;
import com.example.yongin.feature.main.MainViewModel;
import com.example.yongin.R;
import com.example.yongin.databinding.FragmentMemberSelectBinding;
import com.example.yongin.feature.onProgress.ProgressViewModel;
import com.example.yongin.helper.ItemClickListener;
import com.example.yongin.model.Member;
import com.example.yongin.recyclerView.MyRecyclerView;

import static android.content.ContentValues.TAG;
import static com.example.yongin.helper.Values.ADD_MEMBER;
import static com.example.yongin.helper.Values.NEXT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemberSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberSelectFragment extends ProgressFragment {
    private FragmentMemberSelectBinding binding;
    private MyRecyclerView recyclerView;
    private static final String TYPE = "TYPE";
    private String type;

    public MemberSelectFragment() {
        Log.d(TAG, "MemberSelectFragment: ");
    }

    public static MemberSelectFragment newInstance(String type) {
        MemberSelectFragment fragment = new MemberSelectFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_select, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);

        setRecyclerView(view);
        setViewModelAndBinding();

        viewModel.setTitle(type + "멤버 선택");
        getLiveDataForAction();

    }

    private void setViewModelAndBinding(){
        viewModel = new ViewModelProvider(getActivity()).get(ProgressViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getActivity());
    }

    private void setRecyclerView(View view){
        Log.d(TAG, "setRecyclerView: ");
        recyclerView = view.findViewById(R.id.member_recyclerView);
        MemberAdapter adapter = new MemberAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position) {
                ((Member) adapter.getItemList().get(position)).changeSelected();
            }
        });
    }


    private void getLiveDataForAction(){
        viewModel.getLiveDataForButtonClick().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String type) {
                switch (type){
                    case ADD_MEMBER:
                        startActivity(new Intent(getActivity(), MemberRegisterActivity.class));
                        break;

                    default:
                        break;
                }
            }
        });
    }
}
