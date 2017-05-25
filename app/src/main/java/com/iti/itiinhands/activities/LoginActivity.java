package com.iti.itiinhands.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.R;
import com.iti.itiinhands.model.LoginResponse;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.Response;

/**
 * Created by Mahmoud on 5/21/2017.
 */

public class LoginActivity extends AppCompatActivity implements NetworkResponse {

    private EditText userNameEdTxt;
    private EditText passwordEdTxt;
    private Button loginBtn;
    private TextView userNameCheckTv;
    private TextView passwordCheckTv;
    private Handler handleLogin;
    private TextView networkErrorTv;
    private Button continueAsGuest;
    private NetworkManager networkManager;
    private NetworkResponse myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        userNameEdTxt = (EditText) findViewById(R.id.userNameLoginViewId);
        passwordEdTxt = (EditText) findViewById(R.id.passwordLoginViewId);
        loginBtn = (Button) findViewById(R.id.loginbtnLoginViewId);
        userNameCheckTv = (TextView) findViewById(R.id.userNameCheckLoginViewId);
        passwordCheckTv = (TextView) findViewById(R.id.passwordCheckLoginViewId);
        networkErrorTv = (TextView) findViewById(R.id.networkFaildLoginViewId);
        networkManager = NetworkManager.getInstance(getApplicationContext());
        continueAsGuest=(Button)findViewById(R.id.continueAsGuest);

        myRef = this;
        continueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),GraduateSideMenu.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkManager.isOnline()) {
                    networkErrorTv.setVisibility(View.INVISIBLE);
                    userNameCheckTv.setText("");
                    passwordCheckTv.setText("");
                    userNameCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    passwordCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    if (userNameEdTxt.length() > 0 && passwordEdTxt.length() > 0) {
                        //send message to handle after get data from webservice
//                        new Thread (new Runnable(){
//                            @Override
//                            public void run() {
                        networkManager.getLoginAuthData(myRef, 1, userNameEdTxt.getText().toString(), passwordEdTxt.getText().toString());
//                            }
//                        }).start();

//                        /////////////////
//                        Bundle bundle = new Bundle();
//                        Message msg = new Message();
//                        //put response on bundle
//
//                        msg.setData(bundle);
//                        handleLogin.sendMessage(msg);
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
        });

        handleLogin = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                ////get response from bundle
//
//                String status="";
//                String error="";
//                int userId=0;
//                //get data from msg and then check status value if success navigate to next activity
//                //if failed print the error in username or password check textView
//                switch(status){
//                    case "success":
//                        //save userID in SharedPreferences
//                        SharedPreferences data = getSharedPreferences("userData", 0);
//                        SharedPreferences.Editor editor = data.edit();
//                        editor.putInt("userId",userId);
//                        editor.commit();
//                        //navigate using intent to next Activity
//                        Intent intent = new Intent();
//
//                        startActivity(intent);
//                        break;
//                    case "fail":
//                        switch (error){
//                            case "incorrect username":
//                                userNameCheckTv.setText("Username is incorrect");
//                                userNameCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
//                                break;
//                            case "incorrect password":
//                                passwordCheckTv.setText("Password is incorrect");
//                                passwordCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
//                                break;
//                        }
//                        break;
//                }
            }
        };

        userNameEdTxt.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (userNameEdTxt.length() == 0) {
                    userNameCheckTv.setText("Username is empty");
                    userNameCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                }
                if (passwordEdTxt.length() == 0) {
                    passwordCheckTv.setText("Password is empty");
                    passwordCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
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
                    passwordEdTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    passwordEdTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, 0, 0);
                }
            }
        });


    }



    @Override
    public void onResponse(Response response) {
        LoginResponse loginResponse = (LoginResponse) response;
        String status = loginResponse.getStatus();
        String error = loginResponse.getError();
        int userId = loginResponse.getData();
        //get data from msg and then check status value if success navigate to next activity
        //if failed print the error in username or password check textView
        switch (status) {
            case "success":
                //save userID in SharedPreferences
                SharedPreferences data = getSharedPreferences("userData", 0);
                SharedPreferences.Editor editor = data.edit();
                editor.putInt("userId", userId);
                ///////////////////////////////////////
                //get all student data
                ///////////////////////////////////////

                editor.commit();
                //navigate using intent to next Activity
                System.out.print("");
                Intent intent=new Intent(getApplicationContext(),SideMenuActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent();
//
//                startActivity(intent);
                break;
            case "fail":
                switch (error) {
                    case "incorrect username":
                        userNameCheckTv.setText("Username is incorrect");
                        userNameCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                        break;
                    case "incorrect password":
                        passwordCheckTv.setText("Password is incorrect");
                        passwordCheckTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                        break;
                }
                break;
        }
    }

    @Override
    public void onFaliure() {
        Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_LONG).show();

    }
}