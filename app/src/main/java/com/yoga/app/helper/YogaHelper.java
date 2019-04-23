package com.yoga.app.helper;

import android.content.Context;
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

}
