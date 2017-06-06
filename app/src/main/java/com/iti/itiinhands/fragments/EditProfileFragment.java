package com.iti.itiinhands.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.GitData;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.behance.BehanceData;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.services.LinkedInLogin;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.DataSerializer;
import com.iti.itiinhands.utilities.UserDataSerializer;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mahmoud on 5/30/2017.
 */

public class EditProfileFragment extends Fragment implements NetworkResponse {


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
    private EditProfileFragment myRef;
    private String linkedInUrl;
    private String gitUrl;
    private String behanceUrl;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LinkedInLogin(getActivity().getApplicationContext());
        sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile, container, false);

        profilePicIv = (ImageView) view.findViewById(R.id.change_Imageview);
        mobileEt = (EditText) view.findViewById(R.id.mobileText);
        emailEt = (EditText) view.findViewById(R.id.emailText);
        githubEt = (EditText) view.findViewById(R.id.gitEdEditId);
        githubSearch = (ImageButton) view.findViewById(R.id.searchGitBtn);
        githubImg = (ImageView) view.findViewById(R.id.gitImgResponse);
        behanceEt = (EditText) view.findViewById(R.id.behanceEdEditId);
        behanceSearch = (ImageButton) view.findViewById(R.id.searchBehanceBtn);
        behanceImg = (ImageView) view.findViewById(R.id.behanceImgResponse);
        linkedinBtn = (ImageView) view.findViewById(R.id.linkedinBtn);
        cancelBtn = (Button) view.findViewById(R.id.cancelBtnEditId);
        submitBtn = (Button) view.findViewById(R.id.submitBtnEditId);
        myRef = this;
        prepareView();
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
                NetworkManager.getInstance(getActivity().getApplicationContext()).getGitData(myRef, githubEt.getText().toString());
            }
        });


        ///search for behance account
        behanceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkManager.getInstance(getActivity().getApplicationContext()).getBehanceData(myRef, behanceEt.getText().toString());

            }
        });

        ///search for linkedin account
        linkedinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });

        ///save edit profile
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userData.setGitUrl(gitUrl);
                userData.setBehanceUrl(behanceUrl);
                userData.setLinkedInUrl(linkedInUrl);
                userData.setStudentEmail(emailEt.getText().toString());
                userData.setStudentMobile(mobileEt.getText().toString());
                int userId = sharedPreferences.getInt(Constants.TOKEN, 0);
                int userType = sharedPreferences.getInt(Constants.USER_TYPE, 0);
                NetworkManager.getInstance(getActivity().getApplicationContext()).setUserProfileData(myRef, userType, userId, userData);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.USER_OBJECT, UserDataSerializer.serialize(userData));
                editor.commit();
                redirectFrag();
            }
        });

        ///cancel edit profile
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectFrag();
            }
        });

        return view;
    }

    private void handleLogin() {


        //LISessionManager object and pass the context, scope(list of member permission), callback method
        // and a boolean value that determines the behaviour when the LinkedIn app is not installed.

        LISessionManager.getInstance(getContext())
                .init(getActivity(), buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {

                        Log.i("result", "error");
                        fetchPersonalData();

                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        Log.i("error", error.toString());
                        Toast.makeText(getActivity().getApplicationContext(), "sync fail", Toast.LENGTH_LONG).show();
                    }
                }, true);
    }

    private void fetchPersonalData() {

        //get profile url from linkedApi bu sending get request and apply listener for seccess and failure
        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,public-profile-url)";
        APIHelper apiHelper = APIHelper.getInstance(getActivity().getApplicationContext());
        apiHelper.getRequest(getActivity(), url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!
                Log.i("result", "api success");
                JSONObject jsonObject = apiResponse.getResponseDataAsJson();
                try {
                    linkedInUrl = jsonObject.getString("publicProfileUrl");

                    Log.i("profile url =", linkedInUrl + " and id: " + jsonObject.getString("id"));
                    Toast.makeText(getActivity().getApplicationContext(), "sync done", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making GET request!
                Toast.makeText(getActivity().getApplicationContext(), "wrong account", Toast.LENGTH_LONG).show();
                Log.i("result", "api error");
            }
        });
    }

    // set the permission to retrieve basic information of User's linkedIn account
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }


    private void redirectFrag() {

        Fragment fragment = new StudentProfileFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private void prepareView() {
        if (userData.getStudentEmail() != null)
            emailEt.setText(userData.getStudentEmail());
        if (userData.getStudentMobile() != null)
            mobileEt.setText(userData.getStudentMobile());
        if (userData.getBehanceUrl() != null) {
            behanceEt.setText(userData.getBehanceUrl());
            behanceUrl = userData.getBehanceUrl();
        }

        if (userData.getGitUrl() != null) {
            githubEt.setText(userData.getGitUrl());
            gitUrl = userData.getGitUrl();
        }

        if (userData.getLinkedInUrl() != null) {
            linkedInUrl = userData.getLinkedInUrl();
        }

        if (userData.getImagePath() != null)
            Picasso.with(getActivity().getApplicationContext()).load(userData.getImagePath()).into(profilePicIv);
    }

    @Override
    public void onResponse(Response response) {

                if (response instanceof BehanceData) {


                    BehanceData data = (BehanceData) response;
                    behanceUrl = data.getUser().getUrl();
                    HashMap<Integer, String> images = data.getUser().getImages();
                    Picasso.with(getActivity().getApplicationContext()).load(images.get(50)).into(behanceImg);
                }else
                if (response instanceof GitData) {
                    GitData data = (GitData) response;
                    gitUrl = data.getHtml_url();
                    Picasso.with(getActivity().getApplicationContext()).load(data.getAvatar_url()).into(githubImg);

                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "wrong account", Toast.LENGTH_LONG).show();
                }

            }



    @Override
    public void onFailure() {
        Toast.makeText(getActivity().getApplicationContext(), "sync fail", Toast.LENGTH_LONG).show();
    }
}
