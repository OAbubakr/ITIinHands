package com.iti.itiinhands.model;

/**
 * Created by Mahmoud on 5/25/2017.
 */

public class Response {
    private Object responseData;
    private String status;
    private String error;

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
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
