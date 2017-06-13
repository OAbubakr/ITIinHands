package com.iti.itiinhands.networkinterfaces;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by omari on 6/13/2017.
 */

public class NetworkUtilities {


    public  void networkFailure(Context context){
        if (context!= null){

            Toast.makeText(context, "Failed, please check you internet connection.", Toast.LENGTH_SHORT).show();

        }

    }


}
