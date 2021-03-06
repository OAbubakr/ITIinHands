package com.iti.itiinhands.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Job Post");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



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
        final JobVacancy jobVacancy = (JobVacancy) intent.getSerializableExtra("job post");




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

            cvTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                    intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
                    intent.setData(Uri.parse("mailto:"+jobVacancy.getJobCVTo())); // or just "mailto:" for blank
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    startActivity(intent);
                }
            });
        }

        Picasso.with(getApplicationContext())
                .load(jobVacancy.getCompanyLogoPath())
                .placeholder(R.drawable.c_pic)
                .error(R.drawable.c_pic)
                .into(companyImage);




        String dateString = DateFormat.format("MM/dd/yyyy", new Date(jobVacancy.getJobClosingDate())).toString();
        closeDate.setText(dateString);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
