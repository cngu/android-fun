package com.cngu.androidfun.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.cngu.androidfun.R;
import com.cngu.androidfun.base.BaseActivity;
import com.cngu.androidfun.base.IBaseFragment;
import com.cngu.androidfun.debug.Debug;
import com.cngu.androidfun.demo.DemoFragmentFactory;
import com.cngu.androidfun.demo.IDemoFragment;


public class MainActivity extends BaseActivity implements IRootView {
    public static final int NO_DEMO_FRAGMENT_ID = -1;

    private static final boolean DEBUG = true;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG_MAIN = "cngu.fragment.tag.MAIN";
    private static final String FRAGMENT_TAG_DEMO = "cngu.fragment.tag.DEMO";
    private static final String KEY_FRAGMENT_DEMO_ID = "cngu.key.FRAGMENT_DEMO_ID";

    private FragmentManager mFragmentManager;
    private DemoFragmentFactory mDemoFragmentFactory;

    private IMainFragment mMainView;
    private IDemoFragment mDemoView;

    private boolean mDualPane;

    // Saved state
    private int mDemoFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create helper *Managers
        mFragmentManager = getSupportFragmentManager();
        mDemoFragmentFactory = new DemoFragmentFactory();

        mDualPane = getResources().getBoolean(R.bool.dual_pane);

        // Get reference to MainFragment if it already exists in FragmentManager; otherwise create it
        mMainView = (MainFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG_MAIN);
        if (mMainView == null) {
            Log.i(TAG, "Couldn't find existing MainFragment; creating new one.");
            mMainView = MainFragment.newInstance();
        } else {
            Log.i(TAG, "Found existing MainFragment; reusing it now.");
        }

        if (savedInstanceState == null) {
            mDemoFragmentId = NO_DEMO_FRAGMENT_ID;
        } else {
            mDemoFragmentId = savedInstanceState.getInt(KEY_FRAGMENT_DEMO_ID, NO_DEMO_FRAGMENT_ID);
        }

        Log.d(TAG, "onCreate - BEGIN Back stack size: " + mFragmentManager.getBackStackEntryCount());

        int topIndex = mFragmentManager.getBackStackEntryCount()-1;
        if (topIndex >= 0) {
            FragmentManager.BackStackEntry top = mFragmentManager.getBackStackEntryAt(topIndex);
            String topFragmentName = top.getName();

            if (topFragmentName != null && topFragmentName.equals(FRAGMENT_TAG_DEMO)) {
                Log.i(TAG, "A DemoFragment is at the top of the back stack: " + mFragmentManager.popBackStackImmediate());
            } else {
                Log.i(TAG, "DemoFragment is not at the top of the back stack: " + topFragmentName);
            }
        }

        if (mDemoFragmentId != NO_DEMO_FRAGMENT_ID && !mDualPane) {
            Log.i(TAG, "Attaching DemoFragment to container.");

            mDemoView = mDemoFragmentFactory.createDemoFragment(mDemoFragmentId);
            mFragmentManager.beginTransaction()
                    .replace(R.id.content_fragment_container, mDemoView.asFragment(), FRAGMENT_TAG_DEMO)
                    .addToBackStack(FRAGMENT_TAG_DEMO)
                    .commit();
        }
        else {
            Log.i(TAG, "Attaching MainFragment to container.");
            mFragmentManager.beginTransaction()
                    .replace(R.id.content_fragment_container, mMainView.asFragment(), FRAGMENT_TAG_MAIN)
                    .commit();
        }

        Log.d(TAG, "onCreate - END Back stack size: " + mFragmentManager.getBackStackEntryCount());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_FRAGMENT_DEMO_ID, mDemoFragmentId);
    }

    /**
     * Handling the Back key on the navigation bar happens here. MainFragment is expected to
     * implement IBackKeyListener and this Activity will forward the event to it.
     *
     * <p>This is a simple solution for our scenario, but if there are multiple fragments, consider
     * sending a local broadcast to all fragments with the following notes in mind:
     * <p>Implementation Note 1: All fragments not on screen should unregister their broadcast
     * receiver so only the top most fragment receives it.
     * <p>Implementation Note 2: You'll have to find another way to determine if the fragment wants
     * to ignore the back key (in which case you call super.onBackPressed() in the Activity).
     */
    @Override
    public void onBackPressed() {
        boolean backHandled = false;

        Log.d(TAG, "onBackPressed - BEGIN Back stack size: " + mFragmentManager.getBackStackEntryCount());

        if (isFragmentOnScreen(mMainView)) {
            Log.d(TAG, "Letting MainFragment handle back.");

            backHandled |= mMainView.onBackPressed();
        }

        if (isFragmentOnScreen(mDemoView)) {
            Log.d(TAG, "Letting DemoFragment handle back.");

            boolean demoViewHandledBack = mDemoView.onBackPressed();
            backHandled |= demoViewHandledBack;

            if (!demoViewHandledBack) {
                setDemoFragmentId(NO_DEMO_FRAGMENT_ID);

                // Post to the UI thread because this may cause MainView to execute a fragment
                // transaction after onSaveInstanceState was called:
                // 1) Turn "Don't keep activities" off
                // 2) Open app in portrait and select action topic
                // 3) Rotate to landscape, and then rotate back to portrait
                // 4) Press recent apps button, re-open app
                // 5) Press back button
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.setDemoFragmentId(NO_DEMO_FRAGMENT_ID);
                    }
                });
            }
        }

        Log.d(TAG, "onBackPressed - AFTER Back stack size: " + mFragmentManager.getBackStackEntryCount());

        if (!backHandled) {
            Log.d(TAG, "Neither MainFragment nor DemoFragment handled back.");
            super.onBackPressed();
        }
    }

    @Override
    public void setDemoFragmentId(int demoFragmentId) {
        mDemoFragmentId = demoFragmentId;

        if (mDemoFragmentId != NO_DEMO_FRAGMENT_ID && !mDualPane) {
            if (Debug.isInDebugMode(DEBUG)) {
                Log.d(TAG, "Showing new DemoFragment");
            }

            mDemoView = mDemoFragmentFactory.createDemoFragment(mDemoFragmentId);

            // BUG NOTE: The views in the leaving fragment (the one being animated/transitioned out)
            // except the toolbar, all disappear when this transition animation starts.
            // https://code.google.com/p/android/issues/detail?id=55228
            // http://stackoverflow.com/questions/14900738/nested-fragments-disappear-during-transition-animation
            // http://stackoverflow.com/questions/13982240/how-to-animate-fragment-removal
            // http://stackoverflow.com/questions/5959256/android-whats-wrong-with-my-fragment-transition-animation
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
                    .replace(R.id.content_fragment_container, mDemoView.asFragment(), FRAGMENT_TAG_DEMO)
                    .addToBackStack(FRAGMENT_TAG_DEMO)
                    .commit();
        }
    }

    private boolean isFragmentOnScreen(IBaseFragment fragment) {
        boolean isOnScreen = fragment != null && fragment.asFragment().isVisible();

        if (Debug.isInDebugMode(DEBUG)) {
            Log.d(TAG, "Is Fragment " + fragment + " on screen? " + isOnScreen);
        }

        return isOnScreen;
    }

    //region ILifecycleLoggable
    @Override
    public boolean onLogLifecycle() {
        return DEBUG;
    }
    //endregion
}
