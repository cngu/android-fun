package com.cngu.androidfun.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.cngu.androidfun.R;
import com.cngu.androidfun.base.BaseActivity;
import com.cngu.androidfun.base.IBaseFragment;
import com.cngu.androidfun.demo.IDemoFragment;


public class MainActivity extends BaseActivity implements IRootView {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG_MAIN = "cngu.fragment.tag.MAIN";
    private static final String FRAGMENT_TAG_DEMO = "cngu.fragment.tag.DEMO";

    private FragmentManager mFragmentManager;
    private IMainFragment mMainView;
    private IDemoFragment mDemoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create helper *Managers
        mFragmentManager = getSupportFragmentManager();

        // Get reference to MainFragment if it already exists in FragmentManager; otherwise create it
        mMainView = (MainFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG_MAIN);
        if (mMainView == null) {
            Log.i(TAG, "Couldn't find existing MainFragment; creating new one.");
            mMainView = MainFragment.newInstance();
        } else {
            Log.i(TAG, "Found existing MainFragment; reusing it now.");
        }

        // We only need to attach the fragment when this Activity is first opened.
        if (savedInstanceState == null) {
            Log.i(TAG, "Attaching MainFragment to container.");
            mFragmentManager.beginTransaction()
                    .replace(R.id.content_fragment_container, mMainView.asFragment(), FRAGMENT_TAG_MAIN)
                    .commit();
        }
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

        if (isFragmentOnScreen(mMainView)) {
            backHandled |= mMainView.onBackPressed();
        }

        if (isFragmentOnScreen(mDemoView)) {
            backHandled |= mDemoView.onBackPressed();
        }

        if (!backHandled) {
            super.onBackPressed();
        }
    }

    private boolean isFragmentOnScreen(IBaseFragment fragment) {
        return fragment != null && fragment.asFragment().isVisible();
    }

    //region ILifecycleLoggable
    @Override
    public boolean onLogLifecycle() {
        return false;
    }
    //endregion
}
