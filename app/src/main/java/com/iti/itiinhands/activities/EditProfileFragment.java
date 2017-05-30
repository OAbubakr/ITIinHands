package com.iti.itiinhands.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.StudentProfileFragment;

/**
 * Created by Mahmoud on 5/30/2017.
 */

public class EditProfileFragment extends Fragment {
    private UserData userData;
    private TextView firstTv;
    private TextView secondTv;
    private TextView thirdTv;
    private TextView fourthTv;
    private Button uploadBtn;
    private Button cancelBtn;
    private Button submitBtn;
    private EditText gitEd;
    private EditText linkedInEd;
    private EditText behanceEd;
    private ImageView profilePicIv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent activityIntent = getActivity().getIntent();
        userData = (UserData) activityIntent.getExtras().getSerializable("userData");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile, container, false);
        firstTv = (TextView) view.findViewById(R.id.firstTvEditViewId);
        secondTv = (TextView) view.findViewById(R.id.secondTvEditViewId);
        thirdTv = (TextView) view.findViewById(R.id.thirdTvEditViewId);
        fourthTv = (TextView) view.findViewById(R.id.fourthTvEditViewId);
        uploadBtn = (Button) view.findViewById(R.id.uploadImgBtnEditViewId);
        cancelBtn = (Button) view.findViewById(R.id.cancelBtnEditId);
        submitBtn = (Button) view.findViewById(R.id.submitBtnEditId);
        gitEd = (EditText) view.findViewById(R.id.gitEdEditId);
        linkedInEd = (EditText) view.findViewById(R.id.linkedInEdEditId);
        behanceEd = (EditText) view.findViewById(R.id.behanceEdEditId);

        firstTv.setText(userData.getName());
        secondTv.setText(new Integer(userData.getIntakeId()).toString());
        thirdTv.setText(userData.getTrackName());
        fourthTv.setText(userData.getBranchName());
//        gitEd.setText(userData.getGitUrl());
//        linkedInEd.setText(userData.getLinkedInUrl());
//        behanceEd.setText(userData.getBehanceUrl());

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               redirectFrag();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              redirectFrag();
            }
        });

        return view;
    }

    private void redirectFrag(){
        //fdds
        Fragment fragment = new StudentProfileFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
