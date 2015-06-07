package com.cngu.androidfun.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A Topic represents a general category or specific subject that can be demoed.
 *
 * <p><b>NOTE:</b> because this class is abstract, it cannot provide an implementation for
 * Parcelable.Creator. This means that it cannot be used as an argument in an AIDL; if you attempt
 * to do so, the state of the concrete subclasses will be lost. There are two workarounds for this:
 * <ol>
 *     <li>If it makes sense to do so in the future, make Topic concrete and provide an implementation
 *         of Parcelable.Creator.
 *     <li>Put this instance in a {@link android.os.Bundle} and pass the Bundle through AIDL instead.
 * </ol>
 */
public abstract class Topic implements Parcelable {
    private static final String DEFAULT_TITLE = "<No title>";
    private static final String DEFAULT_DESCRIPTION = "";

    private String mTitle;
    private String mDescription;

    /**
     * @param title Short title for this topic.
     * @param description Short description for this topic.
     */
    public Topic(String title, String description) {
        setTitle(title);
        setDescription(description);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title == null ? DEFAULT_TITLE : title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description == null ? DEFAULT_DESCRIPTION : description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(Topic.class.getSimpleName()).append("{")
            .append("title=").append("\"").append(mTitle).append("\"")
            .append(" description=").append("\"").append(mDescription).append("\"")
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
        out.writeString(mTitle);
        out.writeString(mDescription);
    }

    /*
    public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };
    */

    protected Topic(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
    }
    //endregion
}
