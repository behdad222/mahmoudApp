package com.mahmoud.mahmoudapp.Interface;

import com.mahmoud.mahmoudapp.Entity.RegisterEntity;
import com.mahmoud.mahmoudapp.Entity.RegisterResponse;
import com.mahmoud.mahmoudapp.StaticField;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface RegisterApi {
    @POST("/token-auth/register/")
    void register(
            @Body RegisterEntity registerEntity,
            Callback<RegisterResponse> callback);
}
