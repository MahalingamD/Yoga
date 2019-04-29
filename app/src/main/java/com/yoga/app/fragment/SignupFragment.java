package com.yoga.app.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.yoga.app.R;
import com.yoga.app.activities.WelcomeActivity;
import com.yoga.app.adapter.CountryAdapter;
import com.yoga.app.adapter.GenderAdapter;
import com.yoga.app.adapter.HealthAdapter;
import com.yoga.app.adapter.ProfessionAdapter;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.model.Banner;
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

import static com.yoga.app.constant.PrefConstants.ACCESS_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    View mView;
    private RetrofitInstance myRetrofitInstance;
    ProgressDialog aProgressDialog;

    Spinner mGenderSpin, mCountrySpin, mHealthSpin, mProfessionSpin;

    ArrayList<Gender> mGenderArrayList;
    ArrayList<HealthDisorder> mHealthArrayList;
    ArrayList<Profession> mProfessionArrayList;
    ArrayList<Country> mCountryArrayList;

    GenderAdapter mGenderAdapter;
    HealthAdapter mHealthAdapter;
    ProfessionAdapter mProfessionAdapter;
    CountryAdapter mCountryAdapter;

    TextInputLayout mNameTxt, mEmailTxt, mPhoneNumberTxt, mAgeTxt, mPasswordTxt;
    TextInputEditText mNameEdit, mEmailEdit, mMobileEdit, mAgeEdit, mPasswordEdit;

    String mCountryCode, mCountryId, mGender, mHealth, mProfession;

    Button mSignupButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_signup, container, false);

        init(mView);
        return mView;
    }

    private void init(View mView) {
        if (this.myRetrofitInstance == null) {
            myRetrofitInstance = new RetrofitInstance();
        }
        mNameTxt = mView.findViewById(R.id.signup_name_text_input);
        mEmailTxt = mView.findViewById(R.id.signup_email_text_input);
        mPhoneNumberTxt = mView.findViewById(R.id.signup_phone_text_input);
        mAgeTxt = mView.findViewById(R.id.signup_age_text_input);
        mPasswordTxt = mView.findViewById(R.id.signup_password_text_input);

        mNameEdit = mView.findViewById(R.id.signup_name_edit_text);
        mEmailEdit = mView.findViewById(R.id.sign_up_email_edit_text);
        mMobileEdit = mView.findViewById(R.id.signup_phone_edit_text);
        mAgeEdit = mView.findViewById(R.id.signup_age_edit_text);
        mPasswordEdit = mView.findViewById(R.id.signup_password_edit_text);

        mGenderSpin = mView.findViewById(R.id.signup_gender_spin);
        mHealthSpin = mView.findViewById(R.id.signup_health_spinner);
        mProfessionSpin = mView.findViewById(R.id.signup_profession_spinner);
        mCountrySpin = mView.findViewById(R.id.signup_country_spin);

        mSignupButton = mView.findViewById(R.id.fragment_signup_btn);

        mGenderArrayList = new ArrayList<>();
        mHealthArrayList = new ArrayList<>();
        mProfessionArrayList = new ArrayList<>();
        mCountryArrayList = new ArrayList<>();

        clickListener();

        getRegisterValue();
    }

    private void clickListener() {
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (YogaNetworkManager.isInternetOnCheck(getActivity())) {
                    if (validation()) {
                        putRegisterValue();
                    }
                } else {

                }
            }
        });
    }

    private boolean validation() {
        boolean acheck = true;

        if (mNameEdit.getText().toString().trim().length() > 0) {
            mNameTxt.setErrorEnabled(false);
        } else {
            acheck = false;
            mNameTxt.setErrorEnabled(true);
            mNameTxt.setError("Please Enter your Name");
        }

        if (mEmailEdit.getText().toString().trim().length() > 0) {
            if (YogaHelper.isValidEmail(mEmailEdit.getText().toString()))
                mEmailTxt.setErrorEnabled(false);
        } else {
            acheck = false;
            mEmailTxt.setErrorEnabled(true);
            mEmailTxt.setError("Please Enter your Email Address");
        }

        if (mMobileEdit.getText().toString().trim().length() > 0) {
            if (mMobileEdit.getText().toString().trim().length() == 10)
                mPhoneNumberTxt.setErrorEnabled(false);
        } else {
            acheck = false;
            mPhoneNumberTxt.setErrorEnabled(true);
            mPhoneNumberTxt.setError("Please Enter your Mobile number");
        }

        if (mAgeEdit.getText().toString().trim().length() > 0) {
            mAgeTxt.setErrorEnabled(false);
        } else {
            acheck = false;
            mAgeTxt.setErrorEnabled(true);
            mAgeTxt.setError("Please Enter your Age");
        }

        if (mPasswordEdit.getText().toString().trim().length() > 0) {
            mPasswordTxt.setErrorEnabled(false);
        } else {
            acheck = false;
            mPasswordTxt.setErrorEnabled(true);
            mPasswordTxt.setError("Please Enter your Password");
        }

        return acheck;
    }

    private void setSpinnerValues() {

        mGenderAdapter = new GenderAdapter(getActivity(), android.R.layout.simple_spinner_item, mGenderArrayList);
        mGenderSpin.setAdapter(mGenderAdapter);

        mGenderSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Gender aGender = (Gender) parent.getItemAtPosition(position);
                mGender = aGender.gender_id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mHealthAdapter = new HealthAdapter(getActivity(), android.R.layout.simple_spinner_item, mHealthArrayList);
        mHealthSpin.setAdapter(mHealthAdapter);

        mHealthSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HealthDisorder aGender = (HealthDisorder) parent.getItemAtPosition(position);
                mHealth = aGender.healthdo_id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mProfessionAdapter = new ProfessionAdapter(getActivity(), android.R.layout.simple_spinner_item, mProfessionArrayList);
        mProfessionSpin.setAdapter(mProfessionAdapter);

        mProfessionSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Profession aGender = (Profession) parent.getItemAtPosition(position);
                mProfession = aGender.profession_id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mCountryAdapter = new CountryAdapter(getActivity(), android.R.layout.simple_spinner_item, mCountryArrayList);
        mCountrySpin.setAdapter(mCountryAdapter);

        mCountrySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Country aGender = (Country) parent.getItemAtPosition(position);
                mCountryId = aGender.country_id;
                mCountryCode = aGender.country_phonecode;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void getRegisterValue() {
        aProgressDialog = new ProgressDialog(getActivity());
        aProgressDialog.show();

        //Log.e("Id", YogaHelper.aDeviceId(getActivity()));

        Map<String, String> aHeaderMap = new HashMap<>();
        aHeaderMap.put("X-Localization", "en");

        myRetrofitInstance.getAPI().RegisterAPI(aHeaderMap).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response) {
                aProgressDialog.dismiss();
                if (response != null) {
                    Response data = response.body();
                    if (data != null) {
                        if (data.getSuccess() == 1) {
                            mGenderArrayList = (ArrayList<Gender>) data.getData().getmGenderList();
                            mHealthArrayList = (ArrayList<HealthDisorder>) data.getData().getmDisorderList();
                            mProfessionArrayList = (ArrayList<Profession>) data.getData().getmProfessionList();
                            mCountryArrayList = (ArrayList<Country>) data.getData().getmCountryList();

                            setSpinnerValues();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                aProgressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void putRegisterValue() {
        aProgressDialog = new ProgressDialog(getActivity());
        aProgressDialog.show();

        //Log.e("Id", YogaHelper.aDeviceId(getActivity()));

        Map<String, String> aHeaderMap = new HashMap<>();
        aHeaderMap.put("name", mNameEdit.getText().toString().trim());
        aHeaderMap.put("email", mEmailEdit.getText().toString().trim());
        aHeaderMap.put("phone", mMobileEdit.getText().toString().trim());
        aHeaderMap.put("password", mPasswordEdit.getText().toString().trim());
        aHeaderMap.put("country", mCountryId);

        aHeaderMap.put("country_code", mCountryCode);
        aHeaderMap.put("age", mAgeEdit.getText().toString().trim());
        aHeaderMap.put("health_disorder[" + 0 + "]", mHealth);

        aHeaderMap.put("gender", mGender);
        aHeaderMap.put("profession", mProfession);
        aHeaderMap.put("lang_id", "1");
        aHeaderMap.put("fcm_id", "1cnvxmvnmx2345678n");

        myRetrofitInstance.getAPI().PutRegisterAPI(aHeaderMap).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response) {
                aProgressDialog.dismiss();
                if (response != null) {
                    Response data = response.body();
                    if (data != null) {
                        if (data.getSuccess() == 1) {
                            // data.getMessage();
                            showAlertDialog(data.getMessage());
                            String aAccountId = data.getData().account_id;
                            String aOTP = data.getData().otp;

                            Prefs.putString("Acount_id", aAccountId);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                aProgressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showAlertDialog(String aMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getActivity().getString(R.string.app_name));
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(aMessage);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }

}
