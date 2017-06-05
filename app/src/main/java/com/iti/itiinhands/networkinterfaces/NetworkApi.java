package com.iti.itiinhands.networkinterfaces;

import com.iti.itiinhands.beans.*;
import com.iti.itiinhands.beans.InstructorEvaluation;
import com.iti.itiinhands.beans.JobOpportunity;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Company;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.beans.EmpHour;
import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.JobVacancy;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.LoginResponse;
import com.iti.itiinhands.model.Permission;
import com.iti.itiinhands.model.StudentDataByTrackId;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.*;
import com.iti.itiinhands.model.behance.BehanceData;
import com.iti.itiinhands.model.schedule.SessionModel;

import java.util.ArrayList;

import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.schedule.Supervisor;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by admin on 5/22/2017.
 */

public interface NetworkApi {

    @POST("login/onLoginAuth")
    public Call<Response> onLoginAuth(@Body LoginRequest request);

    @GET("getEmpHours")
    public Call<Response> getEmpHours(@Query("id") int id , @Query("start") String start , @Query("end") String end);

    @GET("getStudentGrades")
    public Call<Response> getGrades(@Query("id") int id);
//    (@Query("userType") int userType,@Query("userName") String userName,
//                                           @Query("password") String password);

    @GET("getBranches")
    public Call<Response> getBranches();

    @GET("getCourses")
    public Call<Response> getCoursesByTrack(@Query("trackId")int id);


    @POST("getStudentSchedule")
    public Call<Response> getStudentSchedule (@Body LoginRequest request);



    @GET("getEvents")
    public Call<Response> getEvents();

    @GET("getCompanyProfile")
    public Call<Response> getCompanyProfile(@Query("companyID")int id);

    @GET("getAllVacancies")
    public Call<Response> getJobs();

    @GET("profile/onGetUserData")
    public Call<Response> getUserData(@Query("userType") int userType,@Query("userId") int userId);

    @POST("profile/onSetUserData")
    public Call<Response> setUserData(@Query("userType") int userType,@Query("userId") int userId,@Body UserData userData);

    @POST("postJob")
    public Call<Response> postJob(@Body JobOpportunity jobOpportunity);

    @GET("getInstructorSchedule")
    public  Call<Response> getInstructorSchedule(@Query("instructorId")int instructorId );

    @GET("getStudentSchedule")
    public  Call<Response> getStudentSchedule(@Query("studentId") int studentId);


    @GET("getTrackSchedule")
    Call<Response> getTrackSchedule(@Query("trackId")int trackId);


//////////
@GET("getStudentsByTrackId")
    public Call<Response>getAllStudentsByTracId(@Query("id")int id);

    @GET
    public Call<Response> getBehanceData(@Url String url,@Query("api_key") String apiKey);

    @GET
    public Call<Response> getGitData(@Url String url);


    @GET("getInstructorByBranch")
    public Call<Response> getInstructorByBranch(@Query("id") int branchId, @Query("excludeId") int excludeId);

    @GET("getBranchesNames")
    public Call<Response> getBranchesNames();

    @Multipart
    @POST("{id}/fileupload")
    Call<Response> uploadImage(@Part MultipartBody.Part file , @Path("id") int id);


    @GET("getSupervisorByTrackId")
    public Call<Response>getSupervisor(@Query("id") int id);


    @POST("addPermission")
    public Call<Response> sendPermission(@Body Permission permission);


    @GET("getInstructorEvaluation")
    public Call<Response> getInstructorEvaluation(@Query("instId") int instId);

}
