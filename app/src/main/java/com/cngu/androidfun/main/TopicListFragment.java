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
import com.cngu.androidfun.debug.Debug;
import com.cngu.androidfun.view.TopicView;

/**
 * A fragment used to display a list of {@link com.cngu.androidfun.data.Topic}.
 */
public class TopicListFragment extends BaseFragment implements ITopicListFragment {
    private static final String TAG = TopicListFragment.class.getSimpleName();

    private static boolean DEBUG = true;

    private TopicListAdapter mTopicListAdapter;
    private TopicView.OnClickListener mTopicClickListener;
    private SelectableTopicList mTopicList;

    private Bundle mStateBundle;

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
        super.onCreateView(inflater, container, savedInstanceState);

        mStateBundle = savedInstanceState;

        RecyclerView topicList = (RecyclerView) inflater.inflate(
                R.layout.fragment_topic_list, container, false);

        // Disable animations if we're on LOLLIPOP or higher because we'll supply our own animations.
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    topicList.setItemAnimator(null);
        //}

        Context context = topicList.getContext();

        mTopicListAdapter = new TopicListAdapter(topicList);

        // On first creation, no ViewPager pages are created yet, so its adapter's getItem() is
        // called to create the page for the first time. The adapter overrides instantiateItem()
        // to initialize this fragment (that was just created in getItem()) with the topic list and
        // click listener. This fragment's lifecycle now begins with onAttach().
        //
        // On recreation, similarly to fragments managed by FragmentManager, onAttach() and
        // onCreate() are called before much else can happen, even before MainActivity.onCreate().
        // However, unlike FragmentManager, the ViewPager proceeds to restore this fragment's state
        // as well via onCreateView(), onActivityCreated(), and onViewStateRestored(). Eventually
        // onStart() and onResume() are called, and the ViewPager finally calls the adapter's
        // instantiateItem() and we finally have a chance to do our initialization. No further
        // lifecycle callbacks are invoked after this (until onPause()).
        //
        // Thus, this check is needed here (and in setTopicList() and setTopicClickListener())
        // because this fragment may be initialized before (first creation) or after (recreation)
        // onCreateView(), and we can't move this code to a later lifecycle callback because there
        // are none that occur after the adapter's instantiateItem().
        if (mTopicClickListener != null && mTopicList != null) {
            if (Debug.isInDebugMode(DEBUG)) {
                Log.d(TAG, "onCreateView - topic click listener and topic list not set yet");
            }

            mTopicListAdapter.setTopicClickListener(mTopicClickListener);
            mTopicListAdapter.setTopicList(mTopicList);
        }

        topicList.setLayoutManager(new LinearLayoutManager(context));
        topicList.setAdapter(mTopicListAdapter);

        return topicList;
    }

    @Override
    public void setSelected(int selectedIndex) {
        mTopicList.setSelected(selectedIndex, true);
    }

    @Override
    public void setTopicList(SelectableTopicList topicList) {
        if (Debug.isInDebugMode(DEBUG)) {
            Log.d(TAG, "setTopicList");
        }

        mTopicList = topicList;

        // If this fragment is being recreated by the ViewPager, then we're now registering a click
        // listener after the RecyclerView adapter has already been created in onCreateView.
        if (mTopicListAdapter != null) {
            mTopicListAdapter.setTopicList(mTopicList);
        }
    }

    @Override
    public void setTopicClickListener(TopicView.OnClickListener listener) {
        if (Debug.isInDebugMode(DEBUG)) {
            Log.d(TAG, "setTopicClickListener");
        }

        mTopicClickListener = listener;

        // If this fragment is being recreated by the ViewPager, then we're now registering a click
        // listener after the RecyclerView adapter has already been created in onCreateView.
        if (mTopicListAdapter != null) {
            mTopicListAdapter.setTopicClickListener(mTopicClickListener);
        }
    }

    //region ILifecycleLoggable
    @Override
    public boolean onLogLifecycle() {
        return false;
    }
    //endregion
}
