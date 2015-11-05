package com.mahmoud.mahmoudapp.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mahmoud.mahmoudapp.Adapter.AlbumAdapter;
import com.mahmoud.mahmoudapp.DB.DBAdapter;
import com.mahmoud.mahmoudapp.DB.Model.AlbumModel;
import com.mahmoud.mahmoudapp.Entity.Photo;
import com.mahmoud.mahmoudapp.R;

import java.util.ArrayList;

public class AlbumFragment extends Fragment  {
    private RecyclerView recyclerView;
    private AlbumAdapter adapter;
    private DBAdapter db;
    private ArrayList<Photo> photos;
    private ProgressBar progressBar;
    private String albumTitle;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.asar, container, false);

        albumTitle = getArguments().getString("title");

        photos = new ArrayList<>();
        db = new DBAdapter(getContext());

        progressBar = (ProgressBar) view.findViewById(R.id.loading);

        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new AlbumAdapter(getContext(), photos);
        recyclerView.setAdapter(adapter);

        showData();

        return view;
    }

    private void showData() {
        AlbumModel albumsModel = db.getAlbum(albumTitle);

        for (int j = 0; j < albumsModel.getPhotos().size(); j++) {
            Photo photo = new Photo();
            photo.setUrl(albumsModel.getPhotos().get(j).getUrl());
            photo.setDescripton(albumsModel.getPhotos().get(j).getDescripton());

            photos.add(photo);
        }

        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);

        if (photos.size() == 0) {
            Toast.makeText(getActivity(), getString(R.string.list_empty),
                    Toast.LENGTH_LONG).show();
        }
    }
}