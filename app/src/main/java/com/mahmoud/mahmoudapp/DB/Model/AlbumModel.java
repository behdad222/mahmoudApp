package com.mahmoud.mahmoudapp.DB.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlbumModel extends RealmObject {
    @PrimaryKey
    private String title;
    private String cover;
    private RealmList<PhotoModel> photos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public RealmList<PhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(RealmList<PhotoModel> photos) {
        this.photos = photos;
    }
}
