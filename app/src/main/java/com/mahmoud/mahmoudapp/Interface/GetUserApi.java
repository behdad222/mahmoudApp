package com.mahmoud.mahmoudapp.Interface;

import com.mahmoud.mahmoudapp.Entity.UserResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

public interface GetUserApi {
    @GET("/token-auth/me/")
    void getUser(
            @Header("Accept-Language") String acceptLanguage,
            @Header("Authorization") String token,
            Callback<UserResponse> callback);
}