package com.example.recyclerview_sungchulbyun;


import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Author : Jongchan Kim - ace.kim@navercorp.com
 * Created date : 2020-01-21
 *
 */

class WordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Context mContext;
    private LayoutInflater mInflater;
    private File[] mList;
    private String current;

    private static final int LINEAR = 0;
    private static final int GRID = 1;

    public WordListAdapter(){
        this.current = Environment.getRootDirectory().toString();
    }


    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    private OnItemClickListener clickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.clickListener = listener;
    }

    public void setCurrent(String name){
        this.current = name;
    }

    public String getCurrent(){
        return this.current;
    }

    // TODO : 어댑터 패턴이란 무엇인가? (https://ko.wikipedia.org/wiki/어댑터_패턴)
    public WordListAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if(MainActivity.getViewType()==LINEAR) {
            itemView = mInflater.inflate(R.layout.item_linear, parent, false);
            return new LinearItemViewHolder(itemView);
        }
        else {
            itemView = mInflater.inflate(R.layout.item_grid, parent, false);
            return new GridItemViewHolder(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {

        return MainActivity.getViewType();
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType()==GRID) {
            ((GridItemViewHolder)holder).name.setText(mList[position].getName());
            ((GridItemViewHolder)holder).num.setText(mList[position].listFiles()!=null ? ""+mList[position].listFiles().length:""+0);
            if(!mList[position].isDirectory()){
                ((GridItemViewHolder)holder).num.setText("");
            }
            else{
                ((GridItemViewHolder)holder).num.append("개");
            }
            ((GridItemViewHolder)holder).date.setText(transFormat.format(mList[position].lastModified()));
            ((GridItemViewHolder)holder).image.setImageResource(mList[position].isDirectory() ? R.drawable.folder:R.drawable.exe);
            ((GridItemViewHolder)holder).layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    File nfile = new File(mList[position].getAbsolutePath());
                    if(nfile.isDirectory()){
                        setCurrent(nfile.getAbsolutePath());
                        MainActivity.loc.setText(nfile.getName());

                        if(nfile.listFiles()!=null) {
                            setWordList(nfile.listFiles(), getItemViewType(position));
                        }
                        else{
                            mList=new File[0];
                            notifyDataSetChanged();
                        }
                    }
                    else{
                        Toast.makeText(WordListAdapter.this.mContext, "This is not Directory", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {
            ((LinearItemViewHolder)holder).name.setText(mList[position].getName());
            ((LinearItemViewHolder)holder).num.setText(mList[position].listFiles()!=null ? ""+mList[position].listFiles().length:""+0);
            if(!mList[position].isDirectory()){
                ((LinearItemViewHolder)holder).num.setText("");
            }
            else{
                ((LinearItemViewHolder)holder).num.append("개");
            }
            ((LinearItemViewHolder)holder).date.setText(transFormat.format(mList[position].lastModified()));
            ((LinearItemViewHolder)holder).image.setImageResource(mList[position].isDirectory() ? R.drawable.folder:R.drawable.exe);
            ((LinearItemViewHolder)holder).layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    File nfile = new File(mList[position].getAbsolutePath());
                    if(nfile.isDirectory()){
                        setCurrent(nfile.getAbsolutePath());
                        MainActivity.loc.setText(nfile.getName());

                        if(nfile.listFiles()!=null) {
                            setWordList(nfile.listFiles(), getItemViewType(position));
                        }
                        else{
                            mList=new File[0];
                            notifyDataSetChanged();
                        }
                    }
                    else{
                        Toast.makeText(WordListAdapter.this.mContext, "This is not Directory", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        if (mList == null || mList.length == 0) {
            return 0;
        }
        return mList.length;
    }

    public void setWordList(File[] list, int vType) {
        mList=list;
        notifyDataSetChanged();
    }

    /**
     * 모든 리스트 영역의 뷰를 바꾸지 않고, 영역을 제한하여 뷰를 바꾸는 것이 가능
     * notifyItemRangeChanged();
     * notifyItemRangeInserted();
     * notifyItemRangeRemoved();
     */
}