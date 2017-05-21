package com.iti.itiinhands.beans;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Evaluation {
    private int[] courseEvaluation= new int[7];
    private ArrayList<Array> instructorEvaluations = new ArrayList<>();

    public int[] getCourseEvaluation() {
        return courseEvaluation;
    }

    public void setCourseEvaluation(int[] courseEvaluation) {
        this.courseEvaluation = courseEvaluation;
    }

    public ArrayList<Array> getInstructorEvaluations() {
        return instructorEvaluations;
    }

    public void setInstructorEvaluations(ArrayList<Array> instructorEvaluations) {
        this.instructorEvaluations = instructorEvaluations;
    }
}
