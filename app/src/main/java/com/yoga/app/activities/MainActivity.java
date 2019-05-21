package com.yoga.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yoga.app.R;
import com.yoga.app.base.APPFragmentManager;
import com.yoga.app.fragment.CoursesListFragment;
import com.yoga.app.fragment.DashboardFragment;
import com.yoga.app.fragment.MoreFragment;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.model.DashProfile;

import de.hdodenhof.circleimageview.CircleImageView;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

public class MainActivity extends ActivityManagePermission implements BottomNavigationView.OnNavigationItemSelectedListener, PermissionResult {

   BottomNavigationView mNavigation;
   APPFragmentManager mFragmentManager;

   Toolbar mToolbar;

   private static long myBackPressed;

   CircleImageView mCircleImageView;


   @Override
   protected void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_main );

      askPermission();

      mToolbar = findViewById( R.id.toolbar );
      mCircleImageView = findViewById( R.id.main_profile );

      mFragmentManager = new APPFragmentManager( this );

      mNavigation = findViewById( R.id.navigationView );
      mNavigation.setOnNavigationItemSelectedListener( this );
      DefaultFragment();

   }

   public void showToolbar() {
      mToolbar.setVisibility( View.VISIBLE );
   }

   public void hideToolbar() {
      mToolbar.setVisibility( View.GONE );
   }

   public void showBottomToolbar() {
      mNavigation.setVisibility( View.VISIBLE );
   }

   public void hideBottomToolbar() {
      mNavigation.setVisibility( View.GONE );
   }

   public void setProfileImage( DashProfile aProfile ) {
      if( aProfile != null )
         if( aProfile.account_photo != null && !aProfile.account_photo.isEmpty() )
            Picasso.with( this ).load( aProfile.account_photo ).placeholder( R.drawable.ic_user ).fit().into( mCircleImageView );
   }


   private void DefaultFragment() {
      mFragmentManager.clearAllFragments();
      mFragmentManager.updateContent( new DashboardFragment(), "Dashboard Fragment", null );
   }

   @Override
   public boolean onNavigationItemSelected( @NonNull MenuItem item ) {

      mFragmentManager.clearAllFragments();
      switch( item.getItemId() ) {

         case R.id.navigation_home:
            mFragmentManager.updateContent( new DashboardFragment(), "Dashboard Fragment", null );
            break;

         case R.id.navigation_course:
            mFragmentManager.updateContent( new CoursesListFragment(), "Course Fragment", null );
            //startActivity(new Intent(MainActivity.this, CourseActivity.class));
            break;

         case R.id.navigation_settings:
            mFragmentManager.updateContent( new MoreFragment(), "Settings Fragment", null );
            break;

         case R.id.navigation_more:
            mFragmentManager.updateContent( new MoreFragment(), "More Fragment", null );
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
      if( aCount == 1 ) {
         if( !( getCurrentFragment() instanceof DashboardFragment ) ) {
            mNavigation.getMenu().getItem( 0 ).setChecked( true );
            mFragmentManager.clearAllFragments();
            mFragmentManager.updateContent( new DashboardFragment(), "Dashboard Fragment", null );
         } else {
            exitApp();
         }
      } else
         mFragmentManager.onBackPress();

   }


   private Fragment getCurrentFragment() {
      FragmentManager aFragmentManager = getSupportFragmentManager();
      String aFragmentTag = aFragmentManager.getBackStackEntryAt( aFragmentManager.getBackStackEntryCount() - 1 ).getName();
      return getSupportFragmentManager().findFragmentByTag( aFragmentTag );
   }

   public void exitApp() {
      try {
         if( myBackPressed + 2000 > System.currentTimeMillis() ) {

            Intent aIntent = new Intent( Intent.ACTION_MAIN );
            aIntent.addCategory( Intent.CATEGORY_HOME );
            aIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( aIntent );
            finish();
         } else {
            Toast.makeText( getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT ).show();
            myBackPressed = System.currentTimeMillis();
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
      Fragment fragment = getSupportFragmentManager().findFragmentById( R.id.main_container );
      fragment.onActivityResult( requestCode, resultCode, data );
   }

   private void askPermission() {
      try {
         askCompactPermissions( new String[]{
                 PermissionUtils.Manifest_READ_PHONE_STATE,
                 PermissionUtils.Manifest_READ_EXTERNAL_STORAGE,
                 PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE
         }, this );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public boolean checkPermissionGranted() {
      boolean isGranted = false;
      try {
         isGranted = isPermissionsGranted( this, new String[]{ PermissionUtils.Manifest_READ_PHONE_STATE,
                 PermissionUtils.Manifest_READ_EXTERNAL_STORAGE, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE } );
      } catch( Exception e ) {
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
      showAlertDialog( "You need permission to use application" );
   }

   @Override
   public void permissionForeverDenied() {
      showSettingAlertDialog( "You need permission to use application" );
   }

   /**
    * @param aMessage aMessage
    */
   public void showAlertDialog( String aMessage ) {
      try {
         AlertDialog.Builder builder = new AlertDialog.Builder( this );
         builder.setMessage( aMessage )
                 .setTitle( this.getString( R.string.app_name ) )
                 .setCancelable( false )
                 .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                       askPermission();
                    }
                 } );

         AlertDialog alert = builder.create();
         alert.show();
         // Change the buttons color in dialog
         Button pbutton = alert.getButton( DialogInterface.BUTTON_POSITIVE );
         pbutton.setTextColor( ContextCompat.getColor( this, R.color.black ) );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   /**
    * @param aMessage aMessage
    */
   public void showSettingAlertDialog( String aMessage ) {
      try {
         AlertDialog.Builder builder = new AlertDialog.Builder( this );
         builder.setMessage( aMessage )
                 .setTitle( this.getString( R.string.app_name ) )
                 .setCancelable( false )
                 .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                       YogaHelper.startInstalledAppDetailsActivity( MainActivity.this );
                    }
                 } );

         AlertDialog alert = builder.create();
         alert.show();
         // Change the buttons color in dialog
         Button pbutton = alert.getButton( DialogInterface.BUTTON_POSITIVE );
         pbutton.setTextColor( ContextCompat.getColor( this, R.color.black ) );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   protected void onResume() {
      super.onResume();
      if( checkPermissionGranted() ) {
         askPermission();
      }
   }
}
