package com.iti.itiinhands.broadcast_receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.utilities.Constants;

import org.json.JSONObject;

import java.util.LinkedHashMap;

public class UpdateAccessTokens extends BroadcastReceiver {

    private static final long DELTA = 1000 * 60 * 30;    //30 mins
    public static final long REFRESH_FREQUENCY_SHORT = 1000 * 60 * 10;    //10 mins
    public static final long REFRESH_FREQUENCY_LONG = 1000 * 60 * 40;    //40 mins

    public static final String DOWNLOAD_FINISHED = "DOWNLOAD_FINISHED";
    public static final String UPDATE_ACCESS_TOKEN_ALARM = "UPDATE_ACCESS_TOKEN_ALARM";

    public UpdateAccessTokens() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkManager networkManager = NetworkManager.getInstance(context);

        String action = intent.getAction();
        if(action != null) {
            if (action.equals(DOWNLOAD_FINISHED)) {
                Response data = (Response) intent.getSerializableExtra("response");
                if (data.getStatus().equals(Response.SUCCESS)) {
                    LinkedHashMap<String, Object> linkedTreeMap = (LinkedHashMap<String, Object>) data.getResponseData();
                    String access_token = (String) linkedTreeMap.get("access_token");
                    double expiry_date = (double) linkedTreeMap.get("expiry_date");

                    createAlarm(context, System.currentTimeMillis() + REFRESH_FREQUENCY_LONG, 0);

                    SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.TOKEN, access_token);
                    editor.putLong(Constants.EXPIRY_DATE, (long) expiry_date);
                    editor.apply();

                }
            } else if (action.equals(UPDATE_ACCESS_TOKEN_ALARM)) {
                if (networkManager.isOnline()) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                    long accessTokenExpiryDate = sharedPreferences.getLong(Constants.EXPIRY_DATE, -1);
                    if (accessTokenExpiryDate != -1) {
                        long remainingTime = accessTokenExpiryDate - System.currentTimeMillis();
                        createAlarm(context, System.currentTimeMillis() + REFRESH_FREQUENCY_SHORT, 0);

                        if (remainingTime <= DELTA) {
                            String refreshToken = sharedPreferences.getString(Constants.REFRESH_TOKEN, "");
                            networkManager.renewAccessToken(refreshToken, NetworkManager.RENEW_ALARM_MANAGER);
                        }

                    }

                }

            }
        }


    }

    public static void createAlarm(Context context, long date, int id) {
        Intent intent = new Intent(context, UpdateAccessTokens.class);
        intent.setAction(UPDATE_ACCESS_TOKEN_ALARM);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, date, pendingIntent);
    }



}
