package com.iti.itiinhands.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;

import com.iti.itiinhands.beans.Instructor;
import com.iti.itiinhands.model.Course;

import java.util.ArrayList;

/**
 * Created by Sandra on 5/8/2017.
 */

public class TrackCoursesAdapter extends RecyclerView.Adapter<TrackCoursesAdapter.ViewHolder> {
    private ArrayList<Course> courses;



    public TrackCoursesAdapter(ArrayList<Course> courses) {
        this.courses = courses;
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
    public void onBindViewHolder(TrackCoursesAdapter.ViewHolder holder, int position) {

        // - get element from  dataset at this position
        // - replace the contents of the view with that element
        holder.courseNameTV.setText(courses.get(position).getCourseName());
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
