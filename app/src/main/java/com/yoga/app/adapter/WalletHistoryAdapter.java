package com.yoga.app.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.model.WalletHistoryItem;

import java.util.ArrayList;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {

    FragmentActivity mContext;
    ArrayList<WalletHistoryItem> mCourseVideosArrayList;
    WalletHistoryAdapter.Callback mCallback;

    public WalletHistoryAdapter(FragmentActivity aContext, ArrayList<WalletHistoryItem> courseVideosArrayList,
                                WalletHistoryAdapter.Callback callback) {
        mContext = aContext;
        mCourseVideosArrayList = courseVideosArrayList;
        mCallback = callback;
    }

    @NonNull
    @Override
    public WalletHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inflate_wallet_list_item, parent, false);
        return new WalletHistoryAdapter.ViewHolder(itemView);
    }

    public void update(ArrayList<WalletHistoryItem> courseVideosArrayList) {
        mCourseVideosArrayList = courseVideosArrayList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHistoryAdapter.ViewHolder viewHolder, final int i) {

        final WalletHistoryItem walletHistoryItem = mCourseVideosArrayList.get(i);

        viewHolder.titleTXT.setText(walletHistoryItem.getWhistoryTypeName());
        viewHolder.amountTXT.setText(""+walletHistoryItem.getWhistoryCoins());

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourseVideosArrayList.size();
    }

    public interface Callback {
        void click(int aPostion, String s);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView parent;
        TextView titleTXT, amountTXT;
        ImageView paymentModeIMG;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent_lay);
            titleTXT = itemView.findViewById(R.id.wallet_list_item_title_txt);
            amountTXT = itemView.findViewById(R.id.wallet_list_item_amount_txt);
            paymentModeIMG = itemView.findViewById(R.id.pay_up_down_img);
        }
    }
}
