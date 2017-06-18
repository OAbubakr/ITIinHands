package com.iti.itiinhands.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.CoursesForInstructors;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.TrackInstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by engra on 6/18/2017.
 */


public class Course_InstrAdapter extends RecyclerView.Adapter<Course_InstrAdapter.Course_InstrHolder> {

    private List<Instructor> instructors = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public Course_InstrAdapter(Context context, List<Instructor> instructors){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.instructors = instructors;
    }
    @Override
    public Course_InstrHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  inflater.inflate(R.layout.students_of_track_list_item, parent,false);
        Course_InstrHolder course_instrHolder = new Course_InstrHolder(v);
        return course_instrHolder;
    }
    @Override
    public void onBindViewHolder(Course_InstrHolder holder, final int position) {
        holder.instructorNameTV.setText(instructors.get(position).getInstructorName());
    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }


    public static class Course_InstrHolder extends RecyclerView.ViewHolder {

        public TextView instructorNameTV;
        public Course_InstrHolder(View view) {
            super(view);
            instructorNameTV = (TextView) view.findViewById(R.id.studentName);

        }
    }
}
