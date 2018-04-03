package com.gzr7702.freshlybaked.widget;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.gzr7702.freshlybaked.RecipeLoader;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */

public class IngredientService extends IntentService {
    public static final String ACTION_FETCH_RECIPES = "com.gzr7702.freshlybaked.widget.action.FOO";


    public IngredientService() {
        super("IngredientService");
        Log.v("IngredientService", "Starting service...");
    }

    /**
     * Starts this service to perform action ACTION_FETCH_RECIPES with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFetchRecipes(Context context) {
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
        // TODO: use RecipeLoader to get recipes
        Log.v("IngredientService", "Fetching recipes...");
        RecipeLoader rl = new RecipeLoader(this);
        startActionFetchRecipes(this);
    }

}
