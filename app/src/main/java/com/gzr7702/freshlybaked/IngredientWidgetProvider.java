package com.gzr7702.freshlybaked;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.gzr7702.freshlybaked.data.Recipe;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    private static String LOG_TAG = "IngredientWidgetProvider";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, ArrayList<Recipe> recipeList) {

        Log.v(LOG_TAG, "updateAppWidget");

        // Set up intent to open widget
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);

        // set up pending intent that will start service
        Intent serviceIntent = new Intent(context, IngredientService.class);
        serviceIntent.setAction(IngredientService.ACTION_FETCH_RECIPES);
        PendingIntent recipeServicePendingIntent = PendingIntent.getService(context, 0, serviceIntent, 0);
        views.setPendingIntentTemplate(R.id.widget_baked_good_list, recipeServicePendingIntent);
        views.setEmptyView(R.id.widget_baked_good_list, R.id.empty_view);

        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putParcelableArrayListExtra("recipes", recipeList);
        views.setRemoteAdapter(R.id.widget_baked_good_list, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateIngredientWidget(Context context, AppWidgetManager appWidgetManager,
                                              int[] appWidgetIds, ArrayList<Recipe> recipeList) {
        Log.v(LOG_TAG, "updateIngredientWidget");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeList);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.v(LOG_TAG, "onUpdate");
        IngredientService.startActionFetchRecipes(context);
    }

    @Override
    public void onEnabled(Context context) {
        Log.v(LOG_TAG, "onEnabled");
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        Log.v(LOG_TAG, "onDisabled");
        // Enter relevant functionality for when the last widget is disabled
    }
}

