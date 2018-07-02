package com.gzr7702.freshlybaked;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */

public class IngredientService extends IntentService {
    public static final String ACTION_FETCH_RECIPES = "com.gzr7702.freshlybaked.widget.action.FOO";
    ArrayList<Recipe> mRecipeList = new ArrayList<>();
    private final String LOG_TAG = RecipeLoader.class.getSimpleName();

    public IngredientService() {
        super("IngredientService");
        Log.v("IngredientService", "in constructor...");
    }

    /**
     * Starts this service to perform action ACTION_FETCH_RECIPES with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFetchRecipes(Context context) {
        Log.v("IngredientService", "in startActionFetchRecipes()...");
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(ACTION_FETCH_RECIPES);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            // Check just in case someone else uses this service
            if (ACTION_FETCH_RECIPES.equals(action)) {
                handleActionFetchRecipes();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFetchRecipes() {
        Log.v("IngredientService", "Fetching recipes...");


        // Fetch data
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

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
                mRecipeList = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // line end for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                mRecipeList = null;
            }
            resultJsonStr = buffer.toString();
            mRecipeList = getDataFromJson(resultJsonStr);
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

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        //Trigger data update to handle the  Recycler view widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_baked_good_list);
        IngredientWidgetProvider.updateIngredientWidget(this, appWidgetManager, appWidgetIds, mRecipeList);
    }

    private ArrayList<Recipe> getDataFromJson(String jsonString) throws JSONException {

        ArrayList<Recipe> recipeList = new ArrayList<>();

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
                    Log.v(LOG_TAG, "Ingredients are null");
                    String ingredientString = "No ingredients available";
                    int quantity = 0;
                    String measureString = "0";
                    ingredientArrayList.add(new Ingredient(quantity, measureString, ingredientString));
                } else {
                    for (int j = 0; j < ingredientJsonArray.length(); j++) {
                        String ingredientString = ingredientJsonArray.getJSONObject(j).getString("ingredient");
                        if (ingredientString == null) {
                            ingredientString = "Ingredient is missing for this step";
                        }

                        int quantity = ingredientJsonArray.getJSONObject(j).getInt("quantity");
                        String measureString = ingredientJsonArray.getJSONObject(j).getString("measure");
                        if (measureString == null) {
                            measureString = "Measure not provided";
                        }
                        ingredientArrayList.add(new Ingredient(quantity, measureString, ingredientString));
                    }
                }

                // populate instructions array
                JSONArray instructionsJsonArray = recipe.getJSONArray("steps");
                if (instructionsJsonArray == null) {
                    Log.v(LOG_TAG, "Instructions are null");
                    String shortDescription = "There are no instructions";
                    String videoUrl = "";
                    String regularDescription = "There are no instructions";
                    String thumbnailUrl = "";
                    instructionArrayList.add(new Instruction(shortDescription, regularDescription, videoUrl, thumbnailUrl));
                } else {
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
                }

                recipeList.add(new Recipe(name, servings, ingredientArrayList, instructionArrayList, imageUrl));
            }
        } else {
            Log.v(LOG_TAG, "No data found");
            return null;
        }

        return recipeList;
    }

}
