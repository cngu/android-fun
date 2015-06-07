package com.cngu.androidfun.base;

import android.support.v4.app.Fragment;

/**
 * Interface defining the API that are common to all custom fragments. The implementation of this
 * API should only be provided in {@link BaseFragment}, but may be overridden if necessary.
 *
 * <p>Custom fragments that need access to this common API should:
 * <ol>
 *     <li>have their concrete class extend from BaseFragment, and
 *     <li>have their interface extend IBaseFragment, or have their concrete class defined to
 *         implement IBaseFragment directly.
 * </ol>
 */
public interface IBaseFragment {
    Fragment asFragment();
}
