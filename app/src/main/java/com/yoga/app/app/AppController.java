package com.yoga.app.app;


import android.app.Application;

import android.content.ContextWrapper;


import com.yoga.app.utils.Prefs;


public class AppController extends Application {

   public static final String TAG = AppController.class.getSimpleName();
   private static AppController myInstance;

   public static synchronized AppController getInstance() {
      return myInstance;
   }


   @Override
   public void onCreate() {
      super.onCreate();
      myInstance = this;


      // Initialize the Shared Preferences class
     new Prefs.Builder().setContext( this )
              .setMode( ContextWrapper.MODE_PRIVATE )
              .setPrefsName( getPackageName() )
              .setUseDefaultSharedPreference( true ).build();

   }



}