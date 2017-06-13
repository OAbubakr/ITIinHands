package com.iti.itiinhands.fragments;

import android.app.DatePickerDialog;


import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Build;

import com.iti.itiinhands.R;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Permission;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.schedule.Supervisor;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.DataSerializer;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by admin on 5/30/2017.
 */

public class PermissionFragment extends Fragment implements NetworkResponse {

    TextView supervisorName;
    TextView date;
    ImageView end;
    ImageView start;
    ImageView dateArrow;
    Button send;
    TextView startTime;
    TextView endTime;
    EditText cause;
    NetworkManager networkManager;
    Permission permission;
    UserData userData;
    LinearLayout datePart;
    LinearLayout startTimePart;
    LinearLayout endTimePart;
    TextView errorMessage;
    TextView errorMessageDate;
    TextView errorMessageStart;
    TextView errorMessageEnd;
    TextView errorMessageComment;
    boolean dateFlag = true;
    boolean startFlag = true;
    boolean endFlag = true;
    Calendar timeCheckStart = Calendar.getInstance();
    Calendar timeCheckEnd = Calendar.getInstance();
    int startHourCheck;
    int monthCheck;
    int startMinuteCheck;
    int yearCheck;
    int dayCheck;
    int endMinuteCheck;
    int endHourCheck;


    ProgressBar spinner;

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

        datePart = (LinearLayout) view.findViewById(R.id.datePart);
        startTimePart = (LinearLayout) view.findViewById(R.id.startTimePart);
        endTimePart = (LinearLayout) view.findViewById(R.id.endTimePart);
        date = (TextView) view.findViewById(R.id.datedate);
        networkManager = NetworkManager.getInstance(getActivity());
        if (networkManager.isOnline()) {
            networkManager.getSupervisor(this, userData.getPlatformIntakeId());
        } else {

            new NetworkUtilities().networkFailure(getActivity());

        }


        dateArrow = (ImageView) view.findViewById(R.id.date_arrow);
        supervisorName = (TextView) view.findViewById(R.id.supervisorName);
        start = (ImageView) view.findViewById(R.id.startTime_button);
        end = (ImageView) view.findViewById(R.id.endTime_button);
        startTime = (TextView) view.findViewById(R.id.startTime);
        endTime = (TextView) view.findViewById(R.id.endTime);
        cause = (EditText) view.findViewById(R.id.permissionCause);
        send = (Button) view.findViewById(R.id.permissionSend);
        errorMessageDate = (TextView) view.findViewById(R.id.error_message_permission_Date);
        errorMessageStart = (TextView) view.findViewById(R.id.error_message_permission_StartTime);
        errorMessageEnd = (TextView) view.findViewById(R.id.error_message_permission_EndTime);
        errorMessageComment = (TextView) view.findViewById(R.id.error_message_permission_Cause);


        permission = new Permission();
        permission.setCreatorID(userData.getId());
        permission.setStudentName(userData.getName());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String currentDate = formatter.format(c.getTime());
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm a");
        date.setText(currentDate);
        String currentTime = formatter2.format(c.getTime());
        timeCheckEnd.add(Calendar.HOUR, 1);
        String currentTimePlusHour = formatter2.format(timeCheckEnd.getTime());
        startTime.setText(currentTime);
        endTime.setText(currentTimePlusHour);

        yearCheck = c.get(Calendar.YEAR);
        monthCheck = c.get(Calendar.MONTH);
        dayCheck = c.get(Calendar.DAY_OF_MONTH);
        startHourCheck = c.get(Calendar.HOUR);
        startMinuteCheck = c.get(Calendar.MINUTE);
        endMinuteCheck = timeCheckEnd.get(Calendar.MINUTE);
        endHourCheck = timeCheckEnd.get(Calendar.HOUR);

        int temp = monthCheck + 1;

        permission.setPermissionDate(dayCheck + "/" + temp + "/" + yearCheck);
        permission.setFromMin(startMinuteCheck);
        permission.setFromH(startHourCheck);
        permission.setToH(endHourCheck);
        permission.setToMin(endMinuteCheck);

        send.setEnabled(false);
        setButtonColorTint(Color.GRAY);

