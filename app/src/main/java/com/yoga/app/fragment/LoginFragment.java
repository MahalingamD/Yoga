package com.yoga.app.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.yoga.app.BuildConfig;
import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.model.Response;
import com.yoga.app.service.RetrofitInstance;
import com.yoga.app.utils.Prefs;
import com.yoga.app.utils.ProgressDialog;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

import static com.yoga.app.constant.PrefConstants.ACCESS_TOKEN;
import static com.yoga.app.helper.YogaHelper.checkInternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

   private static final int RC_SIGN_IN = 9001;
   private GoogleSignInClient mGoogleSignInClient;
   private FirebaseAuth mAuth;
   private CallbackManager mCallbackManager;
   FragmentActivity mContext;

   @BindView(R.id.login_google_btn)
   Button mGoogleLoginBtn;

   @BindView(R.id.login_facebook_btn)
   Button mFacebookLoginBTN;

   @BindView(R.id.login_facebook)
   LoginButton mFbLoginBtn;

   @BindView(R.id.fragment_login_btn)
   Button mLoginBtn;

   @BindView(R.id.login_email_text_input)
   TextInputLayout mEmailText;

   @BindView(R.id.login_password_text_input)
   TextInputLayout mPasswordText;

   @BindView(R.id.login_email_edit_text)
   TextInputEditText mEmailEditText;

   @BindView(R.id.login_password_edit_text)
   TextInputEditText mPasswordEditText;

   private String TAG = " ";
   private RetrofitInstance myRetrofitInstance;
   ProgressDialog aProgressDialog;

   @Override
   public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
      FacebookSdk.sdkInitialize( getActivity() );
      View mView = inflater.inflate( R.layout.fragment_login, container, false );
      ButterKnife.bind( this, mView );
      init( mView );
      return mView;
   }

   private void init( View aView ) {
      mContext = getActivity();
      mAuth = FirebaseAuth.getInstance();

      if( this.myRetrofitInstance == null ) {
         myRetrofitInstance = new RetrofitInstance();
      }

      facebook();
      google();

      mContext.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN );
      clickListener();
   }

   private void clickListener() {

      mFacebookLoginBTN.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick( View view ) {
            mFbLoginBtn.performClick();
         }
      } );

      mLoginBtn.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick( View view ) {
            //Intent aIntent = new Intent(mContext, MainActivity.class);
            //startActivity(aIntent);

            if( validation() ) {

               Map<String, String> params = new HashMap<>();
               params.put( "email", mEmailEditText.getText().toString() );
               params.put( "password", mPasswordEditText.getText().toString() );
               callLogin( params );
            }


         }
      } );

   }

   private boolean validation() {
      boolean aCheck = true;

      if( mEmailEditText.getText().toString().trim().length() != 0 ) {

         if( YogaHelper.isValidEmail( mEmailEditText.getText().toString() ) ) {
            mEmailText.setErrorEnabled( false );
         } else {
            aCheck = false;
            mEmailText.setErrorEnabled( true );
            mEmailText.setError( "Kindly Enter your valid Email Address" );
         }

      } else {
         aCheck = false;
         mEmailText.setErrorEnabled( true );
         mEmailText.setError( "Kindly Enter your Email Address" );
      }


      if( mPasswordEditText.getText().toString().trim().length() != 0 ) {
         mPasswordText.setErrorEnabled( false );
      } else {
         aCheck = false;
         mPasswordText.setErrorEnabled( true );
         mPasswordText.setError( "Kindly Enter your Password" );
      }

      return aCheck;
   }

   private void callLogin( Map<String, String> params ) {
      if( checkInternet( mContext ) ) {
         aProgressDialog = new ProgressDialog( getActivity() );
         aProgressDialog.show();

         myRetrofitInstance.getAPI().LoginAPI( YogaHelper.aDeviceId( getActivity() ), params ).enqueue( new Callback<Response>() {
            @Override
            public void onResponse( @NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response ) {
               aProgressDialog.dismiss();
               Response data = response.body();
               if( data != null ) {
                  if( data.getSuccess() == 1 ) {
                     Prefs.putString( ACCESS_TOKEN, data.getData().getAccess_token() );
                     Prefs.putBoolean( "Login_status", true );
                     callHomeScreen();
                  } else {
                     YogaHelper.showAlertDialog( getActivity(), data.getError() );
                  }
               } else {
                  YogaHelper.showAlertDialog( getActivity(), getString( R.string.label_something ) );
               }
            }

            @Override
            public void onFailure( Call<Response> call, Throwable t ) {
               aProgressDialog.dismiss();
               YogaHelper.showAlertDialog( getActivity(), getString( R.string.label_something ) );
            }
         } );
      } else {
         YogaHelper.showAlertDialog( getActivity(), getString( R.string.label_internet ) );
      }
   }

   private void callHomeScreen() {
      Intent intent = new Intent( getActivity(), MainActivity.class );
      intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
      startActivity( intent );
   }

   private void facebook() {
      mCallbackManager = CallbackManager.Factory.create();
      mFbLoginBtn.setReadPermissions( "email", "public_profile" );
      mFbLoginBtn.registerCallback( mCallbackManager, new FacebookCallback<LoginResult>() {
         @Override
         public void onSuccess( LoginResult loginResult ) {
            handleFacebookAccessToken( loginResult.getAccessToken() );
         }

         @Override
         public void onCancel() {
         }

         @Override
         public void onError( FacebookException error ) {
            Log.e( TAG, "Error " + error.toString() );
         }
      } );


   }

   private void handleFacebookAccessToken( AccessToken token ) {
      Log.d( TAG, "handleFacebookAccessToken:" + token );

      AuthCredential credential = FacebookAuthProvider.getCredential( token.getToken() );
      mAuth.signInWithCredential( credential )
              .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete( @NonNull Task<AuthResult> task ) {
                    if( task.isSuccessful() ) {
                       FirebaseUser user = mAuth.getCurrentUser();

                       //  Log.e( "FB Login user", user.getDisplayName() );
                       //   Log.e( "FB Login Email", user.getEmail() );
                       //   Log.e( "FB Login photo", user.getPhotoUrl().toString() );
                       FirebaseUserMetadata amata = user.getMetadata();
                       List<UserInfo> aList = ( List<UserInfo> ) user.getProviderData();

                       callHomeScreen();

                       for( UserInfo aUser : aList ) {
                          Log.e( "Display is verified", "" + aUser.isEmailVerified() );
                       }


                    } else {
                       // If sign in fails, display a message to the user.
                       Log.w( TAG, "signInWithCredential:failure", task.getException() );
                       Toast.makeText( getActivity(), "Authentication failed.",
                               Toast.LENGTH_SHORT ).show();

                    }

                 }
              } );
   }

   private void google() {
      GoogleSignInOptions gso = new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
              .requestIdToken( getString( R.string.default_web_client_id ) )
              .requestEmail()
              .build();

      mGoogleSignInClient = GoogleSignIn.getClient( mContext, gso );

      mGoogleLoginBtn.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick( View view ) {
            signIn();

         }
      } );
   }

   private void signIn() {
      Intent signInIntent = mGoogleSignInClient.getSignInIntent();
      mContext.startActivityForResult( signInIntent, RC_SIGN_IN );
   }

   @Override
   public void onActivityResult( int requestCode, int resultCode, Intent data ) {
      super.onActivityResult( requestCode, resultCode, data );

      // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
      if( requestCode == RC_SIGN_IN ) {
         Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent( data );
         try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult( ApiException.class );
            firebaseAuthWithGoogle( account );
         } catch( ApiException e ) {
            // Google Sign In failed, update UI appropriately
            Log.w( TAG, "Google sign in failed", e );
         }
      } else {

         mCallbackManager.onActivityResult( requestCode, resultCode, data );
      }
   }


   private void firebaseAuthWithGoogle( GoogleSignInAccount acct ) {
      Log.d( TAG, "firebaseAuthWithGoogle:" + acct.getId() );
      AuthCredential credential = GoogleAuthProvider.getCredential( acct.getIdToken(), null );
      mAuth.signInWithCredential( credential )
              .addOnCompleteListener( mContext, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete( @NonNull Task<AuthResult> task ) {
                    if( task.isSuccessful() ) {
                       // Sign in success, update UI with the signed-in user's information
                       // Log.d( TAG, "signInWithCredential:success" );
                       FirebaseUser user = mAuth.getCurrentUser();
                       // Log.e( "Display Name", user.getDisplayName() );
                       // Log.e( "Display Email", user.getEmail() );
                       // Log.e( "Display phone_number", user.getPhotoUrl().toString() );
                       user.getProviderData();

                       List<UserInfo> aList = ( List<UserInfo> ) user.getProviderData();

                       callHomeScreen();

                       for( UserInfo aUser : aList ) {
                          Log.e( "Display is verified", "" + aUser.isEmailVerified() );
                       }

                    } else {
                       Log.w( TAG, "signInWithCredential:failure", task.getException() );
                    }

                 }
              } );
   }


}
