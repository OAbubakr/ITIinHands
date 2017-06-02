package com.iti.itiinhands.model;

/**
 * Created by salma on 31/05/2017.
 */

public class JobVacancy {
    int jobID;
    int companyID;
    String companyName;
    String jobCode;
    String jobTitle;
    String jobDesc;
    String jobYearExperience;
    long jobClosingDate;
    String jobCVTo;
    int jobNoNeed;
    int subTrackID;
    long jobDate;

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobYearExperience() {
        return jobYearExperience;
    }

    public void setJobYearExperience(String jobYearExperience) {
        this.jobYearExperience = jobYearExperience;
    }





    public String getJobCVTo() {
        return jobCVTo;
    }

    public void setJobCVTo(String jobCVTo) {
        this.jobCVTo = jobCVTo;
    }

    public int getJobNoNeed() {
        return jobNoNeed;
    }

    public void setJobNoNeed(int jobNoNeed) {
        this.jobNoNeed = jobNoNeed;
    }

    public int getSubTrackID() {
        return subTrackID;
    }

    public void setSubTrackID(int subTrackID) {
        this.subTrackID = subTrackID;
    }

    public long getJobClosingDate() {
        return jobClosingDate;
    }

    public void setJobClosingDate(long jobClosingDate) {
        this.jobClosingDate = jobClosingDate;
    }

    public long getJobDate() {
        return jobDate;
    }

    public void setJobDate(long jobDate) {
        this.jobDate = jobDate;
    }
}
