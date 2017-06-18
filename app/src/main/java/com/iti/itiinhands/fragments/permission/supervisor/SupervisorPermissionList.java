package com.iti.itiinhands.fragments.permission.supervisor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.permission.StudentPermissionsAdapter;
import com.iti.itiinhands.adapters.permission.supervisor.SupervisorPermissionsAdapter;
import com.iti.itiinhands.model.Permission;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;


public class SupervisorPermissionList extends Fragment implements NetworkResponse {
    private boolean flag = false;


    private ArrayList<Permission> permissions = new ArrayList<>();
    private RecyclerView recyclerView;
    private SupervisorPermissionsAdapter studentPermissionsAdapter;
    private NetworkManager networkManager;
    private int id;


    public SupervisorPermissionList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_student_permission_list, container, false);
        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.branch_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Bundle b = getArguments();
        if (b != null) flag = true;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);

        id = sharedPreferences.getInt(Constants.USER_ID, 0);


        if (networkManager.isOnline()) {
            networkManager.getSupervisorPermissions(this, id);

        } else {
            new NetworkUtilities().networkFailure(getContext());
        }



        return view;

    }


    private void temp() {

        Permission p = new Permission();
        p.setComment("fdfddkfgsdfhsdn'pjcipsadh fhs;idfhipgsd kdfjgs;dhfg ' sdfjgsidpfh'dst");
        p.setPermissionStatus("fucj okk");
        p.setFromH(1);
        p.setFromMin(30);
        p.setToH(2);
        p.setToMin(30);
        p.setPermissionDate("30/6/2017");
        permissions.add(p);
        permissions.add(p);

        studentPermissionsAdapter = new SupervisorPermissionsAdapter(permissions, getActivity().getApplicationContext(), this);
        recyclerView.setAdapter(studentPermissionsAdapter);

    }


    @Override
    public void onResponse(Response response) {
        if (response != null && getActivity() != null) {
            if (response.getStatus().equals(Response.SUCCESS)) {
                permissions = DataSerializer.convert(response.getResponseData(), new TypeToken<ArrayList<Permission>>() {
                }.getType());

                if (flag) {

                    ArrayList<Permission> filtered = new ArrayList<>();

                    for (Permission p : permissions) {

                        if (p.getPermissionStatus().equalsIgnoreCase("pending")) filtered.add(p);

                    }

                    studentPermissionsAdapter = new SupervisorPermissionsAdapter(filtered, getActivity().getApplicationContext(), this);
                    recyclerView.setAdapter(studentPermissionsAdapter);

                } else {
                    studentPermissionsAdapter = new SupervisorPermissionsAdapter(permissions, getActivity().getApplicationContext(), this);
                    recyclerView.setAdapter(studentPermissionsAdapter);

                }

//                studentPermissionsAdapter = new StudentPermissionsAdapter(permissions, getActivity().getApplicationContext());
//                recyclerView.setAdapter(studentPermissionsAdapter);
            } else onFailure();
        } else {
            onFailure();
        }
    }

    @Override
    public void onFailure() {

        new NetworkUtilities().networkFailure(getContext());
    }


    public void sendResponse(int permissionId, boolean b) {
        if (networkManager.isOnline()) {
            networkManager.sendPermissionResponse(this, permissionId, b);

        } else {
            new NetworkUtilities().networkFailure(getContext());
        }


    }

    public void responseSent(boolean b) {
        if (b) {
            if (getActivity() != null) {
                Toast.makeText(getActivity(), "Response sent.", Toast.LENGTH_SHORT).show();

                if (networkManager.isOnline()) {
                    networkManager.getSupervisorPermissions(this, id);

                } else {
                    new NetworkUtilities().networkFailure(getContext());
                }
            }


        } else {
            new NetworkUtilities().networkFailure(getContext());
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&  getContext()!=null){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);

            id = sharedPreferences.getInt(Constants.USER_ID, 0);


            if (networkManager.isOnline()) {
                networkManager.getSupervisorPermissions(this, id);

            } else {
                new NetworkUtilities().networkFailure(getContext());
            }



        }
    }


}