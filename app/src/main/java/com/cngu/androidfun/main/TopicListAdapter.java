package com.cngu.androidfun.main;

import android.animation.Animator;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cngu.androidfun.R;
import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.view.TopicView;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder> {

    private static final long SELECTION_ANIMATION_DURATION = 350L;

    private SelectableTopicList mTopicList;
    private TopicView.OnClickListener mTopicClickListener;

    private Animator mSelectionAnimator;

    public void setTopicList(SelectableTopicList topicList) {
        mTopicList = topicList;
    }

    public void setTopicClickListener(TopicView.OnClickListener topicClickListener) {
        mTopicClickListener = topicClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Must manually set layout params here because TopicView never sets its own layout params,
        // so it defaults to WRAP_CONTENT. However, if this TopicView was added to a LinearLayout,
        // these layout params would not be needed. So there's some different behavior between
        // child views added to LinearLayout and RecyclerView.
        TopicView tv = new TopicView(parent.getContext());
        tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolder(tv);
    }

    // We use instanceof here only because this is a simple scenario, and we won't be adding
    // more types of topics. If we do decide to add more topics in the future, we should perform
    // one of the following refactorings:
    //
    // 1) Visitor Pattern
    //    - "Element" will be Topic, and declare a accept(Visitor, TopicView) method.
    //    - Create a Visitor class that encapsulates all of the logic to initialize a passed-in
    //    TopicView with the data from a passed in Topic
    //    - Topic.accept(Visitor, TopicView) would look like: visitor.visit(this, topicView)
    //
    // 2) Move this type-checking and TopicView initializing code into a separate helper class
    //    - This simply localizes the type checking to one class, so any future changes only
    //      need to be done on this class and nowhere else.
    //
    // 3) Topic.loadInto(TopicView)
    //    - Force each topic subclass to know how to load its own data into a given TopicView.
    //    - This isn't a great approach because Topics will now be cluttered with View logic.
    //    - Doesn't follow SRP.
    @Override
    public void onBindViewHolder(ViewHolder holder, int viewType) {
        int topicPosition = holder.getAdapterPosition();
        final Topic topic = mTopicList.get(topicPosition);

        holder.titleTextView.setText(topic.getTitle());
        holder.descriptionTextView.setText(topic.getDescription());

        if (topic instanceof ActionTopic) {
            holder.expandIconImageView.setVisibility(View.INVISIBLE);
        } else if (topic instanceof MenuTopic) {
            holder.expandIconImageView.setVisibility(View.VISIBLE);
        }

        if (!mTopicList.isSelected(topicPosition)) {
            holder.selectedBackgroundView.setVisibility(View.INVISIBLE);
        } else {
            holder.selectedBackgroundView.setVisibility(View.VISIBLE);
        }

        attachEventListener(holder);
    }

    @Override
    public int getItemCount() {
        return mTopicList.size();
    }

    private void setNewSelection(int topicPosition) {
        // Clear the current selection
        int currentSelection = mTopicList.getSelected()[0];
        mTopicList.setSelected(currentSelection, false);
        notifyItemChanged(currentSelection);

        // Select the new item
        mTopicList.setSelected(topicPosition, true);
    }

    private void notifyClickListener(Topic topic, ViewHolder holder) {
        if (topic instanceof ActionTopic) {
            mTopicClickListener.onActionTopicClicked((ActionTopic) topic, holder);
        } else if (topic instanceof MenuTopic) {
            mTopicClickListener.onMenuTopicClicked((MenuTopic) topic, holder);
        }
    }

    private void attachEventListener(final ViewHolder holder) {
        final TopicView topicView = (TopicView) holder.itemView;
        final View backgroundView = holder.selectedBackgroundView;
        final int topicPosition = holder.getAdapterPosition();
        final Topic topic = mTopicList.get(topicPosition);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            topicView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Ignore taps on items that are already selected
                    if (mTopicList.isSelected(topicPosition)) {
                        return;
                    }

                    setNewSelection(topicPosition);

                    // Stop any existing animation
                    if (mSelectionAnimator != null) {
                        mSelectionAnimator.end();
                        mSelectionAnimator = null;
                    }

                    int upX = topicView.getUpX();
                    int upY = topicView.getUpY();

                    // Prepare the animation to show the highlighted selection background
                    int radius;
                    int width = backgroundView.getWidth();
                    int distanceToRight = width - upX;
                    if (distanceToRight > width/2) {
                        radius = distanceToRight;
                    } else {
                        radius = width - distanceToRight;
                    }
                    mSelectionAnimator = ViewAnimationUtils.createCircularReveal(
                            backgroundView, upX, upY, 0, radius);

                    mSelectionAnimator.setDuration(SELECTION_ANIMATION_DURATION);
                    mSelectionAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            backgroundView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            // Only process the current animator. If this was called from an old
                            // Animator that was cancel()'d or end()'d, ignore it.
                            if (mSelectionAnimator == animator) {
                                //notifyItemChanged(topicPosition);
                                notifyClickListener(topic, holder);
                            }
                        }

                        @Override public void onAnimationCancel(Animator animator) {}
                        @Override public void onAnimationRepeat(Animator animator) {}
                    });

                    // Animate the selection background
                    mSelectionAnimator.start();
                }
            });
        }
        else
        {
            topicView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTopicList.isSelected(topicPosition)) {
                        return;
                    }

                    setNewSelection(topicPosition);
                    notifyItemChanged(topicPosition);
                    notifyClickListener(topic, holder);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageView expandIconImageView;
        public View selectedBackgroundView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            expandIconImageView = (ImageView) itemView.findViewById(R.id.expand_icon);
            selectedBackgroundView = itemView.findViewById(R.id.selected_background);
        }
    }
}
