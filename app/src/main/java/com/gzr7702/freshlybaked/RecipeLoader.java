package com.gzr7702.freshlybaked;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.gzr7702.freshlybaked.data.Ingredient;
import com.gzr7702.freshlybaked.data.Instruction;
import com.gzr7702.freshlybaked.data.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Fetch the results of a query based on Author, Title, Publish Date
 */

public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {
    private final String LOG_TAG = RecipeLoader.class.getSimpleName();

    public RecipeLoader (Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Recipe> loadInBackground() {


        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        List<Recipe> recipeList = new ArrayList<>();

        // Will contain the raw JSON response as a string.
        String resultJsonStr = null;

        try {
            URL url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");


            // Create the request to recipe URL, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // line end for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            resultJsonStr = buffer.toString();
            recipeList = getDataFromJson(resultJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return recipeList;
    }

    private List<Recipe> getDataFromJson(String jsonString) throws JSONException {

        List<Recipe> recipeList = new ArrayList<>();

        JSONArray recipeJson = new JSONArray(jsonString);

        if (recipeJson instanceof JSONArray) {
            for (int i = 0; i < recipeJson.length(); i++) {
                JSONObject recipe = recipeJson.getJSONObject(i);

                String name = recipe.getString("name");
                if (name == null) {
                    name = "Some amazing dessert";
                }

                String servings = recipe.getString("servings");
                if (servings == null) {
                    name = "Feeds a lot of people";
                }

                ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
                ArrayList<Instruction> instructionArrayList = new ArrayList<>();
                // We handle no image later when we use it
                String imageUrl = recipe.getString("image");

                // populate ingredients array
                JSONArray ingredientJsonArray = recipe.getJSONArray("ingredients");
                if (ingredientJsonArray == null) {
                    //TODO: make a an array w/one ingredient that says "no ingredients"
                    Log.v(LOG_TAG, "Ingredients are null");
                }

                for (int j = 0; j < ingredientJsonArray.length(); j++) {
                    String ingredientString = ingredientJsonArray.getJSONObject(j).getString("ingredient");
                    if (ingredientString == null) {
                        ingredientString = "Ingredient is missing for this step";
                    }

                    int quantityString = ingredientJsonArray.getJSONObject(j).getInt("quantity");
                    String measureString = ingredientJsonArray.getJSONObject(j).getString("measure");
                    if (measureString == null) {
                        measureString = "Measure not provided";
                    }
                    ingredientArrayList.add(new Ingredient(quantityString, measureString, ingredientString));
                }

                // populate instructions array
                JSONArray instructionsJsonArray = recipe.getJSONArray("steps");
                if (instructionsJsonArray == null) {
                    //TODO: make a an array w/one instruction that says "no instructions"
                    Log.v(LOG_TAG, "Instructions are null");
                }
                for (int k = 0; k < instructionsJsonArray.length(); k++) {
                    //create Ingredient object, at to ingredient array
                    String shortDescription = instructionsJsonArray.getJSONObject(k).getString("shortDescription");
                    if (shortDescription == null) {
                        shortDescription = "No description available";
                    }

                    String regularDescription = instructionsJsonArray.getJSONObject(k).getString("description");
                    if (regularDescription == null) {
                        regularDescription = "No description available";
                    }
                    String videoUrl = instructionsJsonArray.getJSONObject(k).getString("videoURL");
                    if (videoUrl == null) {
                        videoUrl = "";
                    }

                    String thumbnailUrl = instructionsJsonArray.getJSONObject(k).getString("thumbnailURL");
                    if (thumbnailUrl == null) {
                        thumbnailUrl = "";
                    }

                    instructionArrayList.add(new Instruction(shortDescription, regularDescription, videoUrl, thumbnailUrl));
                }

                recipeList.add(new Recipe(name, servings, ingredientArrayList, instructionArrayList, imageUrl));
            }
        } else {
            // TODO: deal with no data
            Log.v(LOG_TAG, "No data found");
        }

        for(Recipe currentRecipe : recipeList) {
            Log.v(LOG_TAG, currentRecipe.toString());
        }
        return recipeList;
    }

}
