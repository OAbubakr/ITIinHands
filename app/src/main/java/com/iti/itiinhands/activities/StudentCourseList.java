package com.iti.itiinhands.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CourseAdapter;
import com.iti.itiinhands.beans.StudentCourse;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;
import java.util.List;

public class StudentCourseList extends Fragment implements NetworkResponse {

    private List<StudentCourse> studentCourses;
    NetworkManager networkManager;
    private NetworkResponse myRef;
    public RecyclerView SCourses_RV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_student_course_list);
        View view = inflater.inflate(R.layout.activity_student_course_list, container, false);
        networkManager = NetworkManager.getInstance(getActivity());
        myRef = this;
        networkManager.getStudentsGrades(myRef, 6761);
        SCourses_RV = (RecyclerView) view.findViewById(R.id.rvStudentCourses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SCourses_RV.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onResponse(Response object) {
        if (object.equals(Response.SUCCESS)) {
            List<StudentGrade> list = DataSerializer.convert(object.getResponseData(),new TypeToken<List<StudentGrade>>(){}.getType());

//            List<StudentGrade> list = (List<StudentGrade>) object.getResponseData();
            CourseAdapter courseAdapter = new CourseAdapter(getActivity(), list);
            SCourses_RV.setAdapter(courseAdapter);
        }
    }

    @Override
    public void onFailure() {

    }
}
