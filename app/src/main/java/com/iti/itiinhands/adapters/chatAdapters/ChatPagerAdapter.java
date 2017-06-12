package com.iti.itiinhands.adapters.chatAdapters;

/**
 * Created by omari on 5/28/2017.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iti.itiinhands.fragments.chat.ChatFragment;
import com.iti.itiinhands.fragments.chat.RecentChatsFragment;

public class ChatPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ChatFragment tab1;
    RecentChatsFragment tab2;

    public ChatPagerAdapter(FragmentManager fm, ChatFragment chatFragment, RecentChatsFragment recentChatsFragment, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        tab1 = chatFragment;
        tab2 = recentChatsFragment;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return tab1;
            case 1:
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}