package com.iti.itiinhands.model;

/**
 * Created by Mahmoud on 5/25/2017.
 */

public class Response {
    private Object reponseData;
    private String status;
    private String error;

    public Object getResponseData() {
        return reponseData;
    }

    public void setResponseData(Object reponseData) {
        this.reponseData = reponseData;
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
