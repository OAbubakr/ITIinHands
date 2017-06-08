package com.iti.itiinhands.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
//import com.iti.itiinhands.networkinterfaces.Response;

/**
 * Created by Mahmoud on 5/22/2017.
 */

public class LoginResponse extends Response implements Serializable {

    private UserLogin data;
    private String statusLogin;
    private String errorLogin;

    public UserLogin getData() {
        return data;
    }

    public void setData(UserLogin data) {
        this.data = data;
    }


    public String getStatusLogin() {
        return statusLogin;
    }


    public void setStatusLogin(String statusLogin) {
        this.statusLogin = statusLogin;
    }


    public String getErrorLogin() {
        return errorLogin;
    }


    public void setErrorLogin(String errorLogin) {
        this.errorLogin = errorLogin;
    }



}
