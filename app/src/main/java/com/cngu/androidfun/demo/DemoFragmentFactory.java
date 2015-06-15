package com.cngu.androidfun.demo;

import com.cngu.androidfun.main.MainFragment;

public class DemoFragmentFactory {

    public DemoFragmentFactory() {
    }

    public IDemoFragment createDemoFragment(int demoFragId) {
        switch (demoFragId)
        {
            default:
                return MainFragment.TestFragment.newInstance(String.valueOf(demoFragId));
        }
    }
}
