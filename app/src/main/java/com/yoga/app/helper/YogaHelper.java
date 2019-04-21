package com.yoga.app.helper;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.yoga.app.utils.YogaNetworkManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class YogaHelper {

   public static String TAG = YogaHelper.class.getSimpleName();

   public static boolean checkInternet( Context aContext ) {
      return YogaNetworkManager.isInternetOnCheck( aContext );
   }


}
