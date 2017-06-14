package com.iti.itiinhands.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.GridAdapterForHours;
import com.iti.itiinhands.beans.EmpHour;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.DataSerializer;
import com.iti.itiinhands.utilities.UserDataSerializer;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class WorkingHours extends Fragment implements com.iti.itiinhands.networkinterfaces.NetworkResponse,
        View.OnClickListener {

    public final String days = "Days";
    public final String hours = "Hours";
    NetworkManager networkManager;
    private com.iti.itiinhands.networkinterfaces.NetworkResponse myRef;
    private SlidingUpPanelLayout mLayout;
    private static final String TAG = "MainAcitvity";
    TextView textView, t1, t2;
    int[] images = {R.drawable.path3150, R.drawable.path, R.drawable.path3141, R.drawable.group2009,
            R.drawable.group2013, R.drawable.group2011, R.drawable.group2018, R.drawable.path3159};
    String[] data = new String[]{
            "Working Days", "Absence", "Permissions", "Mission hours", "Late days",
            "Attend days", "Attend hours", "Vacation hours"
    };
    Calendar c1;
    GridView hoursGrid;
    EditText startDate, endDate;
    private int mYear, mMonth, mDay;
    EmpHour empHour;
    private int flag = 0, secondFlag = 0;
    TextView numberText, wordtext;
    UserData userData;
    int id;
    Button startBtn;
    int gridFlag  = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_working_hours, container, false);

        getActivity().setTitle("Working hours");

//        setContentView(R.layout.fragment_working_hours);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        getActivity().setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        networkManager = NetworkManager.getInstance(getContext());
        hoursGrid = (GridView) view.findViewById(R.id.HoursGridView);
        mLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        startBtn = (Button) view.findViewById(R.id.startBtn);
        numberText = (TextView) view.findViewById(R.id.resultText);
        wordtext = (TextView) view.findViewById(R.id.result2Text);
        t1 = (TextView) view.findViewById(R.id.t1);
        t2 = (TextView) view.findViewById(R.id.t2);
        startDate = (EditText) view.findViewById(R.id.startDateTv);
        endDate = (EditText) view.findViewById(R.id.endDateTv);
        hoursGrid.setAdapter(new GridAdapterForHours(getContext(), data, images));
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        myRef = this;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
        id = sharedPreferences.getInt(Constants.USER_ID, 0);
        hoursGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (gridFlag == 1) {
                    switch (position) {
                        case 0:
                            numberText.setText(empHour.getWorkingDays().toString());
                            wordtext.setText(days);
                            onBackPressed();
                            break;
                        case 1:
                            numberText.setText(empHour.getAbsenceDays().toString());
                            wordtext.setText(days);
                            onBackPressed();
                            break;
                        case 2:
                            numberText.setText(empHour.getPermissionHours().toString());
                            wordtext.setText(hours);
                            onBackPressed();
                            break;
                        case 3:
                            numberText.setText(empHour.getMissionHours().toString());
                            wordtext.setText(hours);
                            onBackPressed();
                            break;
                        case 4:
                            numberText.setText(empHour.getLateDays().toString());
                            wordtext.setText(days);
                            onBackPressed();
                            break;
                        case 5:
                            numberText.setText(empHour.getAttendDays().toString());
                            wordtext.setText(days);
                            onBackPressed();
                            break;
                        case 6:
                            numberText.setText(empHour.getAttendHours().toString());
                            wordtext.setText(hours);
                            onBackPressed();
                            break;
                        case 7:
                            numberText.setText(empHour.getVacationHours().toString());
                            wordtext.setText(hours);
                            onBackPressed();
                            break;
                    }
                } else {
                    Toast.makeText(getActivity(), "Select date first", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

        panelListener();


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkManager.isOnline()){
                    if (!(endDate.getText().toString().isEmpty() || startDate.getText().toString().isEmpty())){
                        networkManager.getEmployeeHours(myRef, id, startDate.getText().toString(),
                                endDate.getText().toString());
                        startBtn.setBackgroundResource(R.drawable.path_895);
                        startBtn.setClickable(false);
                    }else{
                        Toast.makeText(getActivity(), "select date first", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    onFailure();
                }
            }
        });
        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response.getStatus().equals(Response.SUCCESS)) {
            empHour = DataSerializer.convert(response.getResponseData(), EmpHour.class);
            startBtn.setClickable(true);
            startBtn.setBackgroundResource(R.drawable.path_1);
            gridFlag = 1;
        }
    }

    @Override
    public void onFailure() {
        new NetworkUtilities().networkFailure(getActivity());
    }

    public void panelListener() {

        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            // During the transition of expand and collapse onPanelSlide function will be called.
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.e(TAG, "onPanelSlide, offset " + slideOffset);
            }

            // This method will be call after slide up layout
            @Override
            public void onPanelExpanded(View panel) {
                Log.e(TAG, "onPanelExpanded");

            }

            // This method will be call after slide down layout.
            @Override
            public void onPanelCollapsed(View panel) {
                Log.e(TAG, "onPanelCollapsed");
            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.e(TAG, "onPanelAnchored");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.e(TAG, "onPanelHidden");
            }
        });
    }

    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.getActivity().onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.startDateTv) {

            final Calendar current = Calendar.getInstance();
            c1 = Calendar.getInstance();
            mYear = c1.get(Calendar.YEAR);
            mMonth = c1.get(Calendar.MONTH);
            mDay = c1.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            c1.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                            if (c1.compareTo(current) >= 0) {
                                //The set Date/Time already passed
                                t1.setVisibility(View.VISIBLE);
                                startDate.setText("");
                                flag = 0;
                            } else {
                                startDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                flag = 1;
                                t1.setVisibility(View.INVISIBLE);
                            }

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (v.getId() == R.id.endDateTv) {
            if (flag != 1) {
//                Toast.makeText(getContext(), "Choose Start Date First", Toast.LENGTH_SHORT).show();
                t2.setText("Choose Start Date First");
                t2.setVisibility(View.VISIBLE);
            } else {
                final Calendar current = Calendar.getInstance();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                c.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                                if (c.compareTo(current) >= 0) {
                                    //The set Date/Time already passed
                                    t2.setVisibility(View.VISIBLE);
                                    t2.setText("This date is not available");
                                    endDate.setText("");
                                    secondFlag = 0;
                                } else if (c.compareTo(c1) <= 0) {
                                    endDate.setText("");
                                    t2.setVisibility(View.VISIBLE);
                                    t2.setText("This date is not available");
                                    secondFlag = 0;
                                }  else{
                                    endDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                    secondFlag = 1;
                                    t2.setVisibility(View.INVISIBLE);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        }
    }


    public void getCurrentDate(View view) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM / dd / yyyy ");
        String strDate = "Current Date : " + mdformat.format(calendar.getTime());
    }

}
