package com.iti.itiinhands.model;

/**
 * Created by home on 5/28/2017.
 */

public class Instructor {

    private int instuctorId;
    private String instructorName;
    private int branchId;
    private String imagePath;
    private String branchName;
    private String arabicBranchName;

    public String getArabicBranchName() {
        return arabicBranchName;
    }

    public void setArabicBranchName(String arabicBranchName) {
        this.arabicBranchName = arabicBranchName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getInstuctorId() {
        return instuctorId;
    }

    public void setInstuctorId(int instuctorId) {
        this.instuctorId = instuctorId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }


}
