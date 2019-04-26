package com.yoga.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.adapter.ViewpagerAdapter;
import com.yoga.app.utils.viewpager.AutoScrollViewPager;
import com.yoga.app.videoplayer.VideoPlayerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    AutoScrollViewPager viewPager;

    Integer[] imageId = {R.drawable.banner, R.drawable.yoga_benefit, R.drawable.yoga_men, R.drawable.yog_women};
    String[] imagesName = {"image1", "image2", "image3", "image4"};

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

        viewPager = mView.findViewById(R.id.auto_scroll_viewPager);
        viewPager.startAutoScroll();
        viewPager.setInterval(3000);
        viewPager.setCycle(true);
        viewPager.setStopScrollWhenTouch(true);

        PagerAdapter adapter = new ViewpagerAdapter(getActivity(), imageId, imagesName);
        viewPager.setAdapter(adapter);

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
