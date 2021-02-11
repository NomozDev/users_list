package com.first.retrofitapp.retrofits;

import com.first.retrofitapp.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceAnnotationUser {
    @GET("users")
    Call<Status> getUsers();

    @POST("users/create")
    Call<Status>  createuser(@Body User user);

    @POST("users/update/{id}")
    Call<User> updateByUser(
            @Path("id") String id,
            @Body User user);
    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String id);

}
