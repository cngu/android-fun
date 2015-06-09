package com.cngu.androidfun.main;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cngu.androidfun.R;
import com.cngu.androidfun.base.BaseFragment;


/**
 * View {@link Fragment} that is intended to be attached to {@link MainActivity}.
 */
public class MainFragment extends BaseFragment implements IMainFragment {

    private static final String KEY_NUM_OPEN_PAGES = "cngu.key.NUM_OPEN_PAGES";

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

    private IMainPresenter mPresenter;
    private TopicListPagerAdapter mTopicListPagerAdapter;
    private TabLayout mTopicListPagerTabs;
    private ViewPager mTopicListPager;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Create helper *Managers
        FragmentManager fragmentManager = getChildFragmentManager();

        // Use the toolbar as our Action Bar (i.e. not in standalone mode)
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        // Load state
        int numOpenPages;
        if (savedInstanceState == null) {
            numOpenPages = 0;
        } else {
            numOpenPages = savedInstanceState.getInt(KEY_NUM_OPEN_PAGES, 0);
        }

        // Initialize and connect ViewPager and TabLayout
        mTopicListPagerAdapter = new TopicListPagerAdapter(fragmentManager);

        mTopicListPager = (ViewPager) view.findViewById(R.id.topic_list_pager);
        mTopicListPager.setAdapter(mTopicListPagerAdapter);
        mTopicListPagerAdapter.addNewPages(numOpenPages);

        mTopicListPagerTabs = (TabLayout) view.findViewById(R.id.topic_list_pager_tabs);
        mTopicListPagerTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTopicListPagerTabs.setupWithViewPager(mTopicListPager);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_NUM_OPEN_PAGES, mTopicListPagerAdapter.getCount());
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

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.add_page:
                mTopicListPagerAdapter.addNewPage();

                int newPageIndex = mTopicListPagerAdapter.getCount() - 1;
                CharSequence newPageTitle = mTopicListPagerAdapter.getPageTitle(newPageIndex);
                final TabLayout.Tab newTab = mTopicListPagerTabs.newTab().setText(newPageTitle);

                /**
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

                return true;
            case R.id.remove_page:
                // Must refresh the ViewPager BEFORE removing the tab so that the tab
                // underline/indicator can be seen animating back to the previous tab.
                mTopicListPagerAdapter.removeLastPage();

                int removedPageIndex = mTopicListPagerAdapter.getCount();
                mTopicListPagerTabs.removeTabAt(removedPageIndex);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void registerPresenter(IMainPresenter presenter) {
        mPresenter = presenter;
    }
}
