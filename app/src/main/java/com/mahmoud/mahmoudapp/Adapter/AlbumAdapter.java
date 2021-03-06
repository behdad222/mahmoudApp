package com.mahmoud.mahmoudapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahmoud.mahmoudapp.Entity.Photo;
import com.mahmoud.mahmoudapp.R;
import com.mahmoud.mahmoudapp.View.ImageDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Photo> photos;

    public AlbumAdapter(Context context, ArrayList<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView cover;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) itemView.findViewById(R.id.album_title);
            cover = (ImageView) itemView.findViewById(R.id.album_cover);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            new ImageDialog(context, photos, getAdapterPosition()).show();
        }
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.album_cover, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(photos.get(position).getUrl())
                .into(holder.cover);

        holder.title.setText(photos.get(position).getDescripton());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}