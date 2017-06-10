package com.iti.itiinhands.fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.JobOpportunity;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;
import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class PostJobFragment extends Fragment implements NetworkResponse, View.OnTouchListener {

    private EditText  jobTitle, jobDescription, jobYearExperience,
              jobClosingDate, jobSendTo, jobNoNeed;

    private TextView checkEmail, checkTitle, checkDesc, checkYear,
            checkCloseDate, checkNeed;

    private TextView companyName;
    private ImageView companyImage;
    private Button postButton;
    private NetworkManager networkManager;
    String email, yearNumb, closingDate;
    int noNeed;
    Boolean check = true, closeDateF = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_post_job, container, false);

        SharedPreferences data = getActivity().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        final int companyId = data.getInt(Constants.USER_TYPE, 0);
        UserData userData = UserDataSerializer.deSerialize(data.getString(Constants.USER_OBJECT,""));
        final String compName = userData.getCompanyName();
        final String compImage = userData.getCompanyLogoPath();
        System.out.println("----------------------------"+ compImage);

        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());
        companyName = (TextView) view.findViewById(R.id.comp_name);
        companyName.setText(compName);

        companyImage = (ImageView) view.findViewById(R.id.comp_logo);

        if(companyImage != null){
            Picasso.with(getActivity().getApplicationContext())
                    .load(compImage)
                    .into(companyImage);
        }


        //Edit Text For Input Date
        jobTitle = (EditText) view.findViewById(R.id.jobTitle);
        jobDescription = (EditText) view.findViewById(R.id.jobDescription);
        jobSendTo = (EditText) view.findViewById(R.id.jobSendTo);
        jobYearExperience = (EditText) view.findViewById(R.id.jobYearExperience);
        jobNoNeed = (EditText) view.findViewById(R.id.jobNoNeed);
        jobClosingDate = (EditText) view.findViewById(R.id.jobClosingDate);

        //Check Text For Validation Warning
        checkEmail = (TextView) view.findViewById(R.id.email_check);
        checkTitle = (TextView) view.findViewById(R.id.title_check);
        checkDesc = (TextView) view.findViewById(R.id.desc_check);
        checkYear = (TextView) view.findViewById(R.id.year_check);
        checkCloseDate = (TextView) view.findViewById(R.id.closeDate_check);
        checkNeed = (TextView) view.findViewById(R.id.need_check);

        //Click Listener For Edit Texts
        jobTitle.setOnTouchListener(this);
        jobDescription.setOnTouchListener(this);
        jobSendTo.setOnTouchListener(this);
        jobYearExperience.setOnTouchListener(this);
        jobNoNeed.setOnTouchListener(this);

        //----------------------------------------JOB CLOSING DATE----------------------------------
        jobClosingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCloseDate.setText("");
                checkCloseDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        System.out.println("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        jobClosingDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        closingDate = selectedyear + "-" + selectedmonth + "-" + selectedday;
                        closeDateF = checkDate(selectedyear, selectedmonth-1, selectedday);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();
            }
        });

        //---------------------------------------POST BUTTON----------------------------------------
        postButton = (Button) view.findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check = true;
                //---------------------------------------EMAIL--------------------------------------
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                email = jobSendTo.getText().toString().trim();
                if(email.isEmpty() || !email.matches(emailPattern)){
                    checkEmail.setText("Not valid email");
                    checkEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                    check = false;
                }

                //----------------------------------------YEAR--------------------------------------
                yearNumb = jobYearExperience.getText().toString();
                try{
                    Integer.parseInt(yearNumb);
                }catch (NumberFormatException e){
                    checkYear.setText("Please enter number of year experience");
                    checkYear.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                    check = false;
                }

                //------------------------------------NUMBER NEEDED---------------------------------
                String needNumb = jobNoNeed.getText().toString();
                try{
                    noNeed = Integer.parseInt(needNumb);
                }catch (NumberFormatException e){
                    checkNeed.setText("Please enter number of people needed");
                    checkNeed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                    check = false;
                }

                //-------------------------------------JOB TITLE------------------------------------
                String title = jobTitle.getText().toString();
                if(title.isEmpty()){
                    checkTitle.setText("Please enter job title");
                    checkTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                    check = false;
                }

                //----------------------------------JOB DESCRIPTION---------------------------------
                String desc = jobDescription.getText().toString();
                if(desc.isEmpty()){
                    checkDesc.setText("Please enter job description");
                    checkDesc.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                    check = false;
                }

                //----------------------------------CLOSING DATE------------------------------------
                String closingDate = jobClosingDate.getText().toString();
                if(!closeDateF || closingDate.isEmpty()){
                    checkCloseDate.setText("Please enter valid closing date");
                    checkCloseDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                    check = false;
                }
                System.out.println("Check " + check + "- closing date " + closeDateF);

                if(check){ //All data is valid

                    JobOpportunity jobOpportunity = new JobOpportunity(companyId, "", title, desc, yearNumb,
                        closingDate, email, noNeed, 0, "", compName);

                    if (networkManager.isOnline()){
                        networkManager.postJob(PostJobFragment.this, jobOpportunity);
                    }
                }
            }
        });

        return view;
    }

    //------------------------------------------CHECK DATE------------------------------------------
    public boolean checkDate(int myyear, int mymonth, int myday) {
        boolean notPassed;
        Calendar current = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(myyear, mymonth, myday, 0, 0, 0);

        if (cal.compareTo(current) <= 0) {
            notPassed = false;
        } else {
            notPassed = true;
        }
        return notPassed;
    }

    @Override
    public void onResponse(Response response) {
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.jobSendTo:
                checkEmail.setText("");
                checkEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            case R.id.jobTitle:
                checkTitle.setText("");
                checkTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            case R.id.jobDescription:
                checkDesc.setText("");
                checkDesc.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            case R.id.jobYearExperience:
                checkYear.setText("");
                checkYear.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            case R.id.jobNoNeed:
                checkNeed.setText("");
                checkNeed.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
        }
        return false;
    }
}
