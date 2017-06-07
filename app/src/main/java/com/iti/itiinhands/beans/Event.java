package com.iti.itiinhands.beans;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//import com.iti.itiinhands.networkinterfaces.Response;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Event  {

    private int eventId;
    private String title;
    private String description;
    private long eventStart;
    private long eventEnd;


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEventStart() {
        return eventStart;
    }

    public void setEventStart(long eventStart) {
        this.eventStart = eventStart;
    }

    public long getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(long eventEnd) {
        this.eventEnd = eventEnd;
    }
}
