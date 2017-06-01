package com.iti.itiinhands.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.model.JobVacancy;

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

        public ViewHolder(View itemView) {
            super(itemView);

            jobTilte = (TextView)itemView.findViewById(R.id.item_title);
            companyName = (TextView)itemView.findViewById(R.id.item_detail);
            jobDesc = (TextView)itemView.findViewById(R.id.desc);
            jobExp = (TextView)itemView.findViewById(R.id.exp);
            closeDate = (TextView)itemView.findViewById(R.id.close_date);
            cvTo = (TextView)itemView.findViewById(R.id.cvto);
            noNeed = (TextView)itemView.findViewById(R.id.noneed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

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
        viewHolder.jobDesc.setText(jobVacancies.get(i).getJobDesc());
        viewHolder.jobExp.setText(jobVacancies.get(i).getJobYearExperience());
        viewHolder.noNeed.setText(jobVacancies.get(i).getJobNoNeed()+"");

        if (jobVacancies.get(i).getCompanyName() == null){
            viewHolder.companyName.setText("INVALID");
        }
        else {
            viewHolder.companyName.setText(jobVacancies.get(i).getCompanyName());
        }
        if (jobVacancies.get(i).getJobCVTo() == null){
            viewHolder.cvTo.setText("INVALID");
        }
        else {
            viewHolder.cvTo.setText(jobVacancies.get(i).getJobCVTo());
        }



        String dateString = DateFormat.format("MM/dd/yyyy", new Date(jobVacancies.get(i).getJobClosingDate())).toString();
        viewHolder.closeDate.setText(dateString);



    }

    @Override
    public int getItemCount() {
        return jobVacancies.size();
    }
}
