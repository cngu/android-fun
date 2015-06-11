package com.cngu.androidfun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.cngu.androidfun.R;
import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.main.TopicListAdapter;

/**
 * A View representation of {@link com.cngu.androidfun.data.Topic}.
 */
public class TopicView extends FrameLayout {
    private int mUpX;
    private int mUpY;

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

    public int getUpX() {
        return mUpX;
    }

    public int getUpY() {
        return mUpY;
    }

    /**
     * This is overridden primarily to observe the touch events without interfering with
     * ?selectableItemBackground, unlike {@link android.view.View#setOnTouchListener(OnTouchListener)}.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mUpX = (int) event.getX();
            mUpY = (int) event.getY();
        }

        return super.onTouchEvent(event);
    }

    public interface OnClickListener {
        void onActionTopicClicked(ActionTopic topic, TopicListAdapter.ViewHolder viewHolder);
        void onMenuTopicClicked(MenuTopic topic, TopicListAdapter.ViewHolder viewHolder);
    }
}
