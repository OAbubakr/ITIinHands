package com.iti.itiinhands.networkinterfaces;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.iti.itiinhands.activities.EmployeeHours;
import com.iti.itiinhands.beans.InstructorEvaluation;
import com.iti.itiinhands.beans.JobOpportunity;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Company;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.model.GitData;
import com.iti.itiinhands.model.LoginResponse;
import com.iti.itiinhands.model.Permission;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.beans.EmpHour;
import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.JobVacancy;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.StudentDataByTrackId;
import com.iti.itiinhands.model.behance.BehanceData;
import com.iti.itiinhands.model.schedule.SessionModel;
import com.iti.itiinhands.model.schedule.Supervisor;
import com.iti.itiinhands.utilities.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 5/22/2017.
 */

public class NetworkManager {


//    private static final String BASEURL = "http://172.16.4.239:8084/restfulSpring/";
//    private static final String BASEURL = "http://172.16.2.40:8085/restfulSpring/"; // Ragab ip and url

//    private static final String BASEURL = "http://172.16.3.46:9090/restfulSpring/"; // Omar ITI
    private static final String BASEURL = "http://192.168.1.3:8085/restfulSpring/"; // Omar ITI

//    private static final String BASEURL = "http://192.168.43.4:8090/restfulSpring/";
//    private static final String BASEURL = "http://172.16.2.40:8085/restfulSpring/";
    private static NetworkManager newInstance;
    private static Retrofit retrofit;
    private static final String API_KEY_BEHANCE = "SXf62agQ8r0xCNCSf1q30HJMmozKmAFA";
    //    private NetworkResponse network;
    private static Context context;

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
                            .baseUrl(BASEURL).client(getRequestHeader(60, 60))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return newInstance;
    }

    private static OkHttpClient getRequestHeader(int readTime, int connectTime) {


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(readTime, TimeUnit.SECONDS)
                .connectTimeout(connectTime, TimeUnit.SECONDS).addNetworkInterceptor(new AddHeaderInterceptor())
                .build();

        return okHttpClient;
    }


    private static final class AddHeaderInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            SharedPreferences data = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
            String token = data.getString(Constants.TOKEN,"");
            Request request = chain.request();
            request = request.newBuilder().addHeader("Authorization",token ).build();
            return chain.proceed(request);
        }
    }

    public void getStudentsGrades(NetworkResponse networkResponse, int id) {
        final NetworkResponse network = networkResponse;

        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getGrades(id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                network.onResponse(response.body());  //return list of studentgrades { List<StudentGrade> }
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });
    }
    //    ----------------------- upload  images -----------------------------------------------------
    public void uploadImage(NetworkResponse networkResponse,String imagePath ,int id) {
        final NetworkResponse network = networkResponse;
        System.out.println("##########################################");
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.uploadImage(fileToUpload,id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                 Toast.makeText(this, response.body().toString() ,Toast.LENGTH_SHORT).show();
                System.out.println("********************* "+response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                System.out.println("********************* failed");
                System.out.println(t.toString());

            }
        });

    }
    //    -----------------------get employee hours-----------------------------------------------------
    public void getEmployeeHours(NetworkResponse networkResponse, int id, String start, String end) {
        final NetworkResponse network = networkResponse;

        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getEmpHours(id, start, end);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                network.onResponse(response.body());

//                System.out.println("response is" + response.body().getWorkingDays());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });
    }
//--------------------------------------------------------------------------------------------------
    //--------------------------------GET LOGIN AUTH DATA-------------------------------------------


    public void getInstructorsByBranch(final NetworkResponse networkResponse, int branchId, int excludeID){
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getInstructorByBranch(branchId, excludeID);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                networkResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                networkResponse.onFailure();
            }
        });

    }

    public void getBranchesNames(final NetworkResponse networkResponse) {
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getBranchesNames();
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                networkResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                networkResponse.onFailure();
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
                Response loginResponse = response.body();
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
    public void getEvents(NetworkResponse networkResponse) {

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getEvents();

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                ArrayList<Event> events = (ArrayList<Event>) response.body();
                network.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });
    }

    //////////////////////////////////getProfile data /////////////
    public void getUserProfileData(NetworkResponse networkResponse, int userType, int id) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getUserData(id,userType);
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
    ///////////////////setProfile data///////////////
    public void setUserProfileData(NetworkResponse networkResponse, int userType, int userId, UserData userdata) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.setUserData(userType, userId,userdata);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                Response result = response.body();
