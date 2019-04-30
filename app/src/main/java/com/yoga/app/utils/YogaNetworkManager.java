//---------------------------------------------------------------------------
//
// file. TPINetworkManager.java
//
// copyright.
// Sundharam Motors.
//
// authors.
// Jayaprakash S
//
// note.
// The file contains the implementation of network checking process.
//
// description.
//  * The purpose of this class is to check whether the network available r not. 
//  
//---------------------------------------------------------------------------

package com.yoga.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class YogaNetworkManager {

    public static boolean isInternetOnCheck(Context aContext) {

        boolean aResult = false;

        ConnectivityManager aConnecMan = (ConnectivityManager) aContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        //
        if ((aConnecMan.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)
                || (aConnecMan.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING)
                || (aConnecMan.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING)
                || (aConnecMan.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)) {

            aResult = true;

        } else if ((aConnecMan.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED)
                || (aConnecMan.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED)) {

            aResult = false;
        }

        return aResult;
    }

    /**
     * Check the internet connection and return true or false
     *
     * @param aContext Context
     * @return boolean
     */

    public final boolean isInternetOn(Context aContext) {
        try {

            try {

                if (aContext == null)
                    return false;

                ConnectivityManager connectivityManager = (ConnectivityManager) aContext
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}