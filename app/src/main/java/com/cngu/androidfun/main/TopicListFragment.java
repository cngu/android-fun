package com.cngu.androidfun.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cngu.androidfun.R;
import com.cngu.androidfun.base.BaseFragment;
import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;

import java.util.ArrayList;
import java.util.List;

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
        RecyclerView topicList = (RecyclerView) inflater.inflate(
                R.layout.fragment_topic_list, container, false);

        Context context = topicList.getContext();

        List<Topic> test = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            test.add(new MenuTopic("Menu Topic", "Menu Topic " + i, null));
        }
        LinearLayoutManager topicLayoutManager = new LinearLayoutManager(context);
        TopicListAdapter topicListAdapter = new TopicListAdapter(test);


        topicList.setLayoutManager(topicLayoutManager);
        topicList.setAdapter(topicListAdapter);

        return topicList;
    }
}
