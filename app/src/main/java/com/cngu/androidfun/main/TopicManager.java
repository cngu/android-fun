package com.cngu.androidfun.main;

import android.os.Bundle;

import com.cngu.androidfun.data.Topic;

/**
 * A TopicManager serves two purposes:
 * <ol>
 *     <li>contain an in-memory (i.e. not-persisted) database of {@link Topic}s.
 *     <li>manage a history of visited {@link Topic}s.
 * </ol>
 *
 * <p>Due to the static nature of the Topic menu navigation UI, persistence of these Topics is not
 * required and can easily be created on demand.
 */
public class TopicManager implements ITopicManager {

    @Override
    public void loadHistory(Bundle savedInstanceState) {

    }

    @Override
    public void saveHistory(Bundle savedInstanceState) {

    }

}
