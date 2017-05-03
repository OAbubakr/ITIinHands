package com.iti.itiinhands.beans;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Bus {
    private int busId;
    private String busLine;
    private String lineImgURL;
    private String startPoint;
    private String startTime;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public String getLineImgURL() {
        return lineImgURL;
    }

    public void setLineImgURL(String lineImgURL) {
        this.lineImgURL = lineImgURL;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
