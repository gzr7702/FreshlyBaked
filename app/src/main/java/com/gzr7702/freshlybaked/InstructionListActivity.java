package com.gzr7702.freshlybaked;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.gzr7702.freshlybaked.data.Instruction;
import com.gzr7702.freshlybaked.data.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity that contains a list of Instructions for one particular recipe. It contains its own
 * RecyclerView
 */

public class InstructionListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private Recipe mRecipe;
    private ArrayList<Instruction> mInstructionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_list);

        // Grab the parcelable Recipe
        Bundle data = getIntent().getExtras();
        mRecipe = data.getParcelable("Recipe");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mRecipe.getName());


        Log.v("InstructionListActivity", "recipe: " + mRecipe);

        mInstructionList = mRecipe.getInstructionList();

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
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mInstructionList));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Instruction> mValues;

        public SimpleItemRecyclerViewAdapter(List<Instruction> items) {
            Log.v("InstructionListActivity", "In simpleitmeRecyclerViewAdapter");
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.instruction_row, parent, false);
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
                        arguments.putString(InstructionDetailFragment.ARG_ITEM_ID, stepNumber);
                        InstructionDetailFragment fragment = new InstructionDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipestep_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, InstructionDetailActivity.class);
                        intent.putExtra(InstructionDetailFragment.ARG_ITEM_ID, stepNumber);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            Log.v("InstructionListActivity", "In getItemCount");
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
                mStepView = (TextView) view.findViewById(R.id.step_number_text);
                mDescriptionView = (TextView) view.findViewById(R.id.instruction_text);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mDescriptionView.getText() + "'";
            }
        }
    }
}
