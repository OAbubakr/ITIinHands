package com.iti.itiinhands.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.model.Company;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;

public class CompanyProfileFragment extends Fragment implements NetworkResponse{

    TextView name;
    TextView email;
    TextView address;
    TextView phone;
    TextView mobile;
    TextView website;
    TextView knowledge;
    private NetworkManager networkManager;
    Company company;
    public CompanyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_profile, container, false);
        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());

        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        address = (TextView) view.findViewById(R.id.address);
        phone = (TextView) view.findViewById(R.id.phone);
        mobile = (TextView) view.findViewById(R.id.mobile);
        website = (TextView) view.findViewById(R.id.website);
        knowledge = (TextView) view.findViewById(R.id.knowledge);

        networkManager.getCompanyProfile(this,4);


        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResponse(Object response) {

        company = (Company) response;
        name.setText(company.getCompanyName());
        mobile.setText(company.getCompanyMobile());
        address.setText(company.getCompanyAddress());
        phone.setText(company.getCompanyPhone());
        website.setText(company.getCompanyWebSite());
        email.setText(company.getCompanyEmail());
        knowledge.setText(company.getCompanyAreaKnowledge());
    }

    @Override
    public void onFailure() {

    }


    public interface OnFragmentInteractionListener {

    }
}
