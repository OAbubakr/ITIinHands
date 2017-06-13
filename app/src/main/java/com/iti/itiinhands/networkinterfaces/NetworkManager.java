package com.iti.itiinhands.networkinterfaces;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.iti.itiinhands.activities.LoginActivity;
import com.iti.itiinhands.beans.JobOpportunity;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.GitData;
import com.iti.itiinhands.model.LoginResponse;
import com.iti.itiinhands.model.Permission;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.behance.BehanceData;
import com.iti.itiinhands.services.UpdateAccessToken;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.io.File;
import java.io.IOException;
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


//    public static final String BASEURL = "http://172.16.4.239:8084/restfulSpring/";
//    private static final String BASEURL = "http://172.16.2.40:8085/restfulSpring/"; // Ragab ip and url

//    private static final String BASEURL = "http://172.16.3.46:9090/restfulSpring/"; // Omar ITI
//    private static final String BASEURL = "http://192.168.1.17:8085/restfulSpring/"; // Omar ITI
    public static final String BASEURL = "http://172.16.3.46:9090/restfulSpring/";

    private static NetworkManager newInstance;
    private static Retrofit retrofit;
    private static final String API_KEY_BEHANCE = "SXf62agQ8r0xCNCSf1q30HJMmozKmAFA";
    //    private NetworkResponse network;
    private static Context context;
    public static final int RENEW_INTERCEPTOR = 0;
    public static final int RENEW_ALARM_MANAGER = 1;
    //ur activity must implements NetworkResponse
    private static boolean IS_UPDATING_ACCESS_TOKEN = false;
    public static boolean AUTHINTICATION_REQUIRED = false;
    public Retrofit getRetrofit(){
        return retrofit;
    }

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
                .connectTimeout(connectTime, TimeUnit.SECONDS).addInterceptor(new AddHeaderInterceptor())

                .build();

        return okHttpClient;
    }


    private static final class AddHeaderInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            final SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
            String token = sharedPreferences.getString(Constants.TOKEN, "");
            Request request = chain.request();
            request = request.newBuilder().addHeader("Authorization", token).build();
            okhttp3.Response response = chain.proceed(request);
            ResponseBody responseBody = response.body();
            String responseAsString = responseBody.string();

            try {
                Response jsonResponse = new Gson().fromJson(responseAsString, Response.class);

                if (jsonResponse.getStatus().equals(Response.FAILURE)) {

                    if (jsonResponse.getError().equals(Response.EXPIRED_ACCESS_TOKEN)) {


                        if(AUTHINTICATION_REQUIRED){

                            SharedPreferences setting = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                            if (setting.getBoolean(Constants.LOGGED_FLAG, false)) {
                                //un subscribe from topics

                                int userType = sharedPreferences.getInt(Constants.USER_TYPE, 0);
                                UserData userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
                                String myId = String.valueOf(userData.getId());
                                String myType = "";
                                switch (userType) {
                                    case 1:
                                        myType = "student";
                                        break;
                                    case 2:
                                        myType = "staff";
                                        break;
                                }
                                String myChatId = myType + "_" + myId;

                                //unsubscribe from topics
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("events");
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("jobPosts");
                                FirebaseMessaging.getInstance().unsubscribeFromTopic(myChatId);

                                SharedPreferences.Editor editor = setting.edit();
                                editor.remove(Constants.LOGGED_FLAG);
                                editor.remove(Constants.TOKEN);
                                editor.remove(Constants.USER_TYPE);
                                editor.remove(Constants.USER_OBJECT);
                                editor.apply();

                                //stop the service
                                boolean stopped = context.stopService(new Intent(context, UpdateAccessToken.class));

                      //          Toast.makeText(context, "Expired session", Toast.LENGTH_SHORT).show();

                                AUTHINTICATION_REQUIRED = false;

                                Intent intent = new Intent(context, LoginActivity.class);
                                ComponentName cn = intent.getComponent();
                                Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                context.startActivity(mainIntent);

                            }


                        }

                        String refreshToken = sharedPreferences.getString(Constants.REFRESH_TOKEN, "");
                        if (!refreshToken.isEmpty() & !IS_UPDATING_ACCESS_TOKEN) {
                            NetworkManager.getInstance(context).renewAccessToken(refreshToken);
                        }

                    } else if (jsonResponse.getError().equals(Response.EXPIRED_REFRESH_TOKEN)) {
                        AUTHINTICATION_REQUIRED = true;
                    }
                }
            } catch (Exception e) {
                    e.printStackTrace();

            }
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), responseAsString))
                    .build();

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
    public void uploadImage(NetworkResponse networkResponse, String imagePath, int id) {
        final NetworkResponse network = networkResponse;
        System.out.println("##########################################");
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.uploadImage(fileToUpload, id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                 Toast.makeText(this, response.body().toString() ,Toast.LENGTH_SHORT).show();
                System.out.println("********************* " + response.body());
                network.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                System.out.println("********************* failed");
                System.out.println(t.toString());

            }
        });

    }


