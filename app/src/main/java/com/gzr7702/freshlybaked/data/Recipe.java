package com.gzr7702.freshlybaked.data;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by rob on 10/19/17.
 */

public class Recipe implements Parcelable {
    private String name;
    private String servings;
    private Ingredient[] ingredientList;
    private Instruction[] instructionList;
    private String imageUrl;

    public Recipe(String name, String servings, Ingredient[] ingredientList,
                  Instruction[] instructionList, String imageUrl) {
        this.name = name;
        this.ingredientList = ingredientList;
        this.instructionList = instructionList;
        this.servings = servings;
        this.imageUrl = imageUrl;
    }

    public Recipe(Parcel parcel) {
        name = parcel.readString();
        servings = parcel.readString();
        imageUrl = parcel.readString();
    }

    public String getName() {
        return this.name;
    }

    public String getServings() {
        return this.servings;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public Ingredient[] getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(Ingredient[] ingredientList) {
        this.ingredientList = ingredientList;
    }

    public Instruction[] getInstructionist() {
        return instructionList;
    }

    public void setInstructionist(Instruction[] instructionist) {
        this.instructionList = instructionist;
    }

    public String toString() {
        // just return description for testing
        return "Recipe " + this.name + " that serves " + this.servings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelableArray(ingredientList, 0);
        dest.writeParcelableArray(instructionList, 0);
        dest.writeString(servings);
        dest.writeString(imageUrl);
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
