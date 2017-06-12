package com.iti.itiinhands.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Calendar;


public class WorkingHours extends AppCompatActivity implements com.iti.itiinhands.networkinterfaces.NetworkResponse,
        View.OnClickListener {

    NetworkManager networkManager;
    private com.iti.itiinhands.networkinterfaces.NetworkResponse myRef;
    private SlidingUpPanelLayout mLayout;
    private static final String TAG = "MainAcitvity";
    TextView textView;
    int[] images = {R.drawable.path3150, R.drawable.path, R.drawable.path3141, R.drawable.group2009,
            R.drawable.group2013, R.drawable.group2011, R.drawable.group2018, R.drawable.path3159};
    String[] data = new String[]{
            "Working hours", "Absence", "Permissions", "Mission hours", "Late days",
            "Attend days", "Attend hours", "Vacation"
    };
    GridView hoursGrid;
    EditText startDate, endDate;
    private int mYear, mMonth, mDay;
    EmpHour empHour;
    private int flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_working_hours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        networkManager = NetworkManager.getInstance(this);
        hoursGrid = (GridView) findViewById(R.id.HoursGridView);
        startDate = (EditText) findViewById(R.id.startDateTv);
        endDate = (EditText) findViewById(R.id.endDateTv);
        hoursGrid.setAdapter(new GridAdapterForHours(this, data, images));
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        myRef = this;
        hoursGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(WorkingHours.this, "You Clicked at " + position, Toast.LENGTH_SHORT).show();

            }
        });

        init();
        panelListener();
    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void onFailure() {

    }

    public void init() {

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
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


    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.getEmpBtn) {
//            if (!(endDate.getText().toString().isEmpty() || startDate.getText().toString().isEmpty())) {
//                System.out.println(startDate.getText().toString());
//                System.out.println(endDate.getText().toString());
//
//            } else {
//                Toast.makeText(this, "please select date firsst", Toast.LENGTH_SHORT).show();
//            }
//
//        } else

        if (v.getId() == R.id.startDateTv) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            startDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            flag = 1;
                            System.out.println("Flag is :" +flag);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            System.out.println("***********Start Date");
        } else if (v.getId() == R.id.endDateTv) {
            if(flag !=1){
                Toast.makeText(this, "Choose Start Date First", Toast.LENGTH_SHORT).show();
            }else{
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                endDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                networkManager.getEmployeeHours(myRef, 1018, startDate.getText().toString(),
                                    endDate.getText().toString());

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        }
    }
}
