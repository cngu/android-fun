package com.cngu.androidfun.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * A View representation of {@link com.cngu.androidfun.data.Topic}.
 */
public class TopicView extends LinearLayout {
    public TopicView(Context context) {
        super(context);
    }

    public TopicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TopicView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    
}
