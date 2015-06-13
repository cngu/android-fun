package com.cngu.androidfun.main;

import com.cngu.androidfun.base.IBaseFragment;

public interface IMainFragment extends IBaseFragment, IBackKeyListener {
    void registerPresenter(IMainPresenter presenter);

    void setTopicManager(ITopicManager topicManager);

    void addNewPage();

    void reverseNavigationBackToPage(int page, boolean clearSelection);

    int getPageCount();

    int getCurrentPage();
}
