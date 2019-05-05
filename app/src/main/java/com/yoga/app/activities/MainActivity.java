package com.yoga.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yoga.app.R;
import com.yoga.app.base.APPFragmentManager;
import com.yoga.app.fragment.CoursesListFragment;
import com.yoga.app.fragment.DashboardFragment;
import com.yoga.app.fragment.MoreFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView mNavigation;
    APPFragmentManager mFragmentManager;

    Toolbar mToolbar;

    private static long myBackPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);

        mFragmentManager = new APPFragmentManager(this);

        mNavigation = findViewById(R.id.navigationView);
        mNavigation.setOnNavigationItemSelectedListener(this);
        DefaultFragment();

    }

    public void showToolbar() {
        mToolbar.setVisibility(View.VISIBLE);
    }

    public void hideToolbar() {
        mToolbar.setVisibility(View.GONE);
    }

    public void showBottomToolbar() {
        mNavigation.setVisibility(View.VISIBLE);
    }

    public void hideBottomToolbar() {
        mNavigation.setVisibility(View.GONE);
    }



    private void DefaultFragment() {
        mFragmentManager.clearAllFragments();
        mFragmentManager.updateContent(new DashboardFragment(), "Dashboard Fragment", null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        mFragmentManager.clearAllFragments();
        switch (item.getItemId()) {

            case R.id.navigation_home:
                mFragmentManager.updateContent(new DashboardFragment(), "Dashboard Fragment", null);
                break;

            case R.id.navigation_course:
                mFragmentManager.updateContent(new CoursesListFragment(), "Course Fragment", null);
                //startActivity(new Intent(MainActivity.this, CourseActivity.class));
                break;

            case R.id.navigation_settings:
                mFragmentManager.updateContent(new MoreFragment(), "Settings Fragment", null);
                break;

            case R.id.navigation_more:
                mFragmentManager.updateContent(new MoreFragment(), "More Fragment", null);
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
       /* if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else if (mNavigation.getSelectedItemId() == R.id.navigation_home) {
            finish();
        } else {
            mFragmentManager.updateContent(new DashboardFragment(), "Dashboard Fragment", null);
        }*/

        int aCount = mFragmentManager.getBackstackCount();
        if (aCount == 1) {
            if (!(getCurrentFragment() instanceof DashboardFragment)) {
                mNavigation.getMenu().getItem(0).setChecked(true);
                mFragmentManager.clearAllFragments();
                mFragmentManager.updateContent(new DashboardFragment(), "Dashboard Fragment", null);
            } else {
                exitApp();
            }
        } else
            mFragmentManager.onBackPress();

    }


    private Fragment getCurrentFragment() {
        FragmentManager aFragmentManager = getSupportFragmentManager();
        String aFragmentTag = aFragmentManager.getBackStackEntryAt(aFragmentManager.getBackStackEntryCount() - 1).getName();
        return getSupportFragmentManager().findFragmentByTag(aFragmentTag);
    }

    public void exitApp() {
        try {
            if (myBackPressed + 2000 > System.currentTimeMillis()) {

                Intent aIntent = new Intent(Intent.ACTION_MAIN);
                aIntent.addCategory(Intent.CATEGORY_HOME);
                aIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(aIntent);
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
                myBackPressed = System.currentTimeMillis();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
