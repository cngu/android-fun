package com.cngu.androidfun.demo.common;

import com.cngu.androidfun.base.BaseFragment;

/**
 * Convenience class so when we create demos later on, we just have to create a fragment that can
 * extend from this DemoFragment class instead of worrying about BaseFragment and IDemoFragment.
 */
public abstract class DemoFragment extends BaseFragment implements IDemoFragment {

    @Override
    public boolean onLogLifecycle() {
        return false;
    }
}
