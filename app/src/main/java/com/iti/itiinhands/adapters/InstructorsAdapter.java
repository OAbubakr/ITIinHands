package com.iti.itiinhands.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.CoursesForInstructors;
import com.iti.itiinhands.beans.Instructor;
import com.iti.itiinhands.beans.Track;
import com.iti.itiinhands.model.TrackInstructor;

import java.util.ArrayList;

/**
 * Created by Sandra on 5/8/2017.
 */

public class InstructorsAdapter extends RecyclerView.Adapter<InstructorsAdapter.ViewHolder> {
    ArrayList<TrackInstructor> instructors;
    private Context context;

    public InstructorsAdapter(ArrayList<TrackInstructor> instructors, Context context){
        this.context = context;
        this.instructors = instructors;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public InstructorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.instructor_in_track, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(InstructorsAdapter.ViewHolder holder, final int position) {

        // - get element from  dataset at this position
        // - replace the contents of the view with that element
        holder.instructorNameTV.setText(instructors.get(position).getInstructorName());
        holder.instructorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,CoursesForInstructors.class);
                i.putExtra("instId",instructors.get(position).getInstructorID());
                i.putExtra("inst name",instructors.get(position).getInstructorName());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView instructorNameTV;
        public ImageView instructorImageView;
        public ViewHolder(View view) {
            super(view);
            instructorNameTV = (TextView) view.findViewById(R.id.instructorNameTV);
            instructorImageView = (ImageView) view.findViewById(R.id.instructorImageView);
        }
    }
}
