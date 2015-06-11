package com.cngu.androidfun.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cngu.androidfun.debug.ILifecycleLoggable;

/**
 * An abstract BaseFragment that implements the API provided in IBaseFragment.
 *
 * <p>Custom fragments that wish to access this API should:
 * <ol>
 *     <li>have their concrete class extend from BaseFragment, and
 *     <li>have their interface extend IBaseFragment, or have their concrete class defined to
 *         implement IBaseFragment directly.
 * </ol>
 *
 * <p>Lifecycle logging is also handled if {@link ILifecycleLoggable#onLogLifecycle()} returns true.
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment, ILifecycleLoggable {

    protected final String TAG = getClass().getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (onLogLifecycle()) {
            Log.v(TAG, "onAttach(Activity)");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (onLogLifecycle()) {
            Log.v(TAG, "onCreate(" + savedInstanceState + ")");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (onLogLifecycle()) {
            Log.v(TAG, "onCreateView(Inflater, ViewGroup, " + savedInstanceState + ")");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (onLogLifecycle()) {
            Log.v(TAG, "onActivityCreated(" + savedInstanceState + ")");
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (onLogLifecycle()) {
            Log.v(TAG, "onViewStateRestored(" + savedInstanceState + ")");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (onLogLifecycle()) {
            Log.v(TAG, "onStart()");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (onLogLifecycle()) {
            Log.v(TAG, "onResume()");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (onLogLifecycle()) {
            Log.v(TAG, "onPause()");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (onLogLifecycle()) {
            Log.v(TAG, "onStop()");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (onLogLifecycle()) {
            Log.v(TAG, "onDestroyView()");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (onLogLifecycle()) {
            Log.v(TAG, "onDestroy()");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (onLogLifecycle()) {
            Log.v(TAG, "onDetach()");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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

    public Fragment asFragment() {
        return this;
    }
}
