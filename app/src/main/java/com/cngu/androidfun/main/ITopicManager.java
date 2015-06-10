package com.cngu.androidfun.main;

import android.os.Bundle;

import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;

public interface ITopicManager {
    /**
     * Returns a previously selected {@link MenuTopic} in the 'selected topic' history.
     *
     * @param historyIndex The index of the selected {@link MenuTopic} in history, where 0 is the
     *                     oldest {@link MenuTopic}.
     * @return The selected {@link MenuTopic} in history, or null if 'historyIndex' is beyond the
     *         bounds of the history stack.
     */
    MenuTopic getTopicInHistory(int historyIndex);

    /**
     * Adds a {@link MenuTopic} to the 'selected topic' history stack. To persist the history stack
     * across orientation changes, see {@link ITopicManager#loadHistory(Bundle)} and
     * {@link ITopicManager#saveHistory(Bundle)}.
     *
     * @param topic A topic to save to the history stack of selected topics.
     */
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
