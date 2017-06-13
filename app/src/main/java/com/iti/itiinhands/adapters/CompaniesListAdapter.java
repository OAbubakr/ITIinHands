package com.iti.itiinhands.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.CompanyProfileActivity;
import com.iti.itiinhands.activities.Tracks;
import com.iti.itiinhands.fragments.CompanyProfileFragment;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Company;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud on 6/8/2017.
 */

public class CompaniesListAdapter extends RecyclerView.Adapter<CompaniesListAdapter.MyViewHolder>{

    private Context context;
    private List<Company> companiesList;

    public CompaniesListAdapter(Context context, List<Company> companiesList) {

        this.companiesList = companiesList;
        this.context = context;
    }

    @Override
    public CompaniesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_cell, parent, false);
        return new CompaniesListAdapter.MyViewHolder(itemView, companiesList);
    }

    @Override
    public void onBindViewHolder(CompaniesListAdapter.MyViewHolder holder, int position) {
        holder.bind(companiesList.get(position));
    }

    @Override
    public int getItemCount() {
        return companiesList.size();
    }
    ///////////////////////////////////////////////////////////
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private List<Company> companiesList;
        public TextView cvTo;
        public TextView companyName;
        public ImageView companyImage;

        public MyViewHolder(View itemView, final List<Company> companiesList) {
            super(itemView);
            this.companiesList = companiesList;
            companyName = (TextView)itemView.findViewById(R.id.item_detail);

            cvTo = (TextView)itemView.findViewById(R.id.cvto);

            companyImage = (ImageView) itemView.findViewById(R.id.img);
        }

        public void bind(final Company company){
            companyName.setText(company.getCompanyName());

            if (company.getCompanyName() == null){
                companyName.setText("NULL");
            }
            else {
                companyName.setText(company.getCompanyName());
            }
            if (company.getCompanyEmail() == null){
                cvTo.setText("NULL");
            }
            else {
                cvTo.setText(company.getCompanyEmail());
            }

            Picasso.with(context)
                    .load(company.getCompanyLogoPath())
                    .placeholder(R.drawable.c_pic)
                    .error(R.drawable.c_pic)
                    .into(companyImage);





            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context,company.getCompanyName(),Toast.LENGTH_SHORT).show();

//                    Fragment fragment = new CompanyProfileFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("company",company);
//                    bundle.putInt("flag",1);
//                    fragment.setArguments(bundle);
//                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack(null);
//                    fragmentTransaction.commit();

                    Intent intent = new Intent(context, CompanyProfileActivity.class);
                    intent.putExtra("company",company);
                    //intent.putExtra("flag",1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }


    }
}
