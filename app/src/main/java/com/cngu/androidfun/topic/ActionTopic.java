package com.cngu.androidfun.topic;

import android.os.Parcel;
import android.os.Parcelable;

import com.cngu.androidfun.enums.TopicFragmentId;

/**
 * An ActionTopic represents a single subject that can be demoed.
 */
public class ActionTopic extends Topic {
    private TopicFragmentId mTopicFragmentId;

    /**
     * @param title Short title for this ActionTopic.
     * @param description Short description for this ActionTopic.
     * @param launchFragmentId Id of the topic fragment to launch when this topic is activated.
     *                         Cannot be null.
     */
    public ActionTopic(String title, String description, TopicFragmentId launchFragmentId) {
        super(title, description);
        setTopicFragmentToLaunch(launchFragmentId);
    }

    public TopicFragmentId getTopicFragmentToLaunch() {
        return mTopicFragmentId;
    }

    public void setTopicFragmentToLaunch(TopicFragmentId launchFragmentId) {
        if (launchFragmentId == null) {
            throw new IllegalArgumentException("launchFragmentId cannot be null.");
        }
        mTopicFragmentId = launchFragmentId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(ActionTopic.class.getSimpleName()).append("{")
                .append("title=").append("\"").append(getTitle()).append("\"")
                .append(" description=").append("\"").append(getDescription()).append("\"")
                .append(" topicFragId=").append(mTopicFragmentId.toString())
                .append("}");

        return sb.toString();
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeSerializable(mTopicFragmentId);
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
        mTopicFragmentId = (TopicFragmentId) in.readSerializable();
    }
    //endregion
}
