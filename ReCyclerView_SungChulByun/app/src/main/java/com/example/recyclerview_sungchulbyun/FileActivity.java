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

public class FileActivity extends AppCompatActivity {
    private TextView cancel;
    private TextView finish;
    private EditText edit;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naming);

        cancel = findViewById(R.id.file_cancel);
        finish = findViewById(R.id.file_finish);
        edit = findViewById(R.id.file_edit);

        cancel.setOnClickListener(myListener);
        finish.setOnClickListener(myListener);

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
                    setResult(RESULT_OK, fintent);
                    finish();
                    break;
            }
        }
    };
}
