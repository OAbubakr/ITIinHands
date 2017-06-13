package com.iti.itiinhands.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompanyProfileFragment extends Fragment {

    TextView name;
    TextView email;
    TextView address;
    TextView phone;
    TextView mobile;
    TextView website;
    TextView knowledge;
    TextView numberOfEmployees;
    ImageView companyLogo;
    private NetworkManager networkManager;
    UserData company;
    Company companyStudent;
    int flag;

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

        getActivity().setTitle("Profile");

        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        address = (TextView) view.findViewById(R.id.address);
        phone = (TextView) view.findViewById(R.id.phone);
        mobile = (TextView) view.findViewById(R.id.mobile);
        website = (TextView) view.findViewById(R.id.website);
        knowledge = (TextView) view.findViewById(R.id.knowledge);
        companyLogo=(ImageView)view.findViewById(R.id.company_logo);
        numberOfEmployees =(TextView)view.findViewById(R.id.number);





        Bundle bundle = this.getArguments();
        if (bundle != null) {
            flag = bundle.getInt("flag", 1);


            if (flag == 1) {
                Log.i("flag", "from all companies");
                companyStudent = (Company) bundle.getSerializable("company");
                name.setText(companyStudent.getCompanyName());
                mobile.setText(companyStudent.getCompanyMobile());
                address.setText(companyStudent.getCompanyAddress());
                phone.setText(companyStudent.getCompanyPhone());
                website.setText(companyStudent.getCompanyWebSite());
                email.setText(companyStudent.getCompanyEmail());
                knowledge.setText(companyStudent.getCompanyAreaKnowledge());
                numberOfEmployees.setText(String.valueOf(companyStudent.getCompanyNoOfEmp())+" Employees");

                if(companyStudent.getCompanyLogoPath()!=null) {
                    Picasso.with(getActivity().getApplicationContext()).load(companyStudent.getCompanyLogoPath()).placeholder(R.drawable.c_pic)
                            .error(R.drawable.c_pic)
                            .into(companyLogo);
                }
            }else if (flag == 2){
                Log.i("flag","from shared pref");
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
            numberOfEmployees.setText(String.valueOf(company.getCompanyNoOfEmp())+" Employees");
            knowledge.setText(company.getCompanyAreaKnowledge());
            Picasso.with(getActivity().getApplicationContext()).load(company.getCompanyLogoPath()).into(companyLogo);



            if(company.getCompanyLogoPath()!=null) {
                Picasso.with(getActivity().getApplicationContext()).load(company.getCompanyLogoPath()).placeholder(R.drawable.c_pic)
                        .error(R.drawable.c_pic)
                        .into(companyLogo);
            }
        }
                    if (company.getCompanyLogoPath() != null) {
                        Picasso.with(getActivity().getApplicationContext()).load(company.getCompanyLogoPath()).placeholder(R.drawable.c_pic)
                                .error(R.drawable.c_pic)
                                .into(companyLogo);
                    }
                }
            }

        //**********************************************website action***************************

            website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String webSite;
                    if (flag == 1) {

                        webSite=companyStudent.getCompanyWebSite();
                    } else {

                        webSite=company.getCompanyWebSite();

                    }
                    if(webSite!=null&&webSite.length()>0) {
                        if (Patterns.WEB_URL.matcher(webSite).matches()) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(webSite));
                            startActivity(i);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid website", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

            //******************************************comapny's email action ********************

            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                    intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
                    if (flag == 1) {
                        intent.setData(Uri.parse("mailto:" + companyStudent.getCompanyEmail())); // or just "mailto:" for blank

                    } else {
                        intent.setData(Uri.parse("mailto:" + company.getCompanyEmail())); // or just "mailto:" for blank
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    startActivity(intent);

                }
            });

            //**************************************** company phone action ********************************

            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent callIntent;
                    if (flag == 1) {
                        callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + companyStudent.getCompanyPhone()));
                    } else {
                        callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + company.getCompanyPhone()));

                    }
                    startActivity(callIntent);

                }
            });

            //**************************************** company's mobile action ********************************

            mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent callIntent;
                    if (flag == 1) {
                        callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + companyStudent.getCompanyMobile()));
                    } else {
                        callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + company.getCompanyMobile()));

                    }
                    startActivity(callIntent);

                }
            });


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

//    @Override
//    public void onResponse(Response response) {
//
//        if (response.getStatus().equals(Response.SUCCESS)) {
//            company = DataSerializer.convert(response.getResponseData(),UserData.class);
//
////            company = (UserData) response.getResponseData();
//            name.setText(company.getCompanyName());
//            mobile.setText(company.getCompanyMobile());
//            address.setText(company.getCompanyAddress());
//            phone.setText(company.getCompanyPhone());
//            website.setText(company.getCompanyWebSite());
//            email.setText(company.getCompanyEmail());
//            knowledge.setText(company.getCompanyAreaKnowledge());
//        }
//    }
//
//    @Override
//    public void onFailure() {
//
//    }

//        if (response.getStatus().equals(Response.SUCCESS)) {
//            company = DataSerializer.convert(response.getResponseData(),UserData.class);
//
////            company = (UserData) response.getResponseData();
//            name.setText(company.getCompanyName());
//            mobile.setText(company.getCompanyMobile());
//            address.setText(company.getCompanyAddress());
//            phone.setText(company.getCompanyPhone());
//            website.setText(company.getCompanyWebSite());
//            email.setText(company.getCompanyEmail());
//            numberOfEmployees.setText(String.valueOf(company.getCompanyNoOfEmp()+"Employess"));
//            knowledge.setText(company.getCompanyAreaKnowledge());
//        }
    }



