package com.cngu.androidfun.main;

/**
 * Presenter for {@link MainFragment}.
 */
public class MainPresenter implements IMainPresenter {

    private IMainFragment mView;

    public MainPresenter(IMainFragment view) {
        mView = view;
    }
}
