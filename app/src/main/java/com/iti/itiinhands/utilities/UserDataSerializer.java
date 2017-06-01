package com.iti.itiinhands.utilities;

import com.google.gson.Gson;
import com.iti.itiinhands.dto.UserData;

/**
 * Created by omari on 6/1/2017.
 */

public class UserDataSerializer {
    public static UserData deSerialize(String userdata) {
        return new Gson().fromJson(userdata, UserData.class);
    }

    public static String serialize(UserData userData) {
        return new Gson().toJson(userData);
    }
}
