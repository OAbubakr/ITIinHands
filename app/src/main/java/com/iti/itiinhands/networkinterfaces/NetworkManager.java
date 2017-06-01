package com.iti.itiinhands.networkinterfaces;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.beans.EmpHour;
import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.StudentDataByTrackId;
import com.iti.itiinhands.model.schedule.SessionModel;

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

    private static final String BASEURL = "http://172.16.2.40:8085/restfulSpring/"; // Ragab ip and url
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

//    -----------------------get employee hours-----------------------------------------------------
public void getEmployeeHours(NetworkResponse networkResponse, int id,String start,String end) {
    final NetworkResponse network = networkResponse;

    NetworkApi web = retrofit.create(NetworkApi.class);
    Call<EmpHour> call = web.getEmpHours(id,start,end);
    call.enqueue(new Callback<EmpHour>() {
        @Override
        public void onResponse(Call<EmpHour> call, retrofit2.Response<EmpHour> response) {
            network.onResponse(response.body());

            System.out.println("response is"+response.body().getWorkingDays());
        }

        @Override
        public void onFailure(Call<EmpHour> call, Throwable t) {
            t.printStackTrace();
            Log.e("network", t.toString());
            network.onFailure();
        }
    });


}
//--------------------------------------------------------------------------------------------------
    //--------------------------------GET LOGIN AUTH DATA-------------------------------------------

    public void getInstructorsByBranch(final NetworkResponse networkResponse, int branchId, int excludeId){
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<List<Instructor>> call = web.getInstructorByBranch(branchId, excludeId);
        call.enqueue(new Callback<List<Instructor>>() {
            @Override
            public void onResponse(Call<List<Instructor>> call, retrofit2.Response<List<Instructor>> response) {
                networkResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Instructor>> call, Throwable t) {
                t.printStackTrace();
                networkResponse.onFailure();
            }
        });

    }

    public void getBranchesNames(final NetworkResponse networkResponse){
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<List<Branch>> call = web.getBranchesNames();
        call.enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Call<List<Branch>> call, retrofit2.Response<List<Branch>> response) {
                networkResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Branch>> call, Throwable t) {
                t.printStackTrace();
                networkResponse.onFailure();
            }
        });

    }


    public void getLoginAuthData(NetworkResponse networkResponse, int userId, String userName, String password) {

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);

//        Call<LoginResponse> call = web.onLoginAuth(userId,userName,password);
        Call<com.iti.itiinhands.model.Response> call = web.onLoginAuth(new LoginRequest(userId, userName, password));
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response loginResponse = response.body();
                network.onResponse(loginResponse);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });


    }

    //------------------------------------GET EVENTS------------------------------------------------
    public void getEvents(NetworkResponse networkResponse) {

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<List<Event>> call = web.getEvents();

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, retrofit2.Response<List<Event>> response) {
                ArrayList<Event> events = (ArrayList<Event>) response.body();
                network.onResponse(events);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });
    }

    //////////////////////////////////getProfile data /////////////
    public void getStudentProfileData(NetworkResponse networkResponse, int userType, int userId) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getUserData(userType, userId);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response result = response.body();
                network.onResponse(result);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });


    }

    ////////////////////////////////////////////
    //-------------------------------------POST JOB-------------------------------------------------
    public void postJob(NetworkResponse networkResponse, int companyId, String jobCode,
                        String jobTitle, String jobDesc, String experience, String closingDate,
                        String sendTo, int jobNoNeed, int subTrackId, String jobDate) {

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
                Log.e("network", t.toString());
                network.onFailure();
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

    public void getBranches(NetworkResponse networkResponse) {

        final NetworkResponse network = networkResponse;

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
                Log.e("network", t.toString());
                network.onFailure();
            }
        });

    }

    public void getCoursesByTrack(NetworkResponse networkResponse, int id) {

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<ArrayList<Course>> call = web.getCoursesByTrack(id);
        call.enqueue(new Callback<ArrayList<Course>>() {
            @Override
            public void onResponse(Call<ArrayList<Course>> call, retrofit2.Response<ArrayList<Course>> response) {
                ArrayList<Course> courses = response.body();
                network.onResponse(courses);
            }

            @Override
            public void onFailure(Call<ArrayList<Course>> call, Throwable t) {

                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });
    }

    public void getInstructorSchedule(NetworkResponse networkResponse, int id) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);


        Call<List<SessionModel>> call = web.getInstructorSchedule(id);

        call.enqueue(new Callback<List<SessionModel>>() {

            @Override
            public void onResponse(Call<List<SessionModel>> call, retrofit2.Response<List<SessionModel>> response) {

                network.onResponse(response.body());

            }

            @Override
            public void onFailure(Call<List<SessionModel>> call, Throwable t) {
                network.onFailure();

            }
        });

    }


    public void getStudentSchedule(NetworkResponse networkResponse, int id) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);

        Call<List<SessionModel>> call = web.getStudentSchedule(id);
        call.enqueue(new Callback<List<SessionModel>>() {
            @Override
            public void onResponse(Call<List<SessionModel>> call, retrofit2.Response<List<SessionModel>> response) {
                network.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<SessionModel>> call, Throwable t) {
                network.onFailure();

            }
        });

    }


    public void getTrackSchedule(NetworkResponse networkResponse, int id) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);

        Call<List<SessionModel>> call = web.getTrackSchedule(id);
        call.enqueue(new Callback<List<SessionModel>>() {
            @Override
            public void onResponse(Call<List<SessionModel>> call, retrofit2.Response<List<SessionModel>> response) {
                network.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<SessionModel>> call, Throwable t) {
                network.onFailure();

            }
        });

    }


    public void getAllStudentsByTrackId(final NetworkResponse networkResponse, int id) {

        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<ArrayList<StudentDataByTrackId>> call = web.getAllStudentsByTracId(id);

        call.enqueue(new Callback<ArrayList<StudentDataByTrackId>>() {
            @Override
            public void onResponse(Call<ArrayList<StudentDataByTrackId>> call, retrofit2.Response<ArrayList<StudentDataByTrackId>> response) {

                networkResponse.onResponse(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<StudentDataByTrackId>> call, Throwable t) {

                networkResponse.onFailure();

            }
        });


    }


    ////////////////////get behance data/////////
    public void getBehanceData(final NetworkResponse networkResponse){

    }
}
