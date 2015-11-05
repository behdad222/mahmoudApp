package com.mahmoud.mahmoudapp.Interface;


import com.mahmoud.mahmoudapp.Entity.Asar;

import retrofit.Callback;
import retrofit.http.GET;

public interface GetAsarApi {
    @GET("/files/kave.json")
    void getAsar(
            Callback<Asar> callback);
}
