package com.gzr7702.freshlybaked;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

/**
 * An activity representing a single Instruction detail screen.
 */

public class InstructionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            String description = getIntent().getStringExtra(InstructionDetailFragment.DESCRIPTION);
            String videoUrl = getIntent().getStringExtra(InstructionDetailFragment.VIDEO_URL);
            arguments.putString(InstructionDetailFragment.DESCRIPTION, description);
            arguments.putString(InstructionDetailFragment.VIDEO_URL, videoUrl);
            Log.v("bundle instr detail: ", arguments.toString());
            InstructionDetailFragment fragment = new InstructionDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.instruction_detail_container, fragment)
                    .commit();
        }
    }
}
