package com.iti.itiinhands.model;

import java.io.Serializable;

/**
 * Created by Mahmoud on 5/31/2017.
 */

public class GitData extends Response implements Serializable {

    private String avatar_url;
    private String html_url;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatarUrl(String avatar_url) {
        this.avatar_url = avatar_url;
    }
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtmlUrl(String html_url) {
        this.html_url = html_url;
    }
    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
