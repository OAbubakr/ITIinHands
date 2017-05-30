package com.iti.itiinhands.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;

import com.iti.itiinhands.beans.StudentCourse;
import com.iti.itiinhands.beans.StudentGrade;

import java.util.Collections;
import java.util.List;

/**
 * Created by engragabz on 5/5/2017.
 */


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<StudentGrade> studentCourseList = Collections.emptyList();
    private LayoutInflater inflater;
    private Context mycontext;

    public CourseAdapter(Context context, List<StudentGrade> studentCourseList) {
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
        StudentGrade studentGrade = studentCourseList.get(position);
        holder.courseName.setText(studentGrade.getCourseName());
        holder.gradeName.setText(studentGrade.getGradeName());

    }

    @Override
    public int getItemCount() {
        return studentCourseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView courseName,gradeName;
        Button evaluate_Button;


        public CourseViewHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.event_time);
            gradeName = (TextView) itemView.findViewById(R.id.gradeTV);
            evaluate_Button = (Button) itemView.findViewById(R.id.evaluateBtn);
            evaluate_Button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == evaluate_Button.getId()){
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        }
    }


}
