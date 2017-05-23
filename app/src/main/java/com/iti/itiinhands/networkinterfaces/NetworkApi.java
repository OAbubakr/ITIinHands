package com.iti.itiinhands.networkinterfaces;

import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by admin on 5/22/2017.
 */

public interface NetworkApi {

    //An example
    /*
    @GET("example")
    public Call<Model>getModel();

    @GET("example/{id}")
    public Call<Model>getModel(@Path("id")int id);

    @GET("example")
    public Call<Model>getModel(@Query("id")int id);
     */
//    @FormUrlEncoded
    @GET("login/onLoginAuth")
    public Call<LoginResponse> onLoginAuth(@Query("userType") int userType,@Query("userName") String userName,
                                           @Query("password") String password);
}
