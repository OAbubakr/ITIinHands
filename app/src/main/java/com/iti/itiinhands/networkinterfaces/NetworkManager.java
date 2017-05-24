package com.iti.itiinhands.networkinterfaces;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 5/22/2017.
 */

public class NetworkManager {


    private static final String BASEURL = "http://172.16.4.239:8084/restfulSpring/";
    private static NetworkManager newInstance;
    private static Retrofit retrofit ;

//    private NetworkResponse network;
    private Context context;

    //ur activity must implements NetworkResponse

    private NetworkManager(Context context) {
        this.context = context;
    }

    public static NetworkManager getInstance(Context context){
        if(newInstance == null){
            synchronized (NetworkManager.class){
                if(newInstance==null){
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



    public void getLoginAuthData(NetworkResponse networkResponse,int userId,String userName,String password){
        final NetworkResponse network=networkResponse;

        NetworkApi web = retrofit.create(NetworkApi.class);

//        Call<LoginResponse> call = web.onLoginAuth(userId,userName,password);
        Call<LoginResponse> call = web.onLoginAuth(new LoginRequest(userId,userName,password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                network.onResponse(loginResponse);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("network",t.toString());
                network.onFaliure();
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

}
