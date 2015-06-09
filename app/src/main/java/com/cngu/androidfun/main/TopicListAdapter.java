package com.cngu.androidfun.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cngu.androidfun.R;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.view.TopicView;

import java.util.List;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder> {

    private List<Topic> mTopicList;

    public TopicListAdapter(List<Topic> topics) {
        mTopicList = topics;
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
        Topic topic = mTopicList.get(holder.getAdapterPosition());

        holder.titleTextView.setText(topic.getTitle());
        holder.descriptionTextView.setText(topic.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTopicList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
