package com.iti.itiinhands.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.StudentProfileFragment;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;

/**
 * Created by Mahmoud on 5/30/2017.
 */

public class EditProfileFragment extends Fragment {


//    private UserData userData;
//    private TextView firstTv;
//    private TextView secondTv;
//    private TextView thirdTv;
//    private TextView fourthTv;
//    private Button uploadBtn;
//    private Button cancelBtn;
//    private Button submitBtn;
//    private EditText gitEd;
//    private EditText linkedInEd;
//    private EditText behanceEd;
//    private ImageView profilePicIv;


    private UserData userData;
    private ImageView profilePicIv;
    private EditText mobileEt;
    private EditText emailEt;
    private EditText githubEt;
    private ImageButton githubSearch;
    private ImageView githubImg;
    private EditText behanceEt;
    private ImageButton behanceSearch;
    private ImageView behanceImg;
    private ImageView linkedinBtn;
    private Button cancelBtn;
    private Button submitBtn;






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile, container, false);
//        firstTv = (TextView) view.findViewById(R.id.firstTvEditViewId);
//        secondTv = (TextView) view.findViewById(R.id.secondTvEditViewId);
//        thirdTv = (TextView) view.findViewById(R.id.thirdTvEditViewId);
//        fourthTv = (TextView) view.findViewById(R.id.fourthTvEditViewId);
//        uploadBtn = (Button) view.findViewById(R.id.uploadImgBtnEditViewId);
//        cancelBtn = (Button) view.findViewById(R.id.cancelBtnEditId);
//        submitBtn = (Button) view.findViewById(R.id.submitBtnEditId);
//        gitEd = (EditText) view.findViewById(R.id.gitEdEditId);
//        linkedInEd = (EditText) view.findViewById(R.id.linkedInEdEditId);
//        behanceEd = (EditText) view.findViewById(R.id.behanceEdEditId);
//
//        firstTv.setText(userData.getName());
//        secondTv.setText(new Integer(userData.getIntakeId()).toString());
//        thirdTv.setText(userData.getTrackName());
//        fourthTv.setText(userData.getBranchName());


//        uploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               redirectFrag();
//            }
//        });
//
//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              redirectFrag();
//            }
//        });

        profilePicIv = (ImageView) view.findViewById(R.id.change_Imageview);
        mobileEt = (EditText) view.findViewById(R.id.mobileText);
        emailEt = (EditText) view.findViewById(R.id.emailText);
        githubEt = (EditText) view.findViewById(R.id.gitEdEditId);
        githubSearch = (ImageButton) view.findViewById(R.id.searchGitBtn);
        githubImg = (ImageView)  view.findViewById(R.id.gitImgResponse);
        behanceEt= (EditText) view.findViewById(R.id.behanceEdEditId);
        behanceSearch = (ImageButton) view.findViewById(R.id.searchBehanceBtn);
        behanceImg = (ImageView)  view.findViewById(R.id.behanceImgResponse);
        linkedinBtn = (ImageView) view.findViewById(R.id.linkedinBtn);
        cancelBtn = (Button) view.findViewById(R.id.cancelBtnEditId);
        submitBtn = (Button) view.findViewById(R.id.submitBtnEditId);


        ///change profile pic
        profilePicIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ///search for github account
        githubSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        ///search for behance account
        behanceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ///search for linkedin account
        linkedinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ///save edit profile
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ///cancel edit profile
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    private void redirectFrag(){

        Fragment fragment = new StudentProfileFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
