package com.yoga.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.adapter.WalletHistoryAdapter;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.model.Wallet;
import com.yoga.app.model.WalletHistoryItem;
import com.yoga.app.service.RetrofitInstance;
import com.yoga.app.utils.Prefs;
import com.yoga.app.utils.ProgressDialog;

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
public class WalletFragment extends Fragment implements View.OnClickListener, WalletHistoryAdapter.Callback {

    FragmentActivity myContext;
    TextView mTitleTXT, wallet_amount;
    ImageView mBackArrowIMG;
    RecyclerView mRecycle;
    WalletHistoryAdapter mAdapter;
    ArrayList<WalletHistoryItem> mData = new ArrayList();
    ProgressDialog aProgressDialog;
    Wallet wallet;
    private RetrofitInstance myRetrofitInstance;


    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        init(view);
        listeners();
        setRecyclerView();
        getWalletDetails();

        return view;
    }

    private void init(View view) {
        myContext = getActivity();
        mTitleTXT = view.findViewById(R.id.title_txt);
        mBackArrowIMG = view.findViewById(R.id.back_arrow);
        mRecycle = view.findViewById(R.id.main_course_video_list);
        wallet_amount = view.findViewById(R.id.wallet_amount);

        if (this.myRetrofitInstance == null) {
            myRetrofitInstance = new RetrofitInstance();
        }
        ((MainActivity) getActivity()).hideToolbar();

    }

    private void listeners() {
        mBackArrowIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).hideToolbar();
    }

    private void setRecyclerView() {
        mRecycle.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new WalletHistoryAdapter(getActivity(), mData, this);
        mRecycle.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void getWalletDetails() {
        aProgressDialog = new ProgressDialog(getActivity());
        aProgressDialog.show();

        Log.e("Id", YogaHelper.aDeviceId(getActivity()));

        Map<String, String> aHeaderMap = new HashMap<>();

        aHeaderMap.put("X-Device", YogaHelper.aDeviceId(getActivity()));
        //aHeaderMap.put("X-Localization", "en");
        aHeaderMap.put("Authorization", "Bearer " + Prefs.getString(ACCESS_TOKEN, ""));

        myRetrofitInstance.getAPI().getWalletDetails(aHeaderMap).enqueue(new Callback<Wallet>() {
            @Override
            public void onResponse(@NotNull Call<Wallet> call, @NotNull retrofit2.Response<Wallet> response) {
                aProgressDialog.dismiss();
                if (response != null) {
                    Wallet data = response.body();
                    if (data != null) {
                        if (data.getSuccess() == 1) {
                            wallet = data;
                            mAdapter.update(data.getData().getWalletHistory());
                            updateData();
                        }
                    } else {
                        YogaHelper.showAlertDialog(getActivity(), "Something went wrong");
                    }
                }
            }

            @Override
            public void onFailure(Call<Wallet> call, Throwable t) {
                aProgressDialog.dismiss();
                //  Toast.makeText( getActivity(), "Something went wrong", Toast.LENGTH_SHORT ).show();
                YogaHelper.showAlertDialog(getActivity(), "Something went wrong");

            }
        });
    }

    private void updateData() {
        wallet_amount.setText(""+wallet.getData().getWalletCoins());
    }

    @Override
    public void click(int aPostion, String s) {

    }

}
