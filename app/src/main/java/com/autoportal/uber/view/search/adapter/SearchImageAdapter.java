package com.autoportal.uber.view.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.autoportal.uber.R;
import com.autoportal.uber.model.Photo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SearchImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<Photo> list ;

    public SearchImageAdapter(List<Photo> list){
        if(list == null){
           list = new ArrayList<>();
        }
        this.list = list;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_image,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(list.get(position).getUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setNewList(List<Photo> list){
        if(list == null){
            return;
        }
        this.list = list;
        notifyDataSetChanged();
    }

}
