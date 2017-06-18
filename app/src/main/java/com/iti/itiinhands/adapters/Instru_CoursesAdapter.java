package com.iti.itiinhands.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.Course;

import java.util.Collections;
import java.util.List;

/**
 * Created by engra on 6/18/2017.
 */

public class Instru_CoursesAdapter extends RecyclerView.Adapter<Instru_CoursesAdapter.CourseViewHolder> {

    private List<Course> courseList = Collections.emptyList();
    private LayoutInflater inflater;
    private Context mycontext;


    public Instru_CoursesAdapter(Context context, List<Course> courseList) {
        inflater = LayoutInflater.from(context);
        this.mycontext = context;
        this.courseList = courseList;
    }
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.instructor_courses_listitem, parent, false);
        CourseViewHolder courseViewHolder = new CourseViewHolder(view);

        return courseViewHolder;
    }
    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.courseName.setText(course.getCourseName());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{
        TextView courseName;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.course_list_item);
        }
    }


}
