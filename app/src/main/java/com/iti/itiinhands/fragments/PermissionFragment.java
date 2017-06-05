package com.iti.itiinhands.fragments;

import android.app.DatePickerDialog;


import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.iti.itiinhands.R;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Permission;
import com.iti.itiinhands.model.schedule.Supervisor;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.util.Calendar;


/**
 * Created by admin on 5/30/2017.
 */

public class PermissionFragment extends Fragment implements NetworkResponse{

    TextView supervisorName;
    TextView date;
    Button end;
    Button start;
    Button dateArrow;
    Button send;
    TextView startTime;
    TextView endTime;
    EditText cause;
    NetworkManager networkManager;
    Permission permission;
    UserData userData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.permission_fragment, container, false);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));

        date = (TextView) view.findViewById(R.id.datedate);
        networkManager = NetworkManager.getInstance(getActivity());
        networkManager.getSupervisor(this ,userData.getIntakeId());
        dateArrow =(Button) view.findViewById(R.id.date_arrow);
        supervisorName = (TextView) view.findViewById(R.id.supervisorName);
        start =(Button) view.findViewById(R.id.startTime_button);
        end = (Button)view.findViewById(R.id.endTime_button);
        startTime = (TextView)view.findViewById(R.id.startTime);
        endTime = (TextView)view.findViewById(R.id.endTime);
        cause = (EditText)view.findViewById(R.id.permissionCause);
        send =(Button) view.findViewById(R.id.permissionSend);
        permission = new Permission();
//        permission.setCreatorID(userData.g);



        dateArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        selectedmonth = selectedmonth + 1;
                        date.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        permission.setPermissionDate(selectedyear+"-"+selectedmonth+"-"+selectedday+" 00:00:00");

                    }
                }, mYear, mMonth, mDay);

                mDatePicker.show();



            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                        String am_pm = "AM";
                        if (selectedHour >= 12) {
                            am_pm = "PM";
                            if (selectedHour >= 13 && selectedHour < 24) {
                                selectedHour -= 12;
                            } else {
                                selectedHour = 12;
                            }
                        } else if (selectedHour == 0) {
                            selectedHour = 12;
                        }

                        startTime.setText(selectedHour + ":" + selectedMinute + " " + am_pm);
                        permission.setFromH(selectedHour);
                        permission.setFromMin(selectedMinute);

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });



        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), R.style.DatePickerTheme,new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                        String am_pm = "AM";
                        if (selectedHour >= 12) {
                            am_pm = "PM";
                            if (selectedHour >= 13 && selectedHour < 24) {
                                selectedHour -= 12;
                            } else {
                                selectedHour = 12;
                            }
                        } else if (selectedHour == 0) {
                            selectedHour = 12;
                        }

                        endTime.setText(selectedHour + ":" + selectedMinute + " " + am_pm);
                        permission.setToH(selectedHour);
                        permission.setToMin(selectedMinute);

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permission.setComment(cause.getText().toString());





            }
        });










    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResponse(Object response) {

        Supervisor supervisor = (Supervisor)response;

        supervisorName.setText(supervisor.getName());

        permission.setEmpID(supervisor.getId());


    }

    @Override
    public void onFailure() {




    }
}
