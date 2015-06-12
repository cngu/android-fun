package com.cngu.androidfun.main;

import com.cngu.androidfun.base.IBaseFragment;

public interface IMainFragment extends IBaseFragment {
    void registerPresenter(IMainPresenter presenter);

    void setTopicManager(ITopicManager topicManager);

    void addNewPage();

    void goToNextPage();

    void goBackToPage(int page, boolean clearSelection);

    int getPageCount();

    int getCurrentPage();
}
