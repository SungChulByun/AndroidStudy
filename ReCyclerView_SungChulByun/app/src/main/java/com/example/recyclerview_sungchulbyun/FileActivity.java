package com.example.recyclerview_sungchulbyun;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class FileActivity extends AppCompatActivity {
    private int type;
    private TextView cancel;
    private TextView finish;
    private EditText edit;
    private EditText content;
    private ConstraintLayout layout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naming);

        Intent intent = getIntent();
        type = intent.getExtras().getInt("type");

        Log.d("naming", "onCreate: type"+type);

        cancel = findViewById(R.id.file_cancel);
        finish = findViewById(R.id.file_finish);
        edit = findViewById(R.id.file_edit);
        content = findViewById(R.id.naming_content);

        cancel.setOnClickListener(myListener);
        finish.setOnClickListener(myListener);


        if(type==1){
            layout = findViewById(R.id.text_box);
            layout.setMinHeight(1900);
            content.setVisibility(View.VISIBLE);
            ImageView imageView = findViewById(R.id.create_image);
            imageView.setImageResource(R.drawable.create_txt);
            EditText editText = findViewById(R.id.file_edit);
            editText.setHint("파일 이름을 입력하세요.");


        }
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.file_cancel:
                    finish();
                    break;
                case R.id.file_finish:
                    Intent fintent = new Intent(FileActivity.this, MainActivity.class);
                    fintent.putExtra("filename", edit.getText().toString());
                    fintent.putExtra("content", content.getText().toString());
                    setResult(RESULT_OK, fintent);
                    finish();
                    break;
            }
        }
    };
}
