package com.iti.itiinhands.networkinterfaces;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.LoginResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 5/22/2017.
 */

public class NetworkManager {


//    private static final String BASEURL = "http://172.16.4.239:8084/restfulSpring/";
    private static final String BASEURL = "http://172.16.2.224:8084/restfulSpring/"; // Ragab ip and url
    private static NetworkManager newInstance;
    private static Retrofit retrofit;

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


    //--------------------------------------GET STUDENTS GRADES-------------------------------------
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
                network.onFaliure();
            }
        });
    }

    //--------------------------------GET LOGIN AUTH DATA-------------------------------------------
    public void getLoginAuthData(NetworkResponse networkResponse, int userId, String userName, String password) {

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
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
                network.onFaliure();
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
                network.onFaliure();
            }
        });
    }

    //-------------------------------------POST JOB-------------------------------------------------
    public void postJob(NetworkResponse networkResponse, int companyId, String jobCode,
                        String jobTitle, String jobDesc, String experience, String closingDate,
                        String sendTo, int jobNoNeed, int subTrackId, String jobDate){

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Void> call = web.postJob(companyId, jobCode, jobTitle, jobDesc, experience,
                                         closingDate, sendTo, jobNoNeed, subTrackId, jobDate);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Log.e("network",t.toString());
                network.onFaliure();
            }
        });
    }

    //----------------------------------------CHECK NETWORK-----------------------------------------
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


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

}
