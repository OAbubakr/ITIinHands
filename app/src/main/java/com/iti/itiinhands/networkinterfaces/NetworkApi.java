package com.iti.itiinhands.networkinterfaces;

import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.LoginResponse;
import com.iti.itiinhands.model.schedule.SessionModel;

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
    public Call<LoginResponse> onLoginAuth(@Body LoginRequest request);

    @GET("getStudentGrades")
    public Call<List<StudentGrade>> getGrades(@Query("id") int id);

    @POST("getStudentSchedule")
    public Call<SessionModel> getStudentSchedule (@Body LoginRequest request);

    @GET("getEvents")
    public Call<List<Event>> getEvents();

    @GET("postJob")
    public Call<Void> postJob(@Query("companyId") int companyId, @Query("jobCode") String jopCode,
                                        @Query("jobTitle") String jopTitle, @Query("jobDesc") String jopDesc,
                                        @Query("experience") String experience, @Query("closingDate") String closingDate,
                                        @Query("sendTo") String sendTo, @Query("jobNoNeed") int jopNoNeed,
                                        @Query("subTrackId") int subTrackId , @Query("jobDate") String jopDate);
}
