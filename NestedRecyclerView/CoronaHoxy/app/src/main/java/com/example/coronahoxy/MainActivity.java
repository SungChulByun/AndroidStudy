package com.example.coronahoxy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import static com.example.coronahoxy.LoginActivity.mOAuthLoginInstance;

public class MainActivity extends AppCompatActivity {

    private CityAdapter city;
    private ViewPager2 pager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        findViewById(R.id.main_logout).setOnClickListener(mylistener);

    }
    private View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_logout:
                    mOAuthLoginInstance.logout(LoginActivity.mContext);
                    //Todo Logout 하시겠습니까? 확인 메세지
                    onBackPressed();
                    Log.d("asdf", "onClick: ");
                    break;
            }
        }
    };

    void init(){
        city = new CityAdapter();
        city.addItem(new String[] {"서울", "11", "2"});
        city.addItem(new String[] {"경기", "22", "4"});
        city.addItem(new String[] {"대전", "33", "6"});
        city.addItem(new String[] {"대구", "44", "8"});

        pager = findViewById(R.id.main_viewpager2);
        pager.setOffscreenPageLimit(5);
        pager.setAdapter(city);
    }
}
