package com.example.recyclerview_sungchulbyun;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteCheck extends AppCompatActivity {
    private TextView cancel;
    private TextView delete;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_popup);

        cancel = findViewById(R.id.delete_cancel);
        delete = findViewById(R.id.delete_sure);

        cancel.setOnClickListener(myClickListener);
        delete.setOnClickListener(myClickListener);

    }
    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.delete_cancel:
                    finish();
                    break;
                case R.id.delete_sure:
                    Intent intent = new Intent(DeleteCheck.this, MainActivity.class);
                    intent.putExtra("result", "delete");
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    };
}
