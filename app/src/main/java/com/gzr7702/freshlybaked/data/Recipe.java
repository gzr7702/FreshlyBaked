package com.gzr7702.freshlybaked.data;

import java.util.ArrayList;

/**
 * Created by rob on 10/19/17.
 */

public class Recipe {
    private String name;
    private String servings;
    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Instruction> instructionist;

    public Recipe(String name, String servings, ArrayList<Ingredient> ingredientList, ArrayList<Instruction> instructionList) {
        this.name = name;
        this.ingredientList = ingredientList;
        this.instructionist = instructionList;
        this.servings = servings;
    }

    public String getName() {
        return this.name;
    }

    public String getServings() {
        return this.servings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public ArrayList<Instruction> getInstructionist() {
        return instructionist;
    }

    public void setInstructionist(ArrayList<Instruction> instructionist) {
        this.instructionist = instructionist;
    }

    public String toString() {
        // just return description for testing
        return "Recipe " + this.name + " that serves " + this.servings;
    }
}
