package com.cngu.androidfun.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cngu.androidfun.TopicListFragment;

/**
 * A {@link android.support.v4.view.ViewPager} adapter to manage a list of {@link com.cngu.androidfun.TopicListFragment}.
 */
public class TopicListPagerAdapter extends FragmentStatePagerAdapter {

    public TopicListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TopicListFragment.newInstance().asFragment();
    }

    @Override
    public int getCount() {
        return 11;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "TAB " + position;
    }
}
