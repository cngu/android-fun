package com.cngu.androidfun.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An ActionTopic represents a single subject that can be demoed.
 */
public class ActionTopic extends Topic {
    private int mDemoFragmentId;

    /**
     * @param title Short title for this ActionTopic.
     * @param description Short description for this ActionTopic.
     * @param demoFragmentId Id of the topic fragment to launch when this topic is activated.
     *                         Cannot be null.
     */
    public ActionTopic(String title, String description, int demoFragmentId) {
        super(title, description);
        setDemoFragmentId(demoFragmentId);
    }

    public int getDemoFragmentId() {
        return mDemoFragmentId;
    }

    public void setDemoFragmentId(int demoFragmentId) {
        mDemoFragmentId = demoFragmentId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(ActionTopic.class.getSimpleName()).append(System.identityHashCode(this)).append("{")
                .append("title=").append("\"").append(getTitle()).append("\"")
                .append(" description=").append("\"").append(getDescription()).append("\"")
                .append(" demoFragId=").append(mDemoFragmentId)
                .append("}");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        ActionTopic that = (ActionTopic) o;

        if (mDemoFragmentId != that.mDemoFragmentId) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mDemoFragmentId;
        return result;
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(mDemoFragmentId);
    }

    public static final Parcelable.Creator<ActionTopic> CREATOR = new Parcelable.Creator<ActionTopic>() {
        @Override
        public ActionTopic createFromParcel(Parcel in) {
            return new ActionTopic(in);
        }

        @Override
        public ActionTopic[] newArray(int size) {
            return new ActionTopic[size];
        }
    };

    private ActionTopic(Parcel in) {
        super(in);
        mDemoFragmentId = in.readInt();
    }
    //endregion
}
