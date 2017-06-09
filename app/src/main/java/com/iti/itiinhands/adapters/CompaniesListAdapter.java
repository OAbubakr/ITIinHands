package com.iti.itiinhands.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.model.Company;

import java.util.List;

/**
 * Created by Mahmoud on 6/8/2017.
 */

public class CompaniesListAdapter extends ArrayAdapter {

    private Context context;
    private List<Company> companiesList;

    public CompaniesListAdapter(Context context, List<Company> companiesList) {
        super(context, R.layout.company_cell, companiesList);
        this.companiesList = companiesList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.company_cell,parent,false);

        TextView name = (TextView) view.findViewById(R.id.companyNameListId);
        name.setText(companiesList.get(position).getCompanyName() );

        return view;
    }
}
