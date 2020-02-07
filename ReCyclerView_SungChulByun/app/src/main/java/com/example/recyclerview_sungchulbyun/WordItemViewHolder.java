package com.example.recyclerview_sungchulbyun;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Jongchan Kim - ace.kim@navercorp.com
 * Created date : 2020-01-21
 */
class LinearItemViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout layout;
    TextView name;
    TextView num;
    TextView date;
    ImageView image;

    LinearItemViewHolder(@NonNull View itemView) {
        super(itemView);
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