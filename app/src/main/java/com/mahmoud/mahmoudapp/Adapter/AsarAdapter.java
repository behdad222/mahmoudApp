package com.mahmoud.mahmoudapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahmoud.mahmoudapp.Entity.Album;
import com.mahmoud.mahmoudapp.R;
import com.mahmoud.mahmoudapp.View.Activity.MainActivity;
import com.mahmoud.mahmoudapp.View.Fragment.AlbumFragment;
import com.mahmoud.mahmoudapp.View.Fragment.AsarFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AsarAdapter extends RecyclerView.Adapter<AsarAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Album> albums;

    public AsarAdapter(Context context, ArrayList<Album> albums) {
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
            AlbumFragment fragment = new AlbumFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", albums.get(getAdapterPosition()).getTitle());

            fragment.setArguments(bundle);

            FragmentTransaction transaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }

    @Override
    public AsarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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