        datePart.setOnClickListener(new View.OnClickListener() {
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

                        if (checkDate(selectedyear, selectedmonth - 1, selectedday)) {
                            permission.setPermissionDate(selectedday + "/" + selectedmonth + "/" + selectedyear);
                            errorMessageDate.setText("");
                            errorMessageDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            dateFlag = true;
                            if (dateFlag && startFlag && endFlag) {
                                errorMessageComment.setText("");
                                errorMessageComment.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            }

                        } else {
                            errorMessageDate.setText("Invalid Date");
                            errorMessageDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                            dateFlag = false;
                        }

                    }
                }, mYear, mMonth, mDay);

                mDatePicker.show();


            }
        });


        startTimePart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), R.style.DatePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        timeCheckStart.set(Calendar.HOUR_OF_DAY, selectedHour);
                        timeCheckStart.set(Calendar.MINUTE, selectedMinute);

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

                        String time = selectedHour + ":" + selectedMinute + " " + am_pm;

                        startTime.setText(time);

                        if (timeCheckStart.getTimeInMillis() > mcurrentTime.getTimeInMillis()) {
//
                            //permisson
                            permission.setFromH(selectedHour);

                            permission.setFromMin(selectedMinute);

                            startFlag = true;
                            errorMessageStart.setText("");
                            errorMessageStart.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                            if (dateFlag && startFlag && endFlag) {
                                errorMessageComment.setText("");
                                errorMessageComment.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            }


                        } else {
                            errorMessageStart.setText("Invalid Time");
                            errorMessageStart.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                            startFlag = false;
                        }


                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });


        endTimePart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), R.style.DatePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        timeCheckEnd.set(Calendar.HOUR_OF_DAY, selectedHour);
                        timeCheckEnd.set(Calendar.MINUTE, selectedMinute);

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

                        String time = selectedHour + ":" + selectedMinute + " " + am_pm;
                        endTime.setText(time);


                        if (timeCheckEnd.getTimeInMillis() > timeCheckStart.getTimeInMillis()) {
//
                            //permisson
                            permission.setToH(selectedHour);
                            permission.setToMin(selectedMinute);

                            endFlag = true;
                            errorMessageEnd.setText("");
                            errorMessageEnd.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                            if (dateFlag && startFlag && endFlag) {
                                errorMessageComment.setText("");
                                errorMessageComment.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            }

                        } else {
                            errorMessageEnd.setText("Invalid Time");
                            errorMessageEnd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);
                            endFlag = false;
                        }


                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dateFlag && startFlag && endFlag) {

                    if (cause.getText().toString().equals("")) {
                        errorMessageComment.setText("You must send a comment.");
                        errorMessageComment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);

                    } else if (cause.getText().length() < 15) {
                        errorMessageComment.setText("too short comment.");
                        errorMessageComment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);

                    } else {

                        if (send != null) send.setEnabled(false);
                        setButtonColorTint(Color.GRAY);
                        errorMessageComment.setText("");
                        errorMessageComment.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        permission.setComment(cause.getText().toString());
                        if (networkManager.isOnline()) {
                            networkManager.sendPermission(PermissionFragment.this, permission);
                        } else onFailure();

//                        FragmentTransaction trns = getFragmentManager().beginTransaction();
//
//                        trns.replace(R.id.content_frame,new AboutIti());

                        //   trns.commit();


                    }


                } else {
                    errorMessageComment.setText("Check invalid inputs.");
                    errorMessageComment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning_sign, 0);


                }


            }
        });


        spinner = (ProgressBar) view.findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResponse(Response response) {




            if (response != null && response.getStatus().equals(Response.SUCCESS)) {

                if (response.getResponseData() != null) {

                    Supervisor supervisor = DataSerializer.convert(response.getResponseData(), Supervisor.class);
                    supervisorName.setText(supervisor.getName());
                    permission.setEmpID(supervisor.getId());
                    if (send != null) send.setEnabled(true);
                    setButtonColorTint(Color.parseColor("#7F0000"));


                } else {

                    if (getActivity() != null)
                        Toast.makeText(getActivity(), "Your Permission has been sent successfully", Toast.LENGTH_SHORT).show();

                    cause.setText("");

                    if (send != null) send.setEnabled(true);
                    setButtonColorTint(Color.parseColor("#7F0000"));


                }


            }else onFailure();

        spinner.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
//        Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
      if (spinner!=null)  spinner.setVisibility(View.GONE);

        onFail();


    }


    public boolean checkDate(int myyear, int mymonth, int myday) {
        boolean notPassed = true;
        Calendar current = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(myyear, mymonth, myday, 0, 0);


        if (!DateUtils.isToday(cal.getTimeInMillis())) {

            if (cal.compareTo(current) < 0) {
                notPassed = false;
            } else {
                notPassed = true;
            }
        }

        return notPassed;
    }


    public void onFail() {

        if (permission.getEmpID() == 0) {

            new NetworkUtilities().networkFailure(getActivity());
            if (send != null) send.setEnabled(false);
            setButtonColorTint(Color.GRAY);

        } else {

            new NetworkUtilities().networkFailure(getActivity());
            if (send != null) send.setEnabled(true);
            setButtonColorTint(Color.parseColor("#7F0000"));


        }

    }

    private void setButtonColorTint(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            send.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            Drawable wrapDrawable = DrawableCompat.wrap(send.getBackground());
            DrawableCompat.setTint(wrapDrawable, color);
            send.setBackgroundDrawable(DrawableCompat.unwrap(wrapDrawable));
        }
    }


}
