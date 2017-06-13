package com.iti.itiinhands.fragments.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.events.EventAdapter;
import com.iti.itiinhands.adapters.events.EventsPagerAdapter;
import com.iti.itiinhands.model.Event;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;

/**
 * Created by admin on 6/10/2017.
 */

public class EventTabFragment extends Fragment implements NetworkResponse {

    private ViewPager viewPager;
    private ArrayList<Event> eventsList = new ArrayList<>();
    private EventAdapter eventsAdapter;
    private NetworkManager networkManager;
    private EventListFragment eventListFragment;
    private EventCalendarFragment eventCalendarFragment;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        setHasOptionsMenu(true);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.event_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Calendar"));

        eventListFragment = new EventListFragment();
        eventsAdapter = new EventAdapter(eventsList, getActivity().getApplicationContext());
        eventListFragment.setEventsAdapter(eventsAdapter);


        eventCalendarFragment = new EventCalendarFragment();

        //    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) view.findViewById(R.id.event_pager);
        final EventsPagerAdapter adapter = new EventsPagerAdapter(
                tabLayout.getTabCount(), getActivity().getSupportFragmentManager(), eventCalendarFragment, eventListFragment);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());
        prepareEventData();

    }

    private void prepareEventData() {

        if (networkManager.isOnline()) {
            networkManager.getEvents(this);
        }
    }

    @Override
    public void onResponse(Response response) {


        if (response != null && response.getStatus().equals(Response.SUCCESS)) {

            ArrayList<Event> result = DataSerializer.convert(response.getResponseData(), new TypeToken<ArrayList<Event>>() {
            }.getType());
            eventsList.addAll(result);

            progressBar.setVisibility(View.GONE);

            eventsAdapter.notifyDataSetChanged();
            eventCalendarFragment.setAllEvents(eventsList);

        }
        else
            onFailure();


    }


    @Override
    public void onFailure() {
        new NetworkUtilities().networkFailure(getActivity());
    }


}
