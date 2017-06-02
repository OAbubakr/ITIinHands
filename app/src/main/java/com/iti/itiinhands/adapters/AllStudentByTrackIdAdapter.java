package com.iti.itiinhands.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.CompanyStudentProfile;
import com.iti.itiinhands.activities.Tracks;
import com.iti.itiinhands.model.StudentDataByTrackId;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by admin on 5/29/2017.
 */

public class AllStudentByTrackIdAdapter  extends RecyclerView.Adapter<AllStudentByTrackIdAdapter.MyViewHolder>{

    private ArrayList<StudentDataByTrackId> students;
    Context context;

    public AllStudentByTrackIdAdapter(ArrayList<StudentDataByTrackId>students,Context context){
        this.students=students;
        this.context = context;
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.students_of_track_list_item, parent, false);


        return new MyViewHolder(viewItem,students);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.bind(students.get(position));

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ArrayList<StudentDataByTrackId> students;
        TextView studentName;


        public MyViewHolder(View itemView, ArrayList<StudentDataByTrackId> students) {
            super(itemView);
            this.students = students;
            studentName =(TextView) itemView.findViewById(R.id.studentName);


        }

        public void bind(final StudentDataByTrackId studnet){

            studentName.setText(studnet.getEnglishname());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent tracksView = new Intent(context, CompanyStudentProfile.class);
                    Bundle  b= new Bundle();
                    b.putInt("flag",1);
                    tracksView.putExtra("bundle",b);
                    tracksView.putExtra("stuId",studnet.getStudentId());
                    tracksView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(tracksView);


                }
            });
        }





    }



}
