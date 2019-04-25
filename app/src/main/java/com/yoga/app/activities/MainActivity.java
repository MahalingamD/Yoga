package com.yoga.app.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.yoga.app.R;
import com.yoga.app.base.APPFragmentManager;
import com.yoga.app.fragment.DashboardFragment;
import com.yoga.app.fragment.MoreFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView mNavigation;
    APPFragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = new APPFragmentManager(this);

        mNavigation = findViewById(R.id.navigationView);
        mNavigation.setOnNavigationItemSelectedListener(this);
        DefaultFragment();

    }

    private void DefaultFragment() {
        mFragmentManager.clearAllFragments();
        mFragmentManager.updateContent(new DashboardFragment(), "More Fragment", null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        mFragmentManager.clearAllFragments();
        switch (item.getItemId()) {

            case R.id.navigation_home:
                mFragmentManager.updateContent(new DashboardFragment(), "More Fragment", null);
                break;

            case R.id.navigation_course:
                mFragmentManager.updateContent(new MoreFragment(), "More Fragment", null);
                break;

            case R.id.navigation_settings:
                mFragmentManager.updateContent(new MoreFragment(), "More Fragment", null);
                break;

            case R.id.navigation_more:
                mFragmentManager.updateContent(new MoreFragment(), "More Fragment", null);
                break;
        }

        return true;
    }
}
