package com.cngu.androidfun.main;

import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;

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
        if (mTopicManager.isActionTopicReached()) {
            mTopicManager.popTopicFromHistory();
        }
        mTopicManager.pushTopicToHistory(topic);
    }

    @Override
    public void onMenuTopicClicked(MenuTopic topic, TopicListAdapter.ViewHolder viewHolder) {
        int currentPage = mView.getCurrentPage();
        int lastPage = mView.getPageCount()-1;

        if (currentPage < lastPage) {
            int numTopicsInHistoryToRemove = mTopicManager.getHistorySize() - currentPage - 1;
            while (numTopicsInHistoryToRemove-- > 0) {
                mTopicManager.popTopicFromHistory();
            }

            mView.goBackToPage(currentPage, false);
        }

        mTopicManager.pushTopicToHistory(topic);
        mView.addNewPage();
    }
}
