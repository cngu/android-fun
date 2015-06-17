package com.cngu.androidfun.demo.activitytransition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cngu.androidfun.R;
import com.cngu.androidfun.demo.common.DemoFragment;

public class ActivityTransitionDemo extends DemoFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context = getActivity();

        int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;

        RelativeLayout layout = new RelativeLayout(context);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(matchParent, matchParent));
        layout.setPadding(0, getResources().getDimensionPixelSize(R.dimen.status_bar_height), 0, 0);

        TextView tv = new TextView(context);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            tv.setLayoutParams(new ViewGroup.LayoutParams(matchParent, matchParent));
            tv.setText("ActivityTransitions utilizes ActivityOptions.makeSceneTransitionAnimation," +
                    " which requires API level 21");
            tv.setTextColor(Color.RED);
            tv.setGravity(Gravity.CENTER);

            return tv;
        }

        tv.setText("Click the following button to start the SourceTransitionActivity that" +
                " displays an initial scene. Another button will be in that activity" +
                " to start the TargetTransitionActivity, and you can observe the shared" +
                " views animate during the activity transition.");

        Button startActivityButton = new Button(context);
        startActivityButton.setText("Start SourceTransitionActivity");
        startActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity parent = getActivity();
                parent.startActivity(new Intent(parent, SourceTransitionActivity.class));
            }
        });

        layout.addView(tv);
        layout.addView(startActivityButton);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) startActivityButton.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

        return layout;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
