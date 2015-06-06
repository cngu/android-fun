package com.cngu.androidfun;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG_MAIN = "cngu.fragment.tag.MAIN";

    private IMainFragment mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create helper *Managers
        FragmentManager fragmentManager = getSupportFragmentManager();

        //
        // Bootstrap Main MVP
        //
        // TODO: Create Presenter and Model

        // Get reference to MainFragment if it already exists in FragmentManager; otherwise create it
        mView = (MainFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG_MAIN);
        if (mView == null) {
            Log.i(TAG, "Couldn't find existing MainFragment; creating new one.");
            mView = MainFragment.newInstance();
        } else {
            Log.i(TAG, "Found existing MainFragment; reusing it now.");
        }

        // TODO: Init MainFragment

        // We only need to attach the fragment when this Activity is first opened.
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.content_fragment_container, mView.asFragment(), FRAGMENT_TAG_MAIN)
                    .commit();
        }
    }
}
