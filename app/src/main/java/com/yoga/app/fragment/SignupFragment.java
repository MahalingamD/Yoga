package com.yoga.app.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.yoga.app.R;
import com.yoga.app.activities.WelcomeActivity;
import com.yoga.app.adapter.CountryAdapter;
import com.yoga.app.adapter.GenderAdapter;
import com.yoga.app.adapter.HealthAdapter;
import com.yoga.app.adapter.ProfessionAdapter;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.model.Country;
import com.yoga.app.model.Gender;
import com.yoga.app.model.HealthDisorder;
import com.yoga.app.model.Profession;
import com.yoga.app.model.Response;
import com.yoga.app.service.RetrofitInstance;
import com.yoga.app.utils.Prefs;
import com.yoga.app.utils.ProgressDialog;
import com.yoga.app.utils.YogaNetworkManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

   View mView;
   private RetrofitInstance myRetrofitInstance;
   ProgressDialog aProgressDialog;

   FragmentActivity mContext;

   Spinner mGenderSpin, mCountrySpin, mHealthSpin, mProfessionSpin;

   ArrayList<Gender> mGenderArrayList;
   ArrayList<HealthDisorder> mHealthArrayList;
   ArrayList<Profession> mProfessionArrayList;
   ArrayList<Country> mCountryArrayList;

   GenderAdapter mGenderAdapter;
   HealthAdapter mHealthAdapter;
   ProfessionAdapter mProfessionAdapter;
   CountryAdapter mCountryAdapter;
   String token = "";


   TextInputLayout mNameTxt, mEmailTxt, mPhoneNumberTxt, mAgeTxt, mPasswordTxt;
   TextInputEditText mNameEdit, mEmailEdit, mMobileEdit, mAgeEdit, mPasswordEdit;

   String mCountryCode, mCountryId, mGender, mHealth, mProfession;

   Button mSignupButton;

   @Override
   public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
      // Inflate the layout for this fragment
      mView = inflater.inflate( R.layout.fragment_signup, container, false );

      mContext = getActivity();
      init( mView );
      return mView;
   }

   private void init( View mView ) {
      if( this.myRetrofitInstance == null ) {
         myRetrofitInstance = new RetrofitInstance();
      }
      mNameTxt = mView.findViewById( R.id.signup_name_text_input );
      mEmailTxt = mView.findViewById( R.id.signup_email_text_input );
      mPhoneNumberTxt = mView.findViewById( R.id.signup_phone_text_input );
      mAgeTxt = mView.findViewById( R.id.signup_age_text_input );
      mPasswordTxt = mView.findViewById( R.id.signup_password_text_input );

      mNameEdit = mView.findViewById( R.id.signup_name_edit_text );
      mEmailEdit = mView.findViewById( R.id.sign_up_email_edit_text );
      mMobileEdit = mView.findViewById( R.id.signup_phone_edit_text );
      mAgeEdit = mView.findViewById( R.id.signup_age_edit_text );
      mPasswordEdit = mView.findViewById( R.id.signup_password_edit_text );

      mGenderSpin = mView.findViewById( R.id.signup_gender_spin );
      mHealthSpin = mView.findViewById( R.id.signup_health_spinner );
      mProfessionSpin = mView.findViewById( R.id.signup_profession_spinner );
      mCountrySpin = mView.findViewById( R.id.signup_country_spin );

      mSignupButton = mView.findViewById( R.id.fragment_signup_btn );

      mGenderArrayList = new ArrayList<>();
      mHealthArrayList = new ArrayList<>();
      mProfessionArrayList = new ArrayList<>();
      mCountryArrayList = new ArrayList<>();


      FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( mContext, new OnSuccessListener<InstanceIdResult>() {
         @Override
         public void onSuccess( InstanceIdResult instanceIdResult ) {
            token = instanceIdResult.getToken();
            Log.e( "newToken", token );
         }
      } );

      clickListener();

      getRegisterValue();


   }

   private void clickListener() {
      mSignupButton.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick( View v ) {
            if( YogaNetworkManager.isInternetOnCheck( mContext ) ) {
               if( validation() ) {

                  String aString = Prefs.getString( "FCM_Token", "" );

                  Log.e( "TOKEN", aString );
                  putRegisterValue();
               }
            } else {
               YogaHelper.showAlertDialog( mContext, "Please check your internet connection" );
            }
         }
      } );
   }

   private boolean validation() {
      boolean acheck = true;

      if( mNameEdit.getText().toString().trim().length() > 0 ) {
         mNameTxt.setErrorEnabled( false );
      } else {
         acheck = false;
         mNameTxt.setErrorEnabled( true );
         mNameTxt.setError( "Please Enter your Name" );
      }

      if( mEmailEdit.getText().toString().trim().length() > 0 ) {
         if( YogaHelper.isValidEmail( mEmailEdit.getText().toString() ) )
            mEmailTxt.setErrorEnabled( false );
      } else {
         acheck = false;
         mEmailTxt.setErrorEnabled( true );
         mEmailTxt.setError( "Please Enter your Email Address" );
      }

      if( mMobileEdit.getText().toString().trim().length() > 0 ) {
         if( mMobileEdit.getText().toString().trim().length() == 10 )
            mPhoneNumberTxt.setErrorEnabled( false );
      } else {
         acheck = false;
         mPhoneNumberTxt.setErrorEnabled( true );
         mPhoneNumberTxt.setError( "Please Enter your Mobile number" );
      }

      if( mAgeEdit.getText().toString().trim().length() > 0 ) {
         int age = Integer.parseInt( mAgeEdit.getText().toString().trim() );
         if( age >= 18 ) {
            mAgeTxt.setErrorEnabled( false );
         } else {
            acheck = false;
            mAgeTxt.setErrorEnabled( true );
            mAgeTxt.setError( "Minimum age 18 years. Please Enter age as more than 18 " );
         }

      } else {
         acheck = false;
         mAgeTxt.setErrorEnabled( true );
         mAgeTxt.setError( "Please Enter your Age" );
      }

      if( mPasswordEdit.getText().toString().trim().length() > 0 ) {

         if( mPasswordEdit.getText().toString().trim().length() >= 6 ) {
            mPasswordTxt.setErrorEnabled( false );
         } else {
            acheck = false;
            mPasswordTxt.setErrorEnabled( true );
            mPasswordTxt.setError( "Minimum password length 6 digit" );
         }
      } else {
         acheck = false;
         mPasswordTxt.setErrorEnabled( true );
         mPasswordTxt.setError( "Please Enter your Password" );
      }

      return acheck;
   }

   private void setSpinnerValues() {

      mGenderAdapter = new GenderAdapter( mContext, android.R.layout.simple_spinner_item, mGenderArrayList );
      mGenderSpin.setAdapter( mGenderAdapter );

      mGenderSpin.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
            Gender aGender = ( Gender ) parent.getItemAtPosition( position );
            mGender = aGender.gender_id;
         }

         @Override
         public void onNothingSelected( AdapterView<?> parent ) {

         }
      } );


      mHealthAdapter = new HealthAdapter( mContext, android.R.layout.simple_spinner_item, mHealthArrayList );
      mHealthSpin.setAdapter( mHealthAdapter );

      mHealthSpin.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
            HealthDisorder aGender = ( HealthDisorder ) parent.getItemAtPosition( position );
            mHealth = aGender.healthdo_id;
         }

         @Override
         public void onNothingSelected( AdapterView<?> parent ) {

         }
      } );

      mProfessionAdapter = new ProfessionAdapter( mContext, android.R.layout.simple_spinner_item, mProfessionArrayList );
      mProfessionSpin.setAdapter( mProfessionAdapter );

      mProfessionSpin.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
            Profession aGender = ( Profession ) parent.getItemAtPosition( position );
            mProfession = aGender.profession_id;
         }

         @Override
         public void onNothingSelected( AdapterView<?> parent ) {

         }
      } );


      mCountryAdapter = new CountryAdapter( mContext, android.R.layout.simple_spinner_item, mCountryArrayList );
      mCountrySpin.setAdapter( mCountryAdapter );

      mCountrySpin.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
            Country aGender = ( Country ) parent.getItemAtPosition( position );
            mCountryId = aGender.country_id;
            mCountryCode = aGender.country_phonecode;
         }

         @Override
         public void onNothingSelected( AdapterView<?> parent ) {

         }
      } );


   }


   private void getRegisterValue() {
      aProgressDialog = new ProgressDialog( mContext );
      aProgressDialog.show();

      //Log.e("Id", YogaHelper.aDeviceId(getActivity()));

      Map<String, String> aHeaderMap = new HashMap<>();
      aHeaderMap.put( "X-Localization", "en" );

      myRetrofitInstance.getAPI().RegisterAPI( aHeaderMap ).enqueue( new Callback<Response>() {
         @Override
         public void onResponse( @NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response ) {
            aProgressDialog.dismiss();
            if( response != null ) {
               Response data = response.body();
               if( data != null ) {
                  if( data.getSuccess() == 1 ) {
                     mGenderArrayList = ( ArrayList<Gender> ) data.getData().getmGenderList();
                     mHealthArrayList = ( ArrayList<HealthDisorder> ) data.getData().getmDisorderList();
                     mProfessionArrayList = ( ArrayList<Profession> ) data.getData().getmProfessionList();
                     mCountryArrayList = ( ArrayList<Country> ) data.getData().getmCountryList();

                     setSpinnerValues();
                  }
               }
            }
         }

         @Override
         public void onFailure( Call<Response> call, Throwable t ) {
            aProgressDialog.dismiss();
            YogaHelper.showAlertDialog( mContext, "Something went wrong" );

         }
      } );
   }

   private void putRegisterValue() {
      aProgressDialog = new ProgressDialog( mContext );
      aProgressDialog.show();

      //Log.e("Id", YogaHelper.aDeviceId(getActivity()));

      Map<String, String> aHeaderMap = new HashMap<>();
      aHeaderMap.put( "name", mNameEdit.getText().toString().trim() );
      aHeaderMap.put( "email", mEmailEdit.getText().toString().trim() );
      aHeaderMap.put( "phone", mMobileEdit.getText().toString().trim() );
      aHeaderMap.put( "password", mPasswordEdit.getText().toString().trim() );
      aHeaderMap.put( "country", mCountryId );

      aHeaderMap.put( "country_code", mCountryCode );
      aHeaderMap.put( "age", mAgeEdit.getText().toString().trim() );
      aHeaderMap.put( "health_disorder[" + 0 + "]", mHealth );

      aHeaderMap.put( "gender", mGender );
      aHeaderMap.put( "profession", mProfession );
      aHeaderMap.put( "lang_id", "1" );

      // String token = "";

      FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( mContext, new OnSuccessListener<InstanceIdResult>() {
         @Override
         public void onSuccess( InstanceIdResult instanceIdResult ) {
            token = instanceIdResult.getToken();
            Log.e( "newToken", token );
         }
      } );

      aHeaderMap.put( "fcm_id", token );
      // aHeaderMap.put( "fcm_id", Prefs.getString( "FCM_Token", "" ) );

      myRetrofitInstance.getAPI().PutRegisterAPI( aHeaderMap ).enqueue( new Callback<Response>() {
         @Override
         public void onResponse( @NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response ) {
            aProgressDialog.dismiss();
            if( response.isSuccessful() ) {
               Response data = response.body();
               if( data != null ) {
                  if( data.getSuccess() == 1 ) {
                     // data.getMessage();
                     //  showAlertDialog( data.getMessage() );
                     String aAccountId = data.getData().account_id;
                     String aOTP = data.getData().otp;

                     Prefs.putString( "Acount_id", aAccountId );

                     showOTPDialog( aAccountId, aOTP );
                  } else {
                     YogaHelper.showAlertDialog( mContext, data.getError() );
                  }
               }
            } else {
               YogaHelper.showAlertDialog( mContext, "Something went wrong" );
            }
         }

         @Override
         public void onFailure( Call<Response> call, Throwable t ) {
            aProgressDialog.dismiss();
            YogaHelper.showAlertDialog( mContext, "Something went wrong" );

         }
      } );
   }

   private void getVerifyOTP( String aAccountId, String aOTP ) {
      aProgressDialog = new ProgressDialog( mContext );
      aProgressDialog.show();

      //Log.e("Id", YogaHelper.aDeviceId(getActivity()));

      Map<String, String> aHeaderMap = new HashMap<>();
      aHeaderMap.put( "account_id", aAccountId );
      aHeaderMap.put( "otp", aOTP );


      myRetrofitInstance.getAPI().getVerifyOTP( aHeaderMap ).enqueue( new Callback<Response>() {
         @Override
         public void onResponse( @NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response ) {
            aProgressDialog.dismiss();
            if( response.isSuccessful() ) {
               Response data = response.body();
               if( data != null ) {
                  if( data.getSuccess() == 1 ) {
                     showAlertDialog( data.getMessage() );
                  } else {
                     YogaHelper.showAlertDialog( mContext, data.getError() );
                  }
               }
            } else {
               YogaHelper.showAlertDialog( mContext, "Something went wrong" );
            }
         }

         @Override
         public void onFailure( @NotNull Call<Response> call, @NotNull Throwable t ) {
            aProgressDialog.dismiss();
            YogaHelper.showAlertDialog( mContext, "Something went wrong" );

         }
      } );
   }


   private void showAlertDialog( String aMessage ) {
      AlertDialog alertDialog = new AlertDialog.Builder( mContext ).create();
      alertDialog.setTitle( mContext.getString( R.string.app_name ) );
      alertDialog.setIcon( R.mipmap.ic_launcher );
      alertDialog.setCancelable( false );
      alertDialog.setMessage( aMessage );
      alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
         @Override
         public void onClick( DialogInterface dialog, int which ) {

            ( ( WelcomeActivity ) mContext ).setDefaultTab();
         }
      } );

      alertDialog.show();
   }


   private void showOTPDialog( final String aAccountId, final String aOTP ) {

      LayoutInflater inflater = LayoutInflater.from( mContext );
      final View view = inflater.inflate( R.layout.otp_layout, null );

      AlertDialog alertDialog = new AlertDialog.Builder( mContext ).create();
      alertDialog.setTitle( mContext.getString( R.string.app_name ) );
      alertDialog.setIcon( R.mipmap.ic_launcher );
      alertDialog.setCancelable( false );
      alertDialog.setMessage( "Enter your yoga OTP" );


      final EditText etComments = view.findViewById( R.id.otp_edit );

      etComments.setText( aOTP );

      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
      params.leftMargin = convertPixelsToDp( 16, mContext );
      params.rightMargin = convertPixelsToDp( 16, mContext );
      etComments.setLayoutParams( params );

      alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
         @Override
         public void onClick( DialogInterface dialog, int which ) {
            getVerifyOTP( aAccountId, aOTP );

         }
      } );


      alertDialog.setView( view );
      alertDialog.show();
   }

   public int convertPixelsToDp( float px, Context context ) {
      Resources resources = context.getResources();
      DisplayMetrics metrics = resources.getDisplayMetrics();
      int dp = ( int ) ( px / ( metrics.densityDpi / 160f ) );
      return dp;
   }

}
