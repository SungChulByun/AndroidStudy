package com.example.viewpager_practice_byun;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpager_practice_byun.CustomData.customData;
import com.example.viewpager_practice_byun.CustomData.dataBlog;
import com.example.viewpager_practice_byun.CustomData.dataImage;
import com.example.viewpager_practice_byun.CustomData.dataNews;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<customData> chatList;
    private LayoutInflater inflater;

    private final String TAG = "spide333";
    private final int TYPE_IMAGE = 9999;
    private final int TYPE_BLOG = 9998;
    private final int TYPE_NEWS = 9997;

    private final int MY_KEY = 9311;

    private int check;

    public ChatAdapter(Context context){
        this.chatList = new ArrayList<customData>();
        this.inflater = LayoutInflater.from(context);
        check = -1;
    }

    private myOnItemClickListener myListener;

    public interface myOnItemClickListener{
        void OnItemClick(int position);
    }
    public void setmyOnItemClickListener(myOnItemClickListener listener){
        this.myListener = listener;
    }

    private View.OnClickListener myItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(MY_KEY);
            myListener.OnItemClick(position);
        }
    };

    public customData getItem(int position){
        return chatList.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_IMAGE) {
            view = inflater.inflate(R.layout.image, parent, false);
            return new ImageHolder(view);
        }
        else if (viewType == TYPE_BLOG) {
            view = inflater.inflate(R.layout.blog, parent, false);
            return new BlogHolder(view);
        }
        else {
            view = inflater.inflate(R.layout.news, parent, false);
            return new NewsHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        check = getItemViewType(position);
        switch (getItemViewType(position)){
            case TYPE_IMAGE:
                ((ImageHolder) holder).setData((dataImage) chatList.get(position));
                ((ImageHolder) holder).linkButton.setOnClickListener(myItemClickListener);
                break;

            case TYPE_BLOG:
                ((BlogHolder) holder).setData((dataBlog) chatList.get(position));
                ((BlogHolder) holder).linkButton.setOnClickListener(myItemClickListener);
                break;

            case TYPE_NEWS:
                ((NewsHolder) holder).setData((dataNews) chatList.get(position));
                ((NewsHolder) holder).linkButton.setOnClickListener(myItemClickListener);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chatList.get(position).getType();
    }

    /**
     notifyItemInserted position 체크 필요
     */
    public void addItem(customData data){
        chatList.add(data);
        notifyDataSetChanged();

        //notifyItemInserted(getItemCount()-1); // 체크 필요 //
    }

}

class NewsHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView description;
    TextView newsDate;
    String link;
    TextView linkButton;

    public NewsHolder(@NonNull View itemView) {
        super(itemView);
        linkButton = itemView.findViewById(R.id.news_link);
        title = itemView.findViewById(R.id.news_title);
        description = itemView.findViewById(R.id.news_description);
        newsDate = itemView.findViewById(R.id.news_date);
        link = null;
    }

    public void setData(dataNews data){
        title.setText(data.getTitle());
        description.setText(data.getDescription());
        newsDate.setText(data.getDate());
        link = data.getLink();
    }

}


class BlogHolder extends RecyclerView.ViewHolder{
    TextView title;
    TextView description;
    TextView blogName;
    TextView linkButton;
    String link;

    public BlogHolder(@NonNull View itemView) {
        super(itemView);
        linkButton = itemView.findViewById(R.id.blog_link);
        title = itemView.findViewById(R.id.blog_title);
        description = itemView.findViewById(R.id.blog_description);
        blogName = itemView.findViewById(R.id.blog_name);
        link = null;
    }

    public void setData(dataBlog data){
        title.setText(data.getTitle());
        description.setText(data.getDescription());
        blogName.setText(data.getName());
        link = data.getLink();
    }
}

class ImageHolder extends RecyclerView.ViewHolder{
    ImageView title;
    TextView showAll;
    String imageLink;
    TextView linkButton;
    public ImageHolder(@NonNull View itemView) {
        super(itemView);
        linkButton = itemView.findViewById(R.id.image_link);
        title = itemView.findViewById(R.id.image_image);
        showAll = itemView.findViewById(R.id.image_link);
        imageLink = null;
    }

    public void setData(dataImage data){
        imageLink = data.getLink();
    }

}