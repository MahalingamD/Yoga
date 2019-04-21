package com.yoga.app.utils;

import android.util.Log;

public class KLog {

    private static boolean isDebug = true;

    public static void debug(String tag, String value){
        if(isDebug){
            Log.d(tag, value);
        }
    }

    public static void error(String tag, String value){
        if(isDebug){
            Log.e(tag, value);
        }
    }

    public static void info(String tag, String value){
        if(isDebug){
            Log.i(tag, value);
        }
    }

    public static void warning(String tag, String value){
        if(isDebug){
            Log.w(tag, value);
        }
    }

}
