package com.iti.itiinhands.model;

import java.io.Serializable;

/**
 * Created by Mahmoud on 5/25/2017.
 */

public class Response implements Serializable {


    private Object responseData;
    private String status;
    private String error;

    public static String SUCCESS = "SUCCESS";
    public static String FAILURE = "FAILURE";

    //authintication error codes
    public static final String INVALID_ACCESS_TOKEN = "INVALID_ACCESS_TOKEN";
    public static final String EXPIRED_ACCESS_TOKEN = "EXPIRED_ACCESS_TOKEN";
    public static final String INVALID_REFRESH_TOKEN = "INVALID_REFRESH_TOKEN";
    public static final String EXPIRED_REFRESH_TOKEN = "EXPIRED_REFRESH_TOKEN";


    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Serializable responseData) {
        this.responseData = responseData;
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
