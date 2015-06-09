package com.cngu.androidfun.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * A {@link android.support.v4.view.ViewPager} adapter to manage a list of {@link TopicListFragment}.
 */
public class TopicListPagerAdapter extends FragmentStatePagerAdapter {

    private int mPageCount;

    public TopicListPagerAdapter(FragmentManager fm) {
        super(fm);
        mPageCount = 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return TopicListFragment.newInstance().asFragment();
    }

    @Override
    public int getCount() {
        return mPageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab " + position;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void addNewPage() {
        addNewPages(1);
    }

    public void addNewPages(int numPages) {
        mPageCount += numPages;
        notifyDataSetChanged();
    }

    public void removeLastPage() {
        mPageCount--;
        notifyDataSetChanged();
    }
}
