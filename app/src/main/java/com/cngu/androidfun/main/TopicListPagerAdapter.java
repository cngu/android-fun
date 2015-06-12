package com.cngu.androidfun.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.debug.Debug;
import com.cngu.androidfun.view.TopicView;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link android.support.v4.view.ViewPager} adapter to manage a list of {@link TopicListFragment}.
 */
public class TopicListPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = TopicListPagerAdapter.class.getSimpleName();

    private static final boolean DEBUG = true;

    private List<ITopicListFragment> mPages;

    private ITopicManager mTopicManager;
    private TopicView.OnClickListener mTopicClickListener;

    public TopicListPagerAdapter(FragmentManager fm) {
        super(fm);
        mPages = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        if (Debug.isInDebugMode(DEBUG)) {
            Log.d(TAG, "getItem(" + position + ")");
        }

        return mPages.get(position).asFragment();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ITopicListFragment item = (TopicListFragment) super.instantiateItem(container, position);

        if (Debug.isInDebugMode(DEBUG)) {
            Log.d(TAG, "instantiateItem(ViewGroup, " + position + ")");
        }

        return item;
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab " + position;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
        //return POSITION_NONE;
    }

    public void addNewPage() {
        addNewPages(1);
    }

    public void addNewPages(int numPages) {
        while (numPages-- > 0) {
            ITopicListFragment item = TopicListFragment.newInstance();

            int newPageIndex = mPages.size();
            MenuTopic topicInHistory = (MenuTopic) mTopicManager.getTopicInHistory(newPageIndex);
            Topic nextTopicInHistory  = mTopicManager.getTopicInHistory(newPageIndex+1);

            List<Topic> topicList = topicInHistory.getSubtopics();
            IListSelector listSelector = new SingleListSelector();
            SelectableTopicList selectableTopicList = new SelectableTopicList(topicList, listSelector);

            if (nextTopicInHistory != null) {
                int indexOfNextTopic = topicInHistory.getIndexOfSubtopic(nextTopicInHistory);
                selectableTopicList.setSelected(indexOfNextTopic, true);
            }

            item.setTopicList(selectableTopicList);
            item.setTopicClickListener(mTopicClickListener);

            mPages.add(item);
        }

        notifyDataSetChanged();
    }

    /**
     * Removes the last page and immediately notifies the ViewPager.
     */
    public void goBackOnePage() {
        if (mPages.isEmpty()) {
            return;
        }

        mPages.remove(mPages.size()-1);
        notifyDataSetChanged();
    }

    /**
     * Removes the last {@link TopicListPagerAdapter#getCount()} - pageIndex - 1 pages.
     *
     * @param pageIndex 0-based index of the page to go back to.
     */
    public void goBackToPage(int pageIndex) {
        if (pageIndex < 0 || pageIndex >= mPages.size()-1) {
            return;
        }

        int pagesToRemove = mPages.size() - (pageIndex + 1);
        while (pagesToRemove-- > 0) {
            mPages.remove(mPages.size()-1);
        }

        notifyDataSetChanged();
    }

    public void setTopicManager(ITopicManager topicManager) {
        mTopicManager = topicManager;
    }

    public void setTopicClickListener(TopicView.OnClickListener listener) {
        mTopicClickListener = listener;
    }
}
