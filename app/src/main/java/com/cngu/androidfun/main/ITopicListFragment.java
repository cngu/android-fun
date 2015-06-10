package com.cngu.androidfun.main;

import com.cngu.androidfun.base.IBaseFragment;
import com.cngu.androidfun.view.TopicView;

public interface ITopicListFragment extends IBaseFragment {

    void setTopicClickListener(TopicView.OnClickListener listener);
}
