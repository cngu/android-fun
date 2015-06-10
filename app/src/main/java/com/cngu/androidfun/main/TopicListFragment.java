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

    private TopicListAdapter mTopicListAdapter;
    private TopicView.OnClickListener mTopicClickListener;
    private List<Topic> mTopicList;

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

        LinearLayoutManager topicLayoutManager = new LinearLayoutManager(context);
        mTopicListAdapter = new TopicListAdapter(null);

        // If this fragment was created for the first time (i.e. its state wasn't saved by the
        // ViewPager), then we already had a chance to register both a topic click listener and a
        // list of topics for the RecyclerView.
        // - This check is needed because unlike MainFragment, it may be the case that this fragment
        // is recreated after a rotation and onCreateView is called BEFORE we have had a chance to
        // register the topic click listener and topic list.
        // - The reason may be because this Fragment is within (and being managed by) a ViewPager.
        if (mTopicClickListener != null && mTopicList != null) {
            mTopicListAdapter.setTopicClickListener(mTopicClickListener);
            mTopicListAdapter.setTopicList(mTopicList);
        }

        topicList.setLayoutManager(topicLayoutManager);
        topicList.setAdapter(mTopicListAdapter);

        return topicList;
    }

    @Override
    public void setTopicList(List<Topic> topicList) {
        mTopicList = topicList;

        // If this fragment is being recreated by the ViewPager, then we're now registering a click
        // listener after the RecyclerView adapter has already been created in onCreateView.
        if (mTopicListAdapter != null) {
            mTopicListAdapter.setTopicList(mTopicList);
        }
    }

    @Override
    public void setTopicClickListener(TopicView.OnClickListener listener) {
        mTopicClickListener = listener;

        // If this fragment is being recreated by the ViewPager, then we're now registering a click
        // listener after the RecyclerView adapter has already been created in onCreateView.
        if (mTopicListAdapter != null) {
            mTopicListAdapter.setTopicClickListener(mTopicClickListener);
        }
    }
}
