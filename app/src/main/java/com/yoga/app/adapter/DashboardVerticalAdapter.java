package com.yoga.app.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.fragment.DashboardFragment;
import com.yoga.app.model.Banner;

import java.util.ArrayList;
import java.util.List;

public class DashboardVerticalAdapter extends RecyclerView.Adapter<DashboardVerticalAdapter.ViewHolder> {

    FragmentActivity mContext;
    ArrayList<String> mDashBoardHeading;
    ArrayList<Banner> mBannerList;

    public DashboardVerticalAdapter(FragmentActivity aContext, ArrayList<String> aDashBoardHeading,
                                    List<Banner> aBannerList) {
        mContext = aContext;
        mDashBoardHeading = aDashBoardHeading;
        mBannerList = (ArrayList<Banner>) aBannerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_vertical_layout, null);
        return new ViewHolder(itemLayoutView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mHeadingTextView.setText(mDashBoardHeading.get(position));

        HorizontalAdapter aHorizontalAdapter = new HorizontalAdapter(mContext, mBannerList);
        holder.mHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.mHorizontalRecyclerView.setAdapter(aHorizontalAdapter);
        holder.mHorizontalRecyclerView.setNestedScrollingEnabled(false);
        aHorizontalAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDashBoardHeading.size();
    }

    public void updateAdapter(List<Banner> aBannerList) {
        mBannerList = (ArrayList<Banner>) aBannerList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mHeadingTextView;
        RecyclerView mHorizontalRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mHeadingTextView = itemView.findViewById(R.id.HeadingTitle);
            mHorizontalRecyclerView = itemView.findViewById(R.id.horizontal_recyclerView);
        }
    }
}
