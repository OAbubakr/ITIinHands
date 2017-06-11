package com.iti.itiinhands.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.GraduateSideMenu;
import com.iti.itiinhands.activities.SideMenuActivity;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.GitData;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.behance.BehanceData;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.services.LinkedInLogin;
import com.iti.itiinhands.utilities.Constants;
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
    private ImageView githubSearch;
    private ImageView githubImg;
    private EditText behanceEt;
    private ImageView behanceSearch;
    private ImageView behanceImg;
    private ImageView linkedinBtn;
    private ImageView cancelBtn;
    private ImageView submitBtn;
    //    private EditProfileFragment myRef;
    private String linkedInUrl;
    private String gitUrl;
    private String behanceUrl;
    SharedPreferences sharedPreferences;
    private static int RESULT_LOAD_IMAGE = 1;
    NetworkManager networkManager;
    private NetworkResponse myRef;
    Uri selectedImage;
    String picturePath;
    private String activityResultFlag;
    int token;
    private SideMenuActivity studentActivity;
    private GraduateSideMenu graduateActivity;
//    private String behanceImageUrl;
//    private String gitImageUrl;


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
        githubSearch = (ImageView) view.findViewById(R.id.searchGitBtn);
        githubImg = (ImageView) view.findViewById(R.id.gitImgResponse);
        behanceEt = (EditText) view.findViewById(R.id.behanceEdEditId);
        behanceSearch = (ImageView) view.findViewById(R.id.searchBehanceBtn);
        behanceImg = (ImageView) view.findViewById(R.id.behanceImgResponse);
        linkedinBtn = (ImageView) view.findViewById(R.id.linkedinBtn);
        cancelBtn = (ImageView) view.findViewById(R.id.cancelBtnEditId);
        submitBtn = (ImageView) view.findViewById(R.id.submitBtnEditId);
        networkManager = NetworkManager.getInstance(getContext());
        myRef=this;

        prepareView();


        ///change profile pic
        profilePicIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultFlag = "uploadPic";
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
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
                int x = githubEt.getText().length();
                if(githubEt.getText().toString()!=null || githubEt.getText().length()>0)
                    userData.setGitUrl(gitUrl);
                else
                    userData.setGitUrl(null);
                if(behanceEt.getText().toString()!=null || behanceEt.getText().length()>0)
                    userData.setBehanceUrl(behanceUrl);
                else
                    userData.setBehanceUrl(null);

                userData.setLinkedInUrl(linkedInUrl);
                userData.setStudentEmail(emailEt.getText().toString());
                userData.setStudentMobile(mobileEt.getText().toString());

                ///put new image path in url
                //userData.setImagePath();
                int userId = sharedPreferences.getInt(Constants.USER_ID, 0);
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
        final int type = sharedPreferences.getInt(Constants.USER_TYPE,0);

        switch(type){
            case 1:
                studentActivity = (SideMenuActivity) getActivity();
                studentActivity.setLinkedInFlag(true);
                break;
            case 4:
                graduateActivity = (GraduateSideMenu) getActivity();
                graduateActivity.setLinkedInFlag(true);
                break;
        }


        LISessionManager.getInstance(getContext())
                .init(getActivity(), buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {

                        Log.i("result", "error");
                        fetchPersonalData();
                        switch(type){
                            case 1:
                                studentActivity.setLinkedInFlag(false);
                                break;
                            case 4:
                                graduateActivity.setLinkedInFlag(false);
                                break;
                        }

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
            behanceEt.setText(prepareUrl(userData.getBehanceUrl()));
            behanceUrl = userData.getBehanceUrl();
            Picasso.with(getActivity().getApplicationContext()).load(userData.getBehanceImageUrl()).into(behanceImg);
        }

        if (userData.getGitUrl() != null) {
            githubEt.setText(prepareUrl(userData.getGitUrl()));
            gitUrl = userData.getGitUrl();
            Picasso.with(getActivity().getApplicationContext()).load(userData.getGitImageUrl()).into(githubImg);
        }

        if (userData.getLinkedInUrl() != null) {
            linkedInUrl = userData.getLinkedInUrl();
        }

//        if (userData.getImagePath() != null)
//            Picasso.with(getActivity().getApplicationContext()).load(userData.getImagePath()).into(profilePicIv);
    }

    private String prepareUrl(String url){
        String result=null;
        url = url.trim();
        String[] data = url.split("/");
        result=data[data.length-1];
        return result;
    }

    @Override
    public void onResponse(Response response) {

        if ( response instanceof BehanceData && ((BehanceData) response).getUser()!=null) {
            BehanceData data = (BehanceData) response;
            behanceUrl = data.getUser().getUrl();
            HashMap<Integer, String> images = data.getUser().getImages();
            userData.setBehanceImageUrl(images.get(50));
            Picasso.with(getActivity().getApplicationContext()).load(images.get(50)).into(behanceImg);
        } else if (response instanceof GitData &&((GitData) response).getMessage()!="Not Found") {
            GitData data = (GitData) response;
            gitUrl = data.getHtml_url();
            userData.setGitImageUrl(data.getAvatar_url());
            Picasso.with(getActivity().getApplicationContext()).load(data.getAvatar_url()).into(githubImg);

        } else {
            Toast.makeText(getActivity().getApplicationContext(), "wrong account", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onFailure() {
        Toast.makeText(getActivity().getApplicationContext(), "sync fail", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultFlag.equals("uploadPic")){

                System.out.println("at the beginning ***************************");
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == -1 && null != data) {
                selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                profilePicIv.setImageURI(selectedImage);
                int id = sharedPreferences.getInt(Constants.USER_ID,0);
                networkManager.uploadImage(myRef, picturePath, id);


                //edit image path
            }

        }

    }


}
