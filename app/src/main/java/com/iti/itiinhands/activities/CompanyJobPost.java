package com.iti.itiinhands.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.model.JobVacancy;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class CompanyJobPost extends AppCompatActivity {

    CardView cardView;


    TextView jobTilte;
    TextView jobDesc;
    TextView jobExp;
    TextView closeDate;
    TextView cvTo;
    TextView noNeed;
    TextView companyName;
    ImageView companyImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_job_post);
        cardView = (CardView) findViewById(R.id.detail_card_view);
        cvTo = (TextView) findViewById(R.id.cvtowho);
        jobTilte = (TextView) findViewById(R.id.item_title);
        companyName = (TextView)findViewById(R.id.item_detail);
        jobDesc = (TextView)findViewById(R.id.desc);
        jobExp = (TextView)findViewById(R.id.exp);
        closeDate = (TextView)findViewById(R.id.close_date);

        noNeed = (TextView)findViewById(R.id.noneed);
        companyImage = (ImageView) findViewById(R.id.img);
        Intent intent = getIntent();
        JobVacancy jobVacancy = (JobVacancy) intent.getSerializableExtra("job post");




        jobTilte.setText(jobVacancy.getJobTitle());
        jobDesc.setText(jobVacancy.getJobDesc());
        jobExp.setText(jobVacancy.getJobYearExperience());
        noNeed.setText(jobVacancy.getJobNoNeed()+"");
        if (jobVacancy.getCompanyName() == null){
            companyName.setText("NULL");
        }
        else {
            companyName.setText(jobVacancy.getCompanyName());
        }
        if (jobVacancy.getJobCVTo() == null){
            cvTo.setText("NULL");
        }
        else {
            cvTo.setText(jobVacancy.getJobCVTo());
        }

        Picasso.with(getApplicationContext())
                .load(jobVacancy.getCompanyLogoPath())
                .into(companyImage);

        String dateString = DateFormat.format("MM/dd/yyyy", new Date(jobVacancy.getJobClosingDate())).toString();
        closeDate.setText(dateString);


    }
}
