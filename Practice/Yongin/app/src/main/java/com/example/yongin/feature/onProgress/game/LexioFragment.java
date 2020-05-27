package com.example.yongin.feature.onProgress.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yongin.R;
import com.example.yongin.databinding.FragmentLexioBinding;
import com.example.yongin.feature.custom.ProgressFragment;
import com.example.yongin.feature.onProgress.ProgressViewModel;
import com.example.yongin.feature.onProgress.memberSelect.MemberSquareAdapter;
import com.example.yongin.recyclerView.MyRecyclerView;

import static com.example.yongin.helper.Values.LEXIO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LexioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LexioFragment extends ProgressFragment {
    private FragmentLexioBinding binding;
    private ProgressViewModel viewModel;
    private MyRecyclerView memberRecyclerView;
    private MyRecyclerView scoreRecyclerView;

    public LexioFragment() {
        // Required empty public constructor
    }

    public static LexioFragment newInstance() {
        LexioFragment fragment = new LexioFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lexio, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView(view);
        setViewModelAndBinding();

    }

    private void setRecyclerView(View view) {
        memberRecyclerView = view.findViewById(R.id.lexio_member_recyclerView);
        MemberSquareAdapter memberAdapter = new MemberSquareAdapter();
        memberRecyclerView.setAdapter(memberAdapter);

        scoreRecyclerView = view.findViewById(R.id.lexio_score_recyclerView);
        LexioScoreAdapter scoreAdapter = new LexioScoreAdapter();
        scoreRecyclerView.setAdapter(scoreAdapter);
    }

    private void setViewModelAndBinding() {
        viewModel = new ViewModelProvider(getActivity()).get(ProgressViewModel.class);
        viewModel.setTitle(LEXIO);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getActivity());
    }

}
