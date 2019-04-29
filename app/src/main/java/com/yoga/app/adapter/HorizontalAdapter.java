package com.yoga.app.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoga.app.R;
import com.yoga.app.model.Banner;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Banner> mBannerList;
    private FragmentActivity mContext;
    int mPosition;

    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;


    HorizontalAdapter(FragmentActivity aContext, ArrayList<Banner> aBannerList, int aPosition) {
        mContext = aContext;
        mBannerList = aBannerList;
        mPosition = aPosition;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_horizontal_layout, null);
        return new ViewHolder(itemLayoutView);
*/
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if (viewType == LAYOUT_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one, parent, false);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = (int)(width * 0.8);
            view.setLayoutParams(params);

            viewHolder = new ViewHolderOne(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_horizontal_layout, parent, false);

            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = (int)(width * 0.8);
            view.setLayoutParams(params);

            viewHolder = new ViewHolder(view);
        }


        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Banner aBanner = mBannerList.get(position);
        if (viewHolder.getItemViewType() == LAYOUT_ONE) {
        } else {
            ViewHolder vaultItemHolder = (ViewHolder) viewHolder;
            Picasso.with(mContext).load(aBanner.banner_file).
                    placeholder(R.drawable.yoga_benefit).into(vaultItemHolder.mImageView);

        }
    }

   /* @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        *//*if (mPosition == 0) {
            holder.mDailyYogaLayout.setVisibility(View.VISIBLE);
            holder.mCategoryLayout.setVisibility(View.GONE);
        } else {
            holder.mCategoryLayout.setVisibility(View.VISIBLE);
            holder.mDailyYogaLayout.setVisibility(View.GONE);
            Picasso.with(mContext).load(aBanner.banner_file).
                    placeholder(R.drawable.yoga_benefit).into(holder.mImageView);
        }*//*


    }
*/
    @Override
    public int getItemCount() {
        return mBannerList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mPosition == 0)
            return LAYOUT_ONE;
        else
            return LAYOUT_TWO;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        LinearLayout mCategoryLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.horizontal_image);
            mCategoryLayout = itemView.findViewById(R.id.category_layout);
        }
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        LinearLayout mDailyYogaLayout;

        public ViewHolderOne(View itemView) {
            super(itemView);
            mDailyYogaLayout = itemView.findViewById(R.id.daily_yoga_layout);
        }
    }

}
