package com.iti.itiinhands.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by engra on 5/28/2017.
 */

public class EmpHour {

    @SerializedName("workingDays")
    @Expose
    private Integer workingDays;
    @SerializedName("absenceDays")
    @Expose
    private Integer absenceDays;
    @SerializedName("lateDays")
    @Expose
    private Integer lateDays;
    @SerializedName("attendDays")
    @Expose
    private Integer attendDays;
    @SerializedName("attendHours")
    @Expose
    private Integer attendHours;
    @SerializedName("missionHours")
    @Expose
    private Integer missionHours;
    @SerializedName("vacationHours")
    @Expose
    private Integer vacationHours;
    @SerializedName("permissionHours")
    @Expose
    private Integer permissionHours;

    public Integer getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Integer workingDays) {
        this.workingDays = workingDays;
    }

    public Integer getAbsenceDays() {
        return absenceDays;
    }

    public void setAbsenceDays(Integer absenceDays) {
        this.absenceDays = absenceDays;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

    public Integer getAttendDays() {
        return attendDays;
    }

    public void setAttendDays(Integer attendDays) {
        this.attendDays = attendDays;
    }

    public Integer getAttendHours() {
        return attendHours;
    }

    public void setAttendHours(Integer attendHours) {
        this.attendHours = attendHours;
    }

    public Integer getMissionHours() {
        return missionHours;
    }

    public void setMissionHours(Integer missionHours) {
        this.missionHours = missionHours;
    }

    public Integer getVacationHours() {
        return vacationHours;
    }

    public void setVacationHours(Integer vacationHours) {
        this.vacationHours = vacationHours;
    }

    public Integer getPermissionHours() {
        return permissionHours;
    }

    public void setPermissionHours(Integer permissionHours) {
        this.permissionHours = permissionHours;
    }
}
