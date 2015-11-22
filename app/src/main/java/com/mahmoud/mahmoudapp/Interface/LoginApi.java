package com.mahmoud.mahmoudapp.Interface;

import com.mahmoud.mahmoudapp.Entity.LoginEntity;
import com.mahmoud.mahmoudapp.Entity.LoginResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

public interface LoginApi {
    @POST("/token-auth/login/")
    void login(
            @Header("Accept-Language") String acceptLanguage,
            @Body LoginEntity loginEntity,
            Callback<LoginResponse> callback);
}
