package com.example.viewpager_practice_byun;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpager_practice_byun.CustomData.dataBlog;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class NewsHolder extends RecyclerView.ViewHolder{

    public NewsHolder(@NonNull View itemView) {
        super(itemView);
    }
}
class BlogHolder extends RecyclerView.ViewHolder{
    private TextView title;
    private TextView description;
    private TextView blogName;
    private TextView blogLink;

    public BlogHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.blog_title);
        description = itemView.findViewById(R.id.blog_description);
        blogName = itemView.findViewById(R.id.blog_name);
        blogLink = itemView.findViewById(R.id.blog_link);
    }

    public void setData(dataBlog data){
        title.setText(data.getTitle());
        description.setText(data.getDescription());
        blogName.setText(data.getName());
    }
}