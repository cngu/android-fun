package com.cngu.androidfun.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.cngu.androidfun.R;
import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.view.TopicView;

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
    private static final String TAG = TopicManager.class.getSimpleName();

    private static final String KEY_HISTORY_STACK = "cngu.key.HISTORY_STACK";

    public ArrayList<Topic> mTopics;
    public ArrayList<Topic> mHistory;
    private boolean mActionTopicReached;

    public TopicManager(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        TopicView rootTopicView = (TopicView) inflater.inflate(R.layout.ui_topic_hierarchy, null);

        mTopics = new ArrayList<>();
        mHistory = new ArrayList<>();
        mActionTopicReached = false;

        Topic rootMenu = generateTopicHierarchy(rootTopicView);

        mHistory.add(rootMenu);
    }

    @Override
    public boolean isActionTopicReached() {
        return mActionTopicReached;
    }

    @Override
    public int getHistorySize() {
        return mHistory.size();
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
        if (mActionTopicReached) {
            throw new IllegalStateException("Cannot navigate to Topics beyond an ActionTopic.");
        }

        if (topic instanceof ActionTopic) {
            mActionTopicReached = true;
        }

        mHistory.add(topic);
    }

    @Override
    public Topic popTopicFromHistory() {
        if (mActionTopicReached) {
            mActionTopicReached = false;
        }
        return mHistory.remove(mHistory.size()-1);
    }

    @Override
    public void loadHistory(Bundle savedInstanceState) {
        mHistory = savedInstanceState.getParcelableArrayList(KEY_HISTORY_STACK);

        Topic top = mHistory.get(mHistory.size()-1);
        mActionTopicReached = (top instanceof ActionTopic);
    }

    @Override
    public void saveHistory(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(KEY_HISTORY_STACK, mHistory);
    }

    @Override
    public boolean isTopicInHistory(Topic topic) {
        for (Topic t : mHistory) {
            if (topic.equals(t)) {
                return true;
            }
        }
        return false;
    }

    private Topic generateTopicHierarchy(TopicView root) {
        if (root == null) {
            return null;
        }

        String title = root.getTitle();
        String description = root.getDescription();

        if (root.getChildCount() > 0)
        {
            MenuTopic mt = new MenuTopic(title, description, null);

            for (int i = 0; i < root.getChildCount(); i++) {
                TopicView child = (TopicView) root.getChildAt(i);
                mt.addSubtopic(generateTopicHierarchy(child));
            }

            return mt;
        }
        else {
            ActionTopic at = new ActionTopic(title, description, root.getDemoFragmentId());
            return at;
        }
    }
}
