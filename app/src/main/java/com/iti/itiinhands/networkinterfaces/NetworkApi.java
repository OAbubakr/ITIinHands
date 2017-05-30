package com.iti.itiinhands.networkinterfaces;

import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.LoginResponse;
import com.iti.itiinhands.model.StudentDataByTrackId;
import com.iti.itiinhands.model.schedule.SessionModel;

import java.util.ArrayList;

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

    @GET("getStudentGrades")
    public Call<List<StudentGrade>> getGrades(@Query("id") int id);
//    (@Query("userType") int userType,@Query("userName") String userName,
//                                           @Query("password") String password);

    @GET("getBranches")
    public Call<ArrayList<Branch>> getBranches();

    @GET("getCourses")
    public Call<ArrayList<Course>> getCoursesByTrack(@Query("trackId")int id);


    @GET("getEvents")
    public Call<List<Event>> getEvents();

    @GET("postJob")
    public Call<Void> postJob(@Query("companyId") int companyId, @Query("jobCode") String jopCode,
                                        @Query("jobTitle") String jopTitle, @Query("jobDesc") String jopDesc,
                                        @Query("experience") String experience, @Query("closingDate") String closingDate,
                                        @Query("sendTo") String sendTo, @Query("jobNoNeed") int jopNoNeed,
                                        @Query("subTrackId") int subTrackId , @Query("jobDate") String jopDate);

    @GET("getInstructorSchedule")
    public  Call<List<SessionModel>> getInstructorSchedule(@Query("instructorId")int instructorId );

    @GET("getStudentSchedule")
    public  Call<List<SessionModel>> getStudentSchedule(@Query("studentId") int studentId);


    @GET("getTrackSchedule")
    Call<List<SessionModel>> getTrackSchedule(@Query("trackId")int trackId);


//////////
@GET("getStudentsByTrackId")
    public Call<ArrayList<StudentDataByTrackId>>getAllStudentsByTracId(@Query("id")int id);

}
