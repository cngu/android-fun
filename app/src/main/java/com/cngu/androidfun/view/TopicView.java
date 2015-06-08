package com.cngu.androidfun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cngu.androidfun.R;

/**
 * A View representation of {@link com.cngu.androidfun.data.Topic}.
 */
public class TopicView extends LinearLayout {
    public TopicView(Context context) {
        this(context, null);
    }

    public TopicView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.topicViewStyle);
    }

    public TopicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        /*
        // Load style attribute vales
        int layoutWidth = LayoutParams.MATCH_PARENT;
        int layoutHeight = getResources().getDimensionPixelSize(R.dimen.topic_view_height);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(layoutWidth, layoutHeight);
        int verticalPadding = 0;
        int horizontalPadding = getResources().getDimensionPixelSize(R.dimen.list_item_horizontal_padding);

        // Set style attributes
        setLayoutParams(lp);
        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        setOrientation(HORIZONTAL);
        setWeightSum(1f);
        */

        // Inflate our children (defined in XML) and attach them to 'this'
        LayoutInflater.from(getContext()).inflate(R.layout.view_topic, this, true);

        // Find and initialize children views
        ((TextView)findViewById(R.id.title)).setText("CUSTOM TITLE");
        ((TextView) findViewById(R.id.description)).setText("CUSTOM DESCRIPTION");
    }
}
