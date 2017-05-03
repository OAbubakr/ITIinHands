package com.iti.itiinhands.beans;

import java.util.Date;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Permission {
    private Date permissionStartTime;
    private Date getPermissionEndTime;
    private Instructor supervisor;

    public Date getPermissionStartTime() {
        return permissionStartTime;
    }

    public void setPermissionStartTime(Date permissionStartTime) {
        this.permissionStartTime = permissionStartTime;
    }

    public Date getGetPermissionEndTime() {
        return getPermissionEndTime;
    }

    public void setGetPermissionEndTime(Date getPermissionEndTime) {
        this.getPermissionEndTime = getPermissionEndTime;
    }

    public Instructor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Instructor supervisor) {
        this.supervisor = supervisor;
    }
}
