package com.iti.itiinhands;

import com.iti.itiinhands.networkinterfaces.NetworkResponse;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 5/22/2017.
 */

public class NetworkManager {


    private static final String baseUrl="";

    private static Retrofit retrofit = null;

    private NetworkResponse network;

    //ur activity must implements NetworkResponse

    public NetworkManager(NetworkResponse network){
        this.network =network;

    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
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








}
