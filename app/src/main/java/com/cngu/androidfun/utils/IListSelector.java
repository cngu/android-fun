package com.cngu.androidfun.utils;

/**
 * An interface to provide a list selection mode strategy.
 */
public interface IListSelector {
    void clearSelection();

    int[] getSelected();

    void setSelected(int position, boolean selected);

    boolean isSelected(int position);
}
