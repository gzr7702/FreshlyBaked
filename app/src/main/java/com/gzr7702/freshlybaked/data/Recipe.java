package com.gzr7702.freshlybaked.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rob on 10/19/17.
 */

public class Recipe implements Parcelable {
    private String name;
    private String servings;
    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Instruction> instructionList;
    private String imageUrl;

    public Recipe(String name, String servings, ArrayList<Ingredient> ingredientList,
                  ArrayList<Instruction> instructionList, String imageUrl) {
        this.name = name;
        this.servings = servings;
        this.ingredientList = ingredientList;
        this.instructionList = instructionList;
        this.imageUrl = imageUrl;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        servings = in.readString();
        ingredientList = in.readArrayList(Ingredient.class.getClassLoader());
        instructionList = in.readArrayList(Instruction.class.getClassLoader());
        imageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(servings);
        dest.writeList(ingredientList);
        dest.writeList(instructionList);
        dest.writeString(imageUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public ArrayList<Ingredient> getIngredientList() {
        return this.ingredientList;
    }

    public ArrayList<Instruction> getInstructionList() {
        return this.instructionList;
    }

    public String getName() {
        return this.name;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append(this.name).append(" that serv ").append(this.servings);

        for (Instruction item : instructionList) {
            message.append(item);
            message.append(" ");
        }

        return message.toString();
    }

}
