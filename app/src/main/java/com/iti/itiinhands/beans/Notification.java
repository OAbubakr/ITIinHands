package com.iti.itiinhands.beans;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Notification {
    public enum Type {event, scheduleChange, announcement}

    private Type type;
    private String title;
    private String notificationMessage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
