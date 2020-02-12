package com.example.recyclerview_sungchulbyun;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TextEditor extends AppCompatActivity {
    private Button cancel;
    private Button finish;
    private EditText story;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.text_editor);

        cancel = findViewById(R.id.edit_cancel);
        finish = findViewById(R.id.edit_finish);
        story = findViewById(R.id.edit_story);

        cancel.setOnClickListener(myClickListener);
        finish.setOnClickListener(myClickListener);
    }

    private View.OnClickListener myClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edit_cancel:
                    finish();
                    break;
                case R.id.edit_finish:
                    Intent intent = new Intent(TextEditor.this, MainActivity.class);
                    intent.putExtra("story", story.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    };
}