package com.iti.itiinhands.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//import com.iti.itiinhands.networkinterfaces.Response;

/**
 * Created by Mahmoud on 5/22/2017.
 */

public class LoginResponse {
    @SerializedName("data")
    @Expose
    private int data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private String error;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
