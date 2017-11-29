package com.gzr7702.freshlybaked;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.gzr7702.freshlybaked.data.Instruction;
import com.gzr7702.freshlybaked.data.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An activity representing a list of Recipe. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Recipe mRecipe;
    private ArrayList<Instruction> mInstructionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.recipestep_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.recipestep_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Grab the parcelable Recipe
        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra("Recipe");

        // Convert ingredient list into ArrayList
        mInstructionList = new ArrayList<>(Arrays.asList(mRecipe.getInstructionList()));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mInstructionList));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Instruction> mValues;

        public SimpleItemRecyclerViewAdapter(List<Instruction> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipestep_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            final String stepNumber = Integer.toString(position+1);
            holder.mStepView.setText(stepNumber);
            holder.mDescriptionView.setText(mValues.get(position).getShortDescription());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(RecipeStepDetailFragment.ARG_ITEM_ID, stepNumber);
                        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipestep_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, RecipeStepDetailActivity.class);
                        intent.putExtra(RecipeStepDetailFragment.ARG_ITEM_ID, stepNumber);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mStepView;
            public final TextView mDescriptionView;
            public Instruction mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mStepView = (TextView) view.findViewById(R.id.id);
                mDescriptionView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mDescriptionView.getText() + "'";
            }
        }
    }
}
