package com.iti.itiinhands.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.InstructorEvaluation;
import com.iti.itiinhands.beans.Notification;
import com.iti.itiinhands.beans.StudentCourse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Notification n = new Notification();
        n.setType(Notification.Type.announcement);

        InstructorEvaluation i  = new InstructorEvaluation();

        Intent intent = new Intent(this,StudentCourseList.class);
        startActivity(intent);
    }
}
