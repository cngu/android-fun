package com.cngu.androidfun.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cngu.androidfun.demo.common.DemoFragment;

// TODO: This fragment is a placeholder for testing only! Replace it with real demo fragments later.
public class TestFragment extends DemoFragment {

    private String mName;

    public static TestFragment newInstance(String name) {
        TestFragment tf = new TestFragment();
        tf.mName = name;

        Bundle args = new Bundle();
        args.putString("NAME", name);
        tf.setArguments(args);

        return tf;
    }

    public TestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle args = getArguments();
        if (args == null) {
            mName = "NONE";
        } else {
            mName = args.getString("NAME");
        }

        Context context = getActivity();

        TextView tv = new TextView(context);
        tv.setTextSize(30);
        tv.setText("DEMO FRAGMENT - " + mName);

        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rl.addView(tv);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

        return rl;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
