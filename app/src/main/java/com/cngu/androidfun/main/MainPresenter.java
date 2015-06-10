package com.cngu.androidfun.main;

import android.util.Log;

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
    public void onActionTopicClicked(ActionTopic topic, int position) {
        Log.d(TAG, String.format("Action Topic on page %d at index %d was clicked!", mView.getCurrentPage(), position));
    }

    @Override
    public void onMenuTopicClicked(MenuTopic topic, int position) {
        Log.d(TAG, String.format("Menu Topic on page %d at index %d was clicked!", mView.getCurrentPage(), position));

        //mView.addNewPage();
    }
}
