package com.yoga.app.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.adapter.CountryAdapter;
import com.yoga.app.adapter.GenderAdapter;
import com.yoga.app.adapter.HealthAdapter;
import com.yoga.app.adapter.ProfessionAdapter;
import com.yoga.app.base.APPFragmentManager;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.imagepicker.ImagePickerActivity;
import com.yoga.app.model.Banner;
import com.yoga.app.model.Country;
import com.yoga.app.model.Gender;
import com.yoga.app.model.HealthDisorder;
import com.yoga.app.model.Pages;
import com.yoga.app.model.Profession;
import com.yoga.app.model.Profile;
import com.yoga.app.model.Response;
import com.yoga.app.service.RetrofitInstance;
import com.yoga.app.utils.Prefs;
import com.yoga.app.utils.ProgressDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.yoga.app.constant.PrefConstants.ACCESS_TOKEN;
import static com.yoga.app.helper.YogaHelper.showAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final int REQUEST_IMAGE = 100;

    View mView;
    ProgressDialog aProgressDialog;
    private RetrofitInstance myRetrofitInstance;

    TextInputEditText mUserNameEdit, mEmailEdit, mPhoneEdit, mAgeEdit;

    Spinner mCountrySpin, mGenderSpin, mHealthSpin, mProfessionSpin;

    ArrayList<Gender> mGenderArrayList;
    ArrayList<HealthDisorder> mHealthArrayList;
    ArrayList<Profession> mProfessionArrayList;
    ArrayList<Country> mCountryArrayList;

    String mCountryCode, mCountryId, mGender, mHealth, mProfession;

    ImageView mEditImage;
    Button mUpdateBut;

    Profile mData;
    GenderAdapter mGenderAdapter;
    HealthAdapter mHealthAdapter;
    ProfessionAdapter mProfessionAdapter;
    CountryAdapter mCountryAdapter;
    CircleImageView mCircleImageView;
    private APPFragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_profile, container, false);

        init(mView);
        return mView;
    }

    private void init(View mView) {

        mFragmentManager = new APPFragmentManager(getActivity());

        mCountrySpin = mView.findViewById(R.id.profile_country_spin);
        mGenderSpin = mView.findViewById(R.id.profile_gender_spin);
        mHealthSpin = mView.findViewById(R.id.profile_health_spinner);
        mProfessionSpin = mView.findViewById(R.id.profile_profession_spinner);

        mUserNameEdit = mView.findViewById(R.id.profile_name_edit_text);
        mEmailEdit = mView.findViewById(R.id.profile_email_edit_text);
        mPhoneEdit = mView.findViewById(R.id.profile_phone_edit_text);
        mAgeEdit = mView.findViewById(R.id.profile_age_edit_text);

        mEditImage = mView.findViewById(R.id.profile_edit);
        mUpdateBut = mView.findViewById(R.id.fragment_update_btn);
        mCircleImageView = mView.findViewById(R.id.profile_image);

        setEditable(false);


        mGenderArrayList = new ArrayList<>();
        mHealthArrayList = new ArrayList<>();
        mProfessionArrayList = new ArrayList<>();
        mCountryArrayList = new ArrayList<>();


        if (this.myRetrofitInstance == null) {
            myRetrofitInstance = new RetrofitInstance();
        }
        clickListener();

        ((MainActivity) getActivity()).hideToolbar();
        ((MainActivity) getActivity()).hideBottomToolbar();
        callProfile();
    }

    private void clickListener() {
        mEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditable(true);
                mUpdateBut.setVisibility(View.VISIBLE);
            }
        });

        mUpdateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putRegisterValue();
            }
        });

        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageCaptureOption();
            }
        });
    }

    private void ImageCaptureOption() {
/*
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
*/
        showImagePickerOptions();
    }


    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getActivity(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        getActivity().startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        getActivity().startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                    Log.e("file_path", getRealPathFromURI(uri));
                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void loadProfile(String url) {
        Log.d("TAG", "Image cache path: " + url);
        Picasso.with(getActivity()).load(url)
                .into(mCircleImageView);
        mCircleImageView.setColorFilter(ContextCompat.getColor(getActivity(), android.R.color.transparent));
    }

    private void loadProfileDefault() {
        Picasso.with(getActivity()).load(R.drawable.ic_user)
                .into(mCircleImageView);
        mCircleImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.profile_default_tint));
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private void setEditable(Boolean aValue) {
        mUserNameEdit.setEnabled(aValue);
        mEmailEdit.setEnabled(false);
        mPhoneEdit.setEnabled(false);
        mAgeEdit.setEnabled(aValue);

        mCountrySpin.setEnabled(false);
        mCountrySpin.setClickable(false);
        mGenderSpin.setEnabled(aValue);
        mGenderSpin.setClickable(aValue);
        mHealthSpin.setEnabled(aValue);
        mHealthSpin.setClickable(aValue);
        mProfessionSpin.setEnabled(aValue);
        mProfessionSpin.setClickable(aValue);

    }


    private void setValues(Profile data) {
        mUserNameEdit.setText(data.data.account_name);
        mEmailEdit.setText(data.data.account_email);
        mPhoneEdit.setText(data.data.account_mobile_no);
        mAgeEdit.setText(data.data.account_age);
    }

    private void setSpinnerValues() {

        mGenderAdapter = new GenderAdapter(getActivity(), android.R.layout.simple_spinner_item, mGenderArrayList);
        mGenderSpin.setAdapter(mGenderAdapter);

        for (int i = 0; i < mGenderArrayList.size(); i++) {
            Gender aGender = mGenderArrayList.get(i);
            if (aGender.gender_id.equals(mData.data.account_gender)) {
                mGenderSpin.setSelection(i);
            }
        }

        mHealthAdapter = new HealthAdapter(getActivity(), android.R.layout.simple_spinner_item, mHealthArrayList);
        mHealthSpin.setAdapter(mHealthAdapter);

        for (int i = 0; i < mHealthArrayList.size(); i++) {
            HealthDisorder aHealth = mHealthArrayList.get(i);
            if (aHealth.healthdo_id.equals(mData.data.account_health_disorder)) {
                mHealthSpin.setSelection(i);
            }
        }

        mProfessionAdapter = new ProfessionAdapter(getActivity(), android.R.layout.simple_spinner_item, mProfessionArrayList);
        mProfessionSpin.setAdapter(mProfessionAdapter);

        for (int i = 0; i < mProfessionArrayList.size(); i++) {
            Profession aProfession = mProfessionArrayList.get(i);
            if (aProfession.profession_id.equals(mData.data.account_profession)) {
                mProfessionSpin.setSelection(i);
            }
        }

        mCountryAdapter = new CountryAdapter(getActivity(), android.R.layout.simple_spinner_item, mCountryArrayList);
        mCountrySpin.setAdapter(mCountryAdapter);

        for (int i = 0; i < mCountryArrayList.size(); i++) {
            Country aCountry = mCountryArrayList.get(i);
            if (aCountry.country_id.equals(mData.data.account_country)) {
                mCountrySpin.setSelection(i);
            }
        }

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

    }


    private void callProfile() {
        aProgressDialog = new ProgressDialog(getActivity());
        aProgressDialog.show();

        Map<String, String> aHeaderMap = new HashMap<>();
        aHeaderMap.put("X-Device", YogaHelper.aDeviceId(getActivity()));
        aHeaderMap.put("Authorization", "Bearer " + Prefs.getString(ACCESS_TOKEN, ""));

        myRetrofitInstance.getAPI().getProfileAPI(aHeaderMap).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NotNull Call<Profile> call,
                                   @NotNull retrofit2.Response<Profile> response) {
                aProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    mData = response.body();
                    if (mData != null) {
                        if (mData.success == 1) {

                            setValues(mData);
                            getRegisterValue();
                        } else {
                            showAlertDialog(getActivity(), mData.error);
                        }
                    } else {
                        showAlertDialog(getActivity(), "Something went wrong");
                    }
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                aProgressDialog.dismiss();
                showAlertDialog(getActivity(), "Something went wrong");

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
                showAlertDialog(getActivity(), "Something went wrong");

            }
        });
    }


    private void putRegisterValue() {
        aProgressDialog = new ProgressDialog(getActivity());
        aProgressDialog.show();

        Map<String, String> aHeaderMap = new HashMap<>();

        aHeaderMap.put("X-Device", YogaHelper.aDeviceId(getActivity()));
        // aHeaderMap.put("X-Localization", "en");
        aHeaderMap.put("Authorization", "Bearer " + Prefs.getString(ACCESS_TOKEN, ""));


        // File ImageFile = new File("");
        //RequestBody propertyImage = RequestBody.create(MediaType.parse("image/*"), ImageFile);
        //MultipartBody.Part ImagePart = MultipartBody.Part.createFormData("photo", ImageFile.getName(), propertyImage);
        MultipartBody.Part ImagePart = null;

        HashMap<String, RequestBody> aImageMap = new HashMap<>();
        aImageMap.put("name", createRequestBody(mUserNameEdit.getText().toString().trim()));
        aImageMap.put("gender", createRequestBody(mGender));
        aImageMap.put("age", createRequestBody(mAgeEdit.getText().toString().trim()));
        aImageMap.put("lang_id", createRequestBody("1"));
        aImageMap.put("health_disorder[" + 0 + "]", createRequestBody(mHealth));
        aImageMap.put("profession", createRequestBody(mProfession));


        myRetrofitInstance.getAPI().upload(aHeaderMap, ImagePart, aImageMap).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response) {
                aProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    Response data = response.body();
                    if (data != null) {
                        if (data.getSuccess() == 1) {
                            //  String aAccountId = data.getData().account_id;
                            //  String aOTP = data.getData().otp;

                            showOkDialog(data.getMessage());
                        } else {
                            showAlertDialog(getActivity(), data.getError());
                        }
                    }
                } else {
                    showAlertDialog(getActivity(), "Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                aProgressDialog.dismiss();
                showAlertDialog(getActivity(), "Something went wrong");

            }
        });
    }

    private void showOkDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getActivity().getString(R.string.app_name));
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mFragmentManager.onBackPress();
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private RequestBody createRequestBody(String aValue) {
        RequestBody aRequest;
        aRequest = RequestBody.create(MediaType.parse("text/plain"), aValue);
        return aRequest;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).hideToolbar();
        ((MainActivity) getActivity()).hideBottomToolbar();
    }
}
