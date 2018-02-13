package com.gzr7702.freshlybaked.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.gzr7702.freshlybaked.MainActivity;
import com.gzr7702.freshlybaked.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    private static String LOG_TAG = "IngredientWidgetProvider";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.v(LOG_TAG, "updateAppWidget");
        Intent intent = new Intent(context, MainActivity.class);
        //intent.setAction(IngredientService.ACTION_FETCH_RECIPES);
        //PendingIntent recipeServicePendingIntent = PendingIntent.getService(context, 0, intent, 0);
        PendingIntent recipeServicePendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);

        views.setOnClickPendingIntent(R.id.widget_cake_image, recipeServicePendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.v(LOG_TAG, "onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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

