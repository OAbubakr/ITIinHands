package com.iti.itiinhands.utilities;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.iti.itiinhands.dto.UserData;

import java.lang.reflect.Type;

/**
 * Created by omari on 6/5/2017.
 */

public class DataSerializer {

    public static <T> T convert(Object object,Class<T> type ) {
        Gson gson = new Gson();

        return gson.fromJson(gson.toJson(object),type);
    }

    public static <T> T convert(Object object,Type type ) {
        Gson gson = new Gson();

        return gson.fromJson(gson.toJson(object),type);
    }

}