//                network.onResponse(result);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
//                t.printStackTrace();
//                Log.e("network", t.toString());
//                network.onFailure();
            }
        });


    }

    ////////////////////////////////////////////
    //-------------------------------------POST JOB-------------------------------------------------
    public void postJob(NetworkResponse networkResponse, JobOpportunity jobOpportunity) {

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.postJob(jobOpportunity);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
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

        Call<Response> call = web.getBranches();

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                network.onResponse(response.body());
            }
            @Override
            public void onFailure(Call<Response> call, Throwable t) {

                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });

    }

    public void getCoursesByTrack(NetworkResponse networkResponse, int id) {

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getCoursesByTrack(id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                ArrayList<Course> courses = response.body();
                network.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });
    }

    public void getInstructorSchedule(NetworkResponse networkResponse, int id) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);


        Call<Response> call = web.getInstructorSchedule(id);

        call.enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                network.onResponse(response.body());

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                network.onFailure();

            }
        });

    }


    public void getStudentSchedule(NetworkResponse networkResponse, int id) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);

        Call<Response> call = web.getStudentSchedule(id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                network.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                network.onFailure();

            }
        });

    }


    public void getTrackSchedule(NetworkResponse networkResponse, int id) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);

        Call<Response> call = web.getTrackSchedule(id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                network.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                network.onFailure();

            }
        });

    }


    public void getAllStudentsByTrackId(final NetworkResponse networkResponse, int id) {

        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getAllStudentsByTracId(id);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                networkResponse.onResponse(response.body());

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

                networkResponse.onFailure();

            }
        });


    }


    ////////////////////get behance data/////////
    public void getBehanceData(final NetworkResponse networkResponse, String name) {
        String url = "https://api.behance.net/v2/users/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<BehanceData> call = web.getBehanceData(name, API_KEY_BEHANCE);
        call.enqueue(new Callback<BehanceData>() {
            @Override
            public void onResponse(Call<BehanceData> call, retrofit2.Response<BehanceData> response) {
                networkResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<BehanceData> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                networkResponse.onFailure();
            }
        });
    }

    public void getCompanyProfile(NetworkResponse networkResponse,int id){
        final NetworkResponse network=networkResponse;
        NetworkApi web =retrofit.create(NetworkApi.class);
        Call<Response> call = web.getCompanyProfile(id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                Company company = response.body();
                network.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

                t.printStackTrace();
                Log.e("network",t.toString());
                network.onFailure();
            }
        });

    }


    public void getAllJobs(NetworkResponse networkResponse){
        final NetworkResponse network=networkResponse;
        NetworkApi web =retrofit.create(NetworkApi.class);
        Call<Response> call = web.getJobs();
        call.enqueue(new Callback<Response>() {


            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                ArrayList<JobVacancy> jobVacancies = (ArrayList<JobVacancy>) response.body();
                network.onResponse(response.body());

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                Log.e("network",t.toString());
                network.onFailure();

            }
        });

    }

    //////////get git data////
    public void getGitData(final NetworkResponse networkResponse, String name) {
        String url = "https://api.github.com/users/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .client(getRequestHeader(60, 60))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<GitData> call = web.getGitData(name);
        call.enqueue(new Callback<GitData>() {
            @Override
            public void onResponse(Call<GitData> call, retrofit2.Response<GitData> response) {
                networkResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<GitData> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                networkResponse.onFailure();
            }
        });
    }
///////////get supervisor
    public void getSupervisor(final NetworkResponse networkResponse , int id){
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getSupervisor(id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                networkResponse.onResponse(response.body());

            }

            @Override
            public void onFailure(Call<Response> call, Throwable throwable) {

                networkResponse.onFailure();

            }
        });



    }


public void sendPermission(NetworkResponse networkResponse , Permission permission){
    NetworkApi web = retrofit.create(NetworkApi.class);

    Call<Response>call = web.sendPermission(permission);

    call.enqueue(new Callback<Response>() {
        @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

System.out.print(response.body());
        }

        @Override
        public void onFailure(Call<Response> call, Throwable t) {
            System.out.print(t.toString());



        }
    });



}





    //------------------------------------GET Instructor Evaluation------------------------------------------------
    public void getInstructorEvaluation(NetworkResponse networkResponse, int instId) {

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getInstructorEvaluation(instId);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                ArrayList<InstructorEvaluation> instructorEvaluations = (ArrayList<InstructorEvaluation>) response.body();
                network.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });
    }

    /////////////////////////////Get all Companies////////////////////
    public void getAllCompaniesData(NetworkResponse networkResponse){
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getAllCompanies();

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                network.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                Log.e("network", t.toString());
                network.onFailure();
            }
        });
    }
}
