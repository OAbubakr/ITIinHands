package com.iti.itiinhands.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.StudentProfileFragment;
import com.iti.itiinhands.model.GitData;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.behance.BehanceData;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.services.LinkedInLogin;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;
import com.jakewharton.picasso.OkHttp3Downloader;
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

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Mahmoud on 6/12/2017.
 */

public class EditProfileActivity extends AppCompatActivity implements NetworkResponse {

    private boolean LinkedInFlag = false;
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
    private ImageView behanceLogo;
    private ImageView githubLogo;
    private ImageView linkedInLogo;
    private EditProfileActivity myActivity;
    private Picasso picasso;
    private int width;
    private int height;
    private String responseType;
    private ProgressBar spinner;
    private String email;
    private TextView checkEmail;
    private TextView checkMobile;
    private boolean emailFlag;
    private boolean mobileFlag;
    private ImageView cancelLinkedIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        new LinkedInLogin(getApplicationContext());
        sharedPreferences = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));

        profilePicIv = (ImageView) findViewById(R.id.change_Imageview);
        mobileEt = (EditText) findViewById(R.id.mobileText);
        emailEt = (EditText) findViewById(R.id.emailText);
        githubEt = (EditText) findViewById(R.id.gitEdEditId);
        githubSearch = (ImageView) findViewById(R.id.searchGitBtn);
        githubImg = (ImageView) findViewById(R.id.gitImgResponse);
        behanceEt = (EditText) findViewById(R.id.behanceEdEditId);
        behanceSearch = (ImageView) findViewById(R.id.searchBehanceBtn);
        behanceImg = (ImageView) findViewById(R.id.behanceImgResponse);
        linkedinBtn = (ImageView) findViewById(R.id.linkedinBtn);
        cancelBtn = (ImageView) findViewById(R.id.cancelBtnEditId);
        submitBtn = (ImageView) findViewById(R.id.submitBtnEditId);
        networkManager = NetworkManager.getInstance(getApplicationContext());
        myRef = this;
        behanceLogo = (ImageView) findViewById(R.id.behanceLogo);
        githubLogo = (ImageView) findViewById(R.id.githubLogo);
        myActivity = this;
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        checkEmail = (TextView) findViewById(R.id.emailCheckEditProfileId);
        checkMobile = (TextView) findViewById(R.id.mobileCheckEditProfileId);
        cancelLinkedIn = (ImageView) findViewById(R.id.cancelLinkedinBtn);

        prepareView();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        final String token = sharedPreferences.getString(Constants.TOKEN, "");
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {

                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        picasso = new Picasso.Builder(getApplicationContext())
                .downloader(new OkHttp3Downloader(client))
                .build();
        picasso.load(NetworkManager.BASEURL + "download/" + userData.getImagePath()).placeholder(R.drawable.profile_pic)
                .resize(width, height / 3)
                .error(R.drawable.profile_pic).into(profilePicIv);

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
                responseType = "gitHub";
                if (networkManager.isOnline()) {
                    networkManager.getGitData(myRef, githubEt.getText().toString());
                } else {
                    new NetworkUtilities().networkFailure(getApplicationContext());
                }

            }
        });


        ///search for behance account
        behanceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                responseType = "behance";
                if (networkManager.isOnline()) {
                    networkManager.getBehanceData(myRef, behanceEt.getText().toString());
                } else {
                    new NetworkUtilities().networkFailure(getApplicationContext());
                }

            }
        });

        ///search for linkedin account
        linkedinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });

        cancelLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkedInUrl="";
                linkedinBtn.setImageResource(R.drawable.group1205);
            }
        });

        ///save edit profile
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (networkManager.isOnline()) {
                    if (githubEt.getText().length() > 0)
                        userData.setGitUrl(gitUrl);
                    else
                        userData.setGitUrl("");
                    if (behanceEt.getText().length() > 0)
                        userData.setBehanceUrl(behanceUrl);
                    else
                        userData.setBehanceUrl("");

                    userData.setLinkedInUrl(linkedInUrl);
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    email = emailEt.getText().toString().trim();
                    if(!email.isEmpty() && !email.matches(emailPattern)){
                        checkEmail.setText("Not valid email");
                        checkEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                        emailFlag = false;
                    }else{
                        userData.setStudentEmail(emailEt.getText().toString());
                        emailFlag = true;
                        checkEmail.setText("");
                        checkEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                    String mobile = mobileEt.getText().toString();
                    if(mobile.length()==0 || mobile.length() ==11 && mobile.startsWith("0") ){
                        userData.setStudentMobile(mobileEt.getText().toString());
                        checkMobile.setText("");
                        checkMobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        mobileFlag = true;
                    }else{
                        checkMobile.setText("Not valid mobile");
                        checkMobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                        mobileFlag = false;
                    }


                    ///put new image path in url
                    //userData.setImagePath();
                    if(mobileFlag && emailFlag) {
                        int userId = sharedPreferences.getInt(Constants.USER_ID, 0);
                        int userType = sharedPreferences.getInt(Constants.USER_TYPE, 0);
                        responseType = "save";
                        if (networkManager.isOnline()) {
                            networkManager.setUserProfileData(myRef, userType, userId, userData);
                            submitBtn.setEnabled(false);
                            cancelBtn.setEnabled(false);
                            submitBtn.setImageResource(R.drawable.savegray);
                            spinner.setVisibility(View.VISIBLE);
                        }else{
                            new NetworkUtilities().networkFailure(getApplicationContext());
                        }
                    }
                }else{
                    new NetworkUtilities().networkFailure(getApplicationContext());
                }
            }
        });

        ///cancel edit profile
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        submitBtn.setImageResource(R.drawable.save);
    }

    private void handleLogin() {

        activityResultFlag = "linkedIn";
        LISessionManager.getInstance(getApplicationContext())
                .init(myActivity, buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {

                        Log.i("result", "error");
                        fetchPersonalData();

                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        Log.i("error", error.toString());
                        Toast.makeText(getApplicationContext(), "Failed, please check you internet connection.", Toast.LENGTH_LONG).show();
                    }
                }, true);
    }

    private void fetchPersonalData() {

        //get profile url from linkedApi bu sending get request and apply listener for seccess and failure
        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,public-profile-url)";
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(myActivity, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!
                Log.i("result", "api success");
                JSONObject jsonObject = apiResponse.getResponseDataAsJson();
                try {
                    linkedInUrl = jsonObject.getString("publicProfileUrl");
                    linkedinBtn.setImageResource(R.drawable.linked_in);
                    Log.i("profile url =", linkedInUrl + " and id: " + jsonObject.getString("id"));
                    Toast.makeText(getApplicationContext(), "sync done", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making GET request!
                Toast.makeText(getApplicationContext(), "wrong account", Toast.LENGTH_LONG).show();
                Log.i("result", "api error");
            }
        });
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    private void prepareView() {
        if (userData.getStudentEmail() != null)
            emailEt.setText(userData.getStudentEmail());
        if (userData.getStudentMobile() != null)
            mobileEt.setText(userData.getStudentMobile());

        if (userData.getBehanceUrl() != null && userData.getBehanceUrl().length() > 0) {
            behanceEt.setText(prepareUrl(userData.getBehanceUrl()));
            behanceUrl = userData.getBehanceUrl();
            behanceLogo.setImageResource(R.drawable.behance);
            Picasso.with(getApplicationContext()).load(userData.getBehanceImageUrl()).into(behanceImg);
        } else {
            behanceLogo.setImageResource(R.drawable.group1207);
        }

        if (userData.getGitUrl() != null && userData.getGitUrl().length() > 0) {
            githubEt.setText(prepareUrl(userData.getGitUrl()));
            gitUrl = userData.getGitUrl();
            githubLogo.setImageResource(R.drawable.github);
            Picasso.with(getApplicationContext()).load(userData.getGitImageUrl()).into(githubImg);
        } else {
            githubLogo.setImageResource(R.drawable.githubgray);
        }

        if (userData.getLinkedInUrl() != null && userData.getLinkedInUrl().length() > 0) {
            linkedInUrl = userData.getLinkedInUrl();
            linkedinBtn.setImageResource(R.drawable.linked_in);
        } else {
            linkedinBtn.setImageResource(R.drawable.group1205);
        }

//        if (userData.getImagePath() != null)
//            Picasso.with(getActivity().getApplicationContext()).load(userData.getImagePath()).into(profilePicIv);
    }

    private String prepareUrl(String url) {
        String result = null;
        url = url.trim();
        String[] data = url.split("/");
        result = data[data.length - 1];
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (activityResultFlag) {
            case "uploadPic":
                System.out.println("at the beginning ***************************");
                if (requestCode == RESULT_LOAD_IMAGE && resultCode == -1 && null != data) {
                    selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    profilePicIv.setImageURI(selectedImage);
                    int id = sharedPreferences.getInt(Constants.USER_ID, 0);
                    if (networkManager.isOnline()) {
                        networkManager.uploadImage(myRef, picturePath, id);
                    } else {
                        new NetworkUtilities().networkFailure(getApplicationContext());
                    }


                    //edit image path
                }
                break;
            case "linkedIn":
                LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
                break;

        }

    }

    @Override
    public void onResponse(Response response) {
        if (response != null) {
            switch (responseType) {
                case "gitHub":
                    if (response instanceof GitData && ((GitData) response).getMessage() != "Not Found") {
                        GitData data = (GitData) response;
                        gitUrl = data.getHtml_url();
                        userData.setGitImageUrl(data.getAvatar_url());
                        Picasso.with(getApplicationContext()).load(data.getAvatar_url()).into(githubImg);
                        githubLogo.setImageResource(R.drawable.github);
                    } else {
                        githubEt.setText("");
                        githubImg.setImageResource(R.drawable.photo);
                        githubLogo.setImageResource(R.drawable.githubgray);
                        Toast.makeText(getApplicationContext(), "wrong account", Toast.LENGTH_LONG).show();
                    }
                    break;
                case "behance":
                    if (response instanceof BehanceData && ((BehanceData) response).getUser() != null) {
                        BehanceData data = (BehanceData) response;
                        behanceUrl = data.getUser().getUrl();
                        HashMap<Integer, String> images = data.getUser().getImages();
                        userData.setBehanceImageUrl(images.get(50));
                        Picasso.with(getApplicationContext()).load(images.get(50)).into(behanceImg);
                        behanceLogo.setImageResource(R.drawable.behance);
                    } else {
                        behanceEt.setText("");
                        behanceImg.setImageResource(R.drawable.photo);
                        behanceLogo.setImageResource(R.drawable.group1207);
                        Toast.makeText(getApplicationContext(), "wrong account", Toast.LENGTH_LONG).show();
                    }
                    break;
                case "save":
                    submitBtn.setEnabled(true);
                    if (response != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.USER_OBJECT, UserDataSerializer.serialize(userData));
                        editor.commit();
                        submitBtn.setImageResource(R.drawable.save);
                        spinner.setVisibility(View.GONE);
                        finish();
                    } else {
                        spinner.setVisibility(View.INVISIBLE);
                        submitBtn.setImageResource(R.drawable.save);
                        new NetworkUtilities().networkFailure(getApplicationContext());
                    }
                    break;
            }
        } else {
            if(responseType.equals("save")){
                spinner.setVisibility(View.INVISIBLE);
                submitBtn.setImageResource(R.drawable.save);
            }
            new NetworkUtilities().networkFailure(getApplicationContext());
        }

    }

    @Override
    public void onFailure() {
        new NetworkUtilities().networkFailure(getApplicationContext());
    }


}
