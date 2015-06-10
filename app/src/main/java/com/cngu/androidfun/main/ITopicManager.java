package com.cngu.androidfun.main;

import android.os.Bundle;

import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;

import java.util.List;

public interface ITopicManager {
    /**
     * Returns an immutable list of {@link Topic}s that are on the specified page number.
     *
     * @param pageNumber The page number that needs a list of {@link Topic}s.
     * @return Immutable list of topics that are on page 'pageNumber', or null if pageNumber is out
     *         of bounds.
     */
    List<Topic> getTopics(int pageNumber);

    void addTopicToHistory(MenuTopic topic);

    /**
     * Reconstructs the history stack of visited {@link Topic}s from a state {@link Bundle}.
     *
     * @param savedInstanceState Bundle to restore history from.
     */
    void loadHistory(Bundle savedInstanceState);

    /**
     * Saves the history stack of visited {@link Topic}s to a state {@link Bundle}.
     *
     * @param savedInstanceState Bundle to save history to.
     */
    void saveHistory(Bundle savedInstanceState);
}
