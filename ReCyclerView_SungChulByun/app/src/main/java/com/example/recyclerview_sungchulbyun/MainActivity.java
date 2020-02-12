package com.example.recyclerview_sungchulbyun;


import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private boolean isSearching;
    private PopupMenu p;

    private final int CREATE_FILE = 2;
    private final int CREATE_TEXT = 3;
    private final int EDIT_TEXT = 4;
    private final int DELETE = 5;



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
                    startActivityForResult(fileIntent, CREATE_FILE);

                    break;
                case R.id.create_text:
                    Intent textIntent = new Intent(MainActivity.this, TextActivity.class);
                    textIntent.putExtra("type", R.integer.NEW_FILE);
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
                makeText(data.getStringExtra("textname"), data.getStringExtra("content"));
            }
        }
        else if(requestCode == DELETE){
            if(resultCode == RESULT_OK){
                delete();
            }
        }
        else if(requestCode == EDIT_TEXT){
            if(resultCode == RESULT_OK) {
                updateText(data.getStringExtra("textname"), data.getStringExtra("content"));
            }
        }
    }


    View.OnClickListener myListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.back:
                    if(mAdapter.getSelectable()){
                        changeSelectable();
                    }
                    else {
                        String above = new File(mAdapter.getCurrent()).getParent();
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
                    changeSelectable();
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

    private void changeSelectable(){
        if(!mAdapter.getSelectable()) {
            selectButton.setText("선택 취소");
            delBarUp();
        }
        else{
            selectButton.setText("선택");
            delBarDown();
        }
        mAdapter.clearSelect();
        mAdapter.setSelectable();
        mAdapter.notifyDataSetChanged();
    }

    private void delete(){
        deleteCheck();
        changeSelectable();
    }

    private void makeTextAlert(final File file){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        Log.d(TAG, "makeTextAlert: text here");
        alert.setTitle("파일을 수정하시겠습니까?");
        final String filename = file.getName();
        final String content = ReadFileText(file);
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //open text file
                Intent textIntent = new Intent(MainActivity.this, TextActivity.class);
                textIntent.putExtra("type", R.integer.OLD_FILE);
                textIntent.putExtra("filename", filename);
                textIntent.putExtra("content", content);


                startActivityForResult(textIntent, EDIT_TEXT);
                
            }
        });
        alert.show();
    }


    private WordListAdapter.OnItemClickListener itemClickListener = new WordListAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(File data, int position) {
            if(!mAdapter.getSelectable()) {

                File nfile = new File(data.getAbsolutePath());
                if (nfile.isDirectory()) {
                    setWordList(data.getAbsolutePath());
                } else if (nfile.getName().endsWith(".txt")) {
                    Log.d(TAG, "onItemClick: text");
                    makeTextAlert(data);
                } else {
                    Toast.makeText(MainActivity.this, "This is not Directory", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                mAdapter.itemSelected(position);
            }
        }
    };

    private WordListAdapter.OnItemLongClickListener itemLongClickListener = new WordListAdapter.OnItemLongClickListener() {
        @Override
        public void myonItemLongClick(File data, int position) {
            if(!mAdapter.getSelectable()){
                changeSelectable();
                mAdapter.itemSelected(position);
            }
        }
    };

    public void makeDir(String filename){
        File nFile = new File(mAdapter.getCurrent()+"/"+filename);
        boolean check = nFile.mkdirs();
        Log.d(TAG, "makeDir current: "+myPath);
        Log.d(TAG, "makeDir: name : "+filename);
        Log.d(TAG, "makeDir: "+check);

        mAdapter.resetAdapter();
    }

    public void makeText(String textname, String content){
        try {
            File nFile = new File(mAdapter.getCurrent()+"/"+textname+".txt");
            FileWriter fw = new FileWriter(nFile, true);
            fw.write(content);
            fw.flush();
            fw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        mAdapter.resetAdapter();
    }
    public void updateText(String origin, String addedContent){
        try {
            File nFile = new File(mAdapter.getCurrent()+"/"+origin);
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(nFile));
            buffWrite.write(addedContent, 0, addedContent.length());
            buffWrite.flush();
            buffWrite.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String ReadFileText(File file){
        String strText = "";
        int nBuffer;
        try{
            BufferedReader bufferRead = new BufferedReader(new FileReader(file));
            while((nBuffer = bufferRead.read()) !=-1){
                strText+=(char)nBuffer;
            }
            bufferRead.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strText;
    }


    public void delBarUp(){
        delBar.animate().alpha(1f).translationY(-delBar.getHeight());
    }
    public void delBarDown(){
        delBar.animate().translationY(delBar.getHeight()).alpha(0.0f);
    }

    public void deleteCheck(){
        if(mAdapter.getSelectCount()>0){
            mAdapter.delete();
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
        mAdapter.setHasStableIds(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setViewType(LINEAR);
        isSearching = false;
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
        mAdapter.setOnItemLongClickListener(itemLongClickListener);

    }
}
