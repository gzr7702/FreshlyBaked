package com.gzr7702.freshlybaked;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzr7702.freshlybaked.dummy.DummyContent;

/**
 * Created by rob on 10/12/17.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView recipeText;
        public ImageView recipeImage;

        public ViewHolder(CardView view) {
            super(view);
            recipeText = (TextView) view.findViewById(R.id.recipe_row_text);
            recipeImage = (ImageView) view.findViewById(R.id.recipe_row_iamge);
        }
    }

    public MainListAdapter(String[] myDataset) {
        mDataset = myDataset;
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
    public void onBindViewHolder(MainListAdapter.ViewHolder holder, int position) {
        holder.recipeText.setText(mDataset[position]);
        holder.recipeImage.setImageResource(R.drawable.cake);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

