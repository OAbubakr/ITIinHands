package com.iti.itiinhands.model.behance;

import com.iti.itiinhands.model.Response;

import java.io.Serializable;

/**
 * Created by Mahmoud on 5/30/2017.
 */

public class BehanceData extends Response implements Serializable {

    private BehanceUser user;

    public BehanceUser getUser() {
        return user;
    }

    public void setUser(BehanceUser user) {
        this.user = user;
    }
}
