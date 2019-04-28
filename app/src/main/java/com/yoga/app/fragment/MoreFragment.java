package com.yoga.app.fragment;


import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.adapter.MorePageAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {


    View mView;
    private RecyclerView mMenuRecyclerView;
    private MorePageAdapter mMoreMenuListAdapter;
    private ArrayList<String> mMenuArrayList;

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

        mMenuRecyclerView = mView.findViewById(R.id.menu_RecyclerView);
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
    }


    MorePageAdapter.Callback mCallback = new MorePageAdapter.Callback() {
        @Override
        public void click(int aPosition, String s) {
            Toast.makeText(getActivity(), "callback", Toast.LENGTH_SHORT).show();

            switch (aPosition) {

                case 7: {
                    showFeedbackAlertDialog();
                }
            }
        }
    };

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
}
