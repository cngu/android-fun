package com.cngu.androidfun;

import android.support.v4.app.Fragment;

/**
 * An abstract BaseFragment that implements the API provided in IBaseFragment.
 *
 * <p>Custom fragments that wish to access this API should:
 * <ol>
 *     <li>have their concrete class extend from BaseFragment, and
 *     <li>have their interface extend IBaseFragment, or have their concrete class defined to
 *         implement IBaseFragment directly.
 * </ol>
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment {

    public Fragment asFragment() {
        return this;
    }
}
