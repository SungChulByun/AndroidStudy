package com.example.recyclerview_sungchulbyun;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    final String TAG = "asdf";
    String rootPath = Environment.getRootDirectory().toString();
    String myPath;
    final String path = rootPath;


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
    private boolean isSearching;
    private PopupMenu p;

    private int CREATE_FILE = 1357;
    private int CREATE_TEXT = 2468;
    private int DELETE = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPath = getFilesDir().getAbsolutePath();

        initList();

        loc = findViewById(R.id.location);

        setWordList(myPath);

//        setWordList("///storage");

        delBar = findViewById(R.id.delete_bar);
        delBar.bringToFront();
        selectButton = findViewById(R.id.select);


        findViewById(R.id.back).setOnClickListener(myListener);
        findViewById(R.id.list_or_grid).setOnClickListener(myListener);
        findViewById(R.id.dots).setOnClickListener(myListener);
        findViewById(R.id.search).setOnClickListener(myListener);
        findViewById(R.id.select).setOnClickListener(myListener);
        delBar.setOnClickListener(myListener);

        ImageView add = findViewById(R.id.add_circle);
        add.setOnClickListener(myListener);

        p = new PopupMenu(getApplicationContext(), add);
        getMenuInflater().inflate(R.menu.popup, p.getMenu());
        p.setOnMenuItemClickListener(myPopupListener);


        bar = findViewById(R.id.search_bar);
        bar.setOnKeyListener(myKeyListener);
        main = findViewById(R.id.mainLayout);

        main.setOnTouchListener(myTouchListener);
        mRecyclerView.addOnItemTouchListener(myItemTouchListener);


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



    private RecyclerView.OnItemTouchListener myItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            if(isSearching){
                return true;
            }
            else {
                return false;
            }
        }
        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

    View.OnTouchListener myTouchListener = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d(TAG, "onTouch: above");
            if(event.getAction() == MotionEvent.ACTION_DOWN&&isSearching){
                hideSearchBar(bar);
                isSearching=false;
                Log.d(TAG, "onTouch: here");
                return true;
            }

            return false;
        }

    };


    // TODO 팝업 메뉴!

    PopupMenu.OnMenuItemClickListener myPopupListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch(item.getItemId()){
                case R.id.create_file:
                    Intent fileIntent = new Intent(MainActivity.this, FileActivity.class);
                    fileIntent.putExtra("type", 0);
                    Log.d(TAG, "onMenuItemClick: here111");
                    startActivityForResult(fileIntent, CREATE_FILE);
                    Log.d(TAG, "onMenuItemClick: here222");

                    break;
                case R.id.create_text:
                    Intent textIntent = new Intent(MainActivity.this, FileActivity.class);
                    textIntent.putExtra("type", 1);
                    startActivityForResult(textIntent, CREATE_TEXT);


                    break;
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_FILE) {
            if(resultCode == RESULT_OK){
                makeDir(data.getStringExtra("filename"));
            }
        }
        else if(requestCode == CREATE_TEXT){
            if(resultCode == RESULT_OK){
                makeText(data.getStringExtra("filename"), data.getStringExtra("content"));
            }
        }
        else if(requestCode == DELETE){
            if(resultCode == RESULT_OK){
                delete();
            }
        }
    }


    View.OnClickListener myListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.back:
                    if(mAdapter.getSelective()){
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
                    }
                    else {
                        String above = new File(mAdapter.getCurrent()).getParent();
                        Log.d(TAG, "onClick: currnet : " + mAdapter.getCurrent());
                        Log.d(TAG, "onClick: parent : " + above);
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
                    isSearching = true;
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
                    Intent delIntent = new Intent(MainActivity.this, DeleteCheck.class);
                    startActivityForResult(delIntent, DELETE);

                    break;

                case R.id.add_circle:
                    Log.d(TAG, "onClick: add circle");
                    p.show();
                    break;
            }
        }
    };

    private void delete(){
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
        Toast.makeText(MainActivity.this, mAdapter.getSelectCount()+"개의 파일 중 "+delnum+"개의 파일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
    }

    private WordListAdapter.OnItemClickListener itemClickListener = new WordListAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(File data) {
            if(!mAdapter.getSelective()) {
                File nfile = new File(data.getAbsolutePath());
                if (nfile.isDirectory()) {
                    setWordList(data.getAbsolutePath());
                    //                setCurrent(nfile.getAbsolutePath());
                    //                MainActivity.loc.setText(nfile.getName());
                    //
                    //                if (nfile.listFiles() != null) {
                    //                    setWordList(nfile.listFiles());
                    //                } else {
                    //                    mList = new File[0];
                    //                    notifyDataSetChanged();
                    //                }
                } else if (nfile.getName().endsWith(".txt")){

                }
                else {
                    Toast.makeText(MainActivity.this, "This is not Directory", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void makeDir(String filename){
        File nFile = new File(mAdapter.getCurrent()+"/"+filename);
        boolean check = nFile.mkdirs();
        Log.d(TAG, "makeDir current: "+myPath);
        Log.d(TAG, "makeDir: name : "+filename);
        Log.d(TAG, "makeDir: "+check);

        mAdapter.setAdapterList(Arrays.asList(new File(mAdapter.getCurrent()).listFiles()));
    }

    public void makeText(String textname, String content){
        File nFile = new File(mAdapter.getCurrent()+"/"+textname+".txt");
        try {
            FileWriter fw = new FileWriter(nFile, true);
            fw.write(content);
            fw.flush();
            fw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        mAdapter.setAdapterList(Arrays.asList(new File(mAdapter.getCurrent()).listFiles()));

    }

    public void delBarUp(){
        delBar.animate().alpha(1f).translationY(-delBar.getHeight());
    }
    public void delBarDown(){
        delBar.animate().translationY(delBar.getHeight()).alpha(0.0f);
    }

    public void deleteCheck(){
        if(mAdapter.getSelectCount()>0){

            delnum = mAdapter.delete();
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
        mAdapter = new WordListAdapter(this, myPath);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setViewType(LINEAR);
        delnum = 0;
        isSearching = false;
        for(int i=0;i<10;i++) makeDir("File" + i);
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
        mAdapter.notifyDataSetChanged();
    }



    // TODO Step2 : 버튼일 클릭 될 때마다 String List를 어댑터에 추가

    private void setWordList(String ppath) {
        File root = new File(ppath);
        loc.setText(root.getName());
        mAdapter.setCurrent(root.getAbsolutePath());
        if(root.listFiles()==null||root.listFiles().length==0){
            Log.d(TAG, "setWordList: null");
            mAdapter.setAdapterList(Arrays.asList(new File[0]));
        }
        else {
            File[] list = root.listFiles();

            mAdapter.setAdapterList(Arrays.asList(list));
        }
        mAdapter.setOnItemClickListener(itemClickListener);
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
