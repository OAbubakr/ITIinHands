package com.iti.itiinhands.model.behance;

import java.io.Serializable;

/**
 * Created by Mahmoud on 5/30/2017.
 */

public class BehanceData implements Serializable {
    private int valid;
    private int http_code;
    private BehanceUser user;

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getHttp_code() {
        return http_code;
    }

    public void setHttp_code(int http_code) {
        this.http_code = http_code;
    }

    public BehanceUser getUser() {
        return user;
    }

    public void setUser(BehanceUser user) {
        this.user = user;
    }
}
