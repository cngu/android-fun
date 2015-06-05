package com.cngu.androidfun.topic;

import java.util.ArrayList;
import java.util.List;

/**
 * A MenuTopic represents a list of {@link com.cngu.androidfun.topic.Topic}.
 */
public class MenuTopic extends Topic {
    private List<Topic> mSubtopics;

    /**
     * @param title Short title for this MenuTopic.
     * @param description Short description for this MenuTopic.
     */
    public MenuTopic(String title, String description) {
        this(title, description, null);
    }

    /**
     * @param title Short title for this MenuTopic.
     * @param description Short description for this MenuTopic.
     * @param subtopics List of subtopics under this MenuTopic.
     */
    public MenuTopic(String title, String description, Topic[] subtopics) {
        super(title, description);

        mSubtopics = new ArrayList<>();

        if (subtopics != null) {
            for (Topic t : subtopics) {
                mSubtopics.add(t);
            }
        }
    }

    /**
     * @return An array of all subtopics.
     */
    public Topic[] getSubtopics() {
        Topic[] result = new Topic[mSubtopics.size()];
        mSubtopics.toArray(result);
        return result;
    }

    /**
     * Adds a subtopic at the specified index.
     *
     * @param index The position in the current list of subtopics to insert at.
     * @param newSubtopic The new subtopic to add.
     */
    public void addSubtopic(int index, Topic newSubtopic) {
        mSubtopics.add(index, newSubtopic);
    }

    /**
     * Appends a subtopic to the end of the list.
     *
     * @param newSubtopic The new subtopic to add.
     */
    public void addSubtopic(Topic newSubtopic) {
        mSubtopics.add(newSubtopic);
    }

    /**
     * Replaces an existing subtopic with a new topic
     *
     * @param index The position of an existing subtopic to replace.
     * @param replacementTopic The new subtopic to replace an existing subtopic.
     * @return The previously existing subtopic that was replaced.
     */
    public Topic setSubtopic(int index, Topic replacementTopic) {
        return mSubtopics.set(index, replacementTopic);
    }

    /**
     * Removes an existing subtopic by index.
     *
     * @param index The position of the existing subtopic to remove.
     * @return The removed subtopic.
     */
    public Topic removeSubtopic(int index) {
        return mSubtopics.remove(index);
    }

    /**
     * Removes an existing subtopic.
     * @param topic An existing subtopic to remove.
     * @return true if the subtopic was found and removed, false otherwise.
     */
    public boolean removeSubtopic(Topic topic) {
        return mSubtopics.remove(topic);
    }
}
