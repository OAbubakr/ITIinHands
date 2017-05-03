package com.iti.itiinhands.beans;

import java.util.ArrayList;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Track {
    private int trackId;
    private String trackName;
    private Instructor trackSupervisor;
    private Branch trackBranch;
    private ArrayList<Student> trackStudents = new ArrayList<>();
    private ArrayList<Instructor> trackInstructors = new ArrayList<>();
    private String description;

}
