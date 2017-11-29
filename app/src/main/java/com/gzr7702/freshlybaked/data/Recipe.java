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
        ingredientList = parcel.createTypedArray(Ingredient.CREATOR);
        instructionList = parcel.createTypedArray(Instruction.CREATOR);
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

    public Instruction[] getInstructionList() {
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
        dest.writeTypedArray(ingredientList, 0);
        dest.writeTypedArray(instructionList, 0);
        dest.writeString(servings);
        dest.writeString(imageUrl);
    }

    public static final Parcelable.Creator<Recipe> CREATOR
            = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
