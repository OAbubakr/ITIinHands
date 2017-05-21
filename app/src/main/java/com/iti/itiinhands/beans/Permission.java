package com.iti.itiinhands.beans;


/**
 * Created by Sandra on 5/3/2017.
 */

public class Permission {
    private long permissionStartTime;
    private long getPermissionEndTime;
    private Instructor supervisor;

    public long getPermissionStartTime() {
        return permissionStartTime;
    }

    public void setPermissionStartTime(long permissionStartTime) {
        this.permissionStartTime = permissionStartTime;
    }

    public long getGetPermissionEndTime() {
        return getPermissionEndTime;
    }

    public void setGetPermissionEndTime(long getPermissionEndTime) {
        this.getPermissionEndTime = getPermissionEndTime;
    }

    public Instructor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Instructor supervisor) {
        this.supervisor = supervisor;
    }
}
