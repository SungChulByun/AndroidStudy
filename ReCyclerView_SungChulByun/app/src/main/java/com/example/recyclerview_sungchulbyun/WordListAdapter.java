package com.example.recyclerview_sungchulbyun;


import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.lang.*;
import java.util.List;

import static com.example.recyclerview_sungchulbyun.MainActivity.bar;

/**
 * Author : Jongchan Kim - ace.kim@navercorp.com
 * Created date : 2020-01-21
 *
 */


class WordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Context mContext;
    private LayoutInflater mInflater;
    private List<File> mList;
    private int[] selectedItem;
    private String current;
    private int viewType;
    private boolean isSelectable;


    private int selectCount;


    private static final int LINEAR = 0;
    private static final int GRID = 1;


    public interface OnItemClickListener{
        public void onItemClick(File data);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setCurrent(String name){
        this.current = name;
    }

    public String getCurrent(){
        return this.current;
    }

    // TODO : 어댑터 패턴이란 무엇인가? (https://ko.wikipedia.org/wiki/어댑터_패턴)

    public WordListAdapter(Context context, String path) {
        this.viewType = LINEAR;
        this.current = path;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.isSelectable = false;
        this.selectCount = 0;
        this.mList = new ArrayList<File>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if(this.getViewType()==LINEAR) {
            itemView = mInflater.inflate(R.layout.item_linear, parent, false);
            return new LinearItemViewHolder(itemView);
        }
        else {
            itemView = mInflater.inflate(R.layout.item_grid, parent, false);
            return new GridItemViewHolder(itemView);
        }
    }
    public void setViewType(int ntype){
        this.viewType = ntype;
    }
    public int getViewType(){
        return this.viewType;
    }


    @Override
    public int getItemViewType(int position) {
        if(viewType == LINEAR)
             return LINEAR;
        else{
            return GRID;
        }
    }

    // 16진수 32비트로 설정하기.
    private static final int POSITION_TAG = 0xFFFFFFF1;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType()==GRID) {
            ((GridItemViewHolder)holder).name.setText(mList.get(position).getName());
            ((GridItemViewHolder)holder).num.setText(mList.get(position).listFiles()!=null ? ""+mList.get(position).listFiles().length:""+0);
            if(!mList.get(position).isDirectory()){
                ((GridItemViewHolder)holder).num.setText("");
            }
            else{
                ((GridItemViewHolder)holder).num.append("개");
            }
            ((GridItemViewHolder)holder).date.setText(transFormat.format(mList.get(position).lastModified()));
            ((GridItemViewHolder)holder).image.setImageResource(mList.get(position).isDirectory() ? R.drawable.folder:R.drawable.exe);
            ((GridItemViewHolder)holder).layout.setTag(POSITION_TAG, position);
            ((GridItemViewHolder)holder).layout.setOnClickListener(clickListener);


        }
        else {
            if(selectedItem[position]==1){
                ((LinearItemViewHolder)holder).background.setVisibility(View.VISIBLE);
                ((LinearItemViewHolder)holder).background.bringToFront();
            }
            else{
                ((LinearItemViewHolder)holder).background.setVisibility(View.GONE);
            }
            if(isSelectable){
                ((LinearItemViewHolder)holder).box.setVisibility(View.VISIBLE);
                if(isThisItemSelected(position)==1){
                    ((LinearItemViewHolder)holder).box.setChecked(true);
                }
                else{
                    ((LinearItemViewHolder)holder).box.setChecked(false);
                }
            }
            else{
                ((LinearItemViewHolder)holder).box.setVisibility(View.GONE);
            }
            ((LinearItemViewHolder)holder).name.setText(mList.get(position).getName());
            ((LinearItemViewHolder)holder).num.setText(mList.get(position).listFiles()!=null ? ""+mList.get(position).listFiles().length:""+0);
            if(!mList.get(position).isDirectory()){
                ((LinearItemViewHolder)holder).num.setText("");
            }
            else{
                ((LinearItemViewHolder)holder).num.append("개");
            }
            ((LinearItemViewHolder)holder).date.setText(transFormat.format(mList.get(position).lastModified()));
            ((LinearItemViewHolder)holder).image.setImageResource(mList.get(position).isDirectory() ? R.drawable.folder:R.drawable.exe);
            ((LinearItemViewHolder)holder).layout.setTag(POSITION_TAG, position);
            ((LinearItemViewHolder)holder).layout.setOnClickListener(clickListener);
        }
    }

    public void setSelective(boolean ntype){
        isSelectable = ntype;
        selectedItem = new int[mList.size()];
        selectCount = 0;
    }
    public boolean getSelective(){
        return isSelectable;
    }

    public int getSelectCount(){
        return selectCount;
    }

    public int delete(){
        int count = 0;
        if(isSelectable){
            for(int i=0;i<selectedItem.length;i++){
                if(selectedItem[i]==1){
                    if(mList.get(i).delete()){
                        count++;
                    }
                }
            }
        }
        if(count>0) setAdapterList(Arrays.asList(new File(getCurrent()).listFiles()));
        return count;
    }

    View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(POSITION_TAG);
            listener.onItemClick(mList.get(position));
            if(isSelectable){
                itemSelected(position);
            }
        }
    };



    @Override
    public int getItemCount() {
        if (mList == null || mList.size() == 0) {
            return 0;
        }
        return mList.size();
    }

    public void setAdapterList(List<File> list) {

        mList = list;
        Collections.sort(mList, new Comparator<File>() {
            @Override
            public int compare(File a, File b) {
                if (a.isDirectory() && b.isDirectory()) {
                    return a.getName().compareTo(b.getName());
                } else if (a.isDirectory() && !b.isDirectory()) {
                    return -1;
                } else if (!a.isDirectory() && b.isDirectory()) {
                    return 1;
                } else {
                    return a.getName().compareTo(b.getName());
                }
            }
        });

        selectedItem = new int[list.size()];

        notifyDataSetChanged();
    }



    public void itemSelected(int pos){ // 1 : selected , 0 : not selected
        if(selectedItem[pos] == 0){
            selectedItem[pos]=1;
            selectCount++;
        }
        else{
            selectedItem[pos]=0;
            selectCount--;
        }
        notifyItemChanged(pos);
    }
    public int isThisItemSelected(int pos){
        return selectedItem[pos];
    }

    /**
     * 모든 리스트 영역의 뷰를 바꾸지 않고, 영역을 제한하여 뷰를 바꾸는 것이 가능
     * notifyItemRangeChanged();
     * notifyItemRangeInserted();
     * notifyItemRangeRemoved();
     */
}

class LinearItemViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout layout;
    TextView name;
    TextView num;
    TextView date;
    ImageView image;
    CheckBox box;
    ImageView background;

    LinearItemViewHolder(@NonNull View itemView) {
        super(itemView);
        background = itemView.findViewById(R.id.background);
        box = itemView.findViewById(R.id.checkbox);
        layout = itemView.findViewById(R.id.item_linear);
        name = itemView.findViewById(R.id.filename);
        num = itemView.findViewById(R.id.num);
        date = itemView.findViewById(R.id.date);
        image = itemView.findViewById(R.id.image);
    }
}

class GridItemViewHolder extends RecyclerView.ViewHolder{
    ConstraintLayout layout;
    TextView name;
    TextView num;
    TextView date;
    ImageView image;


    GridItemViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.item_grid);
        name = itemView.findViewById(R.id.filename);
        num = itemView.findViewById(R.id.num);
        date = itemView.findViewById(R.id.date);
        image = itemView.findViewById(R.id.image);


    }
}