//    -------------------------------------Upload image for graduates-------------------------------------------------

    public void uploadImageGraduates(NetworkResponse networkResponse, String imagePath, int id) {
        final NetworkResponse network = networkResponse;
        System.out.println("##########################################");
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.uploadImageGraduates(fileToUpload, id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                 Toast.makeText(this, response.body().toString() ,Toast.LENGTH_SHORT).show();
                System.out.println("********************* " + response.body());
                network.onResponse(response.body());

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


    public void getInstructorsByBranch(final NetworkResponse networkResponse, int branchId, int excludeID) {
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


    public void getLoginAuthData(NetworkResponse networkResponse, Call<LoginResponse> call) {

        final NetworkResponse network = networkResponse;


//        Call<LoginResponse> call = web.onLoginAuth(userId,userName,password);

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
        Call<Response> call = web.getUserData(id, userType);
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
        Call<Response> call = web.setUserData(userType, userId, userdata);
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
    public void postJob(final NetworkResponse networkResponse, JobOpportunity jobOpportunity) {

        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.postJob(jobOpportunity);

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


    //    ----------------------------------- get all graduates data -----------------------------------
    public void getAllGraduatesByTracId(final NetworkResponse networkResponse, int intakeid, int platformid) {

        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getAllGraduatesByTracId(intakeid, platformid);
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
//    -------------------------------------------Get Intakes-----------------------------------------------------

    public void getIntakes(final NetworkResponse networkResponse) {
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getIntakes();
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


//    ------------------------------------------------------------------------------------------------

    ////////////////////get behance data/////////
    public void getBehanceData(final NetworkResponse networkResponse, String name) {
        String url = "https://api.behance.net/v2/users/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .client(new OkHttpClient.Builder()
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .build())
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

    public void getCompanyProfile(NetworkResponse networkResponse, int id) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
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
                Log.e("network", t.toString());
                network.onFailure();
            }
        });

    }


    public void getAllJobs(NetworkResponse networkResponse) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
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
                Log.e("network", t.toString());
                network.onFailure();

            }
        });

    }

    //////////get git data////
    public void getGitData(final NetworkResponse networkResponse, String name) {
        String url = "https://api.github.com/users/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .client(new OkHttpClient.Builder()
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .build())
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
    public void getSupervisor(final NetworkResponse networkResponse, int id) {
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


    public void sendPermission(final NetworkResponse networkResponse, Permission permission) {
        NetworkApi web = retrofit.create(NetworkApi.class);

        Call<Response> call = web.sendPermission(permission);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                networkResponse.onResponse(response.body());
                System.out.print(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                System.out.print(t.toString());
                networkResponse.onFailure();

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
    public void getAllCompaniesData(NetworkResponse networkResponse) {
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

    public void getUserProfileDataOther(NetworkResponse networkResponse, int userType, int id) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.getUserDataOther(id, userType);
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

    public void sendScheduleChange(NetworkResponse networkResponse, int platformIntakeID) {
        final NetworkResponse network = networkResponse;
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.sendScheduleChange(platformIntakeID);
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


//    public void renewAccessToken(String refreshToken, final int flag) {
//        NetworkApi web = retrofit.create(NetworkApi.class);
//        Call<Response> call = web.renewAccessToken(refreshToken);
//        call.enqueue(new Callback<Response>() {
//             @Override
//             public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                 if (response != null) {
//                     if (response.body().getStatus().equals(Response.SUCCESS)) {
//                         if (flag == RENEW_INTERCEPTOR) {
//
//                             Log.v("ITI_Test", "access token saved");
//
//                             LinkedTreeMap<String, Object> linkedTreeMap =
//                                     (LinkedTreeMap<String, Object>) response.body().getResponseData();
//                             String access_token = (String) linkedTreeMap.get("access_token");
//                             double expiry_date = (double) linkedTreeMap.get("expiry_date");
//
//                             SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
//                             SharedPreferences.Editor editor = sharedPreferences.edit();
//                             editor.putString(Constants.TOKEN, access_token);
//                             editor.putLong(Constants.EXPIRY_DATE, (long) expiry_date);
//                             editor.apply();
//
//                         } else if (flag == RENEW_ALARM_MANAGER) {
//
//                             Response response1 = response.body();
//                             if (response1 != null) {
//                                 Intent intent = new Intent(context, UpdateAccessTokens.class);
//                                 intent.setAction(DOWNLOAD_FINISHED);
//                                 intent.putExtra("response", response1);
//                                 context.sendBroadcast(intent);
//                             }
//
//                         }
//
//
//                     }
//
//                 }
//
//             }
//
//             @Override
//             public void onFailure(Call<Response> call, Throwable t) {
//                 t.printStackTrace();
//                 Log.e("network", t.toString());
//             }
//         }
//
//        );
//    }


    public void renewAccessToken(String refreshToken) {
        NetworkApi web = retrofit.create(NetworkApi.class);
        Call<Response> call = web.renewAccessToken(refreshToken);
        call.enqueue(new Callback<Response>() {
                         @Override
                         public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                             if (response != null && context!=null) {
                                 IS_UPDATING_ACCESS_TOKEN = false;
                                 if (response.body().getStatus().equals(Response.SUCCESS)) {
                                     LinkedTreeMap<String, Object> linkedTreeMap =
                                             (LinkedTreeMap<String, Object>) response.body().getResponseData();
                                     String access_token = (String) linkedTreeMap.get("access_token");
                                     double expiry_date = (double) linkedTreeMap.get("expiry_date");

                                     SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                                     SharedPreferences.Editor editor = sharedPreferences.edit();
                                     editor.putString(Constants.TOKEN, access_token);
                                     editor.putLong(Constants.EXPIRY_DATE, (long) expiry_date);
                                     editor.apply();

                     }

                 }

             }

             @Override
             public void onFailure(Call<Response> call, Throwable t) {
                 IS_UPDATING_ACCESS_TOKEN = false;
                 t.printStackTrace();
                 Log.e("network", t.toString());
             }
         }

        );
    }

}
