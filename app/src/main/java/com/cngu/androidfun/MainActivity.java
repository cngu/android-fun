package com.cngu.androidfun;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG_MAIN = "cngu.fragment.tag.MAIN";

    private IMainFragment mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use the toolbar as our Action Bar (i.e. not in standalone mode)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //
        // Initialize helper *Managers
        //
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        }

        return super.onOptionsItemSelected(item);
    }
}
