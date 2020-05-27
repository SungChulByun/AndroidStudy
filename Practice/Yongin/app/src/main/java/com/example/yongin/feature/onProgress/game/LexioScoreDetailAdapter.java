package com.example.yongin.feature.onProgress.game;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.yongin.R;
import com.example.yongin.recyclerView.BaseViewHolder;
import com.example.yongin.recyclerView.MyAdapter;

import static android.content.ContentValues.TAG;

public class LexioScoreDetailAdapter extends MyAdapter<LexioScoreDetailHolder> {
    @NonNull
    @Override
    public LexioScoreDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new LexioScoreDetailHolder(inflater.inflate(R.layout.holder_lexio_score_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LexioScoreDetailHolder holder, int position) {
        if (position == 0) {
            holder.editText.setVisibility(View.GONE);
        } else {
            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.d(TAG, "onTextChanged: ");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}

class LexioScoreDetailHolder extends BaseViewHolder {
    EditText editText;

    public LexioScoreDetailHolder(@NonNull View itemView) {
        super(itemView);
        editText = itemView.findViewById(R.id.lexio_score_editText);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Log.d(TAG, "LexioScoreDetailHolder: has focus");
                    InputMethodManager imm = (InputMethodManager) itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });
    }
}