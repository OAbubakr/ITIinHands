package com.iti.itiinhands.networkinterfaces;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Company;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.JobVacancy;
import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.LoginResponse;

import java.util.ArrayList;

import retrofit2.*;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 5/22/2017.
 */

public class NetworkManager {


   // private static final String BASEURL = "http://172.16.4.78:8084/restfulSpring/";
    private static final String BASEURL = "http://172.16.4.78:8084/restfulSpring/";
    private static NetworkManager newInstance;
    private static Retrofit retrofit ;

    //    private NetworkResponse network;
    private Context context;

    //ur activity must implements NetworkResponse

    private NetworkManager(Context context) {
        this.context = context;
    }

    public static NetworkManager getInstance(Context context) {
        if (newInstance == null) {
            synchronized (NetworkManager.class) {
                if (newInstance == null) {
                    newInstance = new NetworkManager(context);
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASEURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return newInstance;
    }


    public void getStudentsGrades(NetworkResponse networkResponse, int id) {
        final NetworkResponse network = networkResponse;

        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<List<StudentGrade>> call = web.getGrades(id);
        call.enqueue(new Callback<List<StudentGrade>>() {
            @Override
            public void onResponse(Call<List<StudentGrade>> call, retrofit2.Response<List<StudentGrade>> response) {
                network.onResponse(response.body());  //return list of studentgrades { List<StudentGrade> }
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<List<StudentGrade>> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });


    }

    public void getLoginAuthData(NetworkResponse networkResponse, int userId, String userName, String password) {
        final NetworkResponse network = networkResponse;

        NetworkApi web = retrofit.create(NetworkApi.class);

//        Call<LoginResponse> call = web.onLoginAuth(userId,userName,password);
        Call<LoginResponse> call = web.onLoginAuth(new LoginRequest(userId, userName, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                network.onResponse(loginResponse);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });


    }

    //------------------------------------GET EVENTS------------------------------------------------
    public void getEvents(NetworkResponse networkResponse){

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<List<Event>> call = web.getEvents();

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, retrofit2.Response<List<Event>> response) {
                ArrayList<Event> events =(ArrayList<Event>) response.body();
                network.onResponse(events);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                t.printStackTrace();
                Log.e("network",t.toString());
                network.onFailure();
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //An Example

    /*


     public void getModel(){

        NetworkApi web = retrofit.create(NetworkApi.class);

        Call<Model> call =web.getModel();

        call.enqueue( new Callback<Model>>(){

            @Override
            public void onResponse(Call<Model>> call, retrofit2.Response<Model> response) {

                Model model = response.body();

             network.onSellerResponse(model);


            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

                network.onFialure();


            }




        });





    }




     */


//    public  Retrofit getClient() {
//        if (retrofit == null) {
//            synchronized (NetworkManager.class) {
//                if (retrofit == null) {
//                    retrofit = new Retrofit.Builder()
//                            .baseUrl(baseUrl)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//                }
//            }
//        }
//        return retrofit;
//    }

    public void getBranches(NetworkResponse networkResponse){

        final NetworkResponse network=networkResponse;

        NetworkApi web = retrofit.create(NetworkApi.class);

        Call<ArrayList<Branch>> call = web.getBranches();

        call.enqueue(new Callback<ArrayList<Branch>>() {
            @Override
            public void onResponse(Call<ArrayList<Branch>> call, retrofit2.Response<ArrayList<Branch>> response) {

                ArrayList<Branch> branches = response.body();
                network.onResponse(branches);
            }

            @Override
            public void onFailure(Call<ArrayList<Branch>> call, Throwable t) {

                t.printStackTrace();
                Log.e("network",t.toString());
                network.onFailure();
            }
        });

    }

    public void getCoursesByTrack(NetworkResponse networkResponse,int id){

        final NetworkResponse network=networkResponse;
        NetworkApi web =retrofit.create(NetworkApi.class);
        Call<ArrayList<Course>> call = web.getCoursesByTrack(id);
        call.enqueue(new Callback<ArrayList<Course>>() {
            @Override
            public void onResponse(Call<ArrayList<Course>> call, Response<ArrayList<Course>> response) {
                ArrayList<Course> courses = response.body();
                network.onResponse(courses);
            }

            @Override
            public void onFailure(Call<ArrayList<Course>> call, Throwable t) {

                t.printStackTrace();
                Log.e("network",t.toString());
                network.onFailure();
            }
        });
    }

    public void getCompanyProfile(NetworkResponse networkResponse,int id){
        final NetworkResponse network=networkResponse;
        NetworkApi web =retrofit.create(NetworkApi.class);
        Call<Company> call = web.getCompanyProfile(id);
        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                Company company = response.body();
                network.onResponse(company);
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {

                t.printStackTrace();
                Log.e("network",t.toString());
                network.onFailure();
            }
        });

    }


    public void getAllJobs(NetworkResponse networkResponse){
        final NetworkResponse network=networkResponse;
        NetworkApi web =retrofit.create(NetworkApi.class);
        Call<List<JobVacancy>> call = web.getJobs();
        call.enqueue(new Callback<List<JobVacancy>>() {
            @Override
            public void onResponse(Call<List<JobVacancy>> call, Response<List<JobVacancy>> response) {
                ArrayList<JobVacancy> jobVacancies = (ArrayList<JobVacancy>) response.body();
                network.onResponse(jobVacancies);
            }

            @Override
            public void onFailure(Call<List<JobVacancy>> call, Throwable t) {
                t.printStackTrace();
                Log.e("network",t.toString());
                network.onFailure();

            }
        });

    }
}
