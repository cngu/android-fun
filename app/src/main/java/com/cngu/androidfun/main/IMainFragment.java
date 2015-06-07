package com.cngu.androidfun.main;

import com.cngu.androidfun.base.IBaseFragment;

public interface IMainFragment extends IBaseFragment {
    void registerPresenter(IMainPresenter presenter);
}
