package com.iti.itiinhands.networkinterfaces;

import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.LoginResponse;
import com.iti.itiinhands.model.schedule.SessionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
    @POST("getStudentSchedule")
    public Call<SessionModel> getStudentSchedule (@Body LoginRequest request);
}
