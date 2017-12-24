package com.gzr7702.freshlybaked;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzr7702.freshlybaked.data.Ingredient;
import com.gzr7702.freshlybaked.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rob on 10/12/17.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private List<Recipe> mRecipeList;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View recipeView;
        public TextView recipeText;
        public TextView servingsText;
        public ImageView recipeImage;

        public ViewHolder(CardView view) {
            super(view);
            recipeView = view;
            recipeText = view.findViewById(R.id.recipe_name_text);
            servingsText = view.findViewById(R.id.serving_row_text);
            recipeImage = view.findViewById(R.id.recipe_row_image);
        }
    }

    public MainListAdapter(List<Recipe> recipeList, Context context) {
        mRecipeList = recipeList;
        mContext = context;
    }

    @Override
    public MainListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_row, parent, false);

        ViewHolder vh = new ViewHolder(cv);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MainListAdapter.ViewHolder holder, final int position) {
        String recipeName = mRecipeList.get(position).getName();
        holder.recipeText.setText(recipeName);

        String servings = mRecipeList.get(position).getServings();
        String servingString = "Serves " + servings + " people";
        holder.servingsText.setText(servingString);

        String imageUrl = mRecipeList.get(position).getImageUrl();

        // So we don't send and empty URL to picasso
        if (imageUrl.isEmpty()) {
            imageUrl = null;
        }

        Picasso.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.cake)
                .into(holder.recipeImage);


        holder.recipeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecipeActivity.class);
                Log.v("MainListAdapter", mRecipeList.get(position).getIngredientList().toString());

                intent.putExtra("Recipe", mRecipeList.get(position));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}

