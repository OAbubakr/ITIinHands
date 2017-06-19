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

import com.iti.itiinhands.activities.InstructorsForCourse;
import com.iti.itiinhands.beans.Instructor;
import com.iti.itiinhands.model.Course;

import java.util.ArrayList;

/**
 * Created by Sandra on 5/8/2017.
 */

public class TrackCoursesAdapter extends RecyclerView.Adapter<TrackCoursesAdapter.ViewHolder> {
    private ArrayList<Course> courses;
    private Context context;


    public TrackCoursesAdapter(ArrayList<Course> courses,Context context) {
        this.courses = courses;
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public TrackCoursesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_in_track, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TrackCoursesAdapter.ViewHolder holder, final int position) {

        // - get element from  dataset at this position
        // - replace the contents of the view with that element
        holder.courseNameTV.setText(courses.get(position).getCourseName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context , InstructorsForCourse.class);
                i.putExtra("courseId",courses.get(position).getCourseId());
                i.putExtra("course name",courses.get(position).getCourseName());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView courseNameTV;
        public ViewHolder(View view) {
            super(view);
            courseNameTV = (TextView) view.findViewById(R.id.courseNameTV);
        }
    }
}
