package com.iti.itiinhands.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;

public class ScheduleChanged extends IntentService implements NetworkResponse {


    public ScheduleChanged() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.USER_SHARED_PREFERENCES,0);


        UserData userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT,""));

        NetworkManager networkManager =NetworkManager.getInstance(getApplicationContext());
        networkManager.sendScheduleChange(this,userData.getEmployeePlatformIntake());



    }

    @Override
    public void onResponse(Response response) {
        if(response!=null && getApplicationContext()!=null&& response.getStatus().equals(Response.SUCCESS)){

            Handler handler=new Handler(Looper.getMainLooper());
            handler.post(new Runnable(){
                public void run(){
                   if(getApplicationContext()!=null) Toast.makeText(getApplicationContext(), "Notification Sent", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {failed();}

    }

    @Override
    public void onFailure() {
        failed();
    }

    private void failed(){

        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            public void run(){

                new NetworkUtilities().networkFailure(getApplicationContext());
            }
        });

    }
}

