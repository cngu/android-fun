package com.cngu.androidfun.topic;

import com.cngu.androidfun.enums.TopicFragmentId;

/**
 * An ActionTopic represents a single subject that can be demoed.
 */
public class ActionTopic extends Topic {
    private TopicFragmentId mTopicFragmentId;

    /**
     * @param title Short title for this ActionTopic.
     * @param description Short description for this ActionTopic.
     */
    public ActionTopic(String title, String description) {
        super(title, description);
    }

    /**
     * @param title Short title for this ActionTopic.
     * @param description Short description for this ActionTopic.
     * @param launchFragmentId Id of the topic fragment to launch when this topic is activated.
     */
    public ActionTopic(String title, String description, TopicFragmentId launchFragmentId) {
        super(title, description);
        mTopicFragmentId = launchFragmentId;
    }

    public TopicFragmentId getTopicFragmentToLaunch() {
        return mTopicFragmentId;
    }

    public void setTopicFragmentToLaunch(TopicFragmentId launchFragmentId) {
        mTopicFragmentId = launchFragmentId;
    }
}
