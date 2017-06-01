package com.iti.itiinhands.fragments;


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
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.StudentProfileFragment;
import com.iti.itiinhands.model.GitData;
import com.iti.itiinhands.model.behance.BehanceData;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Mahmoud on 5/30/2017.
 */

public class EditProfileFragment extends Fragment implements NetworkResponse {
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
    private Button gitBtn;
    private Button linkedInBtn;
    private Button behanceBtn;
    private EditProfileFragment myRef;
    private ImageView gitIv;
    private ImageView linkedInIv;
    private ImageView behanceIv;

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
        gitBtn = (Button) view.findViewById(R.id.gitBtnId);
        linkedInBtn = (Button) view.findViewById(R.id.linkedInBtnId);
        behanceBtn = (Button) view.findViewById(R.id.behanceBtnId);
        gitIv = (ImageView) view.findViewById(R.id.gitIvId);
        behanceIv = (ImageView) view.findViewById(R.id.behanceIvId);
        linkedInIv = (ImageView) view.findViewById(R.id.linkedInIvId);

        myRef = this;
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

        gitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.getInstance(getActivity().getApplicationContext()).getGitData(myRef, gitEd.getText().toString());
            }
        });

        behanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.getInstance(getActivity().getApplicationContext()).getBehanceData(myRef, behanceEd.getText().toString());
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

    private void redirectFrag() {

        Fragment fragment = new StudentProfileFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public void onResponse(Object response) {
        if (response != null) {
            if (response instanceof BehanceData) {
                BehanceData data = (BehanceData) response;
                behanceEd.setText(data.getUser().getUrl());
                HashMap<Integer,String> images = data.getUser().getImages();
                Picasso.with(getActivity().getApplicationContext()).load(images.get(50)).into(behanceIv);
            }
            if (response instanceof GitData) {
                GitData data = (GitData) response;
                gitEd.setText(data.getHtml_url());
                Picasso.with(getActivity().getApplicationContext()).load(data.getAvatar_url()).into(gitIv);

            }


        } else {
            Toast.makeText(getActivity().getApplicationContext(), "wrong account", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity().getApplicationContext(), "sync fail", Toast.LENGTH_LONG).show();
    }
}
