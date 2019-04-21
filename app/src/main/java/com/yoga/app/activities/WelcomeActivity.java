package com.yoga.app.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.fragment.LoginFragment;
import com.yoga.app.fragment.SignupFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

   @BindView(R.id.tabs)
   TabLayout mTabLayout;

   @BindView(R.id.viewpager)
   ViewPager mViewPager;

   @Override
   protected void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_welcome );
      ButterKnife.bind( this );

      setupViewPager( mViewPager );
      mTabLayout.setupWithViewPager( mViewPager );
      setupTabIcons();

      tabListener();


   }

   private void tabListener() {
      mTabLayout.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
         @Override
         public void onTabSelected( TabLayout.Tab tab ) {
            ( ( TextView ) tab.getCustomView().findViewById( R.id.tab ) ).setTextSize( 16 );
            ( ( TextView ) tab.getCustomView().findViewById( R.id.tab ) ).setTextColor( ContextCompat.getColor( WelcomeActivity.this, R.color.login_red_dark ) );
            // ( ( TextView ) tab.getCustomView().findViewById( R.id.tab ) ).setTypeface( Typeface.defaultFromStyle( Typeface.BOLD ) );
         }

         @Override
         public void onTabUnselected( TabLayout.Tab tab ) {
            ( ( TextView ) tab.getCustomView().findViewById( R.id.tab ) ).setTextSize( 16 );
            ( ( TextView ) tab.getCustomView().findViewById( R.id.tab ) ).setTextColor( ContextCompat.getColor( WelcomeActivity.this, R.color.login_disable_text ) );
            //( ( TextView ) tab.getCustomView().findViewById( R.id.tab ) ).setTypeface( Typeface.defaultFromStyle( Typeface.NORMAL ) );
         }

         @Override
         public void onTabReselected( TabLayout.Tab tab ) {

         }
      } );
   }

   private void setupViewPager( ViewPager mViewPager ) {

      ViewPagerAdapter adapter = new ViewPagerAdapter( getSupportFragmentManager() );
      adapter.addFragment( new LoginFragment(), "Login" );
      adapter.addFragment( new SignupFragment(), "Sign Up" );
      mViewPager.setAdapter( adapter );


   }

   /**
    * Adding custom view to tab
    */
   private void setupTabIcons() {

      LinearLayout tabOne = ( LinearLayout ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
      TextView aText = tabOne.findViewById( R.id.tab );
      aText.setText(R.string.label_login);
      mTabLayout.getTabAt( 0 ).setCustomView( tabOne );

      /*TextView tabTwo = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
      tabTwo.setText( "Sign Up" );
      mTabLayout.getTabAt( 1 ).setCustomView( tabTwo );*/

      LinearLayout tabTwo = ( LinearLayout ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
      TextView bText = tabTwo.findViewById( R.id.tab );
      bText.setText( R.string.label_sign_up );
      mTabLayout.getTabAt( 1 ).setCustomView( tabTwo );

      setDefaultTab();


   }

   private void setDefaultTab() {
      TabLayout.Tab currentTab = mTabLayout.getTabAt( 0 );
      if( currentTab != null ) {
         View customView = currentTab.getCustomView();
         if( customView != null ) {
            customView.setSelected( true );
            ( ( TextView ) currentTab.getCustomView().findViewById( R.id.tab ) ).setTextSize( 16 );
            ( ( TextView ) currentTab.getCustomView().findViewById( R.id.tab ) ).setTextColor( ContextCompat.getColor( WelcomeActivity.this, R.color.login_red_dark ) );
         }
         currentTab.select();
      }
   }


   class ViewPagerAdapter extends FragmentPagerAdapter {
      private final List<Fragment> mFragmentList = new ArrayList<>();
      private final List<String> mFragmentTitleList = new ArrayList<>();

      public ViewPagerAdapter( FragmentManager manager ) {
         super( manager );
      }

      @Override
      public Fragment getItem( int position ) {
         return mFragmentList.get( position );
      }

      @Override
      public int getCount() {
         return mFragmentList.size();
      }

      public void addFragment( Fragment fragment, String title ) {
         mFragmentList.add( fragment );
         mFragmentTitleList.add( title );
      }

      @Override
      public CharSequence getPageTitle( int position ) {
         return mFragmentTitleList.get( position );
      }
   }
}