package com.cngu.androidfun.main;

import com.cngu.androidfun.data.Topic;

import java.util.List;

/**
 * This class manages a list of {@link Topic}s and their selection state. Its main purpose is to
 * keep selection state data and logic out of {@link Topic}s themselves.
 */
public class SelectableTopicList {
    private List<Topic> mTopics;
    private IListSelector mSelector;

    public SelectableTopicList(List<Topic> topics, IListSelector selector) {
        mTopics = topics;
        mSelector = selector;
    }

    public int[] getSelected() {
        return mSelector.getSelected();
    }

    public void setSelected(int topicIndex, boolean selected) {
        if (topicIndex < 0 || topicIndex >= mTopics.size()) {
            return;
        }

        mSelector.setSelected(topicIndex, selected);
    }

    public boolean isSelected(int topicIndex) {
        return mSelector.isSelected(topicIndex);
    }

    public Topic get(int position) {
        return mTopics.get(position);
    }

    public int size() {
        return mTopics.size();
    }
}
