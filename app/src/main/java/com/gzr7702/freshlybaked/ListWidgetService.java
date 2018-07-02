package com.gzr7702.freshlybaked;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.gzr7702.freshlybaked.data.Recipe;

import java.util.ArrayList;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        ArrayList<Recipe> recipeList = intent.getParcelableArrayListExtra("recipes");
        return new ListRemoteViewFactory(this.getApplicationContext(), recipeList);
    }

}

class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{
    Context mContext;
    ArrayList<Recipe> mRecipeList;

    public ListRemoteViewFactory(Context context, ArrayList<Recipe> recipes) {
        mContext = context;
        mRecipeList = recipes;

    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        IngredientService.startActionFetchRecipes(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_row);
        Recipe recipe = mRecipeList.get(position);
        views.setTextViewText(R.id.widget_row_text, recipe.getName());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
