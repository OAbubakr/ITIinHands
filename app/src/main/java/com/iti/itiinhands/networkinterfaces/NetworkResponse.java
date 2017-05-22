package com.iti.itiinhands.networkinterfaces;

/**
 * Created by admin on 5/22/2017.
 */

public interface NetworkResponse {


    //we can add many onResponse Methods depending on the parameter (our model)

    /*
    public void onResponse(Model model);
     */

    public void onResponse();


    public void onFaliure();



}
