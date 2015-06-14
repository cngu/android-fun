package com.cngu.androidfun.main;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.cngu.androidfun.R;
import com.cngu.androidfun.base.BaseFragment;


/**
 * View {@link Fragment} that is intended to be attached to {@link MainActivity}.
 */
public class MainFragment extends BaseFragment implements IMainFragment {

    private static final String KEY_NUM_OPEN_PAGES = "cngu.key.NUM_OPEN_PAGES";

    // This should always be 1 because if set higher, we can't predict the path the user navigated
    // through the menus to get to the pages past the first.
    public static final int STARTING_NUMBER_OF_PAGES = 1;

    /*
     * It just so happens that the delay of:
     *     1) posting the 'tab selection' message to the main UI thread's message queue, and
     *     2) waiting for the main UI thread's Looper to process this message
     * is sufficient to avoid this bug on "modern" devices (Google Galaxy Nexus: 4.3,
     * and Nexus 5: 5.1.1)!
     *
     * Increase this delay if necessary for older/slower devices.
     */
    private static final long SELECT_NEW_TAB_DELAY = 0L;

    private static final int PAGE_MARGIN_PX = 64;

    private IMainPresenter mPresenter;

    private FragmentManager mFragmentManager;
    private ITopicManager mTopicManager;

    private TopicListPagerAdapter mTopicListPagerAdapter;
    private TabLayout mTopicListPagerTabs;
    private ViewPager mTopicListPager;

    /* View state */
    private int mNumOpenPages;

    private int mTabTransitionDelay;
    private boolean mDoneRemove = true;

    /*
     * This factory method is used instead of overloading the default no-arg constructor
     * because when the system needs to recreate this fragment, it will call the default no-arg
     * constructor, and the arguments passed to the overloaded constructor will be lost.
     * This factory method will persist all arguments in a Bundle, and be restored when the system
     * invokes the default no-arg constructor for fragment recreation.
     */
    public static IMainFragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mTabTransitionDelay = activity.getResources()
                                      .getInteger(android.R.integer.config_shortAnimTime);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mNumOpenPages = STARTING_NUMBER_OF_PAGES;

        // Initialize helper *Managers
        mFragmentManager = getChildFragmentManager();
        mTopicManager = new TopicManager(getActivity());

        mPresenter = new MainPresenter((MainActivity) getActivity(), this);
        mPresenter.setTopicManager(mTopicManager);

        // Load state
        if (savedInstanceState != null) {
            mNumOpenPages = savedInstanceState.getInt(KEY_NUM_OPEN_PAGES, STARTING_NUMBER_OF_PAGES);

            mTopicManager.loadHistory(savedInstanceState);
            Log.d(TAG, "Topic manager LOAD " + mTopicManager.getHistorySize());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Use the toolbar as our Action Bar (i.e. not in standalone mode)
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setFitsSystemWindows(true);

        // NOTE: A new adapter must be created everytime for new ViewPagers. Not sure why.
        // Because we create the adapter here in onCreateView, if another fragment is on top and a
        // orientation change occurs, then onCreateView won't be called so the adapter is null, and
        // onSaveInstanceState can't save mTopicListPagerAdapter.getCount(). This is why we
        // introduced the mNumOpenPages variable.
        mTopicListPagerAdapter = new TopicListPagerAdapter(mFragmentManager);
        mTopicListPagerAdapter.setTopicManager(mTopicManager);
        mTopicListPagerAdapter.setTopicClickListener(mPresenter);
        mTopicListPagerAdapter.setPageCount(mNumOpenPages);

        mTopicListPager = (ViewPager) view.findViewById(R.id.topic_list_pager);
        mTopicListPager.setPageMargin(PAGE_MARGIN_PX);
        mTopicListPager.setAdapter(mTopicListPagerAdapter);

        mTopicListPagerTabs = (TabLayout) view.findViewById(R.id.topic_list_pager_tabs);
        mTopicListPagerTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTopicListPagerTabs.setupWithViewPager(mTopicListPager);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        mNumOpenPages = mTopicListPagerAdapter.getCount();
    }

    //region IBackKeyListener
    @Override
    public boolean onBackPressed() {
        return viewPreviousPage();
    }
    //endregion

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current number of open pages
        outState.putInt(KEY_NUM_OPEN_PAGES, mNumOpenPages);

        // Save the history of topic navigation
        mTopicManager.saveHistory(outState);

        Log.d(TAG, "Topic manager SAVE " + mTopicManager.getHistorySize());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.add_page:
                MainActivity a = (MainActivity) getActivity();
                a.test(new TestFragment());
                return true;
            case R.id.remove_page:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addNewPage() {
        mTopicListPagerAdapter.addNewPage();

        int newPageIndex = mTopicListPagerAdapter.getCount() - 1;
        CharSequence newPageTitle = mTopicListPagerAdapter.getPageTitle(newPageIndex);
        final TabLayout.Tab newTab = mTopicListPagerTabs.newTab().setText(newPageTitle);

        /*
         * DESIGN SUPPORT LIBRARY 22.2.0 BUG: If a new tab is added and selected
         * immediately, the tab underline/indicator scrolls rapidly to the left and
         * off-screen. Subsequent tabs that are added seems to either exhibit the same
         * behavior, or select the tab prior to the new tab.
         *
         * WORKAROUND: Add a new tab, and manually select it after a delay. This is
         * equivalent to TabLayout.addTab(Tab, true), but with a delay introduced before
         * selecting the newly added tab.
         */
        mTopicListPagerTabs.addTab(newTab);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newTab.select();
            }
        }, SELECT_NEW_TAB_DELAY);
    }

    @Override
    public void reverseNavigationBackToPage(int page, boolean clearSelection) {
        mTopicListPagerAdapter.goBackToPage(page, clearSelection);

        for (int i = mTopicListPagerTabs.getTabCount()-1; i > page; i--) {
            mTopicListPagerTabs.removeTabAt(i);
        }
    }

    @Override
    public boolean viewPreviousPage() {
        int previousTabIndex = mTopicListPager.getCurrentItem()-1;
        if (previousTabIndex >= 0) {
            mTopicListPagerTabs.getTabAt(previousTabIndex).select();
            return true;
        }
        return false;
    }

    @Override
    public boolean viewNextPage() {
        int nextTabIndex = mTopicListPager.getCurrentItem()+1;
        if (nextTabIndex < mTopicListPagerTabs.getTabCount()) {
            mTopicListPagerTabs.getTabAt(nextTabIndex).select();
            return true;
        }
        return false;
    }

    @Override
    public int getPageCount() {
        return mTopicListPagerAdapter.getCount();
    }

    @Override
    public int getCurrentPage() {
        return mTopicListPager.getCurrentItem();
    }

    //region ILifecycleLoggable
    @Override
    public boolean onLogLifecycle() {
        return true;
    }
    //endregion


    public static class TestFragment extends BaseFragment {

        public TestFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(30);
            tv.setText("NEW FRAGMENT");

            return tv;
        }

        @Override
        public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
            if (enter) {
                return AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
            } else {
                return AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
            }
        }

        @Override
        public boolean onLogLifecycle() {
            return false;
        }
    }
}

