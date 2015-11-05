package com.mahmoud.mahmoudapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahmoud.mahmoudapp.R;
import com.mahmoud.mahmoudapp.Entity.Album;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Album> albums;

    public AlbumAdapter(Context context, ArrayList<Album> albums) {
        this.context = context;
        this.albums = albums;
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
                .load(albums.get(position).getCover())
                .into(holder.cover);

        holder.title.setText(albums.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}