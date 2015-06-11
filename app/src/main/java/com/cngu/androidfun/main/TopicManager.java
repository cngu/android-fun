package com.cngu.androidfun.main;

import android.os.Bundle;

import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.enums.TopicFragmentId;

import java.util.ArrayList;

/**
 * A TopicManager serves two purposes:
 * <ol>
 *     <li>contain an in-memory (i.e. not-persisted) database of {@link Topic}s.
 *     <li>manage a history of selected {@link Topic}s.
 * </ol>
 *
 * <p>Due to the static nature of the Topic menu navigation UI, persistence of these Topics is not
 * required and can easily be created on demand.
 */
public class TopicManager implements ITopicManager {

    public ArrayList<Topic> mTopics;
    public ArrayList<Topic> mHistory;

    public TopicManager() {
        mTopics = new ArrayList<>();
        mHistory = new ArrayList<>();

        MenuTopic rootMenu = new MenuTopic(null, null);

        // Create a static hierarchy of Topics that the user will navigate through.
        for (int i = 0; i < 10; i++) {
            MenuTopic mt = new MenuTopic("Menu Topic", "Menu Topic " + i, null);
            for (int j = 0; j < 5; j++) {
                mt.addSubtopic(new ActionTopic("Nested Action Topic", "Nested Action Topic " + j, TopicFragmentId.TEST));
            }
            mTopics.add(mt);
            rootMenu.addSubtopic(mt);
        }
        for (int i = 0; i < 10; i++) {
            ActionTopic at = new ActionTopic("Action Topic", "Action Topic " + i, TopicFragmentId.TEST);
            mTopics.add(at);
            rootMenu.addSubtopic(at);
        }

        mHistory.add(rootMenu);
    }

    @Override
    public Topic getTopicInHistory(int pageNumber) {
        if (pageNumber < 0 || pageNumber >= mHistory.size()) {
            return null;
        }

        return mHistory.get(pageNumber);
    }

    @Override
    public void pushTopicToHistory(Topic topic) {
        mHistory.add(topic);
    }

    @Override
    public Topic popTopicFromHistory() {
        return mHistory.remove(mHistory.size()-1);
    }

    @Override
    public void loadHistory(Bundle savedInstanceState) {

    }

    @Override
    public void saveHistory(Bundle savedInstanceState) {

    }

}
