package com.iti.itiinhands.beans;

/**
 * Created by Sandra on 5/3/2017.
 */

public class CourseEvaluation {
    private int workLoad;
    private int objectivesIdentified;
    private int lectureLabCoordination;
    private int materialsAndResources;
    private int intellectualStimulation;
    private int courseStructure;
    private int overallRate;

    public int getIntellectualStimulation() {
        return intellectualStimulation;
    }

    public void setIntellectualStimulation(int intellectualStimulation) {
        this.intellectualStimulation = intellectualStimulation;
    }

    public int getCourseStructure() {
        return courseStructure;
    }

    public void setCourseStructure(int courseStructure) {
        this.courseStructure = courseStructure;
    }

    public int getOverallRate() {
        return overallRate;
    }

    public void setOverallRate(int overallRate) {
        this.overallRate = overallRate;
    }

    public int getWorkLoad() {
        return workLoad;
    }

    public void setWorkLoad(int workLoad) {
        this.workLoad = workLoad;
    }

    public int getObjectivesIdentified() {
        return objectivesIdentified;
    }

    public void setObjectivesIdentified(int objectivesIdentified) {
        this.objectivesIdentified = objectivesIdentified;
    }

    public int getLectureLabCoordination() {
        return lectureLabCoordination;
    }

    public void setLectureLabCoordination(int lectureLabCoordination) {
        this.lectureLabCoordination = lectureLabCoordination;
    }

    public int getMaterialsAndResources() {
        return materialsAndResources;
    }

    public void setMaterialsAndResources(int materialsAndResources) {
        this.materialsAndResources = materialsAndResources;
    }

}
