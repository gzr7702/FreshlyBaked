package com.gzr7702.freshlybaked;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.gzr7702.freshlybaked.IdlingResource.SimpleIdlingResource;
import com.gzr7702.freshlybaked.data.Recipe;

import java.util.List;

/*
    This is the main activity and the list of all available recipes. It utilizes the
    RecipeLoader to fetch data and the MainListAdapter to handle its RecyclerView
 */

public class MainActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<List<Recipe>> {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mEmptyStateTextView;
    private static final int RECIPE_LOADER_ID = 1;

    // Create variable for idling resource for testing
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Check network connection, display empty page if not available
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(RECIPE_LOADER_ID, null, MainActivity.this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.internet_unavailable_message);
        }

        getIdlingResource();
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {

        if(mIdlingResource != null){
            mIdlingResource.setIdleState(true);
        }

        return new RecipeLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mAdapter = new MainListAdapter(data, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
        loader = null;
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
