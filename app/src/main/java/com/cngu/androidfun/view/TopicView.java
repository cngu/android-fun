package com.cngu.androidfun.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cngu.androidfun.R;
import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.main.TopicListAdapter;

/**
 * A View representation of {@link com.cngu.androidfun.data.Topic}.
 */
public class TopicView extends FrameLayout {

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private ImageView mExpandIconImageView;
    private View mSelectionBackgroundView;

    private String mTitle = null;
    private String mDescription = null;
    private int mDemoFragmentId = -1;
    private boolean mInflateChildren = true;

    public TopicView(Context context) {
        this(context, null);
    }

    public TopicView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.topicViewStyle);
    }

    public TopicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Skip loading XML attributes if this TopicView was created programmatically via one of the
        // other constructors.
        if (attrs != null) {
            TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TopicView, 0, 0);
            try {
                mTitle = ta.getString(R.styleable.TopicView_topic_title);
                mDescription = ta.getString(R.styleable.TopicView_topic_description);
                mDemoFragmentId = ta.getInteger(R.styleable.TopicView_demo_frag_id, -1);
                mInflateChildren = ta.getBoolean(R.styleable.TopicView_inflate_topic_view, true);
            } finally {
                ta.recycle();
            }
        }

        if (mInflateChildren) {
            // Inflate our children (defined in XML) and attach them to 'this'
            LayoutInflater.from(getContext()).inflate(R.layout.view_topic, this, true);

            mTitleTextView = (TextView) findViewById(R.id.title);
            mDescriptionTextView = (TextView) findViewById(R.id.description);
            mExpandIconImageView = (ImageView) findViewById(R.id.expand_icon);
            mSelectionBackgroundView = findViewById(R.id.selected_background);
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;

        if (mInflateChildren) {
            if (mTitle != null && !mTitle.trim().isEmpty()) {
                mTitleTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setText(mTitle);
            } else {
                mTitleTextView.setVisibility(View.GONE);
                mTitleTextView.setText("");
            }
        }
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;

        if (mInflateChildren) {
            if (mDescription != null && !mDescription.trim().isEmpty()) {
                mDescriptionTextView.setVisibility(View.VISIBLE);
                mDescriptionTextView.setText(mDescription);
            } else {
                mDescriptionTextView.setVisibility(View.GONE);
                mDescriptionTextView.setText("");
            }
        }
    }

    public void hideSelectionBackground() {
        if (mInflateChildren) {
            mSelectionBackgroundView.setVisibility(View.INVISIBLE);
        }
    }

    public void showSelectionBackground() {
        if (mInflateChildren) {
            mSelectionBackgroundView.setVisibility(View.VISIBLE);
        }
    }

    public void hideExpandIcon() {
        if (mInflateChildren) {
            mExpandIconImageView.setVisibility(View.INVISIBLE);
        }
    }

    public void showExpandIcon() {
        if (mInflateChildren) {
            mExpandIconImageView.setVisibility(View.VISIBLE);
        }
    }

    public int getDemoFragmentId() {
        return mDemoFragmentId;
    }

    public interface OnClickListener {
        void onActionTopicClicked(ActionTopic topic, TopicListAdapter.ViewHolder viewHolder);
        void onMenuTopicClicked(MenuTopic topic, TopicListAdapter.ViewHolder viewHolder);
        void onSelectedTopicClicked();
    }
}
