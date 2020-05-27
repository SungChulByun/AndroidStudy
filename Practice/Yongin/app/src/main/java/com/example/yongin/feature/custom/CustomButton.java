package com.example.yongin.feature.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.yongin.R;

public class CustomButton extends ConstraintLayout {
    private TextView textView;
    private ImageView imageView;
    private String buttonName;

    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_button, this);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.custom_button);
            buttonName = array.getString(R.styleable.custom_button_description);
            array.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textView = findViewById(R.id.button_text);
        imageView = findViewById(R.id.button_image);
        setButtonDetail();

    }

    private void setButtonDetail() {
        switch (buttonName) {
            case "lexio":
                textView.setText("렉시오");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.lexio));
                break;

            case "poker":
                textView.setText("포커");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.poker_icon));
                break;

            case "team":
                textView.setText("사다리타기");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ghost_leg));
                break;

            case "bill":
                textView.setText("영수증");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.bill));
                break;

            case "game":
                textView.setText("게임추천");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.game));
                break;

            case "meal":
                textView.setText("식당정보");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.dish));
                break;

            default:
                break;

        }
    }
}
