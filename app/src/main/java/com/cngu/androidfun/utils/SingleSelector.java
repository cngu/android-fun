package com.cngu.androidfun.utils;

import com.cngu.androidfun.utils.IListSelector;

/**
 * A strategy for managing a single selection state of general items in a list.
 */
public class SingleSelector implements IListSelector {
    private static final int SELECTED_NONE = -1;

    private int mSelectedIndex;

    public SingleSelector() {
        mSelectedIndex = SELECTED_NONE;
    }

    @Override
    public void clearSelection() {
        mSelectedIndex = SELECTED_NONE;
    }

    @Override
    public int[] getSelected() {
        return new int[] {mSelectedIndex};
    }

    @Override
    public void setSelected(int position, boolean selected) {
        mSelectedIndex = selected ? position : SELECTED_NONE;
    }

    @Override
    public boolean isSelected(int position) {
        if (mSelectedIndex == SELECTED_NONE) {
            return false;
        }

        return position == mSelectedIndex;
    }
}
