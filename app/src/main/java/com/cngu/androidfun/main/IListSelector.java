package com.cngu.androidfun.main;

/**
 * An interface to provide a list selection mode strategy.
 */
public interface IListSelector {
    void setSelected(int position, boolean selected);

    boolean isSelected(int position);
}
