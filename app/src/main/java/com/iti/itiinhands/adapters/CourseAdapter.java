package com.iti.itiinhands.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;

import com.iti.itiinhands.beans.StudentCourse;

import java.util.Collections;
import java.util.List;

/**
 * Created by engragabz on 5/5/2017.
 */


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<StudentCourse> studentCourseList = Collections.emptyList();
    private LayoutInflater inflater;
    Context mycontext;

    public CourseAdapter(Context context, List<StudentCourse> studentCourseList) {
        inflater = LayoutInflater.from(context);
        this.mycontext = context;
        this.studentCourseList = studentCourseList;
    }
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_course_item, parent, false);
        CourseViewHolder courseViewHolder = new CourseViewHolder(view);

        return courseViewHolder;
    }
    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        StudentCourse studentCourse = studentCourseList.get(position);
        holder.courseName.setText(studentCourse.getCourseName());
        if (studentCourse.isCourseEvaluate() == true) {
            if (studentCourse.isGradeOnSystem() == true) {
                switch (studentCourse.getGrade()) {
                    case A:
                        holder.courseImage.setImageResource(R.drawable.amark);
                        break;
                    case B:
                        holder.courseImage.setImageResource(R.drawable.calendar);
                        break;
                    case C:
                        holder.courseImage.setImageResource(R.drawable.grades);
                        break;
                    case D:
                        holder.courseImage.setImageResource(R.drawable.icon);
                        break;
                    case F:
                        holder.courseImage.setImageResource(R.drawable.maps);
                        break;
                }
            } else {
                holder.courseImage.setImageResource(R.drawable.playbutton);
            }
        } else {
            if (studentCourse.isCourseComplete() == true) {
                holder.courseImage.setImageResource(R.drawable.stopwatch);
            } else {
                holder.courseImage.setImageResource(R.drawable.user);
            }
        }
    }

    @Override
    public int getItemCount() {
        return studentCourseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        ImageView courseImage;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.course_name);
            courseImage = (ImageView) itemView.findViewById(R.id.course_grade);
        }
    }


}
