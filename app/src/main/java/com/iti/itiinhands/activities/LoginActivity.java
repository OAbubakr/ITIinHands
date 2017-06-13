package com.iti.itiinhands.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.internal.LinkedTreeMap;
import com.iti.itiinhands.beans.Graduate;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.LoginRequest;
import com.iti.itiinhands.model.LoginResponse;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.UserLogin;
import com.iti.itiinhands.networkinterfaces.NetworkApi;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.R;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.services.UpdateAccessToken;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.DataSerializer;
import com.iti.itiinhands.utilities.UserDataSerializer;

import retrofit2.Call;


/**
 * Created by Mahmoud on 5/21/2017.
 */

public class LoginActivity extends AppCompatActivity implements NetworkResponse {

    private EditText userNameEdTxt;
    private EditText passwordEdTxt;
    private Button loginBtn;
    private TextView userNameCheckTv;


    private TextView passwordCheckTv;
    private LinearLayout networkErrorTv;
    private TextView continueAsGuest;
    private NetworkManager networkManager;
    private NetworkResponse myRef;
    private ImageView studentBtn;
    private ImageView graduateBtn;
    private ImageView staffBtn;
    private ImageView companyBtn;
    private int userType = 0;
    private Intent navigationIntent;
    private SharedPreferences data;
    private TextView studentTxt;
    private TextView staffTxt;
    private TextView companyTxt;
    private TextView graduateTxt;
    private ProgressBar spinner;
    private Context context;
    private Call<LoginResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        //check loggedIn flag in shared preferences
        data = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        userType = data.getInt(Constants.USER_TYPE, 0);
        if (data.getBoolean(Constants.LOGGED_FLAG, false)) {
            //navigate using intent to next Activity
            switch (userType) {
                case 5:
                    //type 5 -> goes to Guest side menu
                    navigationIntent = new Intent(getApplicationContext(), GuestSideMenu.class);
                    break;
                case 1:
                    //type 1 -> goes to Student side menu
                    navigationIntent = new Intent(getApplicationContext(), SideMenuActivity.class);
                    break;
                case 2:
                    //type 2 -> goes to Staff side menu
                    navigationIntent = new Intent(getApplicationContext(), StaffSideMenuActivity.class);
                    break;
                case 3:
                    //type 3 -> goes to Company side menu
                    navigationIntent = new Intent(getApplicationContext(), CompanySideMenu.class);
                    break;
                case 4:
                    //type 4 -> goes to Graduate side menu
                    navigationIntent = new Intent(getApplicationContext(), GraduateSideMenu.class);
                    break;
            }
            startActivity(navigationIntent);
            finish();
        }
        ;

        userNameEdTxt = (EditText) findViewById(R.id.userNameLoginViewId);
        passwordEdTxt = (EditText) findViewById(R.id.passwordLoginViewId);
        loginBtn = (Button) findViewById(R.id.loginbtnLoginViewId);
        userNameCheckTv = (TextView) findViewById(R.id.userNameCheckLoginViewId);
        passwordCheckTv = (TextView) findViewById(R.id.passwordCheckLoginViewId);
        networkErrorTv = (LinearLayout) findViewById(R.id.networkFaildLoginViewId);
        networkManager = NetworkManager.getInstance(this);
        continueAsGuest = (TextView) findViewById(R.id.continueAsGuest);
        studentBtn = (ImageView) findViewById(R.id.studentBtnId);
        graduateBtn = (ImageView) findViewById(R.id.graduateBtnId);
        staffBtn = (ImageView) findViewById(R.id.staffBtnId);
        companyBtn = (ImageView) findViewById(R.id.companyBtnId);
        studentTxt = (TextView) findViewById(R.id.srudentTxtLoginId);
        staffTxt = (TextView) findViewById(R.id.staffTxtLoginId);
        companyTxt = (TextView) findViewById(R.id.companyTxtLoginId);
        graduateTxt = (TextView) findViewById(R.id.graduateTxtLoginId);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);
