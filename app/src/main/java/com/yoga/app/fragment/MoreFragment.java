package com.yoga.app.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.activities.WelcomeActivity;
import com.yoga.app.adapter.MorePageAdapter;
import com.yoga.app.base.APPFragmentManager;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.model.Response;
import com.yoga.app.service.RetrofitInstance;
import com.yoga.app.utils.Prefs;
import com.yoga.app.utils.ProgressDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.yoga.app.constant.PrefConstants.ACCESS_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    View mView;
    private RecyclerView mMenuRecyclerView;
    private MorePageAdapter mMoreMenuListAdapter;
    private ArrayList<String> mMenuArrayList;
    APPFragmentManager mFragmentManager;

    private RetrofitInstance myRetrofitInstance;
    ProgressDialog aProgressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_more, container, false);

        init(mView);

        return mView;
    }

    private void init(View mView) {

        ((MainActivity) getActivity()).hideToolbar();
        ((MainActivity) getActivity()).showBottomToolbar();

        if (this.myRetrofitInstance == null) {
            myRetrofitInstance = new RetrofitInstance();
        }

        mMenuRecyclerView = mView.findViewById(R.id.menu_RecyclerView);

        mFragmentManager = new APPFragmentManager(getActivity());
        setRecyclerValue();
    }


    private void setRecyclerValue() {
        try {
            mMenuArrayList = new ArrayList<>();

            String[] aMenuTitles = getActivity().getResources().getStringArray(R.array.menu_items);
            mMenuArrayList.addAll(Arrays.asList(aMenuTitles));

            mMoreMenuListAdapter = new MorePageAdapter(getActivity(), mMenuArrayList, mCallback);
            mMenuRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mMenuRecyclerView.setLayoutManager(mLayoutManager);
            mMenuRecyclerView.setAdapter(mMoreMenuListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).hideToolbar();
        ((MainActivity) getActivity()).showBottomToolbar();
    }


    MorePageAdapter.Callback mCallback = new MorePageAdapter.Callback() {
        @Override
        public void click(int aPosition, String s) {
            Bundle aBundle = new Bundle();

            switch (aPosition) {
                case 0: {
                    mFragmentManager.updateContent(new ProfileFragment(), "ProfileFragment", aBundle);
                    break;
                }
                case 1: {
                    mFragmentManager.updateContent(new WalletFragment(), "WalletFragment", aBundle);
                    break;
                }
                case 3: {
                    mFragmentManager.updateContent(new InviteFriendFragment(), "InviteFriendFragment", aBundle);
                    break;
                }
                case 5: {
                    aBundle.putString("page_value", "about");
                    mFragmentManager.updateContent(new CommonFragment(), "CommonFragment", aBundle);
                    break;
                }
                case 6: {
                    showFeedbackAlertDialog();
                    break;
                }
                case 7: {
                    aBundle.putString("page_value", "faq");
                    mFragmentManager.updateContent(new CommonFragment(), "CommonFragment", aBundle);
                    break;
                }
                case 8: {
                    logoutAlertDialog();
                    break;
                }
                case 20: {
                    aBundle.putString("page_value", "terms");
                    mFragmentManager.updateContent(new CommonFragment(), "CommonFragment", aBundle);
                    break;
                }
                case 21: {
                    aBundle.putString("page_value", "policy");
                    mFragmentManager.updateContent(new CommonFragment(), "CommonFragment", aBundle);
                    break;
                }
            }
        }
    };

    private void logoutAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getActivity().getString(R.string.app_name));
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Prefs.clear().apply();
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        // alertDialog.setView(view);
        alertDialog.show();

    }

    private void showFeedbackAlertDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View view = inflater.inflate(R.layout.feedback_layout, null);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getActivity().getString(R.string.app_name));
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Enter your feedback about this application");

        final EditText etComments = view.findViewById(R.id.etComments);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = convertPixelsToDp(16, getActivity());
        params.rightMargin = convertPixelsToDp(16, getActivity());
        etComments.setLayoutParams(params);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (etComments.getText().toString().trim().length() > 0)
                    callFeedback(etComments.getText().toString());
                else {
                    dialog.dismiss();
                }
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        alertDialog.setView(view);
        alertDialog.show();

        // Change the buttons color in dialog
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
    }

    public int convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));
        return dp;
    }

    private void callFeedback(String params) {
        aProgressDialog = new ProgressDialog(getActivity());
        aProgressDialog.show();

        Map<String, String> aHeaderMap = new HashMap<>();
        aHeaderMap.put("X-Device", YogaHelper.aDeviceId(getActivity()));
        aHeaderMap.put("X-Localization", "en");
        aHeaderMap.put("Authorization", "Bearer " + Prefs.getString(ACCESS_TOKEN, ""));


        myRetrofitInstance.getAPI().putFeedbackDetails(aHeaderMap, params).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response) {
                aProgressDialog.dismiss();
                Response data = response.body();
                if (data != null) {
                    if (data.getSuccess() == 1) {
                        YogaHelper.showAlertDialog(getActivity(), data.getMessage());
                    } else {
                        YogaHelper.showAlertDialog(getActivity(), data.getError());
                    }
                } else {
                    YogaHelper.showAlertDialog(getActivity(), "Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                aProgressDialog.dismiss();
                YogaHelper.showAlertDialog(getActivity(), "Something went wrong");
            }
        });
    }
}
