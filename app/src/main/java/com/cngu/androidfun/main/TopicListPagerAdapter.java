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

import java.util.List;

/**
 * A {@link android.support.v4.view.ViewPager} adapter to manage a list of {@link TopicListFragment}.
 */
public class TopicListPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = TopicListPagerAdapter.class.getSimpleName();

    private static final boolean DEBUG = true;

    private int mPageCount;
    private ITopicManager mTopicManager;
    private TopicView.OnClickListener mTopicClickListener;

    public TopicListPagerAdapter(FragmentManager fm) {
        super(fm);
        mPageCount = 0;
    }

    @Override
    public Fragment getItem(int position) {
        if (Debug.isInDebugMode(DEBUG)) {
            Log.d(TAG, "getItem(" + position + ")");
        }

        return TopicListFragment.newInstance().asFragment();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ITopicListFragment item = (TopicListFragment) super.instantiateItem(container, position);

        if (Debug.isInDebugMode(DEBUG)) {
            Log.d(TAG, "instantiateItem(ViewGroup, " + position + ")");
        }

        MenuTopic topicInHistory = (MenuTopic) mTopicManager.getTopicInHistory(position);
        Topic nextTopicInHistory  = mTopicManager.getTopicInHistory(position+1);

        List<Topic> topicList = topicInHistory.getSubtopics();
        IListSelector listSelector = new SingleListSelector();
        SelectableTopicList selectableTopicList = new SelectableTopicList(topicList, listSelector);

        if (nextTopicInHistory != null) {
            int indexOfNextTopic = topicInHistory.getIndexOfSubtopic(nextTopicInHistory);
            selectableTopicList.setSelected(indexOfNextTopic, true);
        }

        item.setTopicList(selectableTopicList);
        item.setTopicClickListener(mTopicClickListener);

        return item;
    }

    @Override
    public int getCount() {
        return mPageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTopicManager.getTopicInHistory(position).getTitle();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setPageCount(int pageCount) {
        mPageCount = pageCount;
    }

    public void addNewPage() {
        addNewPages(1);
    }

    public void addNewPages(int numPages) {
        mPageCount += numPages;
        notifyDataSetChanged();
    }

    /**
     * Removes the last {@link TopicListPagerAdapter#getCount()} - pageIndex - 1 pages.
     *
     * @param pageIndex 0-based index of the page to go back to.
     * @param clearSelection true to clear the selection of page {@code pageIndex}; false otherwise.
     */
    public void goBackToPage(int pageIndex, boolean clearSelection) {
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
