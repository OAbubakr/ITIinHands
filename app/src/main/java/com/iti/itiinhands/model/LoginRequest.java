package com.iti.itiinhands.model;

/**
 * Created by Mahmoud on 5/22/2017.
 */

public class LoginRequest {
    private int userType;
    private String userName;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(int userType, String userName, String password) {
        this.userType = userType;
        this.userName = userName;
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
