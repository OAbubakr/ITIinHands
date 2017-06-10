package com.iti.itiinhands.model;

import java.io.Serializable;

/**
 * Created by home on 6/10/2017.
 */

public class RenewTokenResponse extends Response implements Serializable {

    public RenewAccessTokenObject getData() {
        return data;
    }

    public void setData(RenewAccessTokenObject data) {
        this.data = data;
    }

    private RenewAccessTokenObject data;


}
