package com.example.user_login.ui.gallary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.user_login.R;

import java.util.List;

public class GallaryAdapter extends RecyclerView.Adapter<GallaryAdapter.GallaryViewAdapter> {

    private Context context;
    private List<String> images;

    public GallaryAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public GallaryViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.gallary_image,parent,false);

        return new GallaryViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GallaryViewAdapter holder, int position) {
        Glide.with(context).load(images.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return images==null?0:images.size();
    }

    public class GallaryViewAdapter extends RecyclerView.ViewHolder {

        ImageView imageView;
        public GallaryViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gallaryimage);
        }
    }
}
