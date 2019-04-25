package com.yoga.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.videoplayer.VideoPlayerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }

    View mView;
    ImageView mYogaHealth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        init(mView);
        return mView;
    }

    private void init(View mView) {

        mYogaHealth = mView.findViewById(R.id.yoga_health_image);

        mYogaHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aIntent = new Intent(getActivity(), VideoPlayerActivity.class);
                startActivity(aIntent);
            }
        });

        ((MainActivity) getActivity()).showToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).showToolbar();
    }
}
