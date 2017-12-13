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

    protected Instruction(Parcel in) {
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Instruction> CREATOR = new Parcelable.Creator<Instruction>() {
        @Override
        public Instruction createFromParcel(Parcel in) {
            return new Instruction(in);
        }

        @Override
        public Instruction[] newArray(int size) {
            return new Instruction[size];
        }
    };

    public String getShortDescription() {
        return this.shortDescription;
    }

    public String toString() {
        String instr = "Description: " + this.description + " video: " + this.videoUrl + " thumbnail: " + this.thumbnailUrl;
        return instr;
    }
}
