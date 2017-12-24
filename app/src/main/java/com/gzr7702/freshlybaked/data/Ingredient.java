package com.gzr7702.freshlybaked.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rob on 10/22/17.
 */

public class Ingredient implements Parcelable {
    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }


    protected Ingredient(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String toString() {
        String ingredientString;
        String lowerMeasure = this.measure.toLowerCase();
        if (lowerMeasure.equals("unit")) {
            ingredientString = this.quantity + " " + this.ingredient;
        } else {
            ingredientString = this.quantity + lowerMeasure + " " + this.ingredient;
        }
        return ingredientString;
    }
}
