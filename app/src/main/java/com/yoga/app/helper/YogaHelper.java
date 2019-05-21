package com.yoga.app.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.widget.Button;

import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.utils.YogaNetworkManager;

public class YogaHelper {

   public static String TAG = YogaHelper.class.getSimpleName();

   public static boolean checkInternet( Context aContext ) {
      return YogaNetworkManager.isInternetOnCheck( aContext );
   }

   public static String aDeviceId( Context aContext ) {
      TelephonyManager tm = ( TelephonyManager ) aContext.getSystemService( Context.TELEPHONY_SERVICE );
      return tm.getDeviceId();
   }

   public final static boolean isValidEmail( CharSequence target ) {
      if( target == null )
         return false;

      return android.util.Patterns.EMAIL_ADDRESS.matcher( target ).matches();
   }

   public static void startInstalledAppDetailsActivity( final Activity context ) {
      if( context == null ) {
         return;
      }
      final Intent i = new Intent();
      i.setAction( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
      i.addCategory( Intent.CATEGORY_DEFAULT );
      i.setData( Uri.parse( "package:" + context.getPackageName() ) );
      i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
      i.addFlags( Intent.FLAG_ACTIVITY_NO_HISTORY );
      i.addFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS );
      context.startActivity( i );
   }


   public static void showAlertDialog( Context mContext, String aMessage ) {
      AlertDialog alertDialog = new AlertDialog.Builder( mContext ).create();
      alertDialog.setTitle( mContext.getString( R.string.app_name ) );
      alertDialog.setIcon( R.mipmap.ic_launcher );
      alertDialog.setCancelable( false );
      alertDialog.setMessage( aMessage );
      alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
         @Override
         public void onClick( DialogInterface dialog, int which ) {

            dialog.dismiss();
         }
      } );

      alertDialog.show();
   }

   /**
    * Px to DP
    *
    * @param context Context
    * @param dp      float
    */
   public static int pxFromDp( final Context context, final float dp ) {
      return ( int ) ( dp * context.getResources().getDisplayMetrics().density );
   }




}
