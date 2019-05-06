package com.yoga.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.model.DashProfile;
import com.yoga.app.model.Profile;
import com.yoga.app.utils.Prefs;


public class InviteFriendFragment extends Fragment {

    Button mInviteFriendBut;
    View mView;
    TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_invite_friend, container, false);

        init(mView);
        return mView;
    }

    private void init(View mView) {

        mInviteFriendBut = mView.findViewById(R.id.fragment_invite_btn);
        mTextView = mView.findViewById(R.id.invite_referral);


        ((MainActivity) getActivity()).hideToolbar();
        ((MainActivity) getActivity()).hideBottomToolbar();

        final DashProfile aProfile = Prefs.getObject("profile", DashProfile.class);

        mTextView.setText(aProfile.account_referral_code);

        //Prefs.putObject("profile", aProfile);

        mInviteFriendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent(aProfile.account_referral_code);
            }
        });


    }

    private void shareIntent(String acode) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getActivity().getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, "This is your Referral code: " + acode + "\n Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
        startActivity(Intent.createChooser(intent, "choose one"));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).hideToolbar();
        ((MainActivity) getActivity()).hideBottomToolbar();
    }
}
