package com.yoga.app.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yoga.app.R;
import com.yoga.app.model.Banner;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    ArrayList<Banner> mBannerList;
    FragmentActivity mContext;

    public HorizontalAdapter(FragmentActivity aContext, ArrayList<Banner> aBannerList) {
        mContext = aContext;
        mBannerList = aBannerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_horizontal_layout, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Banner aBanner = mBannerList.get(position);

        Picasso.with(mContext).load(aBanner.banner_file).
                placeholder(R.drawable.yoga_benefit).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mBannerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.horizontal_image);
        }
    }
}
