package com.cngu.androidfun.main;

import android.os.Handler;
import android.util.Log;

import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.debug.Debug;

/**
 * Presenter for {@link MainFragment}.
 */
public class MainPresenter implements IMainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private static final boolean DEBUG = true;

    private static final int CLICK_DELAY = 125;

    private IRootView mRootView;
    private IMainFragment mView;
    private ITopicManager mTopicManager;

    public MainPresenter(IRootView rootView, IMainFragment view) {
        mRootView = rootView;
        mView = view;
    }

    @Override
    public void setTopicManager(ITopicManager topicManager) {
        mTopicManager = topicManager;
    }

    @Override
    public void onActionTopicClicked(ActionTopic topic, TopicListAdapter.ViewHolder viewHolder) {
        // Ignore taps on items that are already selected
        if (mTopicManager.isTopicInHistory(topic)) {
            if (Debug.isInDebugMode(DEBUG)) {
                Log.d(TAG, "Ignoring action topic click");
            }
            return;
        }

        pushTopicToHistory(topic);

        final int demoFragmentId = topic.getDemoFragmentId();

        if (Debug.isInDebugMode(DEBUG)) {
            Log.d(TAG, "Showing demo fragment with id " + demoFragmentId);
        }

        // Delay showing the new fragment to see the click animation on the TopicView
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRootView.setDemoFragmentId(demoFragmentId);
            }
        }, CLICK_DELAY);

        mView.setDemoFragmentId(demoFragmentId);
    }

    @Override
    public void onMenuTopicClicked(MenuTopic topic, TopicListAdapter.ViewHolder viewHolder) {
        // Ignore taps on items that are already selected
        if (mTopicManager.isTopicInHistory(topic)) {
            if (Debug.isInDebugMode(DEBUG)) {
                Log.d(TAG, "Ignoring menu topic click");
            }

            mView.viewNextPage();
            return;
        }

        pushTopicToHistory(topic);

        mView.addNewPage();
    }

    private void pushTopicToHistory(Topic topic) {
        int currentPage = mView.getCurrentPage();
        int lastPage = mView.getPageCount()-1;

        // If a topic on a previous page was clicked
        if (currentPage < lastPage)
        {
            // Pop topics off history stack until we reach the topic of the page we clicked on
            int numTopicsInHistoryToRemove = mTopicManager.getHistorySize() - currentPage - 1;
            while (numTopicsInHistoryToRemove-- > 0) {
                mTopicManager.popTopicFromHistory();
            }

            // Push the newly selected topic. Note that because we just removed some topics from
            // the history stack, the new top cannot be an ActionTopic.
            mTopicManager.pushTopicToHistory(topic);

            // Refresh ViewPager to go back to the page that contained the clicked topic
            mView.reverseNavigationBackToPage(currentPage, false);
        }
        else
        {
            // Handle selection change from ActionTopic to either MenuTopic or another ActionTopic.
            if (mTopicManager.isActionTopicReached()) {
                mTopicManager.popTopicFromHistory();
            }
            mTopicManager.pushTopicToHistory(topic);
        }
    }

}