//        spinner.setVisibility(View.GONE);
        context = getApplicationContext();
        myRef = this;

        continueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String password = passwordEdTxt.getText().toString();
//                String name = userNameEdTxt.getText().toString();
//
//                getSharedPreferences(FriendsListFragment.SP_NAME, MODE_PRIVATE).edit().putString("myId", password).apply();
//                getSharedPreferences(FriendsListFragment.SP_NAME, MODE_PRIVATE).edit().putString("myName", name).apply();
//
//                Intent intent = new Intent(getApplicationContext(), SideMenuActivity.class);
                Intent intent = new Intent(getApplicationContext(), GuestSideMenu.class);
                //save userType in SharedPreferences
                SharedPreferences data = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                SharedPreferences.Editor editor = data.edit();
                editor.putInt(Constants.USER_TYPE, 5);
                startActivity(intent);
            }
        });
//        loginBtn.setEnabled(true);

        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = 1;
                studentTxt.setTextColor(Color.parseColor("#B71C1C"));
                studentTxt.setBackgroundResource(R.drawable.hyperlink_underline);
                staffTxt.setTextColor(Color.parseColor("#000000"));
                staffTxt.setBackgroundResource(0);
                companyTxt.setTextColor(Color.parseColor("#000000"));
                companyTxt.setBackgroundResource(0);
                graduateTxt.setTextColor(Color.parseColor("#000000"));
                graduateTxt.setBackgroundResource(0);
            }
        });


        graduateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = 4;
                studentTxt.setTextColor(Color.parseColor("#000000"));
                studentTxt.setBackgroundResource(0);
                staffTxt.setTextColor(Color.parseColor("#000000"));
                staffTxt.setBackgroundResource(0);
                companyTxt.setTextColor(Color.parseColor("#000000"));
                companyTxt.setBackgroundResource(0);
                graduateTxt.setTextColor(Color.parseColor("#B71C1C"));
                graduateTxt.setBackgroundResource(R.drawable.hyperlink_underline);
            }
        });

        staffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = 2;
                studentTxt.setTextColor(Color.parseColor("#000000"));
                studentTxt.setBackgroundResource(0);
                staffTxt.setTextColor(Color.parseColor("#B71C1C"));
                staffTxt.setBackgroundResource(R.drawable.hyperlink_underline);
                companyTxt.setTextColor(Color.parseColor("#000000"));
                companyTxt.setBackgroundResource(0);
                graduateTxt.setTextColor(Color.parseColor("#000000"));
                graduateTxt.setBackgroundResource(0);
            }
        });

        companyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = 3;
                studentTxt.setTextColor(Color.parseColor("#000000"));
                studentTxt.setBackgroundResource(0);
                staffTxt.setTextColor(Color.parseColor("#000000"));
                staffTxt.setBackgroundResource(0);
                companyTxt.setTextColor(Color.parseColor("#B71C1C"));
                companyTxt.setBackgroundResource(R.drawable.hyperlink_underline);
                graduateTxt.setTextColor(Color.parseColor("#000000"));
                graduateTxt.setBackgroundResource(0);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType == 0) {
                    Toast.makeText(getApplicationContext(), "Choose a type first to Login", Toast.LENGTH_LONG).show();
                } else {
                    if (networkManager.isOnline()) {

                        networkErrorTv.setVisibility(View.INVISIBLE);
                        userNameCheckTv.setText("");
                        passwordCheckTv.setText("");
                        userNameCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        passwordCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        if (userNameEdTxt.length() > 0 && passwordEdTxt.length() > 0) {
                            NetworkApi web = networkManager.getRetrofit().create(NetworkApi.class);
                            call = web.onLoginAuth(new LoginRequest(userType, userNameEdTxt.getText().toString(), passwordEdTxt.getText().toString()));
                            networkManager.getLoginAuthData(myRef, call);
                            loginBtn.setEnabled(false);
                            setButtonColorTint(Color.GRAY);
                            spinner.setVisibility(View.VISIBLE);
                        } else {
                            if (userNameEdTxt.length() == 0) {
                                userNameCheckTv.setText("Username is empty");
                                userNameCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                            }
                            if (passwordEdTxt.length() == 0) {
                                passwordCheckTv.setText("Password is empty");
                                passwordCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                            }
                        }
                    } else {
                        networkErrorTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        userNameEdTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    userNameCheckTv.setText("");
                    userNameCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    userNameEdTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    userNameEdTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.gender_neutral_user, 0, 0, 0);
                }

            }
        });

        passwordEdTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    passwordCheckTv.setText("");
                    passwordCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    passwordEdTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    passwordEdTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, 0, 0);
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        setButtonColorTint(Color.parseColor("#7F0000"));
    }

    @Override
    public void onResponse(Response result) {
        loginBtn.setEnabled(true);

        if (result != null && result.getResponseData() instanceof LinkedTreeMap) {
            UserData data = DataSerializer.convert(result.getResponseData(), UserData.class);
//                    UserDataSerializer.deSerialize(new Gson().toJson(result.getResponseData()));

            SharedPreferences userData = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
            SharedPreferences.Editor editor = userData.edit();
            editor.putString(Constants.USER_OBJECT, UserDataSerializer.serialize(data));
            editor.putBoolean(Constants.LOGGED_FLAG, true);
            editor.putInt(Constants.USER_ID, data.getId());
            editor.commit();


            setButtonColorTint(Color.parseColor("#7F0000"));
            startActivity(navigationIntent);
            spinner.setVisibility(View.GONE);
            finish();
        } else if (result != null && result instanceof LoginResponse) {
            LoginResponse loginResponse = (LoginResponse) result;
            String status = loginResponse.getStatus();
            String error = loginResponse.getError();
            UserLogin responseDataObj = loginResponse.getData();


            switch (status) {
                case "SUCCESS":
                    String token = responseDataObj.getToken();
                    //save userID and userType in SharedPreferences
                    SharedPreferences data = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putString(Constants.TOKEN, responseDataObj.getToken());
                    editor.putString(Constants.REFRESH_TOKEN, responseDataObj.getRefreshToken());
                    editor.putLong(Constants.EXPIRY_DATE, responseDataObj.getExpiryDate());
                    editor.putInt(Constants.USER_TYPE, userType);
                    editor.apply();

                    switch (userType) {
                        case 1://student
                            navigationIntent = new Intent(getApplicationContext(), SideMenuActivity.class);

                            break;
                        case 2://staff
                            navigationIntent = new Intent(getApplicationContext(), StaffSideMenuActivity.class);

                            break;
                        case 3://company
                            navigationIntent = new Intent(getApplicationContext(), CompanySideMenu.class);

                            break;
                        case 4://graduate
                            navigationIntent = new Intent(getApplicationContext(), GraduateSideMenu.class);

                            break;
                    }

                    networkManager.getUserProfileData(myRef, userType, data.getInt(Constants.USER_ID, 0));

                    break;
                case "FAILURE":
                    spinner.setVisibility(View.INVISIBLE);
                    setButtonColorTint(Color.parseColor("#7F0000"));
                    passwordCheckTv.setText(error);
                    passwordCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                    break;
            }
        } else {
            spinner.setVisibility(View.INVISIBLE);
            setButtonColorTint(Color.parseColor("#7F0000"));
            Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onFailure() {
        if (!call.isCanceled()) {
            loginBtn.setEnabled(true);
            setButtonColorTint(Color.parseColor("#7F0000"));
            loginBtn.setBackgroundResource(R.drawable.rectangle_17);
            spinner.setVisibility(View.INVISIBLE);
            new NetworkUtilities().networkFailure(getApplicationContext());
        }

    }

    private void setButtonColorTint(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loginBtn.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            Drawable wrapDrawable = DrawableCompat.wrap(loginBtn.getBackground());
            DrawableCompat.setTint(wrapDrawable, color);
            loginBtn.setBackgroundDrawable(DrawableCompat.unwrap(wrapDrawable));
        }
    }




    @Override
    protected void onPause() {
        super.onPause();
        if(call != null)
            call.cancel();
    }
}