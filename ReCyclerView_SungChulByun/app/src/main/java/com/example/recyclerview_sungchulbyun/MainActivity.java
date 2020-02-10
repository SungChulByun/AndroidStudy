package com.example.recyclerview_sungchulbyun;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    final String TAG = "asdf";
    final String path = Environment.getRootDirectory().toString();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    public static TextView loc;
    private ConstraintLayout delBar;
    private TextView selectButton;

    private static final int LINEAR = 0;
    private static final int GRID = 1;
    public static EditText bar;
    private ConstraintLayout main;
    private int delnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
        loc = findViewById(R.id.location);
        setWordList(path);

        delBar = findViewById(R.id.delete_bar);
        delBar.bringToFront();
        selectButton = findViewById(R.id.select);


        findViewById(R.id.back).setOnClickListener(myListener);
        findViewById(R.id.list_or_grid).setOnClickListener(myListener);
        findViewById(R.id.dots).setOnClickListener(myListener);
        findViewById(R.id.search).setOnClickListener(myListener);
        findViewById(R.id.select).setOnClickListener(myListener);
        delBar.setOnClickListener(myListener);
        findViewById(R.id.sure).setOnClickListener(myListener);
        findViewById(R.id.cancel).setOnClickListener(myListener);
        findViewById(R.id.yes_or_no).setOnClickListener(myListener);


        bar = findViewById(R.id.search_bar);
        bar.setOnKeyListener(myKeyListener);
        main = findViewById(R.id.mainLayout);

        main.setOnTouchListener(myTouchListener);


    }
    public void hideKeyBoard(EditText et){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public void hideSearchBar(EditText et){
        et.setText("");
        et.clearFocus();
        et.setVisibility(View.INVISIBLE);
        hideKeyBoard(et);
    }
    View.OnKeyListener myKeyListener = new View.OnKeyListener(){
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                hideKeyBoard((EditText) v);
                return true;
            }
            return false;
        }
    };

    View.OnTouchListener myTouchListener = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d(TAG, "onTouch: above");
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                hideSearchBar(bar);

                Log.d(TAG, "onTouch: here");
                return true;
            }

            return false;
        }
    };

    View.OnClickListener myListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.back:
                    if(mAdapter.getSelective()){
                        mAdapter.setSelective(false);
                        mAdapter.notifyDataSetChanged();
                    }
                    else {
                        String above = new File(mAdapter.getCurrent()).getParent();
                        Log.d(TAG, "onClick: currnet" + mAdapter.getCurrent());
                        Log.d(TAG, "onClick: parent" + above);
                        if (above == null || above.equals("/")) {
                            Toast.makeText(MainActivity.this, "There is no location above", Toast.LENGTH_SHORT).show();
                        } else {


                            setWordList(above);
                            mAdapter.setCurrent(above);

                        }
                    }
                    break;

                case R.id.list_or_grid:
                    typeChange();
                    ImageView lg_button = findViewById(R.id.list_or_grid);
                    if(mAdapter.getViewType()==LINEAR){
                        lg_button.setImageResource(R.drawable.list);
                    }
                    else{
                        lg_button.setImageResource(R.drawable.grid);
                    }
                    break;

                case R.id.search:
                    bar.setVisibility(View.VISIBLE);
                    bar.requestFocus();
                    break;

                case R.id.select:
                    if(mAdapter.getViewType() == LINEAR){


                        mAdapter.setSelective(!mAdapter.getSelective());

                        if(mAdapter.getSelective()){
                            selectButton.setText("선택 취소");
                            delBarUp();
                        }
                        else{
                            selectButton.setText("선택");
                            delBarDown();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.delete_bar:
                    popup();
                    break;
                case R.id.yes_or_no:
                case R.id.cancel:
                    hidePopup();
                    break;
                case R.id.sure:
                    hidePopup();
                    Toast.makeText(MainActivity.this, "선택된 "+mAdapter.getSelectCount()+"개의 파일 중"+delnum+"개의 파일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    delnum=0;
                    deleteCheck();
                    mAdapter.setSelective(!mAdapter.getSelective());

                    if(mAdapter.getSelective()){
                        selectButton.setText("선택 취소");
                        delBarUp();
                    }
                    else{
                        selectButton.setText("선택");
                        delBarDown();
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    public void popup(){
        findViewById(R.id.yes_or_no).setVisibility(View.VISIBLE);
        findViewById(R.id.yes_or_no).bringToFront();
    }
    public void hidePopup(){
        findViewById(R.id.yes_or_no).setVisibility(View.GONE);

    }

    public void delBarUp(){
        delBar.animate().translationY(-delBar.getHeight()).alpha(1.0f);
    }
    public void delBarDown(){
        delBar.animate().translationY(delBar.getHeight()).alpha(0.0f);
    }

    public void deleteCheck(){
        if(mAdapter.getSelectCount()>0){

            delnum = mAdapter.delete();

            mAdapter.notifyDataSetChanged();
            delBarDown();
        }
        else{
            Toast.makeText(this, "Nothing to Delete", Toast.LENGTH_SHORT).show();
        }
    }


    // TODO Step2 : RecyclerView에 설정할 Adapter를 생성하고, 리스트의 레이아웃 매니저를 LinearLayoutManager로 설정


    private void initList() {
        mRecyclerView = findViewById(R.id.main_list);
        mRecyclerView.setOnTouchListener(myTouchListener);
        mAdapter = new WordListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setViewType(LINEAR);
        delnum = 0;

    }

    public void typeChange(){
        if(mAdapter.getViewType()==LINEAR){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            mAdapter.setViewType(GRID);
        }
        else{
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.setViewType(LINEAR);
        }
        setWordList(mAdapter.getCurrent());
    }



    // TODO Step2 : 버튼일 클릭 될 때마다 String List를 어댑터에 추가

    private void setWordList(String ppath) {

        File root = new File(ppath);
        loc.setText(root.getName());
        mAdapter.setCurrent(root.getAbsolutePath());
        mAdapter.setWordList(root.listFiles());
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
