package com.iti.itiinhands.fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.EditProfileActivity;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    private ImageView profile_pic;
    private int flag;
    private Picasso picasso;
    private int width;
    private int height;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);

    }

    @Override
    public void onStart() {
        super.onStart();
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
        if(userData != null){
            if(userData.getLinkedInUrl()!=null && userData.getLinkedInUrl().length()>0) {
                linkedInBtn.setEnabled(true);
                linkedInBtn.setImageResource(R.drawable.linked_in);
            }else{
                linkedInBtn.setEnabled(false);
                linkedInBtn.setImageResource(R.drawable.group1205);
            }
            if(userData.getBehanceUrl()!=null && userData.getBehanceUrl().length()>0){
                behanceBtn.setEnabled(true);
                behanceBtn.setImageResource(R.drawable.behance);
            }else{
                behanceBtn.setEnabled(false);
                behanceBtn.setImageResource(R.drawable.group1207);
            }
            if(userData.getGitUrl()!=null && userData.getGitUrl().length()>0){
                gitBtn.setEnabled(true);
                gitBtn.setImageResource(R.drawable.github);
            }else{
                gitBtn.setEnabled(false);
                gitBtn.setImageResource(R.drawable.githubgray);
            }
            //SET USER EMAIL
            if (userData.getStudentEmail() != null)
                fourthTv.setText(userData.getStudentEmail());

            //SET USER PHONE
            if (userData.getStudentMobile() != null)
                fifthTv.setText(userData.getStudentMobile());

            firstTv.setText(userData.getName());
            secondTv.setText("Intake" + new Integer(userData.getIntakeId()).toString() + " - "+ userData.getBranchName());
            thirdTv.setText(userData.getTrackName());

            DisplayMetrics displayMetrics=new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            width=displayMetrics.widthPixels;
            height=displayMetrics.heightPixels;



            //************************************/


            SharedPreferences data = getActivity().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
            final String token = data.getString(Constants.TOKEN, "");
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {

                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", token)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            picasso = new Picasso.Builder(getActivity().getApplicationContext())
                    .downloader(new OkHttp3Downloader(client))
                    .build();
            picasso.load(NetworkManager.BASEURL + "download/" + userData.getImagePath()).placeholder(R.drawable.profile_pic)
                    .resize(width,height/3)
                    .error(R.drawable.profile_pic).into(profile_pic);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_profile, container, false);
        firstTv = (TextView) view.findViewById(R.id.firstTvProfileViewId);
        secondTv = (TextView) view.findViewById(R.id.secondTvProfileViewId);
        thirdTv = (TextView) view.findViewById(R.id.thirdTvProfileViewId);
        fourthTv = (TextView) view.findViewById(R.id.fourthTvProfileViewId);
        fifthTv = (TextView) view.findViewById(R.id.fifthTvProfileViewId);
        gitBtn = (ImageView) view.findViewById(R.id.gitBtnProfileId);
        profile_pic = (ImageView) view.findViewById(R.id.profile_pic);
        linkedInBtn = (ImageView) view.findViewById(R.id.linkedInBtnProfileId);
        behanceBtn = (ImageView) view.findViewById(R.id.behanceBtnProfileId);
        editBtn = (FloatingActionButton) view.findViewById(R.id.editBtnProfileViewId);

        getActivity().setTitle("Profile");

        Bundle b = getArguments(); // company
        if (b != null) flag = b.getInt("flag", 0);
        if (flag == 1) {
            editBtn.setVisibility(View.GONE);
            userData = (UserData) b.getSerializable("student");
        }


        /**********/

        //  Picasso.with(getActivity().getApplicationContext()).load("http://172.16.2.40:8085/restfulSpring/download/"+userData.getImagePath()).placeholder(R.drawable.ic_account_circle_white_48dp).into(profile_pic);




        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(getActivity().getApplicationContext(), EditProfileActivity.class);
                startActivity(editIntent);
//                Fragment fragment = new EditProfileFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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
