package com.cngu.androidfun.demo.common;

import android.content.Context;
import android.content.res.Resources;

import com.cngu.androidfun.R;
import com.cngu.androidfun.demo.activitytransition.ActivityTransitionDemo;
import com.cngu.androidfun.main.TestFragment;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class DemoFragmentFactory {

    private Resources mResources;
    private HashMap<Integer, SoftReference<IDemoFragment>> mDemoFragmentMap;

    public DemoFragmentFactory(Context context) {
        mResources = context.getResources();
        mDemoFragmentMap = new HashMap<>();
    }

    public IDemoFragment createDemoFragment(int demoFragId) {

        SoftReference<IDemoFragment> demoFragmentRef = mDemoFragmentMap.get(demoFragId);
        if (demoFragmentRef != null) {
            return demoFragmentRef.get();
        }

        IDemoFragment demoFragment;

        if (demoFragId == getResInt(R.integer.demo_activitytransition))
        {
            demoFragment = new ActivityTransitionDemo();
        }
        else
        {
            demoFragment = TestFragment.newInstance(String.valueOf(demoFragId));
        }

        mDemoFragmentMap.put(demoFragId, new SoftReference<>(demoFragment));

        return demoFragment;
    }

    private int getResInt(int intResId) {
        return mResources.getInteger(intResId);
    }
}
