package com.mahmoud.mahmoudapp.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mahmoud.mahmoudapp.Adapter.AsarAdapter;
import com.mahmoud.mahmoudapp.DB.DBAdapter;
import com.mahmoud.mahmoudapp.DB.Model.AlbumModel;
import com.mahmoud.mahmoudapp.Entity.Album;
import com.mahmoud.mahmoudapp.Entity.Asar;
import com.mahmoud.mahmoudapp.Entity.Photo;
import com.mahmoud.mahmoudapp.Interface.GetAsarApi;
import com.mahmoud.mahmoudapp.R;
import com.mahmoud.mahmoudapp.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AsarFragment extends Fragment  {
    private RecyclerView recyclerView;
    private AsarAdapter adapter;
    private DBAdapter db;
    private ArrayList<Album> albums;
    private Tools tools;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.asar, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getStringArray(R.array.drawer_titles)[1]);

        tools = new Tools(getContext());
        albums = new ArrayList<>();
        db = new DBAdapter(getContext());

        progressBar = (ProgressBar) view.findViewById(R.id.loading);


        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new AsarAdapter(getContext(), albums);
        recyclerView.setAdapter(adapter);

        if (tools.internetChek()) {
            getFromServer(true);
        } else {
            showData();
        }

        return view;
    }


    public void getFromServer(boolean progress) {
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        }
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.getString(R.string.domainURL))
                .build();

        GetAsarApi getAsarApi = restAdapter.create(GetAsarApi.class);
        getAsarApi.getAsar(
                new Callback<Asar>() {
                    @Override
                    public void success(Asar asar, Response response) {
                        albums.clear();
                        Collections.addAll(albums, asar.getAlbums());

                        db.insertOrUpdateAlbum(asar.getAlbums());
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                        if (albums.size() == 0) {
                            Toast.makeText(getActivity(), getString(R.string.list_empty),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), getString(R.string.error_connection),
                                Toast.LENGTH_LONG).show();

                        showData();
                    }
                }
        );
    }

    private void showData() {
        List<AlbumModel> albumsModel = db.getAllAlbum();
        albums.clear();

        for (int i = 0; i < albumsModel.size(); i++) {
            Album album = new Album();
            album.setTitle(albumsModel.get(i).getTitle());
            album.setCover(albumsModel.get(i).getCover());

            Photo[] photos = new Photo[albumsModel.get(i).getPhotos().size()];
            for (int j = 0; j < albumsModel.get(i).getPhotos().size(); j++) {
                Photo photo = new Photo();
                photo.setUrl(albumsModel.get(i).getPhotos().get(j).getUrl());
                photo.setDescripton(albumsModel.get(i).getPhotos().get(j).getDescripton());

                photos[j]=photo;
            }

            album.setPhotos(photos);

            albums.add(album);
        }

        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);

        if (albums.size() == 0) {
            Toast.makeText(getActivity(), getString(R.string.list_empty),
                    Toast.LENGTH_LONG).show();
        }
    }
}