package com.iti.itiinhands.fragments;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CourseAdapter;
import com.iti.itiinhands.beans.StudentCourse;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.util.List;

public class StudentCourseList extends Fragment implements NetworkResponse{

    private List<StudentCourse> studentCourses;
    NetworkManager networkManager;
    private NetworkResponse myRef;
    public RecyclerView SCourses_RV;
    UserData userData;
    int token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_student_course_list);
        View view = inflater.inflate(R.layout.activity_student_course_list, container, false);
        networkManager = NetworkManager.getInstance(getActivity());
        myRef= this;


        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
        token = sharedPreferences.getInt(Constants.TOKEN,0);

        networkManager.getStudentsGrades(myRef,token);
        SCourses_RV  = (RecyclerView) view.findViewById(R.id.rvStudentCourses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SCourses_RV.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onResponse(Object object) {
        List<StudentGrade> list = (List<StudentGrade>) object;
      if(getActivity()!=null) { CourseAdapter courseAdapter = new CourseAdapter(getActivity(), list);
        SCourses_RV.setAdapter(courseAdapter);}
    }

    @Override
    public void onFailure() {

    }
}
