package com.gzr7702.freshlybaked.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rob on 10/22/17.
 */

public class Instruction implements Parcelable {
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;

    public Instruction(String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Instruction(Parcel parcel) {
        shortDescription = parcel.readString();
        description = parcel.readString();
        videoUrl = parcel.readString();
        thumbnailUrl = parcel.readString();
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String toString() {
        // just return description for testing
        return this.description;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }

    public static final Parcelable.Creator<Instruction> CREATOR
            = new Parcelable.Creator<Instruction>() {
        public Instruction createFromParcel(Parcel in) {
            return new Instruction(in);
        }

        public Instruction[] newArray(int size) {
            return new Instruction[size];
        }
    };

}
