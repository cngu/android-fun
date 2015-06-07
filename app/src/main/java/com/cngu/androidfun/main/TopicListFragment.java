package com.cngu.androidfun.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cngu.androidfun.R;
import com.cngu.androidfun.base.BaseFragment;

/**
 * A fragment used to display a list of {@link com.cngu.androidfun.data.Topic}.
 */
public class TopicListFragment extends BaseFragment implements ITopicListFragment {

    /*
     * This factory method is used instead of overloading the default no-arg constructor
     * because when the system needs to recreate this fragment, it will call the default no-arg
     * constructor, and the arguments passed to the overloaded constructor will be lost.
     * This factory method will persist all arguments in a Bundle, and be restored when the system
     * invokes the default no-arg constructor for fragment recreation.
     */
    public static ITopicListFragment newInstance() {
        return new TopicListFragment();
    }

    public TopicListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_list, container, false);

        TopicListAdapter topicListAdapter = new TopicListAdapter();

        return view;
    }
}
