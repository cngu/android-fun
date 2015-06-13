package com.cngu.androidfun.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.cngu.androidfun.R;
import com.cngu.androidfun.base.BaseActivity;


public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG_MAIN = "cngu.fragment.tag.MAIN";

    private IMainFragment mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create helper *Managers
        FragmentManager fragmentManager = getSupportFragmentManager();
        ITopicManager topicManager = new TopicManager();

        //
        // Bootstrap Main MVP
        //

        // Get reference to MainFragment if it already exists in FragmentManager; otherwise create it
        mView = (MainFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG_MAIN);
        if (mView == null) {
            Log.i(TAG, "Couldn't find existing MainFragment; creating new one.");
            mView = MainFragment.newInstance();
        } else {
            Log.i(TAG, "Found existing MainFragment; reusing it now.");
        }

        IMainPresenter presenter = new MainPresenter(mView, topicManager);
        mView.registerPresenter(presenter);

        // We only need to attach the fragment when this Activity is first opened.
        if (savedInstanceState == null) {
            Log.i(TAG, "Attaching MainFragment to container.");
            fragmentManager.beginTransaction()
                    .add(R.id.content_fragment_container, mView.asFragment(), FRAGMENT_TAG_MAIN)
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
        if (!mView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    //region ILifecycleLoggable
    @Override
    public boolean onLogLifecycle() {
        return false;
    }
    //endregion
}
