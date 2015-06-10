package com.cngu.androidfun.main;

import android.os.Bundle;
import com.cngu.androidfun.data.Topic;

public interface ITopicManager {
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
