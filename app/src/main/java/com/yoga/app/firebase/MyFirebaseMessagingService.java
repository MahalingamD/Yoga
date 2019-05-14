package com.yoga.app.firebase;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.yoga.app.R;
import com.yoga.app.utils.Prefs;

@SuppressLint("Registered")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

   @Override
   public void onNewToken( String token ) {
      super.onNewToken( token );
      Prefs.putString( "FCM_Token", token );

      //sendRegistrationToServer( token );
   }

   @Override
   public void onMessageReceived( RemoteMessage remoteMessage ) {
      super.onMessageReceived( remoteMessage );
      Log.d( "Firebase From: ", remoteMessage.getFrom() );

      if( remoteMessage.getData().size() > 0 ) {
         Log.d( "Firebase Message: ", remoteMessage.getData().toString() );

         generateNotification( remoteMessage );
      }
   }

   private void generateNotification( RemoteMessage data ) {
      String NOTIFICATION_CHANNEL_ID = "10001";

      NotificationCompat.Builder mBuilder = new NotificationCompat.Builder( this, NOTIFICATION_CHANNEL_ID );
      mBuilder.setSmallIcon( R.mipmap.ic_launcher );
      mBuilder.setContentTitle( "TITLE" )
              .setContentText( "Message" )
              .setAutoCancel( false )
              .setSound( Settings.System.DEFAULT_NOTIFICATION_URI );

      NotificationManager mNotificationManager = ( NotificationManager ) this.getSystemService( Context.NOTIFICATION_SERVICE );

      if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {
         int importance = NotificationManager.IMPORTANCE_HIGH;
         NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance );
         notificationChannel.enableLights( true );
         notificationChannel.setLightColor( Color.RED );
         notificationChannel.enableVibration( true );
         notificationChannel.setVibrationPattern( new long[]{ 100, 200, 300, 400, 500, 400, 300, 200, 400 } );
         assert mNotificationManager != null;
         mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID );
         mNotificationManager.createNotificationChannel( notificationChannel );
      }

      assert mNotificationManager != null;
      mNotificationManager.notify( 0 /* Request Code */, mBuilder.build() );

   }
}
