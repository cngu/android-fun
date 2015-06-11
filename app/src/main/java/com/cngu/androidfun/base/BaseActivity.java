package com.cngu.androidfun.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cngu.androidfun.debug.ILifecycleLoggable;

/**
 * This class encapsulates all common functionality in every Activity.
 *
 * <p>Lifecycle logging is also handled if {@link ILifecycleLoggable#onLogLifecycle()} returns true.
 */
public abstract class BaseActivity extends AppCompatActivity implements ILifecycleLoggable{

    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (onLogLifecycle()) {
            Log.v(TAG, "onCreate(" + savedInstanceState + ")");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (onLogLifecycle()) {
            Log.v(TAG, "onStart()");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (onLogLifecycle()) {
            Log.v(TAG, "onRestart()");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (onLogLifecycle()) {
            Log.v(TAG, "onResume()");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (onLogLifecycle()) {
            Log.v(TAG, "onPause()");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (onLogLifecycle()) {
            Log.v(TAG, "onStop()");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (onLogLifecycle()) {
            Log.v(TAG, "onDestroy()");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (onLogLifecycle()) {
            Log.v(TAG, "onSaveInstanceState(" + outState + ")");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (onLogLifecycle()) {
            Log.v(TAG, "onConfigurationChanged(" + newConfig + ")");
        }
    }
}
