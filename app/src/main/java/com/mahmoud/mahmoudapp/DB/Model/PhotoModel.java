package com.mahmoud.mahmoudapp.DB.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PhotoModel extends RealmObject {
    @PrimaryKey
    private String url;
    private String descripton;

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
