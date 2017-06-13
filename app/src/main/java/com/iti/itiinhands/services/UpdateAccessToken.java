package com.iti.itiinhands.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.utilities.Constants;

public class UpdateAccessToken extends IntentService {

    private static final long SLEEP_FOR = 1000 * 60 * 30;
    private static final long FREQUENCY = 1000 * 60 * 20;

    public UpdateAccessToken() {
        super("UpdateAccessToken");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            while(true) {
                try {
                    Thread.sleep(SLEEP_FOR);   //sleep for 30 mins
                    NetworkManager networkManager = NetworkManager.getInstance(getApplicationContext());
                    if(networkManager.isOnline()) {

                        SharedPreferences sharedPreferences = getApplicationContext()
                                .getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);

                        long accessTokenExpiryDate = sharedPreferences.getLong(Constants.EXPIRY_DATE, -1);
                        if (accessTokenExpiryDate != -1) {
                            long remainingTime = accessTokenExpiryDate - System.currentTimeMillis();
                            if (remainingTime <= FREQUENCY) { //before 20 minutes of expiration
                                String refreshToken = sharedPreferences.getString(Constants.REFRESH_TOKEN, "");
                                networkManager.renewAccessToken(refreshToken);
                            }
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
