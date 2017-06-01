package com.iti.itiinhands.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.iti.itiinhands.dto.StudentProfessional;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.R;
import com.iti.itiinhands.model.LoginResponse;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;



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
    private Button continueAsGuest;
    private NetworkManager networkManager;
    private NetworkResponse myRef;
    private Button studentBtn;
    private Button graduateBtn;
    private Button staffBtn;
    private Button companyBtn;
    private int userType = 0;
    private Intent navigationIntent;
    private SharedPreferences data;

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
                case 0:
                    //type 0 -> goes to Guest side menu
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
        networkManager = NetworkManager.getInstance(getApplicationContext());
        continueAsGuest = (Button) findViewById(R.id.continueAsGuest);
        studentBtn = (Button) findViewById(R.id.studentBtnId);
        graduateBtn = (Button) findViewById(R.id.graduateBtnId);
        staffBtn = (Button) findViewById(R.id.staffBtnId);
        companyBtn = (Button) findViewById(R.id.companyBtnId);

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
                SharedPreferences data = getSharedPreferences("userData", 0);
                SharedPreferences.Editor editor = data.edit();
                editor.putInt("userType", 5);
                startActivity(intent);
            }
        });
        loginBtn.setEnabled(true);

        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = 1;
                loginBtn.setText(R.string.studentLogin);
            }
        });

        graduateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = 4;
                loginBtn.setText(R.string.graduateLogin);
            }
        });

        staffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = 2;
                loginBtn.setText(R.string.staffLogin);
            }
        });

        companyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = 3;
                loginBtn.setText(R.string.companyLogin);
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
                            networkManager.getLoginAuthData(myRef, userType, userNameEdTxt.getText().toString(), passwordEdTxt.getText().toString());
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
    public void onResponse(Object response) {

        Response result = (Response) response;
        if (result.getResponseData() instanceof LinkedTreeMap) {
            LinkedTreeMap map = ((LinkedTreeMap) result.getResponseData());
            UserData data = new UserData();
            Double idData = (Double) map.get("intakeId");
            data.setIntakeId(Integer.valueOf(idData.intValue()));
            data.setBranchName((String) map.get("branchName"));
            data.setTrackName((String) map.get("trackName"));
            data.setName((String) map.get("name"));
            if (map.get("imagePath") != null)
                data.setImagePath((String) map.get("imagePath"));
            if (map.get("gitUrl") != null)
                data.setGitUrl((String) map.get("gitUrl"));
            if (map.get("behanceUrl") != null)
                data.setBehanceUrl((String) map.get("behanceUrl"));
            if (map.get("linkedInUrl") != null)
                data.setLinkedInUrl((String) map.get("linkedInUrl"));
            SharedPreferences userData = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
            SharedPreferences.Editor editor = userData.edit();
            editor.putString(Constants.USER_OBJECT, UserDataSerializer.serialize(data));
            editor.commit();
            startActivity(navigationIntent);
            finish();
        } else {
//            LoginResponse loginResponse = (R) response;
            String status = result.getStatus();
            String error = result.getError();
            Double idData = (Double) result.getResponseData();
            int userId = Integer.valueOf(idData.intValue());
            //get data from msg and then check status value if success navigate to next activity
            //if failed print the error in username or password check textView
            switch (status) {
                case "success":
                    //save userID and userType in SharedPreferences
                    SharedPreferences data = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putInt(Constants.TOKEN, userId);
                    editor.putInt(Constants.USER_TYPE, userType);
                    editor.putBoolean(Constants.LOGGED_FLAG, true);
                    editor.commit();

                    switch (userType) {
                        case 1://student
                            navigationIntent = new Intent(getApplicationContext(), SideMenuActivity.class);
                            networkManager.getStudentProfileData(myRef, userType, userId);
                            break;
                        case 2://staff
                            navigationIntent = new Intent(getApplicationContext(), StaffSideMenuActivity.class);
                            startActivity(navigationIntent);
                            finish();
                            break;
                        case 3://company
                            navigationIntent = new Intent(getApplicationContext(), CompanySideMenu.class);
                            startActivity(navigationIntent);
                            finish();
                            break;
                        case 4://guest
                            navigationIntent = new Intent(getApplicationContext(), GraduateSideMenu.class);
                            startActivity(navigationIntent);
                            finish();
                            break;
                    }
                    ///////////////////////////////////////
                    //get all student data
                    ///////////////////////////////////////

//                    startActivity(navigationIntent);
//                    finish();
                    break;
                case "fail":
                    passwordCheckTv.setText(error);
                    passwordCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                    break;
            }
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_LONG).show();

    }
}