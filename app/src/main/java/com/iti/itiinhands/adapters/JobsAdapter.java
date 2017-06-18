package com.iti.itiinhands.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.CompanyJobPost;
import com.iti.itiinhands.model.JobVacancy;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by salma on 31/05/2017.
 */

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {




    private ArrayList<JobVacancy> jobVacancies;
    private Context context;

    public JobsAdapter(ArrayList<JobVacancy> jobVacancies,Context context){
        this.jobVacancies = jobVacancies;
        this.context = context;
    }



    class ViewHolder extends RecyclerView.ViewHolder{


        public TextView jobTilte;
        public TextView jobDesc;
        public TextView jobExp;
        public TextView closeDate;
        public TextView cvTo;
        public TextView noNeed;
        public TextView companyName;
        public ImageView companyImage;

        public ViewHolder(View itemView) {
            super(itemView);

            jobTilte = (TextView)itemView.findViewById(R.id.item_title);
            companyName = (TextView)itemView.findViewById(R.id.item_detail);

            cvTo = (TextView)itemView.findViewById(R.id.cvto);

            companyImage = (ImageView) itemView.findViewById(R.id.img);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

//                    Snackbar.make(v, "Click detected on item " + position,
//                            Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                    Intent intent = new Intent(context, CompanyJobPost.class);
                    intent.putExtra("job post",jobVacancies.get(position));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.job_post_card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }





    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.jobTilte.setText(jobVacancies.get(i).getJobTitle());


        if (jobVacancies.get(i).getCompanyName() == null){
            viewHolder.companyName.setText("NULL");
        }
        else {
            viewHolder.companyName.setText(jobVacancies.get(i).getCompanyName());
        }
        if (jobVacancies.get(i).getJobCVTo() == null){
            viewHolder.cvTo.setText("NULL");
        }
        else {
            viewHolder.cvTo.setText(jobVacancies.get(i).getJobCVTo());
        }

        if(jobVacancies.get(i).getCompanyLogoPath() != null && !jobVacancies.get(i).getCompanyLogoPath().equals("")){
            Picasso.with(context)
                    .load(jobVacancies.get(i).getCompanyLogoPath())
                    .placeholder(R.drawable.c_pic)
                    .error(R.drawable.c_pic)
                    .into(viewHolder.companyImage);
        }





    }

    @Override
    public int getItemCount() {
        return jobVacancies.size();
    }
}
