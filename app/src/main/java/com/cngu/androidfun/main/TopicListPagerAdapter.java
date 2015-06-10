package com.cngu.androidfun.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.cngu.androidfun.view.TopicView;

/**
 * A {@link android.support.v4.view.ViewPager} adapter to manage a list of {@link TopicListFragment}.
 */
public class TopicListPagerAdapter extends FragmentStatePagerAdapter {

    private TopicView.OnClickListener mTopicClickListener;
    private int mPageCount;

    public TopicListPagerAdapter(FragmentManager fm) {
        super(fm);
        mPageCount = 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ITopicListFragment item = (TopicListFragment) super.instantiateItem(container, position);

        Log.d("TAG", "instantiateItem " + position);

        item.setTopicClickListener(mTopicClickListener);

        return item;
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

    /**
     * Removes the last page and immediately notifies the ViewPager.
     */
    public void removeLastPage() {
        if (mPageCount == 0) {
            return;
        }

        mPageCount--;
        notifyDataSetChanged();
    }

    public void setTopicClickListener(TopicView.OnClickListener listener) {
        mTopicClickListener = listener;
    }
}
