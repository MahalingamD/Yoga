package com.yoga.app.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.activities.WelcomeActivity;
import com.yoga.app.adapter.MorePageAdapter;
import com.yoga.app.base.APPFragmentManager;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.model.Profile;
import com.yoga.app.model.Response;
import com.yoga.app.service.RetrofitInstance;
import com.yoga.app.utils.Prefs;
import com.yoga.app.utils.ProgressDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import static com.yoga.app.constant.PrefConstants.ACCESS_TOKEN;
import static com.yoga.app.helper.YogaHelper.showAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

   View mView;
   private RecyclerView mMenuRecyclerView;
   private MorePageAdapter mMoreMenuListAdapter;
   private ArrayList<String> mMenuArrayList;
   APPFragmentManager mFragmentManager;

   private RetrofitInstance myRetrofitInstance;
   ProgressDialog aProgressDialog;
   FragmentActivity mContext;

   Profile mData;

   CircleImageView profile_image;
   TextView mProfile_name;


   @Override
   public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
      // Inflate the layout for this fragment
      mView = inflater.inflate( R.layout.fragment_more, container, false );

      init( mView );

      return mView;
   }

   private void init( View mView ) {

      mContext = getActivity();

      profile_image = mView.findViewById( R.id.profile_image );
      mProfile_name = mView.findViewById( R.id.more_profile_name );

      if( this.myRetrofitInstance == null ) {
         myRetrofitInstance = new RetrofitInstance();
      }

      mMenuRecyclerView = mView.findViewById( R.id.menu_RecyclerView );

      mFragmentManager = new APPFragmentManager( getActivity() );
      setRecyclerValue();

      callProfile();

      ( ( MainActivity ) mContext ).hideToolbar();
      ( ( MainActivity ) mContext ).showBottomToolbar();

   }


   private void setRecyclerValue() {
      try {
         mMenuArrayList = new ArrayList<>();

         String[] aMenuTitles = mContext.getResources().getStringArray( R.array.menu_items );
         mMenuArrayList.addAll( Arrays.asList( aMenuTitles ) );

         mMoreMenuListAdapter = new MorePageAdapter( mContext, mMenuArrayList, mCallback );
         mMenuRecyclerView.setHasFixedSize( true );
         RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( mContext );
         mMenuRecyclerView.setLayoutManager( mLayoutManager );
         mMenuRecyclerView.setAdapter( mMoreMenuListAdapter );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   @Override
   public void onResume() {
      super.onResume();
      ( ( MainActivity ) mContext ).hideToolbar();
      ( ( MainActivity ) mContext ).showBottomToolbar();
      setValues();
   }


   MorePageAdapter.Callback mCallback = new MorePageAdapter.Callback() {
      @Override
      public void click( int aPosition, String s ) {
         Bundle aBundle = new Bundle();

         switch( aPosition ) {
            case 0: {
               mFragmentManager.updateContent( new ProfileFragment(), "ProfileFragment", aBundle );
               break;
            }
            case 1: {
               mFragmentManager.updateContent( new WalletFragment(), "WalletFragment", aBundle );
               break;
            }
            case 3: {
               mFragmentManager.updateContent( new InviteFriendFragment(), "InviteFriendFragment", aBundle );
               break;
            }
            case 5: {
               aBundle.putString( "page_value", "about" );
               mFragmentManager.updateContent( new CommonFragment(), "CommonFragment", aBundle );
               break;
            }
            case 6: {
               showFeedbackAlertDialog();
               break;
            }
            case 7: {
               aBundle.putString( "page_value", "faq" );
               mFragmentManager.updateContent( new CommonFragment(), "CommonFragment", aBundle );
               break;
            }
            case 8: {
               logoutAlertDialog();
               break;
            }
            case 20: {
               aBundle.putString( "page_value", "terms" );
               mFragmentManager.updateContent( new CommonFragment(), "CommonFragment", aBundle );
               break;
            }
            case 21: {
               aBundle.putString( "page_value", "policy" );
               mFragmentManager.updateContent( new CommonFragment(), "CommonFragment", aBundle );
               break;
            }
         }
      }
   };

   private void logoutAlertDialog() {
      AlertDialog alertDialog = new AlertDialog.Builder( mContext ).create();
      alertDialog.setTitle( mContext.getString( R.string.app_name ) );
      alertDialog.setIcon( R.mipmap.ic_launcher );
      alertDialog.setCancelable( false );
      alertDialog.setMessage( "Are you sure you want to logout?" );
      alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
         @Override
         public void onClick( DialogInterface dialog, int which ) {

            Prefs.clear().apply();
            Intent intent = new Intent( getActivity(), WelcomeActivity.class );
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
            mContext.finish();

         }
      } );

      alertDialog.setButton( AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
         @Override
         public void onClick( DialogInterface dialog, int which ) {
            dialog.dismiss();
         }
      } );

      alertDialog.show();
   }

   private void showFeedbackAlertDialog() {
      LayoutInflater inflater = LayoutInflater.from( getActivity() );
      final View view = inflater.inflate( R.layout.feedback_layout, null );

      AlertDialog alertDialog = new AlertDialog.Builder( mContext ).create();
      alertDialog.setTitle( mContext.getString( R.string.app_name ) );
      alertDialog.setIcon( R.mipmap.ic_launcher );
      alertDialog.setCancelable( false );
      alertDialog.setMessage( "Enter your feedback about this application" );

      final EditText etComments = view.findViewById( R.id.etComments );

      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
      params.leftMargin = convertPixelsToDp( 16, getActivity() );
      params.rightMargin = convertPixelsToDp( 16, getActivity() );
      etComments.setLayoutParams( params );

      alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
         @Override
         public void onClick( DialogInterface dialog, int which ) {
            if( etComments.getText().toString().trim().length() > 0 )
               callFeedback( etComments.getText().toString() );
            else {
               dialog.dismiss();
            }
         }
      } );

      alertDialog.setButton( AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
         @Override
         public void onClick( DialogInterface dialog, int which ) {
            dialog.dismiss();
         }
      } );


      alertDialog.setView( view );
      alertDialog.show();

      // Change the buttons color in dialog
      Button pbutton = alertDialog.getButton( DialogInterface.BUTTON_POSITIVE );
      pbutton.setTextColor( ContextCompat.getColor( mContext, R.color.black ) );

      Button nbutton = alertDialog.getButton( DialogInterface.BUTTON_NEGATIVE );
      nbutton.setTextColor( ContextCompat.getColor( mContext, R.color.black ) );
   }

   public int convertPixelsToDp( float px, Context context ) {
      Resources resources = context.getResources();
      DisplayMetrics metrics = resources.getDisplayMetrics();
      int dp = ( int ) ( px / ( metrics.densityDpi / 160f ) );
      return dp;
   }

   private void callFeedback( String params ) {
      aProgressDialog = new ProgressDialog( getActivity() );
      aProgressDialog.show();

      Map<String, String> aHeaderMap = new HashMap<>();
      aHeaderMap.put( "X-Device", YogaHelper.aDeviceId( mContext ) );
      aHeaderMap.put( "X-Localization", "en" );
      aHeaderMap.put( "Authorization", "Bearer " + Prefs.getString( ACCESS_TOKEN, "" ) );


      myRetrofitInstance.getAPI().putFeedbackDetails( aHeaderMap, params ).enqueue( new Callback<Response>() {
         @Override
         public void onResponse( @NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response ) {
            aProgressDialog.dismiss();
            Response data = response.body();
            if( data != null ) {
               if( data.getSuccess() == 1 ) {
                  YogaHelper.showAlertDialog( getActivity(), data.getMessage() );
               } else {
                  YogaHelper.showAlertDialog( getActivity(), data.getError() );
               }
            } else {
               YogaHelper.showAlertDialog( getActivity(), "Something went wrong" );
            }
         }

         @Override
         public void onFailure( Call<Response> call, Throwable t ) {
            aProgressDialog.dismiss();
            YogaHelper.showAlertDialog( getActivity(), "Something went wrong" );
         }
      } );
   }


   private void callProfile() {
      aProgressDialog = new ProgressDialog( getActivity() );
      aProgressDialog.show();

      Map<String, String> aHeaderMap = new HashMap<>();
      aHeaderMap.put( "X-Device", YogaHelper.aDeviceId( mContext ) );
      aHeaderMap.put( "Authorization", "Bearer " + Prefs.getString( ACCESS_TOKEN, "" ) );

      myRetrofitInstance.getAPI().getProfileAPI( aHeaderMap ).enqueue( new Callback<Profile>() {
         @Override
         public void onResponse( @NotNull Call<Profile> call,
                                 @NotNull retrofit2.Response<Profile> response ) {
            aProgressDialog.dismiss();
            if( response.isSuccessful() ) {
               mData = response.body();
               if( mData != null ) {
                  if( mData.success == 1 ) {

                     setValues( mData );

                  } else {
                     showAlertDialog( getActivity(), mData.error );
                  }
               } else {
                  showAlertDialog( getActivity(), "Something went wrong" );
               }
            }
         }

         @Override
         public void onFailure( Call<Profile> call, Throwable t ) {
            aProgressDialog.dismiss();
            showAlertDialog( getActivity(), "Something went wrong" );

         }
      } );
   }

   private void setValues( Profile mData ) {

      if( mData != null ) {
         if( mData.data.account_photo != null && !mData.data.account_photo.isEmpty() ) {
            Picasso.with( mContext ).load( mData.data.account_photo ).
                    placeholder( R.drawable.ic_user ).fit().into( profile_image );
         }

         mProfile_name.setText( mData.data.account_name );
      }

   }


   private void setValues() {

      if( Prefs.getString( "Profile_test", "" ).equals( "1" ) ) {

         String aPimage = Prefs.getString( "Cache_image", "" );
         if( !aPimage.isEmpty() ) {
            Picasso.with( mContext ).load( aPimage ).
                    placeholder( R.drawable.ic_user ).fit().into( profile_image );
         }
      }
   }


}
