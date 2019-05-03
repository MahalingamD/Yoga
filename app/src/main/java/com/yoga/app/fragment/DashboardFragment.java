package com.yoga.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.adapter.DashboardVerticalAdapter;
import com.yoga.app.adapter.ViewpagerAdapter;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.model.Banner;
import com.yoga.app.model.Category;
import com.yoga.app.model.Pages;
import com.yoga.app.model.Response;
import com.yoga.app.service.RetrofitInstance;
import com.yoga.app.utils.Prefs;
import com.yoga.app.utils.ProgressDialog;
import com.yoga.app.utils.viewpager.AutoScrollViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.yoga.app.constant.PrefConstants.ACCESS_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    AutoScrollViewPager viewPager;
    View mView;
    TextView mQuotesMessage;
    ProgressDialog aProgressDialog;
    private RetrofitInstance myRetrofitInstance;

    List<Banner> mBannerList;
    ViewpagerAdapter mAdapter;
    RecyclerView mRecyclerView;
    ArrayList<String> aDashBoardHeading;
    DashboardVerticalAdapter mDashboardVerticalAdapter;
    ArrayList<Category> mCategoryArrayList;
    TextView mBenefitMsg;
    TextView mYogaTitle, mYogaHour, mYogaTime;
    LinearLayout mDashboardLayout;
    int myPadding = 20;
    LinearLayout mQuotesLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init(mView);
        return mView;
    }

    private void init(View mView) {

        mRecyclerView = mView.findViewById(R.id.dashboard_recycler);
        mQuotesMessage = mView.findViewById(R.id.quotes_msg);
        mQuotesLayout = mView.findViewById(R.id.quotes_layout);
        mBenefitMsg = mView.findViewById(R.id.benefit_message);


        View mDailyTaskLayout = mView.findViewById(R.id.daily_task);

        mYogaTitle = mDailyTaskLayout.findViewById(R.id.yoga_title);
        mYogaHour = mDailyTaskLayout.findViewById(R.id.yoga_hours);
        mYogaTime = mDailyTaskLayout.findViewById(R.id.yoga_time);

        mDashboardLayout = mView.findViewById(R.id.dashboard_layout);

        if (this.myRetrofitInstance == null) {
            myRetrofitInstance = new RetrofitInstance();
        }
        mBannerList = new ArrayList<>();
        aDashBoardHeading = new ArrayList<>();
        mCategoryArrayList = new ArrayList<>();
        setViewpager();
        setRecyclerView();
        ((MainActivity) getActivity()).showToolbar();
        callDashboard();
    }

    private void setRecyclerView() {
        for (int i = 1; i <= 5; i++) {
            aDashBoardHeading.add("Heading " + i);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDashboardVerticalAdapter = new DashboardVerticalAdapter(getActivity(), aDashBoardHeading, mCategoryArrayList);
        mRecyclerView.setAdapter(mDashboardVerticalAdapter);
        mDashboardVerticalAdapter.notifyDataSetChanged();
    }

    private void setViewpager() {
        viewPager = mView.findViewById(R.id.auto_scroll_viewPager);
        viewPager.startAutoScroll();
        viewPager.setInterval(3000);
        viewPager.setCycle(true);
        viewPager.setStopScrollWhenTouch(true);
        mAdapter = new ViewpagerAdapter(getActivity(), mBannerList);
        viewPager.setAdapter(mAdapter);

        viewPager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        viewPager.setPadding(YogaHelper.pxFromDp(getActivity(), myPadding), 0, YogaHelper.pxFromDp(getActivity(), myPadding), 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        viewPager.setPageMargin(myPadding);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).showToolbar();
    }


    private void callDashboard() {
        aProgressDialog = new ProgressDialog(getActivity());
        aProgressDialog.show();

        // Log.e( "Id", YogaHelper.aDeviceId( getActivity() ) );

        Map<String, String> aHeaderMap = new HashMap<>();

        aHeaderMap.put("X-Device", YogaHelper.aDeviceId(getActivity()));
        aHeaderMap.put("X-Localization", "en");
        aHeaderMap.put("Authorization", "Bearer " + Prefs.getString(ACCESS_TOKEN, ""));

        myRetrofitInstance.getAPI().DashboardAPI(aHeaderMap).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response) {
                aProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    Response data = response.body();
                    if (data != null) {
                        if (data.getSuccess() == 1) {
                            mDashboardLayout.setVisibility(View.VISIBLE);

                            mBannerList = data.getData().getmBannerList();
                            Pages mPages = data.getData().getmPages();
                            mCategoryArrayList = data.getData().getmCategoryArrayList();

                            Prefs.putObject("pages", mPages);

                            mAdapter.updateAdapter((ArrayList<Banner>) mBannerList);
                            mDashboardVerticalAdapter.updateAdapter(mCategoryArrayList);

                            if (data.getData().getDailyQuotes().dquote_desc_text.isEmpty()) {
                                mQuotesLayout.setVisibility(View.GONE);

                            } else {
                                mQuotesLayout.setVisibility(View.VISIBLE);

                                mQuotesMessage.setText(data.getData().getDailyQuotes().dquote_desc_text);
                            }
                            setBenefit(data.getData().getmBenefits().text, data.getData().getmBenefits().separator);

                        }else{
                            YogaHelper.showAlertDialog(getActivity(), data.getError());
                        }
                    } else {
                        YogaHelper.showAlertDialog(getActivity(), "Something went wrong");
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                aProgressDialog.dismiss();
                //  Toast.makeText( getActivity(), "Something went wrong", Toast.LENGTH_SHORT ).show();
                YogaHelper.showAlertDialog(getActivity(), "Something went wrong");

            }
        });
    }

    private void setBenefit(String aString, String aSeparator) {

        String[] aSplit = aString.split(aSeparator);

        StringBuilder aStringBuilder = new StringBuilder();

        for (int i = 0; i < aSplit.length; i++) {
            aStringBuilder.append(aSplit[i]).append("\n").append("\n");
        }

        mBenefitMsg.setText(aStringBuilder.toString());

    }
}
