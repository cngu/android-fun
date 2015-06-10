package com.cngu.androidfun.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cngu.androidfun.R;
import com.cngu.androidfun.base.BaseFragment;
import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.enums.TopicFragmentId;
import com.cngu.androidfun.view.TopicView;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment used to display a list of {@link com.cngu.androidfun.data.Topic}.
 */
public class TopicListFragment extends BaseFragment implements ITopicListFragment {

    private TopicView.OnClickListener mTopicClickListener;
    private TopicListAdapter mTopicListAdapter;

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
        for (int i = 0; i < 10; i++) {
            test.add(new MenuTopic("Menu Topic", "Menu Topic " + i, null));
        }
        for (int i = 0; i < 10; i++) {
            test.add(new ActionTopic("Action Topic", "Action Topic " + i, TopicFragmentId.TEST));
        }

        LinearLayoutManager topicLayoutManager = new LinearLayoutManager(context);
        mTopicListAdapter = new TopicListAdapter(test);

        // If this fragment was created for the first time (i.e. its state wasn't saved by the
        // ViewPager), we already had a chance to register a presenter
        if (mTopicClickListener != null) {
            mTopicListAdapter.setTopicClickListener(mTopicClickListener);
        }

        topicList.setLayoutManager(topicLayoutManager);
        topicList.setAdapter(mTopicListAdapter);

        return topicList;
    }

    @Override
    public void setTopicClickListener(TopicView.OnClickListener listener) {
        mTopicClickListener = listener;

        // If this fragment is being recreated by the ViewPager
        if (mTopicListAdapter != null) {
            mTopicListAdapter.setTopicClickListener(mTopicClickListener);
        }
    }
}
