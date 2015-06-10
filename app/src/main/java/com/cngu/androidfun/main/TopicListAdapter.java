package com.cngu.androidfun.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cngu.androidfun.R;
import com.cngu.androidfun.data.ActionTopic;
import com.cngu.androidfun.data.MenuTopic;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.view.TopicView;

import java.util.List;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder> {

    private List<Topic> mTopicList;
    private TopicView.OnClickListener mTopicClickListener;

    public TopicListAdapter(List<Topic> topics) {
        mTopicList = topics;
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

    @Override
    public void onBindViewHolder(ViewHolder holder, int viewType) {
        final int topicPosition = holder.getAdapterPosition();
        final Topic topic = mTopicList.get(topicPosition);

        holder.titleTextView.setText(topic.getTitle());
        holder.descriptionTextView.setText(topic.getDescription());

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
        if (topic instanceof ActionTopic) {
            holder.expandIconImageView.setVisibility(View.INVISIBLE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTopicClickListener.onActionTopicClicked((ActionTopic)topic, topicPosition);
                }
            });
        } else if (topic instanceof MenuTopic) {
            holder.expandIconImageView.setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTopicClickListener.onMenuTopicClicked((MenuTopic)topic, topicPosition);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mTopicList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageView expandIconImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            expandIconImageView = (ImageView) itemView.findViewById(R.id.expand_icon);
        }
    }
}
