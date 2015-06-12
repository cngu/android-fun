package com.cngu.androidfun.main;

import android.util.Log;

import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;

/**
 * Presenter for {@link MainFragment}.
 */
public class MainPresenter implements IMainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private IMainFragment mView;
    private ITopicManager mTopicManager;

    public MainPresenter(IMainFragment view, ITopicManager topicManager) {
        mView = view;
        mTopicManager = topicManager;

        mView.setTopicManager(mTopicManager);
    }

    @Override
    public void onActionTopicClicked(ActionTopic topic, TopicListAdapter.ViewHolder viewHolder) {
        pushTopicToHistory(topic);
    }

    @Override
    public void onMenuTopicClicked(MenuTopic topic, TopicListAdapter.ViewHolder viewHolder) {
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
            mView.goBackToPage(currentPage, false);
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
