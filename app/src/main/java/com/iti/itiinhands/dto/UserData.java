package com.iti.itiinhands.dto;

import java.io.Serializable;

/**
 * Created by Mahmoud on 5/28/2017.
 */

/**
 *
 * @author Mahmoud
 */
public class UserData implements Serializable {
    //

    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //student

    private int intakeId;
    private int platformIntakeId;

    public int getPlatformIntakeId() {
        return platformIntakeId;
    }

    public void setPlatformIntakeId(int platformIntakeId) {
        this.platformIntakeId = platformIntakeId;
    }

    private String intakeName;
    private String branchName;
    private String trackName;
    private String name;
    private String imagePath;
    private String gitUrl;
    private String gitImageUrl;
    private String behanceUrl;
    private String behanceImageUrl;
    private String linkedInUrl;
    private String studentEmail;
    private String studentMobile;

    //company
    private String companyName;
    private int companyNoOfEmp;
    private String companyAreaKnowledge;
    private String companyWebSite;
    private String companyAddress;
    private String companyPhone;
    private String companyMobile;
    private String companyEmail;
    private String companyLogoPath;
    private String companyProfilePath;
    private String companyUserName;
    private String companyPassWord;

    //staff
    private int employeeBranchId;
    private String employeeName;
    private String employeeBranchName;
    private String employeePosition;
    private int employeePlatformIntake;
    private int employeeSubTrackId;
    private String employeeSubTrackName;

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getGitImageUrl() {
        return gitImageUrl;
    }

    public void setGitImageUrl(String gitImageUrl) {
        this.gitImageUrl = gitImageUrl;
    }

    public String getBehanceImageUrl() {
        return behanceImageUrl;
    }

    public void setBehanceImageUrl(String behanceImageUrl) {
        this.behanceImageUrl = behanceImageUrl;
    }

    public int getEmployeePlatformIntake() {
        return employeePlatformIntake;
    }

    public void setEmployeePlatformIntake(int employeePlatformIntake) {
        this.employeePlatformIntake = employeePlatformIntake;
    }

    public int getEmployeeSubTrackId() {
        return employeeSubTrackId;
    }

    public void setEmployeeSubTrackId(int employeeSubTrackId) {
        this.employeeSubTrackId = employeeSubTrackId;
    }

    public String getEmployeeSubTrackName() {
        return employeeSubTrackName;
    }

    public void setEmployeeSubTrackName(String employeeSubTrackName) {
        this.employeeSubTrackName = employeeSubTrackName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

    public int getEmployeeBranchId() {
        return employeeBranchId;
    }

    public void setEmployeeBranchId(int employeeBranchId) {
        this.employeeBranchId = employeeBranchId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeBranchName() {
        return employeeBranchName;
    }

    public void setEmployeeBranchName(String employeeBranchName) {
        this.employeeBranchName = employeeBranchName;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyNoOfEmp() {
        return companyNoOfEmp;
    }

    public void setCompanyNoOfEmp(int companyNoOfEmp) {
        this.companyNoOfEmp = companyNoOfEmp;
    }

    public String getCompanyAreaKnowledge() {
        return companyAreaKnowledge;
    }

    public void setCompanyAreaKnowledge(String companyAreaKnowledge) {
        this.companyAreaKnowledge = companyAreaKnowledge;
    }

    public String getCompanyWebSite() {
        return companyWebSite;
    }

    public void setCompanyWebSite(String companyWebSite) {
        this.companyWebSite = companyWebSite;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyMobile() {
        return companyMobile;
    }

    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyLogoPath() {
        return companyLogoPath;
    }

    public void setCompanyLogoPath(String companyLogoPath) {
        this.companyLogoPath = companyLogoPath;
    }

    public String getCompanyProfilePath() {
        return companyProfilePath;
    }

    public void setCompanyProfilePath(String companyProfilePath) {
        this.companyProfilePath = companyProfilePath;
    }

    public String getCompanyUserName() {
        return companyUserName;
    }

    public void setCompanyUserName(String companyUserName) {
        this.companyUserName = companyUserName;
    }

    public String getCompanyPassWord() {
        return companyPassWord;
    }

    public void setCompanyPassWord(String companyPassWord) {
        this.companyPassWord = companyPassWord;
    }


    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getBehanceUrl() {
        return behanceUrl;
    }

    public void setBehanceUrl(String behanceUrl) {
        this.behanceUrl = behanceUrl;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public int getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(int intakeId) {
        this.intakeId = intakeId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
