package com.example.recyclerview_sungchulbyun;


import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.lang.*;
import java.util.List;


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
        void onItemClick(File data, int position);
    }
    public interface OnItemLongClickListener{
        void myonItemLongClick(File data, int position);
    }


    private OnItemClickListener listener;
    private OnItemLongClickListener mymyLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener lcListener){
        this.mymyLongClickListener = lcListener;
    }

    public void setCurrent(String name){
        this.current = name;
    }

    public String getCurrent(){ return this.current; }

    public WordListAdapter(Context context, String path) {
        this.current = path;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.viewType = LINEAR;
        init();
    }

    public void init(){

        this.isSelectable = false;
        this.selectCount = 0;
        this.mList = new ArrayList<>();
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
            if(selectedItem[position]==1){
                ((GridItemViewHolder)holder).background.setVisibility(View.VISIBLE);
                ((GridItemViewHolder)holder).background.bringToFront();
            }
            else{
                ((GridItemViewHolder)holder).background.setVisibility(View.GONE);
            }
            if(isSelectable){
                ((GridItemViewHolder)holder).box.setVisibility(View.VISIBLE);
                ((GridItemViewHolder)holder).box.bringToFront();
                if(isThisItemSelected(position)==1){
                    ((GridItemViewHolder)holder).box.setChecked(true);
                }
                else{
                    ((GridItemViewHolder)holder).box.setChecked(false);
                }
            }
            else{
                ((GridItemViewHolder)holder).box.setVisibility(View.GONE);
            }
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
            ((GridItemViewHolder)holder).layout.setOnLongClickListener(myLongListener);


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
            ((LinearItemViewHolder)holder).layout.setOnLongClickListener(myLongListener);
        }
    }

    public void setSelectable(){
        isSelectable = !isSelectable;
        selectedItem = new int[mList.size()];
        selectCount = 0;
    }
    public boolean getSelectable(){
        return isSelectable;
    }

    public int getSelectCount(){
        return selectCount;
    }
    public void setSelectCount(int x){
        this.selectCount = x;
    }

    public void delete(){
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
        if(count>0) resetAdapter();
    }

    public void resetAdapter(){
        setAdapterList(Arrays.asList(new File(getCurrent()).listFiles()));
    }
    public void clearSelect(){
        selectedItem = new int[mList.size()];
        setSelectCount(0);
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(POSITION_TAG);
            listener.onItemClick(mList.get(position), position);
        }
    };

    private View.OnLongClickListener myLongListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag(POSITION_TAG);
            mymyLongClickListener.myonItemLongClick(mList.get(position), position);
            return true;
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

        init();

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

    @Override
    public long getItemId(int position) {
        return mList.get(position).getAbsolutePath().hashCode();
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
        background = itemView.findViewById(R.id.linear_background);
        box = itemView.findViewById(R.id.linear_checkbox);
        layout = itemView.findViewById(R.id.item_linear);
        name = itemView.findViewById(R.id.linear_filename);
        num = itemView.findViewById(R.id.linear_num);
        date = itemView.findViewById(R.id.linear_date);
        image = itemView.findViewById(R.id.linear_image);
    }
}

class GridItemViewHolder extends RecyclerView.ViewHolder{
    ConstraintLayout layout;
    TextView name;
    TextView num;
    TextView date;
    ImageView image;
    CheckBox box;
    ImageView background;

    GridItemViewHolder(@NonNull View itemView) {
        super(itemView);
        background = itemView.findViewById(R.id.grid_background);
        box = itemView.findViewById(R.id.grid_checkbox);
        layout = itemView.findViewById(R.id.item_grid);
        name = itemView.findViewById(R.id.grid_filename);
        num = itemView.findViewById(R.id.grid_num);
        date = itemView.findViewById(R.id.grid_date);
        image = itemView.findViewById(R.id.grid_image);


    }
}