package com.example.recyclerview_sungchulbyun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class TextActivity extends AppCompatActivity {
    private TextView cancel;
    private TextView finish;
    private EditText edit;
    private EditText content;
    private int type;
    private String origin;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_editor);

        Intent intent = getIntent();
        type = intent.getExtras().getInt("type");


        cancel = findViewById(R.id.text_cancel);
        finish = findViewById(R.id.text_finish);
        edit = findViewById(R.id.text_edit);

        content = findViewById(R.id.text_content);

        origin = edit.getText().toString();

        cancel.setOnClickListener(myListener);
        finish.setOnClickListener(myListener);

        if(type == R.integer.OLD_FILE){
            edit.setClickable(false);
            edit.setFocusable(false);
            edit.setBackgroundColor(Color.argb(0,0,0,0));
            edit.setTextColor(Color.rgb(255, 255, 255));
            edit.setText(intent.getStringExtra("filename"));
            content.setText(intent.getStringExtra("content"));
        }
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.text_cancel:
                    finish();
                    break;
                case R.id.text_finish:
                    Intent tIntent = new Intent(TextActivity.this, MainActivity.class);
                    tIntent.putExtra("textname", edit.getText().toString());
                    tIntent.putExtra("content", content.getText().toString());
                    tIntent.putExtra("origin", origin);
                    setResult(RESULT_OK, tIntent);
                    finish();
                    break;
            }
        }
    };
}
