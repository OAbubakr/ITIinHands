package com.iti.itiinhands.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Company;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.DataSerializer;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.util.List;

public class CompanyProfileFragment extends Fragment implements NetworkResponse {

    TextView name;
    TextView email;
    TextView address;
    TextView phone;
    TextView mobile;
    TextView website;
    TextView knowledge;
    private NetworkManager networkManager;
    UserData company;

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

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
//        userType = sharedPreferences.getInt(Constants.USER_TYPE, 0);
        company = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
        if (company != null) {
            //        company = (Company) response;
            name.setText(company.getCompanyName());
            mobile.setText(company.getCompanyMobile());
            address.setText(company.getCompanyAddress());
            phone.setText(company.getCompanyPhone());
            website.setText(company.getCompanyWebSite());
            email.setText(company.getCompanyEmail());
            knowledge.setText(company.getCompanyAreaKnowledge());
        }

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
    public void onResponse(Response response) {

        if (response.getStatus().equals(Response.SUCCESS)) {
            company = DataSerializer.convert(response.getResponseData(),UserData.class);

//            company = (UserData) response.getResponseData();
            name.setText(company.getCompanyName());
            mobile.setText(company.getCompanyMobile());
            address.setText(company.getCompanyAddress());
            phone.setText(company.getCompanyPhone());
            website.setText(company.getCompanyWebSite());
            email.setText(company.getCompanyEmail());
            knowledge.setText(company.getCompanyAreaKnowledge());
        }
    }

    @Override
    public void onFailure() {

    }


    public interface OnFragmentInteractionListener {

    }
}
