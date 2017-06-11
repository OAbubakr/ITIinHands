package com.iti.itiinhands.fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;
import com.squareup.picasso.Picasso;

/**
 * Created by Mahmoud on 5/28/2017.
 */

public class StudentProfileFragment extends Fragment {

    private UserData userData;
    private TextView firstTv;
    private TextView secondTv;
    private TextView thirdTv;
    private TextView fourthTv;
    private TextView fifthTv;
    private ImageView gitBtn;
    private ImageView linkedInBtn;
    private ImageView behanceBtn;
    private FloatingActionButton editBtn;
    private ImageView profilePicIv;
    private int flag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_profile, container, false);
        firstTv =(TextView) view.findViewById(R.id.firstTvProfileViewId);
        secondTv =(TextView) view.findViewById(R.id.secondTvProfileViewId);
        thirdTv =(TextView) view.findViewById(R.id.thirdTvProfileViewId);
        fourthTv =(TextView) view.findViewById(R.id.fourthTvProfileViewId);
        fifthTv =(TextView) view.findViewById(R.id.fifthTvProfileViewId);
        gitBtn = (ImageView) view.findViewById(R.id.gitBtnProfileId);
       ImageView profile_pic = (ImageView) view.findViewById(R.id.profile_pic);
        linkedInBtn = (ImageView) view.findViewById(R.id.linkedInBtnProfileId);
        behanceBtn = (ImageView) view.findViewById(R.id.behanceBtnProfileId);
        editBtn = (FloatingActionButton) view.findViewById(R.id.editBtnProfileViewId);



        Bundle b = getArguments(); // company
        if (b != null) flag = b.getInt("flag", 0);
        if (flag == 1){
            editBtn.setVisibility(View.GONE);
            userData =(UserData) b.getSerializable("student");
        }
        if(userData != null){
            if(userData.getLinkedInUrl()==null) linkedInBtn.setEnabled(false);
            if(userData.getBehanceUrl()==null) behanceBtn.setEnabled(false);
            if(userData.getGitUrl()==null) gitBtn.setEnabled(false);
        }


        firstTv.setText(userData.getName());
        secondTv.setText("Intake" +new Integer(userData.getIntakeId()).toString()+ userData.getBranchName());
        thirdTv.setText(userData.getTrackName());
        if(userData.getImagePath()!=null) {
            System.out.println("*********" + userData.getImagePath().toString());
            Picasso.with(getActivity().getApplicationContext()).load("http://172.16.2.218:8084/restfulSpring/download/" + userData.getImagePath()).into(profile_pic);
        }//SET USER EMAIL
        if(userData.getStudentEmail() != null)
        fourthTv.setText(userData.getStudentEmail());

        //SET USER PHONE
        if(userData.getStudentMobile() != null)
        fifthTv.setText(userData.getStudentMobile());


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditProfileFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            }
        });


        gitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectUrl(userData.getGitUrl());
            }
        });

        linkedInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectUrl(userData.getLinkedInUrl());
            }
        });

        behanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectUrl(userData.getBehanceUrl());
            }
        });


        return view;
    }

    private void redirectUrl(String url) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


}
