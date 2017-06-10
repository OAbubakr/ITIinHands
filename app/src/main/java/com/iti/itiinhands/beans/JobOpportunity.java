package com.iti.itiinhands.beans;

/**
 * Created by Rana Gamal on 04/06/2017.
 */

public class JobOpportunity {

    private int companyId;
    private String jobCode;
    private String jobTitle;
    private String jobDesc;
    private String experience;
    private String closingDate;
    private String sendTo;
    private int jobNoNeed;
    private int subTrackId;
    private String jobDate;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String companyName;

    public JobOpportunity(int companyId, String jobCode, String jobTitle, String jobDesc, String experience, String closingDate, String sendTo, int jobNoNeed, int subTrackId, String jobDate, String companyName) {
        this.companyId = companyId;
        this.jobCode = jobCode;
        this.jobTitle = jobTitle;
        this.jobDesc = jobDesc;
        this.experience = experience;
        this.closingDate = closingDate;
        this.sendTo = sendTo;
        this.jobNoNeed = jobNoNeed;
        this.subTrackId = subTrackId;
        this.jobDate = jobDate;
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public int getJobNoNeed() {
        return jobNoNeed;
    }

    public void setJobNoNeed(int jobNoNeed) {
        this.jobNoNeed = jobNoNeed;
    }

    public int getSubTrackId() {
        return subTrackId;
    }

    public void setSubTrackId(int subTrackId) {
        this.subTrackId = subTrackId;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }
}
