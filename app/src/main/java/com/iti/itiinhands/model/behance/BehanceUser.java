package com.iti.itiinhands.model.behance;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Mahmoud on 5/30/2017.
 */

public class BehanceUser implements Serializable {
    private String url;
    private HashMap<Integer,String> images;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<Integer, String> getImages() {
        return images;
    }

    public void setImages(HashMap<Integer, String> images) {
        this.images = images;
    }
}
