package com.iti.itiinhands.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;


public class PostJobFragment extends Fragment implements NetworkResponse {

    private EditText jobCode, jobTitle, jobDescription, jobYearExperience,
            jobSubTrack, jobDate, jobClosingDate, jobSendTo;
    private Button postButton;
    private NetworkManager networkManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_post_job, container, false);

        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());
        jobCode = (EditText) view.findViewById(R.id.jobCode);
        jobTitle = (EditText) view.findViewById(R.id.jobTitle);
        jobDescription = (EditText) view.findViewById(R.id.jobDescription);
        jobYearExperience = (EditText) view.findViewById(R.id.jobYearExperience);
        jobSubTrack = (EditText) view.findViewById(R.id.jobSubTrack);
        jobDate = (EditText) view.findViewById(R.id.jobDate);
        jobClosingDate = (EditText) view.findViewById(R.id.jobClosingDate);
        jobSendTo = (EditText) view.findViewById(R.id.jobSendTo);

        postButton = (Button) view.findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences data = getActivity().getSharedPreferences("userData", 0);
                int companyId = data.getInt("userId", 0);
                String code = jobCode.getText().toString();
                String title = jobTitle.getText().toString();
                String desc = jobDescription.getText().toString();
                String experience = jobYearExperience.getText().toString();
                String closingDate = jobClosingDate.getText().toString();
                String sendTo = jobSendTo.getText().toString();
                int noNeed = 0;
                int subTrackId = Integer.parseInt(jobSubTrack.getText().toString());
                String date = jobDate.getText().toString();

                if (networkManager.isOnline()){
                    networkManager.postJob(PostJobFragment.this, companyId, code, title, desc, experience,
                            closingDate, sendTo, noNeed, subTrackId, date);
                }
            }
        });

        return view;
    }

    @Override
    public void onResponse(Object response) {

    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
    }
}
