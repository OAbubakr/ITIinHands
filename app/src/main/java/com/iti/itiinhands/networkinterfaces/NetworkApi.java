package com.iti.itiinhands.networkinterfaces;

import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.LoginResponse;

import java.util.ArrayList;

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
//    @GET("login/onLoginAuth")
    @POST("login/onLoginAuth")
    public Call<LoginResponse> onLoginAuth(@Body LoginRequest request);
//    (@Query("userType") int userType,@Query("userName") String userName,
//                                           @Query("password") String password);

    @GET("getBranches")
    public Call<ArrayList<Branch>> getBranches();

    @GET("getCourses")
    public Call<ArrayList<Course>> getCoursesByTrack(@Query("trackId")int id);
}
