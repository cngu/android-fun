package com.cngu.androidfun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cngu.androidfun.R;
import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;

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
        // Inflate our children (defined in XML) and attach them to 'this'
        LayoutInflater.from(getContext()).inflate(R.layout.view_topic, this, true);
    }

    public interface OnClickListener {
        void onActionTopicClicked(ActionTopic topic, int position);
        void onMenuTopicClicked(MenuTopic topic, int position);
    }
}
