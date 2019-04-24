package com.yoga.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.fragment.LoginFragment;
import com.yoga.app.fragment.SignupFragment;
import com.yoga.app.helper.YogaHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

public class WelcomeActivity extends ActivityManagePermission implements PermissionResult {

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    AppCompatActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        askPermission();

        mContext = this;

        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();

        tabListener();

    }

    private void askPermission() {
        try {
            askCompactPermissions(new String[]{
                    PermissionUtils.Manifest_READ_PHONE_STATE,
                    PermissionUtils.Manifest_READ_EXTERNAL_STORAGE,
                    PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE
            }, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tabListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tab)).setTextSize(16);
                ((TextView) tab.getCustomView().findViewById(R.id.tab)).setTextColor(ContextCompat.getColor(WelcomeActivity.this, R.color.login_red_dark));
                // ( ( TextView ) tab.getCustomView().findViewById( R.id.tab ) ).setTypeface( Typeface.defaultFromStyle( Typeface.BOLD ) );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tab)).setTextSize(16);
                ((TextView) tab.getCustomView().findViewById(R.id.tab)).setTextColor(ContextCompat.getColor(WelcomeActivity.this, R.color.login_disable_text));
                //( ( TextView ) tab.getCustomView().findViewById( R.id.tab ) ).setTypeface( Typeface.defaultFromStyle( Typeface.NORMAL ) );
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager mViewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Login");
        adapter.addFragment(new SignupFragment(), "Sign Up");
        mViewPager.setAdapter(adapter);


    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        TextView aText = tabOne.findViewById(R.id.tab);
        aText.setText(R.string.label_login);
        mTabLayout.getTabAt(0).setCustomView(tabOne);

      /*TextView tabTwo = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
      tabTwo.setText( "Sign Up" );
      mTabLayout.getTabAt( 1 ).setCustomView( tabTwo );*/

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        TextView bText = tabTwo.findViewById(R.id.tab);
        bText.setText(R.string.label_sign_up);
        mTabLayout.getTabAt(1).setCustomView(tabTwo);

        setDefaultTab();


    }

    private void setDefaultTab() {
        TabLayout.Tab currentTab = mTabLayout.getTabAt(0);
        if (currentTab != null) {
            View customView = currentTab.getCustomView();
            if (customView != null) {
                customView.setSelected(true);
                ((TextView) currentTab.getCustomView().findViewById(R.id.tab)).setTextSize(16);
                ((TextView) currentTab.getCustomView().findViewById(R.id.tab)).setTextColor(ContextCompat.getColor(WelcomeActivity.this, R.color.login_red_dark));
            }
            currentTab.select();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (checkPermissionGranted()) {
            askPermission();
        }

    }


    public boolean checkPermissionGranted() {
        boolean isGranted = false;
        try {
            isGranted = isPermissionsGranted(mContext, new String[]{PermissionUtils.Manifest_READ_PHONE_STATE,
                    PermissionUtils.Manifest_READ_EXTERNAL_STORAGE, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return isGranted;
    }


    @Override
    public void permissionGranted() {

    }

    @Override
    public void permissionDenied() {
        showAlertDialog("You need permission to use application");
    }

    @Override
    public void permissionForeverDenied() {
        showSettingAlertDialog("You need permission to use application");
    }


    /**
     * @param aMessage aMessage
     */
    public void showAlertDialog(String aMessage) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(aMessage)
                    .setTitle(mContext.getString(R.string.app_name))
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            askPermission();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            // Change the buttons color in dialog
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param aMessage aMessage
     */
    public void showSettingAlertDialog(String aMessage) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(aMessage)
                    .setTitle(mContext.getString(R.string.app_name))
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            YogaHelper.startInstalledAppDetailsActivity(mContext);
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            // Change the buttons color in dialog
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        try {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
                Log.d("Activity", "ON RESULT CALLED");
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
    }


}
