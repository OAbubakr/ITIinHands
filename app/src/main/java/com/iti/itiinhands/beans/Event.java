package com.iti.itiinhands.beans;


/**
 * Created by Sandra on 5/3/2017.
 */

public class Event {

    private int eventId;
    private String title;
    private String description;
    private long eventStart;
    private long eventEnd;


//    private long startTime;
//    private String location;
//    private String eventOrganizer;
//    private String eventTitle;
//    private String eventDescription;
//    private String imageURL;


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
