package com.example.yongin.recyclerView;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yongin.helper.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.yongin.helper.Values.POSITION_TAG;

public abstract class MyAdapter<T extends BaseViewHolder> extends RecyclerView.Adapter<T> {
    protected List<?> itemList;
    protected ItemClickListener customListener;

    public MyAdapter() {
        itemList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<?> getItemList() {
        return itemList;
    }

    public void setItemList(List<?> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener customListener) {
        this.customListener = customListener;
    }

    public View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(POSITION_TAG);
            customListener.onClickItem(position);
        }
    };
}
