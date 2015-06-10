package com.cngu.androidfun.main;

import com.cngu.androidfun.base.IBaseFragment;
import com.cngu.androidfun.data.Topic;
import com.cngu.androidfun.view.TopicView;

import java.util.List;

public interface ITopicListFragment extends IBaseFragment {

    void setTopicList(List<Topic> topicList);

    void setTopicClickListener(TopicView.OnClickListener listener);
}
