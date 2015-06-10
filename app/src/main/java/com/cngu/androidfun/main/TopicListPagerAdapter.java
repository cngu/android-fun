package com.cngu.androidfun.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.view.TopicView;

import java.util.List;

/**
 * A {@link android.support.v4.view.ViewPager} adapter to manage a list of {@link TopicListFragment}.
 */
public class TopicListPagerAdapter extends FragmentStatePagerAdapter {

    private ITopicManager mTopicManager;
    private IListSelector mListSelector;

    private TopicView.OnClickListener mTopicClickListener;
    private int mPageCount;

    public TopicListPagerAdapter(FragmentManager fm) {
        super(fm);
        mListSelector = new SingleListSelector();
        mPageCount = 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ITopicListFragment item = (TopicListFragment) super.instantiateItem(container, position);

        MenuTopic topicInHistory = mTopicManager.getTopicInHistory(position);
        List<Topic> topicList = topicInHistory.getSubtopics();
        SelectableTopicList selectableTopicList = new SelectableTopicList(topicList, mListSelector);

        item.setTopicList(selectableTopicList);
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
    public void goBackOnePage() {
        if (mPageCount == 0) {
            return;
        }

        mPageCount--;
        notifyDataSetChanged();
    }

    /**
     * Removes the last {@link TopicListPagerAdapter#getCount()} - pageIndex - 1 pages.
     *
     * @param pageIndex 0-based index of the page to go back to.
     */
    public void goBackToPage(int pageIndex) {
        if (pageIndex < 0 || pageIndex >= mPageCount-1) {
            return;
        }

        mPageCount = pageIndex + 1;
        notifyDataSetChanged();
    }

    public void setTopicManager(ITopicManager topicManager) {
        mTopicManager = topicManager;
    }

    public void setTopicClickListener(TopicView.OnClickListener listener) {
        mTopicClickListener = listener;
    }
}
