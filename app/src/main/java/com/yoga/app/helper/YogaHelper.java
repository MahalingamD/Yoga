package com.yoga.app.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

import com.yoga.app.utils.YogaNetworkManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class YogaHelper {

    public static String TAG = YogaHelper.class.getSimpleName();

    public static boolean checkInternet(Context aContext) {
        return YogaNetworkManager.isInternetOnCheck(aContext);
    }

    public static String aDeviceId(Context aContext) {
        TelephonyManager tm = (TelephonyManager) aContext.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
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


}
