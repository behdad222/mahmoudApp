package com.mahmoud.mahmoudapp.DB;

import android.content.Context;

import com.mahmoud.mahmoudapp.DB.Model.AlbumModel;
import com.mahmoud.mahmoudapp.DB.Model.PhotoModel;
import com.mahmoud.mahmoudapp.Entity.Album;
import com.mahmoud.mahmoudapp.Tools;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class DBAdapter {
    private static Realm realm;
    private Tools tools;

    public DBAdapter(Context context) {
        tools = new Tools(context);
        realm = Realm.getInstance(context.getApplicationContext());
    }

    public void insertOrUpdateAlbum(Album[] albums) {
        Album album;
        AlbumModel albumModel;

        realm.beginTransaction();
        for (int i = 0; albums.length > i; i++) {
            album = albums[i];
            albumModel = getAlbum(album.getTitle());

            if (albumModel == null) {
                albumModel = realm.createObject(AlbumModel.class);
            }

            albumModel.setCover(album.getCover());
            albumModel.setTitle(album.getTitle());

            RealmList<PhotoModel> photoModels = new RealmList<>();
            for (int j = 0; j < album.getPhotos().length; j++) {
                PhotoModel photoModel;

                photoModel = getPhoto(album.getPhotos()[j].getUrl());

                if (photoModel == null) {
                    photoModel = realm.createObject(PhotoModel.class);
                }

                photoModel.setDescripton(album.getPhotos()[j].getDescripton());
                photoModel.setUrl(album.getPhotos()[j].getUrl());

                photoModels.add(photoModel);
            }

            albumModel.setPhotos(photoModels);
        }

        realm.commitTransaction();
    }

    public AlbumModel getAlbum(String title) {
        return realm
                .where(AlbumModel.class)
                .equalTo("title", title)
                .findFirst();
    }

    public PhotoModel getPhoto(String url) {
        return realm
                .where(PhotoModel.class)
                .equalTo("url", url)
                .findFirst();
    }

    public List<AlbumModel> getAllAlbum() {
        return realm
                .where(AlbumModel.class)
                .findAll();
    }
}
