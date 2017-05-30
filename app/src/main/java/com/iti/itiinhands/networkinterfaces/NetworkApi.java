package com.iti.itiinhands.networkinterfaces;

import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.*;
import com.iti.itiinhands.model.schedule.SessionModel;
import com.iti.itiinhands.model.Response;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by admin on 5/22/2017.
 */

public interface NetworkApi {

    @POST("login/onLoginAuth")
    public Call<Response> onLoginAuth(@Body LoginRequest request);

    @GET("getStudentGrades")
    public Call<List<StudentGrade>> getGrades(@Query("id") int id);

    @POST("getStudentSchedule")
    public Call<SessionModel> getStudentSchedule (@Body LoginRequest request);

    @GET("getEvents")
    public Call<List<Event>> getEvents();

    @GET("profile/onGetUserData")
    public Call<Response> getUserData(@Query("userType") int userType,@Query("userId") int userId);

    @GET("profile/onSetUserData")
    public Call<Response> setUserData(@Query("userType") int userType,@Query("userId") int userId,@Query("userData") UserData userData);
}
