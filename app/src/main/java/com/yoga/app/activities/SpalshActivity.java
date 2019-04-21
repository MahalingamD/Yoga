package com.yoga.app.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yoga.app.BuildConfig;
import com.yoga.app.R;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.utils.Prefs;

public class SpalshActivity extends AppCompatActivity {

   int DELAY_TIME_FOR_SPLASH_SCREEN = 3000;

   @Override
   protected void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_spalsh );

      viewSplashCount();
   }


   public void viewSplashCount() {
      Handler aHoldScreen = new Handler();
      aHoldScreen.postDelayed( new Runnable() {
         public void run() {
            callNextActivity();
         }
      }, DELAY_TIME_FOR_SPLASH_SCREEN );
   }

   private void callNextActivity() {
      boolean aBool = Prefs.getBoolean( "Login_status", false );
      Intent aIntent;

      if( aBool )
         aIntent = new Intent( this, MainActivity.class );
      else
         aIntent = new Intent( this, WelcomeActivity.class );

      startActivity( aIntent );
      this.finish();

   }
}
