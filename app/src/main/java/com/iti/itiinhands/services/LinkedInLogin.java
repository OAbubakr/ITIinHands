package com.iti.itiinhands.services;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mahmoud on 6/1/2017.
 */

public class LinkedInLogin {

    private static final String PACKAGE = "com.iti.itiinhands";
    private Context context;
    public LinkedInLogin(Context context){
        this.context = context;
        generateHashKey();
    }



    public void generateHashKey() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    PACKAGE,PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.i("result",Base64.encodeToString(md.digest(), Base64.DEFAULT));


            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Name not found", e.getMessage(), e);

        } catch (NoSuchAlgorithmException e) {
            Log.d("Error", e.getMessage(), e);
        }
    }



}
