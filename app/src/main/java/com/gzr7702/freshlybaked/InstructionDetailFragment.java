package com.gzr7702.freshlybaked;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

/**
 * Fragment that lives within the InstructionDetailActivity.
 */

public class InstructionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String DESCRIPTION = "LONG_DESCRIPTION";
    public static final String VIDEO_URL = "VIDEO_URL";
    private String mDescription;
    private String mVideoUrl;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InstructionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDescription = getArguments().getString(DESCRIPTION);
        mVideoUrl = getArguments().getString(VIDEO_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instruction_detail, container, false);

        ((TextView) rootView.findViewById(R.id.instruction_detail)).setText(mDescription);

        return rootView;
    }
}
