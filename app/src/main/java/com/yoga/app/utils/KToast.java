package com.yoga.app.utils;

import android.support.v4.app.FragmentActivity;

import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;
import com.yoga.app.R;


public class KToast {

   public static String TAG = KToast.class.getSimpleName();

   public static int TOP = 1;
   public static int BOTTOM = 0;

   public static int SHORT = 2, LONG = 3, INFINITE = 4;

   public static void simpleToast( FragmentActivity context, String aMessage ) {
      new Flashbar.Builder( context )
              .message( aMessage )
              .duration( Flashbar.DURATION_LONG )
              .show();
   }

   public static void simpleToast( FragmentActivity context, String aMessage, int gravity ) {
      if( gravity == BOTTOM ) {
         new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .duration( Flashbar.DURATION_SHORT )
                 .show();
      } else if( gravity == TOP ) {
         new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .duration( Flashbar.DURATION_SHORT )
                 .show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void simpleToast( FragmentActivity context, String aMessage, int gravity, int Duration ) {

      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      if( gravity == 0 ) {
         new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .duration( duration )
                 .show();
      } else if( gravity == 1 ) {
         new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .duration( duration )
                 .show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void titleToast( FragmentActivity context, String aTitle, String aMessage, int Duration, int gravity ) {

      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      if( gravity == 0 ) {
         new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .show();
      } else if( gravity == 1 ) {
         new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void titleToast( FragmentActivity context, String aTitle, String aMessage, int Duration ) {

      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }
      new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.BOTTOM )
              .message( aMessage )
              .title( aTitle )
              .duration( duration )
              .show();
   }

   public static void titleToast( FragmentActivity context, String aTitle, String aMessage ) {

      Long duration = Flashbar.DURATION_SHORT;

      new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.BOTTOM )
              .message( aMessage )
              .title( aTitle )
              .duration( duration )
              .show();
   }

   public static void backgroundColorToast( FragmentActivity context, String aTitle,
                                            String aMessage, int Duration, int gravity, int backgroundColor ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      if( gravity == 0 ) {
         new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .backgroundColor( backgroundColor )
                 .show();
      } else if( gravity == 1 ) {
         new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .backgroundColor( backgroundColor )
                 .show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void backgroundDrawableToast( FragmentActivity context, String aTitle,
                                               String aMessage, int Duration, int gravity, int backgroundDrawable ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      if( gravity == 0 ) {
         new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .backgroundDrawable( backgroundDrawable )
                 .show();
      } else if( gravity == 1 ) {
         new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .backgroundDrawable( backgroundDrawable )
                 .show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void actionToast( FragmentActivity context, String aTitle,
                                   String aMessage, String actionText, Flashbar.OnActionTapListener listener,
                                   int Duration, int gravity, int backgroundDrawable, boolean isBlockable ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      if( gravity == 0 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .backgroundDrawable( backgroundDrawable )
                 .primaryActionText( actionText )
                 .primaryActionTapListener( listener );
         if( isBlockable ) {
            flashbar.overlayBlockable();
         }
         flashbar.show();
      } else if( gravity == 1 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .backgroundDrawable( backgroundDrawable )
                 .primaryActionText( actionText )
                 .primaryActionTapListener( listener );
         if( isBlockable ) {
            flashbar.overlayBlockable();
         }
         flashbar.show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void actionToast( FragmentActivity context, String aTitle,
                                   String aMessage, String actionText, Flashbar.OnActionTapListener listener,
                                   int Duration, int gravity, boolean isBlockable ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      if( gravity == 0 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .primaryActionText( actionText )
                 .primaryActionTapListener( listener );
         if( isBlockable ) {
            flashbar.overlayBlockable();
         }
         flashbar.show();
      } else if( gravity == 1 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .primaryActionText( actionText )
                 .primaryActionTapListener( listener );
         if( isBlockable ) {
            flashbar.overlayBlockable();
         }
         flashbar.show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void actionToast( FragmentActivity context, String aTitle,
                                   String aMessage, String actionText, Flashbar.OnActionTapListener listener,
                                   int Duration, int gravity ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      if( gravity == 0 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .primaryActionText( actionText )
                 .primaryActionTapListener( listener );
         flashbar.show();
      } else if( gravity == 1 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .primaryActionText( actionText )
                 .primaryActionTapListener( listener );
         flashbar.show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void actionToast( FragmentActivity context, String aTitle,
                                   String aMessage, String actionText,
                                   int Duration, int gravity, boolean isBlockable ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      if( gravity == 0 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .primaryActionText( actionText );
         if( isBlockable ) {
            flashbar.overlayBlockable();
         }
         flashbar.show();
      } else if( gravity == 1 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .primaryActionText( actionText );
         if( isBlockable ) {
            flashbar.overlayBlockable();
         }
         flashbar.show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void actionToast( FragmentActivity context, String aTitle, String aMessage, String actionText,
                                   int Duration, int gravity ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      if( gravity == 0 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .primaryActionText( actionText );
         flashbar.show();
      } else if( gravity == 1 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .primaryActionText( actionText );
         flashbar.show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void actionToast( FragmentActivity context, String aTitle, String aMessage, String actionText, int gravity ) {
      Long duration = Flashbar.DURATION_INDEFINITE;

      if( gravity == 0 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.BOTTOM )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .primaryActionText( actionText );
         flashbar.show();
      } else if( gravity == 1 ) {
         Flashbar.Builder flashbar = new Flashbar.Builder( context )
                 .gravity( Flashbar.Gravity.TOP )
                 .message( aMessage )
                 .title( aTitle )
                 .duration( duration )
                 .primaryActionText( actionText );
         flashbar.show();
      } else {
         KLog.error( TAG, "Gravity unpredictable" );
      }
   }

   public static void actionToast( FragmentActivity context, String aTitle, String aMessage, String actionText ) {
      Long duration = Flashbar.DURATION_INDEFINITE;

      Flashbar.Builder flashbar = new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.BOTTOM )
              .message( aMessage )
              .title( aTitle )
              .duration( duration )
              .primaryActionText( actionText );
      flashbar.show();
   }

   public static void iconAnimToast( FragmentActivity context, String aTitle, String aMessage, int Duration,
                                     int gravity, int backgroundDrawable, int icon, int iconColor ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.TOP )
              .title( aTitle )
              .message( aMessage )
              .backgroundColorRes( backgroundDrawable )
              .showIcon()
              .duration( duration )
              .icon( icon )
              .iconColorFilterRes( iconColor );

      if( gravity == 0 ) {
         builder.gravity( Flashbar.Gravity.BOTTOM );
      } else {
         builder.gravity( Flashbar.Gravity.TOP );
      }
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void iconToast( FragmentActivity context, String aTitle, String aMessage, int Duration,
                                 int gravity, int backgroundDrawable, int icon, int iconColor ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.TOP )
              .title( aTitle )
              .message( aMessage )
              .backgroundColorRes( backgroundDrawable )
              .showIcon()
              .duration( duration )
              .icon( icon )
              .iconColorFilterRes( iconColor );

      if( gravity == 0 ) {
         builder.gravity( Flashbar.Gravity.BOTTOM );
      } else {
         builder.gravity( Flashbar.Gravity.TOP );
      }
      builder.show();
   }

   public static void iconToast( FragmentActivity context, String aTitle, String aMessage, int Duration,
                                 int gravity, int icon, int iconColor ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.TOP )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .icon( icon )
              .iconColorFilterRes( iconColor );

      if( gravity == 0 ) {
         builder.gravity( Flashbar.Gravity.BOTTOM );
      } else {
         builder.gravity( Flashbar.Gravity.TOP );
      }
      builder.show();
   }

   public static void iconToast( FragmentActivity context, String aTitle, String aMessage,
                                 int gravity, int icon, int iconColor ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.TOP )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .icon( icon )
              .iconColorFilterRes( iconColor );

      if( gravity == 0 ) {
         builder.gravity( Flashbar.Gravity.BOTTOM );
      } else {
         builder.gravity( Flashbar.Gravity.TOP );
      }
      builder.show();
   }

   public static void iconToast( FragmentActivity context, String aTitle, String aMessage, int icon, int iconColor ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.TOP )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .icon( icon )
              .iconColorFilterRes( iconColor );

      builder.gravity( Flashbar.Gravity.BOTTOM );
      builder.show();
   }

   public static void iconToast( FragmentActivity context, String aTitle, String aMessage, int icon ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.TOP )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .icon( icon );

      builder.gravity( Flashbar.Gravity.BOTTOM );
      builder.show();
   }

   public static void successToast( FragmentActivity context, String aTitle, String aMessage, int gravity, int Duration ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.success )
              .icon( R.drawable.ic_success )
              .iconColorFilterRes( R.color.white );

      if( gravity == 0 ) {
         builder.gravity( Flashbar.Gravity.BOTTOM );
      } else {
         builder.gravity( Flashbar.Gravity.TOP );
      }
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void successToast( FragmentActivity context, String aTitle, String aMessage, int gravity ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.success )
              .icon( R.drawable.ic_success )
              .iconColorFilterRes( R.color.white );

      if( gravity == 0 ) {
         builder.gravity( Flashbar.Gravity.BOTTOM );
      } else {
         builder.gravity( Flashbar.Gravity.TOP );
      }

      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void successToast( FragmentActivity context, String aTitle, String aMessage ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.success )
              .icon( R.drawable.ic_success )
              .iconColorFilterRes( R.color.white );

      builder.gravity( Flashbar.Gravity.TOP );
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void successToast( FragmentActivity context, String aMessage ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.success )
              .icon( R.drawable.ic_success )
              .iconColorFilterRes( R.color.white );

      builder.gravity( Flashbar.Gravity.TOP );
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void errorToast( FragmentActivity context, String aTitle, String aMessage, int gravity, int Duration ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.error )
              .icon( R.drawable.ic_error )
              .iconColorFilterRes( R.color.white );

      if( gravity == 0 ) {
         builder.gravity( Flashbar.Gravity.BOTTOM );
      } else {
         builder.gravity( Flashbar.Gravity.TOP );
      }
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void errorToast( FragmentActivity context, String aTitle, String aMessage, int gravity ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.error )
              .icon( R.drawable.ic_error )
              .iconColorFilterRes( R.color.white );

      if( gravity == 0 ) {
         builder.gravity( Flashbar.Gravity.BOTTOM );
      } else {
         builder.gravity( Flashbar.Gravity.TOP );
      }
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void errorToast( FragmentActivity context, String aTitle, String aMessage ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.TOP )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.error )
              .icon( R.drawable.ic_error )
              .iconColorFilterRes( R.color.white );

      builder.gravity( Flashbar.Gravity.TOP );
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void errorToast( FragmentActivity context, String aMessage ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.TOP )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.error )
              .icon( R.drawable.ic_error )
              .iconColorFilterRes( R.color.white );

      builder.gravity( Flashbar.Gravity.TOP );
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void warningToast( FragmentActivity context, String aTitle, String aMessage, int gravity, int Duration ) {
      Long duration = Flashbar.DURATION_SHORT;
      if( Duration == SHORT ) {
         duration = Flashbar.DURATION_SHORT;
      } else if( Duration == LONG ) {
         duration = Flashbar.DURATION_LONG;
      } else if( Duration == INFINITE ) {
         duration = Flashbar.DURATION_INDEFINITE;
      }

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.warning )
              .icon( R.drawable.ic_waring )
              .iconColorFilterRes( R.color.white );

      if( gravity == 0 ) {
         builder.gravity( Flashbar.Gravity.TOP );
      } else {
         builder.gravity( Flashbar.Gravity.TOP );
      }
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void warningToast( FragmentActivity context, String aTitle, String aMessage, int gravity ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.warning )
              .icon( R.drawable.ic_waring )
              .iconColorFilterRes( R.color.white );

      if( gravity == 0 ) {
         builder.gravity( Flashbar.Gravity.BOTTOM );
      } else {
         builder.gravity( Flashbar.Gravity.TOP );
      }
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void warningToast( FragmentActivity context, String aTitle, String aMessage ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .title( aTitle )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.warning )
              .icon( R.drawable.ic_waring )
              .iconColorFilterRes( R.color.white );

      builder.gravity( Flashbar.Gravity.TOP );
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void warningToast( FragmentActivity context, String aMessage ) {
      Long duration = Flashbar.DURATION_SHORT;

      Flashbar.Builder builder = new Flashbar.Builder( context )
              .message( aMessage )
              .showIcon()
              .duration( duration )
              .backgroundColorRes( R.color.warning )
              .icon( R.drawable.ic_waring )
              .iconColorFilterRes( R.color.white );

      builder.gravity( Flashbar.Gravity.TOP );
      builder.enterAnimation( FlashAnim.with( context )
              .animateBar()
              .duration( 750 )
              .alpha()
              .overshoot() )
              .exitAnimation( FlashAnim.with( context )
                      .animateBar()
                      .duration( 400 )
                      .accelerateDecelerate() );
      builder.show();
   }

   public static void vibrationToast( FragmentActivity context, String aTitle, String aMessage ) {
      new Flashbar.Builder( context )
              .gravity( Flashbar.Gravity.TOP )
              .title( aTitle )
              .message( aMessage )
              .vibrateOn( Flashbar.Vibration.SHOW, Flashbar.Vibration.DISMISS )
              .show();
   }

}
