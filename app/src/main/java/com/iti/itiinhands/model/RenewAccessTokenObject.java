/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.itiinhands.model;

/**
 *
 * @author home
 */
public class RenewAccessTokenObject{

    private String accessToken;
    private long expiry_date;
    private String token_type;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(long expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    
    
}
