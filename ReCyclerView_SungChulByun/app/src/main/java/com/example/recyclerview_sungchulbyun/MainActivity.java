package com.example.recyclerview_sungchulbyun;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    final String TAG = "sungchul";
    final String path = Environment.getRootDirectory().toString();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    public static TextView loc;
    private ImageView back;
    private ImageView type;
    private static int viewType;

    private static final int LINEAR = 0;
    private static final int GRID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
        loc = findViewById(R.id.location);
        setWordList(path);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String above = new File(mAdapter.getCurrent()).getParent();
                if(above==null){
                    Toast.makeText(MainActivity.this, "There is no location above", Toast.LENGTH_SHORT).show();
                }
                else{
                    setWordList(above);
                    mAdapter.setCurrent(above);
                }
            }
        });

        type = findViewById(R.id.list_or_grid);
        type.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                typeChange();
            }
        });

    }

    // TODO Step2 : RecyclerView에 설정할 Adapter를 생성하고, 리스트의 레이아웃 매니저를 LinearLayoutManager로 설정


    private void initList() {
        mRecyclerView = findViewById(R.id.main_list);
        mAdapter = new WordListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        viewType=LINEAR;
    }

    public void typeChange(){
        if(viewType==LINEAR){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            viewType=GRID;
        }
        else{
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            viewType=LINEAR;
        }
        setWordList(mAdapter.getCurrent());
    }



    // TODO Step2 : 버튼일 클릭 될 때마다 String List를 어댑터에 추가

    public static void setViewType(int type){
        viewType=type;
    }
    public static int getViewType(){
        return viewType;
    }

    private void setWordList(String ppath) {

        File root = new File(ppath);
        Log.d(TAG, "setWordList: "+path);

        loc.setText(root.getName());
        mAdapter.setCurrent(root.getAbsolutePath());
        mAdapter.setWordList(root.listFiles(), viewType);
//        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//
//        int len = fileList.length;
//        RecyclerItem[] rList = new RecyclerItem[len];
//        for(int i=0;i<len;i++){
//            if(fileList[i].getName() !=null) {
//
//                RecyclerItem item = new RecyclerItem();
//
//                String time = transFormat.format(fileList[i].lastModified());
//
//                if (fileList[i].isDirectory()) {
//                    item.setIcon(getDrawable(R.drawable.file));
//                } else {
//                    item.setIcon(getDrawable(R.drawable.exe));
//                }
//                int num ;
//                if(fileList[i].listFiles() ==null) num=0;
//                else num = fileList[i].listFiles().length;
//
//                item.setTitle(fileList[i].getName());
//                item.setDate(time);
//                item.setNum(String.valueOf(num));
//
//                rList[i] = item;
//
//            }
//        }
//        List<RecyclerItem> wordList = Arrays.asList(rList);

    }
}
