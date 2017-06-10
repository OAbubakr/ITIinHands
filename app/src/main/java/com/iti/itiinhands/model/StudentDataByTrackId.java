package com.iti.itiinhands.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 5/29/2017.
 */

public class StudentDataByTrackId implements Serializable {

    private int studentId;
    private String englishname;
    private String arabicname;
    private String applicationNo;
    private Integer fkFacultyId;
    private Integer fkSpecializationId;
    private String subTrackName;
    private Integer subTrackId;
    private Integer branchId;
    private Integer programIntakeId;
    private Integer fkUniversityFacultySpecializationId;
    private Integer fkGraduationGradeId;
    private String graduationYear;
    private String phone;
    private String mobile;
    private String email;
    private String barcode;
    private Integer accFlag;
    private String username;
    private String userpwd;
    private String imagepath;
    private String rejectReason;
    private Date dateOb;
    private String gender;
    private String address;
    private String idno;
    private String graduationGrade;
    private Integer marital;
    private Integer military;
    private Integer language;
    private String socialAccount;
    private String school;
    private String gradProjIdea;
    private String projGrade;
    private String certAqui;
    private String postGradStud;
    private String acadimicInst;
    private String cvpath;
    private String schoolName;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }

    public String getArabicname() {
        return arabicname;
    }

    public void setArabicname(String arabicname) {
        this.arabicname = arabicname;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Integer getFkFacultyId() {
        return fkFacultyId;
    }

    public void setFkFacultyId(Integer fkFacultyId) {
        this.fkFacultyId = fkFacultyId;
    }

    public Integer getFkSpecializationId() {
        return fkSpecializationId;
    }

    public void setFkSpecializationId(Integer fkSpecializationId) {
        this.fkSpecializationId = fkSpecializationId;
    }

    public String getSubTrackName() {
        return subTrackName;
    }

    public void setSubTrackName(String subTrackName) {
        this.subTrackName = subTrackName;
    }

    public Integer getSubTrackId() {
        return subTrackId;
    }

    public void setSubTrackId(Integer subTrackId) {
        this.subTrackId = subTrackId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getProgramIntakeId() {
        return programIntakeId;
    }

    public void setProgramIntakeId(Integer programIntakeId) {
        this.programIntakeId = programIntakeId;
    }

    public Integer getFkUniversityFacultySpecializationId() {
        return fkUniversityFacultySpecializationId;
    }

    public void setFkUniversityFacultySpecializationId(Integer fkUniversityFacultySpecializationId) {
        this.fkUniversityFacultySpecializationId = fkUniversityFacultySpecializationId;
    }

    public Integer getFkGraduationGradeId() {
        return fkGraduationGradeId;
    }

    public void setFkGraduationGradeId(Integer fkGraduationGradeId) {
        this.fkGraduationGradeId = fkGraduationGradeId;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getAccFlag() {
        return accFlag;
    }

    public void setAccFlag(Integer accFlag) {
        this.accFlag = accFlag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getDateOb() {

        return dateOb;
    }

    public void setDateOb(Date dateOb) {
        this.dateOb = dateOb;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getGraduationGrade() {
        return graduationGrade;
    }

    public void setGraduationGrade(String graduationGrade) {
        this.graduationGrade = graduationGrade;
    }

    public Integer getMarital() {
        return marital;
    }

    public void setMarital(Integer marital) {
        this.marital = marital;
    }

    public Integer getMilitary() {
        return military;
    }

    public void setMilitary(Integer military) {
        this.military = military;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getSocialAccount() {
        return socialAccount;
    }

    public void setSocialAccount(String socialAccount) {
        this.socialAccount = socialAccount;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGradProjIdea() {
        return gradProjIdea;
    }

    public void setGradProjIdea(String gradProjIdea) {
        this.gradProjIdea = gradProjIdea;
    }

    public String getProjGrade() {
        return projGrade;
    }

    public void setProjGrade(String projGrade) {
        this.projGrade = projGrade;
    }

    public String getCertAqui() {
        return certAqui;
    }

    public void setCertAqui(String certAqui) {
        this.certAqui = certAqui;
    }

    public String getPostGradStud() {
        return postGradStud;
    }

    public void setPostGradStud(String postGradStud) {
        this.postGradStud = postGradStud;
    }

    public String getAcadimicInst() {
        return acadimicInst;
    }

    public void setAcadimicInst(String acadimicInst) {
        this.acadimicInst = acadimicInst;
    }

    public String getCvpath() {
        return cvpath;
    }

    public void setCvpath(String cvpath) {
        this.cvpath = cvpath;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }




}
