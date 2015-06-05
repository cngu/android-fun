package com.cngu.androidfun.topic;

import com.cngu.androidfun.enums.TopicFragmentId;

import java.util.ArrayList;

/**
 * A Topic represents a general category or specific subject that can be demoed.
 */
public abstract class Topic {
    private String mTitle;
    private String mDescription;

    /**
     * @param title Short title for this topic.
     * @param description Short description for this topic.
     */
    public Topic(String title, String description) {
        mTitle = title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
