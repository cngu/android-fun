package com.cngu.androidfun.main;

import com.cngu.androidfun.base.IBaseFragment;

public interface IMainFragment extends IBaseFragment, IBackKeyListener {
    void setDemoFragmentId(int demoFragmentId);

    void addNewPage();

    void reverseNavigationBackToPage(int page, boolean clearSelection);

    boolean viewPreviousPage();

    boolean viewNextPage();

    int getPageCount();

    int getCurrentPage();
}
