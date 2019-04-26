package com.yoga.app.adapter;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.fragment.MoreFragment;

import java.util.ArrayList;

public class MorePageAdapter extends RecyclerView.Adapter<MorePageAdapter.ViewHolder> {

    FragmentActivity mContext;
    ArrayList<String> mMenuArrayList;
    private ArrayList<Integer> myIcons;
    ArrayList<String> mCheckArrayList;

    public MorePageAdapter(FragmentActivity aContext, ArrayList<String> aMenuArrayList, MoreFragment moreFragment1) {
        mContext = aContext;
        mMenuArrayList = aMenuArrayList;
        mCheckArrayList = new ArrayList<>();

        TypedArray aIcons = mContext.getResources().obtainTypedArray(R.array.menu_icon_normal);
        myIcons = new ArrayList<>();
        for (int i = 0; i < aIcons.length(); i++) {
            myIcons.add(aIcons.getResourceId(i, -1));

            if (i == 4) {
                mCheckArrayList.add("1");
            } else if (i == (aIcons.length() - 1)) {
                mCheckArrayList.add("11");
            } else {
                mCheckArrayList.add("");
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_menu_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.mTitleText.setText(mMenuArrayList.get(i));
        viewHolder.mMenuImage.setImageResource(myIcons.get(i));
        switch (mCheckArrayList.get(i)) {
            case "1":
                viewHolder.mDivView.setVisibility(View.VISIBLE);
                viewHolder.mPrivacyView.setVisibility(View.GONE);
                break;
            case "11":
                viewHolder.mDivView.setVisibility(View.GONE);
                viewHolder.mPrivacyView.setVisibility(View.VISIBLE);
                break;
            default:
                viewHolder.mDivView.setVisibility(View.GONE);
                viewHolder.mPrivacyView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMenuArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleText;
        ImageView mMenuImage;
        RelativeLayout mPrivacyView;
        View mDivView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitleText = itemView.findViewById(R.id.inflate_nav_menu_title);
            mMenuImage = itemView.findViewById(R.id.inflate_nav_menu_image);
            mDivView = itemView.findViewById(R.id.divider);
            mPrivacyView = itemView.findViewById(R.id.condition_view);
        }
    }


    public interface Callback {
        void click(int aPostion);
    }
}
